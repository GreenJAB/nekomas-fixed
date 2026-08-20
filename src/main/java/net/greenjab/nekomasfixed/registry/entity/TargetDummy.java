package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.ParticleRegistry;
import net.minecraft.core.Rotations;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.util.function.Predicate;

public class TargetDummy extends Avatar implements Shearable {
	protected static final EntityDataAccessor<ResolvableProfile> PROFILE = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.RESOLVABLE_PROFILE);
	protected static final EntityDataAccessor<Boolean> ZOMBIE = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.BOOLEAN);
	public static final ResolvableProfile DEFAULT_INFO = ResolvableProfile.Static.EMPTY;
	public static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
	public static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
	public static final Rotations DEFAULT_LEFT_ARM_ROTATION = new Rotations(-5.0F, 0.0F, -5.0F);
	public static final Rotations DEFAULT_RIGHT_ARM_ROTATION = new Rotations(-5.0F, 0.0F, 5.0F);
	public static final Rotations DEFAULT_LEFT_LEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
	public static final Rotations DEFAULT_RIGHT_LEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
	public static final EntityDataAccessor<Rotations> TRACKER_HEAD_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> TRACKER_BODY_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> TRACKER_LEFT_ARM_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> TRACKER_RIGHT_ARM_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> TRACKER_LEFT_LEG_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> TRACKER_RIGHT_LEG_ROTATION = SynchedEntityData.defineId(TargetDummy.class, EntityDataSerializers.ROTATIONS);
	private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE =  entity -> entity instanceof AbstractMinecart abstractMinecartEntity
			&& abstractMinecartEntity.isRideable();
	private int lastHitValue;
	public long lastHitTime;

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
	public void refreshDimensions() {
		double d = this.getX();
		double e = this.getY();
		double f = this.getZ();
		super.refreshDimensions();
		this.setPos(d, e, f);
	}

	private boolean canClip() {
		return !this.isNoGravity();
	}

	@Override
	public boolean isEffectiveAi() {
		return super.isEffectiveAi() && this.canClip();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(PROFILE, DEFAULT_INFO);
		builder.define(ZOMBIE, false);
		builder.define(TRACKER_HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
		builder.define(TRACKER_BODY_ROTATION, DEFAULT_BODY_ROTATION);
		builder.define(TRACKER_LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
		builder.define(TRACKER_RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
		builder.define(TRACKER_LEFT_LEG_ROTATION, DEFAULT_LEFT_LEG_ROTATION);
		builder.define(TRACKER_RIGHT_LEG_ROTATION, DEFAULT_RIGHT_LEG_ROTATION);
	}

	public ResolvableProfile getTargetDummyProfile() {
		return this.entityData.get(PROFILE);
	}

	private void setTargetDummyProfile(ResolvableProfile profile) {
		this.entityData.set(PROFILE, profile);
	}

	public boolean isZombie() {
		return this.entityData.get(ZOMBIE);
	}

	private void setZombie(boolean zombie) {
		this.entityData.set(ZOMBIE, zombie);
	}

	@Override
	public boolean canUseSlot(@NonNull EquipmentSlot slot) {
		return slot != EquipmentSlot.BODY && slot != EquipmentSlot.SADDLE;
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput view) {
		super.addAdditionalSaveData(view);
		view.store("profile", ResolvableProfile.CODEC, this.getTargetDummyProfile());
		view.store("Pose", TargetDummy.PackedRotation.CODEC, this.packRotation());
		view.store("LastDamage", Codec.INT, lastHitValue);
		view.store("IsZombie", Codec.BOOL, isZombie());
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput view) {
		super.readAdditionalSaveData(view);
		this.noPhysics = !this.canClip();
		view.read("profile", ResolvableProfile.CODEC).ifPresent(this::setTargetDummyProfile);
		view.read("Pose", PackedRotation.CODEC).ifPresent(this::unpackRotation);
		view.read("LastDamage", Codec.INT).ifPresent(this::setLastDamage);
		view.read("IsZombie", Codec.BOOL).ifPresent(this::setZombie);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(@NonNull Entity entity) {
	}

	@Override
	protected void pushEntities() {
		for (Entity entity : this.level().getEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE)) {
			if (this.distanceToSqr(entity) <= 0.2) {
				entity.push(this);
			}
		}
	}

	@Override
	public @NonNull InteractionResult interact(Player player, @NonNull InteractionHand hand, @NonNull Vec3 hitPos) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.SHEARS)) {
			if (player.level() instanceof ServerLevel level) {
				this.breakAndDropItem(level, this.damageSources().generic());
				this.spawnBreakParticles();
				this.kill(level);
				itemStack.hurtWithoutBreaking(1, player);
			}
			return InteractionResult.SUCCESS;
		} else if (itemStack.is(Items.NAME_TAG)) {
			if (itemStack.hasNonDefault(DataComponents.CUSTOM_NAME)) {
				Component name = itemStack.get(DataComponents.CUSTOM_NAME);
				if (name!=null) {
					String s = name.tryCollapseToString();
					if (s != null && !s.isEmpty()) setTargetDummyProfile(ResolvableProfile.createUnresolved(name.tryCollapseToString()));
					setZombie(false);
					return InteractionResult.SUCCESS;
				}
			}
			return InteractionResult.PASS;
		} else if (itemStack.is(Items.PLAYER_HEAD)) {
			if (itemStack.hasNonDefault(DataComponents.PROFILE)) {
				ResolvableProfile PC = itemStack.get(DataComponents.PROFILE);
				if (PC!=null) {
					setTargetDummyProfile(PC);
					setZombie(false);
					return InteractionResult.SUCCESS;
				}
			}
			return InteractionResult.PASS;
		} else if (itemStack.is(Items.ZOMBIE_HEAD)||itemStack.is(Items.SKELETON_SKULL)||itemStack.is(Items.ROTTEN_FLESH)) {
			setTargetDummyProfile(DEFAULT_INFO);
			setZombie(true);
			return InteractionResult.SUCCESS;
		} else if (itemStack.is(Items.HAY_BLOCK)) {
			setTargetDummyProfile(DEFAULT_INFO);
			setZombie(false);
			return InteractionResult.SUCCESS;
		} else if (player.isSpectator()) {
			return InteractionResult.SUCCESS;
		} else if (player.level().isClientSide()) {
			return InteractionResult.SUCCESS_SERVER;
		} else {
			EquipmentSlot equipmentSlot = this.getEquipmentSlotForItem(itemStack);
			if (itemStack.isEmpty()) {
                EquipmentSlot equipmentSlot3 = this.getSlotFromPosition(hitPos);
				if (this.hasItemInSlot(equipmentSlot3) && this.equip(player, equipmentSlot3, itemStack, hand)) {
					return InteractionResult.SUCCESS_SERVER;
				}
			} else {
				if (this.equip(player, equipmentSlot, itemStack, hand)) {
					return InteractionResult.SUCCESS_SERVER;
				}
			}

			return InteractionResult.PASS;
		}
	}

	private EquipmentSlot getSlotFromPosition(Vec3 hitPos) {
		EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
		double d = hitPos.y / (this.getScale() * this.getAgeScale());
		EquipmentSlot equipmentSlot2 = EquipmentSlot.FEET;
		if (d >= 0.1 && d < 0.1 + 0.45 && this.hasItemInSlot(equipmentSlot2)) {
			equipmentSlot = EquipmentSlot.FEET;
		} else if (d >= 0.9 + 0.0 && d < 0.9 + 0.7 && this.hasItemInSlot(EquipmentSlot.CHEST)) {
			equipmentSlot = EquipmentSlot.CHEST;
		} else if (d >= 0.4 && d < 0.4 + 0.8 && this.hasItemInSlot(EquipmentSlot.LEGS)) {
			equipmentSlot = EquipmentSlot.LEGS;
		} else if (d >= 1.6 && this.hasItemInSlot(EquipmentSlot.HEAD)) {
			equipmentSlot = EquipmentSlot.HEAD;
		} else if (!this.hasItemInSlot(EquipmentSlot.MAINHAND) && this.hasItemInSlot(EquipmentSlot.OFFHAND)) {
			equipmentSlot = EquipmentSlot.OFFHAND;
		}

		return equipmentSlot;
	}


	private boolean equip(Player player, EquipmentSlot slot, ItemStack stack, InteractionHand hand) {
		ItemStack itemStack = this.getItemBySlot(slot);
        if (player.hasInfiniteMaterials() && itemStack.isEmpty() && !stack.isEmpty()) {
            this.setItemSlot(slot, stack.copyWithCount(1));
            return true;
        } else if (stack.isEmpty() || stack.getCount() <= 1) {
            this.setItemSlot(slot, stack);
            player.setItemInHand(hand, itemStack);
            return true;
        } else if (!itemStack.isEmpty()) {
            return false;
        } else {
            this.setItemSlot(slot, stack.split(1));
            return true;
        }
	}

	public int getLastDamage(){
		return lastHitValue;
	}

	public void setLastDamage(int damage){
		lastHitValue = damage;
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float amount) {
		if (this.isRemoved()) {
			return false;
		} else if (!level.getGameRules().get(GameRules.MOB_GRIEFING) && source.getEntity() instanceof Mob) {
			return false;
		} else if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			this.kill(level);
			return false;
		} else if (this.isInvulnerableTo(level, source)) {
			return false;
		} else if (source.is(DamageTypeTags.IS_EXPLOSION)) {
			this.onBreak(level, source);
			this.kill(level);
			return false;
		} else if (source.getEntity()==null || !((source.getEntity()) instanceof Player)) {
			return false;
		} else {
			amount = this.getDamageAfterArmorAbsorb(source, amount);
			amount = this.getDamageAfterMagicAbsorb(source, amount);
			if (source.getWeaponItem()!=null&&source.getWeaponItem().is(Items.SHEARS)) {
				if (source.isCreativePlayer()) {
					this.playBreakSound();
				} else {
					this.breakAndDropItem(level, source);
					if (source.getEntity() instanceof Player player) source.getWeaponItem().hurtWithoutBreaking(1, player);
				}
				this.spawnBreakParticles();
				this.kill(level);
				return true;
			} else if (source.getEntity() instanceof Player playerEntity && !playerEntity.getAbilities().mayBuild) {
				return false;
			} else if (source.isCreativePlayer()) {
				long l = level.getGameTime();
				if (l - this.lastHitTime > 5L) {
					level.broadcastEntityEvent(this, EntityEvent.ARMORSTAND_WOBBLE);
					this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
					this.lastHitTime = l;
					lastHitValue = (int) amount;
					if (this.level() instanceof ServerLevel) {
						((ServerLevel)this.level())
								.sendParticles(ParticleRegistry.NUMBER, this.getX(), this.getY()+2, this.getZ(), 0, 1, 0, 0, amount);
					}
				} else {
					this.playBreakSound();
					this.spawnBreakParticles();
					this.kill(level);
				}
				return true;
			} else {
				long l = level.getGameTime();
				level.broadcastEntityEvent(this, EntityEvent.ARMORSTAND_WOBBLE);
				this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
				this.lastHitTime = l;
				lastHitValue = (int) amount;
				if (this.level() instanceof ServerLevel) {
					((ServerLevel)this.level())
							.sendParticles(ParticleRegistry.NUMBER, this.getX(), this.getY()+2, this.getZ(), 0, 1, 0, 0, amount);
				}
				return true;
			}
		}
	}

	@Override
	public void handleEntityEvent(byte status) {
		if (status == EntityEvent.ARMORSTAND_WOBBLE) {
			if (this.level().isClientSide()) {
				this.level()
						.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
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
		if (this.level() instanceof ServerLevel) {
			((ServerLevel)this.level())
					.sendParticles(
							new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()),
							this.getX(),
							this.getY(0.6666666666666666),
							this.getZ(),
							10,
							this.getBbWidth() / 4.0F,
							this.getBbHeight() / 4.0F,
							this.getBbWidth() / 4.0F,
							0.05
					);
		}
	}

	private void breakAndDropItem(ServerLevel level, DamageSource damageSource) {
		ItemStack itemStack = new ItemStack(ItemRegistry.TARGET_DUMMY);
		itemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
		Block.popResource(this.level(), this.blockPosition(), itemStack);
		this.onBreak(level, damageSource);
	}

	private void onBreak(ServerLevel level, DamageSource damageSource) {
		this.playBreakSound();
		this.dropAllDeathLoot(level, damageSource);

		for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
			ItemStack itemStack = this.equipment.set(equipmentSlot, ItemStack.EMPTY);
			if (!itemStack.isEmpty()) {
				Block.popResource(this.level(), this.blockPosition().above(), itemStack);
			}
		}
	}

	private void playBreakSound() {
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
	}

	@Override
	protected void tickHeadTurn(float bodyRotation) {
		this.yBodyRotO = this.yRotO;
		this.yBodyRot = this.getYRot();
	}

	@Override
	public void travel(@NonNull Vec3 movementInput) {
		if (this.canClip()) {
			super.travel(movementInput);
		}
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
	public void kill(@NonNull ServerLevel level) {
		this.remove(Entity.RemovalReason.KILLED);
		this.gameEvent(GameEvent.ENTITY_DIE);
	}

	@Override
	public boolean ignoreExplosion(Explosion explosion) {
		return !explosion.shouldAffectBlocklikeEntities() || this.isInvisible();
	}


	public void setHeadRotation(Rotations angle) {
		this.entityData.set(TRACKER_HEAD_ROTATION, angle);
	}

	public void setBodyRotation(Rotations angle) {
		this.entityData.set(TRACKER_BODY_ROTATION, angle);
	}

	public void setLeftArmRotation(Rotations angle) {
		this.entityData.set(TRACKER_LEFT_ARM_ROTATION, angle);
	}

	public void setRightArmRotation(Rotations angle) {
		this.entityData.set(TRACKER_RIGHT_ARM_ROTATION, angle);
	}

	public void setLeftLegRotation(Rotations angle) {
		this.entityData.set(TRACKER_LEFT_LEG_ROTATION, angle);
	}

	public void setRightLegRotation(Rotations angle) {
		this.entityData.set(TRACKER_RIGHT_LEG_ROTATION, angle);
	}

	public Rotations getHeadRotation() {
		return this.entityData.get(TRACKER_HEAD_ROTATION);
	}

	public Rotations getBodyRotation() {
		return this.entityData.get(TRACKER_BODY_ROTATION);
	}

	public Rotations getLeftArmRotation() {
		return this.entityData.get(TRACKER_LEFT_ARM_ROTATION);
	}

	public Rotations getRightArmRotation() {
		return this.entityData.get(TRACKER_RIGHT_ARM_ROTATION);
	}

	public Rotations getLeftLegRotation() {
		return this.entityData.get(TRACKER_LEFT_LEG_ROTATION);
	}

	public Rotations getRightLegRotation() {
		return this.entityData.get(TRACKER_RIGHT_LEG_ROTATION);
	}

	@Override
	public boolean isPickable() {
		return super.isPickable();
	}

	@Override
	public boolean skipAttackInteraction(@NonNull Entity attacker) {
		return attacker instanceof Player playerEntity && !this.level().mayInteract(playerEntity, this.blockPosition());
	}

	@Override
	public @NonNull HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
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
	public void thunderHit(@NonNull ServerLevel level, @NonNull LightningBolt lightning) {
	}

	@Override
	public boolean isAffectedByPotions() {
		return false;
	}

	@Override
	public boolean addEffect(@NonNull MobEffectInstance effect, @Nullable Entity source) {
		return false;
	}

	@Override
	public boolean attackable() {
		return false;
	}

	@Override
	public @NonNull EntityDimensions getDefaultDimensions(@NonNull Pose pose) {
		return this.getType().getDimensions();
	}

	@Override
	public @NonNull ResolvableProfile getProfile() {
		return null;
	}

	@Override
	public ItemStack getPickResult() {
		return new ItemStack(ItemRegistry.TARGET_DUMMY);
	}

	@Override
	public boolean canBeSeenByAnyone() {
		return !this.isInvisible();
	}

	public void unpackRotation(TargetDummy.PackedRotation packedRotation) {
		this.setHeadRotation(packedRotation.head());
		this.setBodyRotation(packedRotation.body());
		this.setLeftArmRotation(packedRotation.leftArm());
		this.setRightArmRotation(packedRotation.rightArm());
		this.setLeftLegRotation(packedRotation.leftLeg());
		this.setRightLegRotation(packedRotation.rightLeg());
	}

	public TargetDummy.PackedRotation packRotation() {
		return new TargetDummy.PackedRotation(this.getHeadRotation(), this.getBodyRotation(), this.getLeftArmRotation(),
				this.getRightArmRotation(), this.getLeftLegRotation(), this.getRightLegRotation());
	}

	@Override
	public void shear(@NonNull ServerLevel level, @NonNull SoundSource shearedSoundCategory, @NonNull ItemStack shears) {
		this.breakAndDropItem(level, this.damageSources().generic());
		this.spawnBreakParticles();
		this.kill(level);
	}

	@Override
	public boolean readyForShearing() {
		return true;
	}

	public record PackedRotation(Rotations head, Rotations body, Rotations leftArm, Rotations rightArm, Rotations leftLeg, Rotations rightLeg) {
		public static final Codec<TargetDummy.PackedRotation> CODEC = RecordCodecBuilder.create(
				  instance -> instance.group(
								Rotations.CODEC.optionalFieldOf("Head", TargetDummy.DEFAULT_HEAD_ROTATION).forGetter(TargetDummy.PackedRotation::head),
								Rotations.CODEC.optionalFieldOf("Body", TargetDummy.DEFAULT_BODY_ROTATION).forGetter(TargetDummy.PackedRotation::body),
								Rotations.CODEC.optionalFieldOf("LeftArm", TargetDummy.DEFAULT_LEFT_ARM_ROTATION).forGetter(TargetDummy.PackedRotation::leftArm),
								Rotations.CODEC.optionalFieldOf("RightArm", TargetDummy.DEFAULT_RIGHT_ARM_ROTATION).forGetter(TargetDummy.PackedRotation::rightArm),
								Rotations.CODEC.optionalFieldOf("LeftLeg", TargetDummy.DEFAULT_LEFT_LEG_ROTATION).forGetter(TargetDummy.PackedRotation::leftLeg),
								Rotations.CODEC.optionalFieldOf("RightLeg", TargetDummy.DEFAULT_RIGHT_LEG_ROTATION).forGetter(TargetDummy.PackedRotation::rightLeg)
						).apply(instance, TargetDummy.PackedRotation::new)
		);
	}
}

