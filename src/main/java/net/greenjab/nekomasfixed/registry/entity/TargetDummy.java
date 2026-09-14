package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.authlib.properties.PropertyMap;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.Rotations;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

import java.util.*;

/**
 * Combat training dummy: a pumpkin-on-hay that records the last damage dealt to it
 * (fed back as a redstone signal), ignores explosion knockback and potions, and is
 * sheared/headed like an armour stand. Ported from 26.x Avatar onto 1.21.1 LivingEntity.
 */
public class TargetDummy extends LivingEntity implements Shearable {
    public static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
    public static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
    public static final Rotations DEFAULT_LEFT_ARM_ROTATION = new Rotations(-5.0F, 0.0F, -5.0F);
    public static final Rotations DEFAULT_RIGHT_ARM_ROTATION = new Rotations(-5.0F, 0.0F, 5.0F);
    public static final Rotations DEFAULT_LEFT_LEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
    public static final Rotations DEFAULT_RIGHT_LEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
    // The 26.x `EntityDataSerializers.RESOLVABLE_PROFILE` built-in doesn't exist in 1.21.1, but the registry is
    // extensible: register a value-type serializer over the profile's built-in stream codec, then sync an accessor.
    public static final EntityDataSerializer<ResolvableProfile> RESOLVABLE_PROFILE =
            EntityDataSerializer.forValueType(ResolvableProfile.STREAM_CODEC);
    public static final ResolvableProfile DEFAULT_INFO =
            new ResolvableProfile(Optional.empty(), Optional.empty(), new PropertyMap());
    protected static final EntityDataAccessor<ResolvableProfile> PROFILE =
            SynchedEntityData.defineId(TargetDummy.class, RESOLVABLE_PROFILE);
    static final EntityDataAccessor<Boolean> ZOMBIE = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.BOOLEAN);
    static final EntityDataAccessor<Rotations> TRACKER_HEAD_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
    static final EntityDataAccessor<Rotations> TRACKER_BODY_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
    static final EntityDataAccessor<Rotations> TRACKER_LEFT_ARM_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
    static final EntityDataAccessor<Rotations> TRACKER_RIGHT_ARM_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
    static final EntityDataAccessor<Rotations> TRACKER_LEFT_LEG_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
    static final EntityDataAccessor<Rotations> TRACKER_RIGHT_LEG_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
    // Last hit's damage, synced so the client renderer can draw the floating number over the
    // dummy (the wobble entity-event already anchors the hit time client-side).
    static final EntityDataAccessor<Float> HIT_DAMAGE =
            SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.FLOAT);
    // Gaussian offset applied to the number's spawn per hit, synced so every viewer sees the
    // same scatter (main jitters each NumberParticle at construction; a synced value keeps
    // that server-authoritative position consistent across clients).
    static final EntityDataAccessor<Vector3f> HIT_JITTER =
            SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.VECTOR3);

    static {
        EntityDataSerializers.registerSerializer(RESOLVABLE_PROFILE);
    }

    private final Map<EquipmentSlot, ItemStack> dummyEquipment = new EnumMap<>(EquipmentSlot.class);
    private int lastHitValue;
    private long lastHitTime;

    public TargetDummy(EntityType<? extends TargetDummy> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createTargetDummyAttributes() {
        return createLivingAttributes().add(Attributes.STEP_HEIGHT, 0.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected Entity.@NonNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ZOMBIE, false);
        builder.define(PROFILE, DEFAULT_INFO);
        builder.define(HIT_DAMAGE, 0.0F);
        builder.define(HIT_JITTER, new Vector3f());
        builder.define(TRACKER_HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        builder.define(TRACKER_BODY_ROTATION, DEFAULT_BODY_ROTATION);
        builder.define(TRACKER_LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
        builder.define(TRACKER_RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
        builder.define(TRACKER_LEFT_LEG_ROTATION, DEFAULT_LEFT_LEG_ROTATION);
        builder.define(TRACKER_RIGHT_LEG_ROTATION, DEFAULT_RIGHT_LEG_ROTATION);
    }

    @Override
    public boolean canUseSlot(@NonNull EquipmentSlot slot) {
        return slot != EquipmentSlot.BODY;
    }

    // LivingEntity abstract equipment implementation, backed by a local map.
    @Override
    public @NonNull ItemStack getItemBySlot(@NonNull EquipmentSlot slot) {
        return dummyEquipment.getOrDefault(slot, ItemStack.EMPTY);
    }

    @Override
    public void setItemSlot(@NonNull EquipmentSlot slot, @NonNull ItemStack stack) {
        dummyEquipment.put(slot, stack);
    }

    @Override
    public @NonNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public boolean isZombie() {
        return this.entityData.get(ZOMBIE);
    }

    private void setZombie(boolean zombie) {
        this.entityData.set(ZOMBIE, zombie);
    }

    public ResolvableProfile getTargetDummyProfile() {
        return this.entityData.get(PROFILE);
    }

    private void setTargetDummyProfile(ResolvableProfile profile) {
        this.entityData.set(PROFILE, profile);
    }

    public int getLastDamage() {
        return this.lastHitValue;
    }

    public void setLastDamage(int damage) {
        this.lastHitValue = damage;
    }

    public long getLastHitTime() {
        return this.lastHitTime;
    }

    public float getLastHitDamage() {
        return this.entityData.get(HIT_DAMAGE);
    }

    private void setLastHitDamage(float damage) {
        this.entityData.set(HIT_DAMAGE, damage);
    }

    public Vector3f getLastHitJitter() {
        return this.entityData.get(HIT_JITTER);
    }

    private void setLastHitJitter(Vector3f jitter) {
        this.entityData.set(HIT_JITTER, jitter);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(@NonNull Entity entity) {
    }

    private boolean canClip() {
        return !this.isNoGravity();
    }

    @Override
    public void travel(@NonNull Vec3 movementInput) {
        if (this.canClip()) {
            super.travel(movementInput);
        }
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return !explosion.interactsWithBlocks() || this.isInvisible();
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    @Override
    public @NonNull Iterable<ItemStack> getArmorSlots() {
        return List.of(this.getItemBySlot(EquipmentSlot.HEAD), this.getItemBySlot(EquipmentSlot.CHEST),
                this.getItemBySlot(EquipmentSlot.LEGS), this.getItemBySlot(EquipmentSlot.FEET));
    }

    @Override
    public boolean isPickable() {
        return super.isPickable();
    }

    @Override
    public boolean skipAttackInteraction(@NonNull Entity attacker) {
        return attacker instanceof Player player && !this.level().mayInteract(player, this.blockPosition());
    }

    @Override
    public @NonNull InteractionResult interact(Player player, @NonNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.SHEARS)) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.breakAndDropItem(serverLevel, this.damageSources().generic());
                this.spawnBreakParticles();
                this.kill();
                itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return InteractionResult.SUCCESS;
        } else if (itemStack.is(Items.NAME_TAG)) {
            Component name = itemStack.get(DataComponents.CUSTOM_NAME);
            if (name != null) {
                String s = name.getString();
                if (!s.isEmpty()) {
                    this.setTargetDummyProfile(new ResolvableProfile(Optional.of(s), Optional.empty(), new PropertyMap()));
                }
                this.setZombie(false);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        } else if (itemStack.is(Items.PLAYER_HEAD)) {
            ResolvableProfile profile = itemStack.get(DataComponents.PROFILE);
            if (profile != null) {
                this.setTargetDummyProfile(profile);
                this.setZombie(false);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        } else if (itemStack.is(Items.ZOMBIE_HEAD) || itemStack.is(Items.SKELETON_SKULL)
                || itemStack.is(Items.ROTTEN_FLESH)) {
            this.setTargetDummyProfile(DEFAULT_INFO);
            this.setZombie(true);
            return InteractionResult.SUCCESS;
        } else if (itemStack.is(Items.HAY_BLOCK)) {
            this.setTargetDummyProfile(DEFAULT_INFO);
            this.setZombie(false);
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.SUCCESS;
        } else if (player.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (player.isShiftKeyDown()) {
            this.swapArmorSet(player, hand);
            return InteractionResult.CONSUME;
        } else {
            if (itemStack.isEmpty()) {
                if (this.equip(player, this.firstOccupiedSlot(), itemStack, hand)) {
                    return InteractionResult.sidedSuccess(this.level().isClientSide());
                }
            } else if (this.equip(player, this.getEquipmentSlotForItem(itemStack), itemStack, hand)) {
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
            return InteractionResult.PASS;
        }
    }

    // Shift-click swaps the dummy's worn armor with the player's, slot by slot — the same
    // swap main's ArmorStandMixin gives armor stands. A slot only swaps when either side
    // holds a matching armor piece, so empty slots don't block the rest of the set.
    private void swapArmorSet(Player player, InteractionHand hand) {
        this.swapArmorSlot(player, EquipmentSlot.HEAD, ItemTags.HEAD_ARMOR);
        this.swapArmorSlot(player, EquipmentSlot.CHEST, ItemTags.CHEST_ARMOR);
        this.swapArmorSlot(player, EquipmentSlot.LEGS, ItemTags.LEG_ARMOR);
        this.swapArmorSlot(player, EquipmentSlot.FEET, ItemTags.FOOT_ARMOR);
        player.swing(hand, true);
    }

    private void swapArmorSlot(Player player, EquipmentSlot slot, TagKey<Item> tag) {
        ItemStack dummyStack = this.getItemBySlot(slot);
        ItemStack playerStack = player.getItemBySlot(slot);
        if (dummyStack.is(tag) || playerStack.is(tag)) {
            this.setItemSlot(slot, playerStack);
            player.setItemSlot(slot, dummyStack);
        }
    }

    private EquipmentSlot firstOccupiedSlot() {
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}) {
            if (!this.getItemBySlot(slot).isEmpty()) {
                return slot;
            }
        }
        return EquipmentSlot.MAINHAND;
    }

    private boolean equip(Player player, EquipmentSlot slot, ItemStack stack, InteractionHand hand) {
        ItemStack current = this.getItemBySlot(slot);
        if (player.hasInfiniteMaterials() && current.isEmpty() && !stack.isEmpty()) {
            this.setItemSlot(slot, stack.copyWithCount(1));
            return true;
        } else if (stack.isEmpty() || stack.getCount() <= 1) {
            this.setItemSlot(slot, stack);
            player.setItemInHand(hand, current);
            return true;
        } else if (!current.isEmpty()) {
            return false;
        } else {
            this.setItemSlot(slot, stack.split(1));
            return true;
        }
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.writeEquipment(tag);
        this.writePose(tag);
        this.writeProfile(tag);
        tag.putInt("LastDamage", this.lastHitValue);
        tag.putBoolean("IsZombie", this.isZombie());
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readEquipment(tag);
        this.readPose(tag);
        this.readProfile(tag);
        this.setLastDamage(tag.getInt("LastDamage"));
        this.setZombie(tag.getBoolean("IsZombie"));
    }

    // Dummy equipment lives in a local map (LivingEntity declares the equipment
    // methods abstract in 1.21.1; armor/hand storage is on Mob), so it must be
    // round-tripped here rather than relying on super.
    private void writeEquipment(CompoundTag tag) {
        CompoundTag equipment = new CompoundTag();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot == EquipmentSlot.BODY) {
                continue;
            }
            ItemStack stack = this.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                equipment.put(slot.getName(), stack.saveOptional(this.registryAccess()));
            }
        }
        tag.put("Equipment", equipment);
    }

    private void readEquipment(CompoundTag tag) {
        if (tag.contains("Equipment", Tag.TAG_COMPOUND)) {
            CompoundTag equipment = tag.getCompound("Equipment");
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot == EquipmentSlot.BODY) {
                    continue;
                }
                if (equipment.contains(slot.getName())) {
                    this.setItemSlot(slot, ItemStack.parseOptional(this.registryAccess(), equipment.getCompound(slot.getName())));
                }
            }
        }
    }

    private void writeProfile(CompoundTag tag) {
        ResolvableProfile profile = this.getTargetDummyProfile();
        profile.name().ifPresent(n -> tag.putString("PName", n));
        profile.id().ifPresent(u -> tag.putString("PId", u.toString()));
    }

    private void readProfile(CompoundTag tag) {
        String name = tag.getString("PName");
        Optional<String> profileName = name.isEmpty() ? Optional.empty() : Optional.of(name);
        Optional<UUID> profileId = tag.contains("PId") ? Optional.of(UUID.fromString(tag.getString("PId"))) : Optional.empty();
        this.setTargetDummyProfile(new ResolvableProfile(profileName, profileId, new PropertyMap()));
    }

    private void writePose(CompoundTag tag) {
        CompoundTag pose = new CompoundTag();
        this.writeRotation(pose, "Head", this.getHeadRotation());
        this.writeRotation(pose, "Body", this.getBodyRotation());
        this.writeRotation(pose, "LeftArm", this.getLeftArmRotation());
        this.writeRotation(pose, "RightArm", this.getRightArmRotation());
        this.writeRotation(pose, "LeftLeg", this.getLeftLegRotation());
        this.writeRotation(pose, "RightLeg", this.getRightLegRotation());
        tag.put("Pose", pose);
    }

    private void writeRotation(CompoundTag tag, String key, Rotations rotation) {
        tag.put(key, rotation.save());
    }

    private void readPose(CompoundTag tag) {
        if (tag.contains("Pose", Tag.TAG_COMPOUND)) {
            CompoundTag pose = tag.getCompound("Pose");
            this.setHeadRotation(this.readRotation(pose, "Head", DEFAULT_HEAD_ROTATION));
            this.setBodyRotation(this.readRotation(pose, "Body", DEFAULT_BODY_ROTATION));
            this.setLeftArmRotation(this.readRotation(pose, "LeftArm", DEFAULT_LEFT_ARM_ROTATION));
            this.setRightArmRotation(this.readRotation(pose, "RightArm", DEFAULT_RIGHT_ARM_ROTATION));
            this.setLeftLegRotation(this.readRotation(pose, "LeftLeg", DEFAULT_LEFT_LEG_ROTATION));
            this.setRightLegRotation(this.readRotation(pose, "RightLeg", DEFAULT_RIGHT_LEG_ROTATION));
        }
    }

    private Rotations readRotation(CompoundTag tag, String key, Rotations fallback) {
        if (tag.contains(key, Tag.TAG_LIST)) {
            return new Rotations(tag.getList(key, Tag.TAG_FLOAT));
        }
        return fallback;
    }

    // main scatters each NumberParticle spawn with Gaussian noise (nextGaussian()/5 on x,z,
    // /10 on y); compute it once per hit here so the synced jitter is identical on every viewer.
    private void rollHitJitter(Level level) {
        RandomSource rng = level.getRandom();
        this.setLastHitJitter(new Vector3f(
                (float) (rng.nextGaussian() / 5.0),
                (float) (rng.nextGaussian() / 10.0),
                (float) (rng.nextGaussian() / 5.0)));
    }

    @Override
    public boolean hurt(@NonNull DamageSource source, float amount) {
        if (this.isRemoved()) {
            return false;
        }
        if (!(this.level() instanceof ServerLevel level)) {
            return false;
        }
        if (!level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && source.getEntity() instanceof Mob) {
            return false;
        }
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            this.onBreak(level, source);
            this.kill();
            return false;
        }
        if (!(source.getEntity() instanceof Player)) {
            return false;
        }

        amount = this.getDamageAfterArmorAbsorb(source, amount);
        amount = this.getDamageAfterMagicAbsorb(source, amount);
        ItemStack weapon = source.getWeaponItem();
        if (weapon != null && weapon.is(Items.SHEARS)) {
            if (source.isCreativePlayer()) {
                this.playBreakSound();
            } else {
                this.breakAndDropItem(level, source);
                if (source.getEntity() instanceof Player player) {
                    weapon.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                }
            }
            this.spawnBreakParticles();
            this.kill();
            return true;
        } else if (source.getEntity() instanceof Player playerEntity && !playerEntity.getAbilities().mayBuild) {
            return false;
        } else if (source.isCreativePlayer()) {
            long time = level.getGameTime();
            if (time - this.lastHitTime > 5L) {
                level.broadcastEntityEvent(this, EntityEvent.ARMORSTAND_WOBBLE);
                this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
                this.lastHitTime = time;
                this.lastHitValue = (int) amount;
                this.rollHitJitter(level);
                this.setLastHitDamage(amount);
            } else {
                this.playBreakSound();
                this.spawnBreakParticles();
                this.kill();
            }
            return true;
        } else {
            level.broadcastEntityEvent(this, EntityEvent.ARMORSTAND_WOBBLE);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
            this.lastHitTime = level.getGameTime();
            this.lastHitValue = (int) amount;
            this.rollHitJitter(level);
            this.setLastHitDamage(amount);
            return true;
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.ARMORSTAND_WOBBLE) {
            if (this.level().isClientSide()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                        SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHitTime = this.level().getGameTime();
            }
        } else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d) || d == 0.0) {
            d = 4.0;
        }
        d *= 64.0;
        return distance < d * d;
    }

    private void spawnBreakParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()),
                    this.getX(), this.getY(0.6666666666666666), this.getZ(),
                    10, this.getBbWidth() / 4.0F, this.getBbHeight() / 4.0F, this.getBbWidth() / 4.0F, 0.05);
        }
    }

    private void breakAndDropItem(ServerLevel level, DamageSource damageSource) {
        ItemStack itemStack = new ItemStack(ItemRegistry.TARGET_DUMMY);
        Component customName = this.getCustomName();
        if (customName != null) {
            itemStack.set(DataComponents.CUSTOM_NAME, customName);
        }
        Block.popResource(this.level(), this.blockPosition(), itemStack);
        this.onBreak(level, damageSource);
    }

    private void onBreak(ServerLevel level, DamageSource damageSource) {
        this.playBreakSound();
        this.dropAllDeathLoot(level, damageSource);

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = this.getItemBySlot(equipmentSlot);
            if (!itemStack.isEmpty()) {
                Block.popResource(this.level(), this.blockPosition().above(), itemStack);
                this.setItemSlot(equipmentSlot, ItemStack.EMPTY);
            }
        }
    }

    private void playBreakSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    @Override
    public LivingEntity.@NonNull Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.ARMOR_STAND_FALL, SoundEvents.ARMOR_STAND_FALL);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NonNull DamageSource source) {
        return SoundEvents.ARMOR_STAND_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ARMOR_STAND_BREAK;
    }

    @Override
    public void thunderHit(@NonNull ServerLevel level, net.minecraft.world.entity.@NonNull LightningBolt lightning) {
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean addEffect(net.minecraft.world.effect.@NonNull MobEffectInstance effect, @Nullable Entity source) {
        return false;
    }

    @Override
    public @NonNull EntityDimensions getDefaultDimensions(@NonNull Pose pose) {
        return this.getType().getDimensions();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ItemRegistry.TARGET_DUMMY);
    }

    @Override
    public void shear(@NonNull SoundSource source) {
        if (this.level() instanceof ServerLevel level) {
            this.breakAndDropItem(level, this.damageSources().generic());
            this.spawnBreakParticles();
            this.kill();
        }
    }

    @Override
    public boolean readyForShearing() {
        return true;
    }

    public Rotations getHeadRotation() {
        return this.entityData.get(TRACKER_HEAD_ROTATION);
    }

    public void setHeadRotation(Rotations angle) {
        this.entityData.set(TRACKER_HEAD_ROTATION, angle);
    }

    public Rotations getBodyRotation() {
        return this.entityData.get(TRACKER_BODY_ROTATION);
    }

    public void setBodyRotation(Rotations angle) {
        this.entityData.set(TRACKER_BODY_ROTATION, angle);
    }

    public Rotations getLeftArmRotation() {
        return this.entityData.get(TRACKER_LEFT_ARM_ROTATION);
    }

    public void setLeftArmRotation(Rotations angle) {
        this.entityData.set(TRACKER_LEFT_ARM_ROTATION, angle);
    }

    public Rotations getRightArmRotation() {
        return this.entityData.get(TRACKER_RIGHT_ARM_ROTATION);
    }

    public void setRightArmRotation(Rotations angle) {
        this.entityData.set(TRACKER_RIGHT_ARM_ROTATION, angle);
    }

    public Rotations getLeftLegRotation() {
        return this.entityData.get(TRACKER_LEFT_LEG_ROTATION);
    }

    public void setLeftLegRotation(Rotations angle) {
        this.entityData.set(TRACKER_LEFT_LEG_ROTATION, angle);
    }

    public Rotations getRightLegRotation() {
        return this.entityData.get(TRACKER_RIGHT_LEG_ROTATION);
    }

    public void setRightLegRotation(Rotations angle) {
        this.entityData.set(TRACKER_RIGHT_LEG_ROTATION, angle);
    }

    @Override
    public void setYBodyRot(float bodyYaw) {
        this.yBodyRotO = this.yRotO = bodyYaw;
        this.yHeadRotO = this.yHeadRot = bodyYaw;
    }

    @Override
    public void setYHeadRot(float headYaw) {
        this.yBodyRotO = this.yRotO = headYaw;
        this.yHeadRotO = this.yHeadRot = headYaw;
    }

    @Override
    protected float tickHeadTurn(float yawSpeed, float deltaTime) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
        return this.yBodyRot;
    }
}