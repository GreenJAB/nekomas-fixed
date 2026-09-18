package net.greenjab.nekomasfixed.registry.entity.moobloom;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * A flower cow. Each spawn picks a flower/colour variant (synced as a string),
 * you can shear the flower bloom off (releases the variant's flower), feed it a
 * moobloom flower to build into a suspicious stew with a bowl, and breed two to
 * inherit a parent's or eaten-flower's variant. Bees pollinate unsheared ones.
 */
public class Moobloom extends Cow {
    public static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.BOOLEAN);
    private static EntityDimensions BABY_BASE_DIMENSIONS;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    private ItemStack LastFlowerEaten = ItemStack.EMPTY;
    private int flowerRegrowTimer = 20 * 60 * 5;

    public Moobloom(EntityType<? extends Moobloom> entityType, Level level) {
        super(entityType, level);
    }

    // No AbstractCow in 1.21.1; mirror the vanilla cow's stats.
    public static AttributeSupplier.Builder createAttributes() {
        return Cow.createAttributes();
    }

    @Override
    public @NonNull SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor level, @NonNull DifficultyInstance difficulty,
                                                 @NonNull MobSpawnType spawnType, @Nullable SpawnGroupData entityData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, entityData);
        this.entityData.set(VARIANT, MoobloomVariants.getRandomVariant().path);
        return data;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0F));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25F, (stack) -> stack.is(ModTags.MOOBLOOM_FLOWERS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public ItemStack getLastFlowerEaten() {
        return LastFlowerEaten;
    }

    public void setLastFlowerEaten(ItemStack stack) {
        LastFlowerEaten = stack;
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.entityData.get(SHEARED));
        tag.putInt("FlowerRegrowTimer", this.flowerRegrowTimer);
        tag.putString("VariantPath", this.entityData.get(VARIANT));
        if (!LastFlowerEaten.isEmpty()) {
            tag.put("Item", LastFlowerEaten.saveOptional(this.registryAccess()));
        }
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSheared(tag.getBoolean("Sheared"));
        this.flowerRegrowTimer = tag.getInt("FlowerRegrowTimer");
        this.entityData.set(VARIANT, tag.getString("VariantPath").isEmpty() ? "ancient_cow_1" : tag.getString("VariantPath"));
        LastFlowerEaten = tag.contains("Item", 10) ? ItemStack.parseOptional(this.registryAccess(), tag.getCompound("Item")) : ItemStack.EMPTY;
    }

    @Override
    public @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.SHEARS) && !this.isBaby()) {
            Level level = this.level();
            if (level instanceof ServerLevel serverLevel) {
                if (this.isShearable()) {
                    this.sheared(serverLevel, SoundSource.PLAYERS, itemStack);
                    this.gameEvent(GameEvent.SHEAR, player);
                    itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.SUCCESS;
        } else if (itemStack.is(Items.BOWL) && !this.isBaby()) {
            Level level = this.level();
            if (!level.isClientSide && level instanceof ServerLevel) {
                ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
                stew.set(DataComponents.SUSPICIOUS_STEW_EFFECTS,
                        new SuspiciousStewEffects(List.of(MoobloomVariants.fromPath(this.entityData.get(VARIANT)).effect)));
                player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, stew));
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    public void sheared(ServerLevel level, SoundSource shearedSoundCategory, ItemStack shears) {
        level.playSound(null, this, SoundEvents.SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        for (int i = 0; i < shears.getCount(); ++i) {
            ItemEntity itemEntity = new ItemEntity(level, this.getX(), this.getY() + 0.2F, this.getZ(),
                    MoobloomVariants.fromPath(this.entityData.get(VARIANT)).flower);
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                    this.random.nextFloat() * 0.05F,
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            level.addFreshEntity(itemEntity);
        }
        this.setSheared(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHEARED, false);
        builder.define(VARIANT, "ancient_cow_1");
    }

    @Override
    public Moobloom getBreedOffspring(@NonNull ServerLevel level, @NonNull AgeableMob other) {
        Moobloom child = EntityTypeRegistry.MOOBLOOM.create(level);
        MoobloomVariants thisVariant = MoobloomVariants.fromPath(this.entityData.get(VARIANT));
        String result = thisVariant.path;
        if (other instanceof Moobloom mate) {
            MoobloomVariants secondVariant = MoobloomVariants.fromPath(mate.entityData.get(VARIANT));
            MoobloomVariants flowerVariant = MoobloomVariants.fromFlower(this.LastFlowerEaten.getItem());
            MoobloomVariants flowerVariant2 = MoobloomVariants.fromFlower(mate.getLastFlowerEaten().getItem());
            double random = level.getRandom().nextFloat();
            if (random <= 0.35) {
                result = thisVariant.path;
            } else if (random <= 0.7) {
                result = secondVariant.path;
            } else if (random <= 0.85) {
                result = flowerVariant.path;
            } else {
                result = flowerVariant2.path;
            }
        }
        Objects.requireNonNull(child).setSheared(true);
        child.entityData.set(VARIANT, result);
        return child;
    }

    public boolean isShearable() {
        return !this.entityData.get(SHEARED);
    }

    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean val) {
        this.entityData.set(SHEARED, val);
        this.flowerRegrowTimer = 20 * 60 * 5;
    }

    public String getVariantPath() {
        return this.entityData.get(VARIANT);
    }

    public void regrowFlowers() {
        this.flowerRegrowTimer = 20 * 60 * 5;
        this.setSheared(false);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.MOOBLOOM_FLOWERS);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.entityData.get(SHEARED)) {
            if (this.flowerRegrowTimer > 0) this.flowerRegrowTimer--;
            else this.regrowFlowers();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.getDeltaMovement().horizontalDistanceSqr() > 0.0001) {
                runAnimationState.startIfStopped(this.tickCount);
                idleAnimationState.stop();
            } else {
                idleAnimationState.startIfStopped(this.tickCount);
                runAnimationState.stop();
            }
        }
    }

    @Override
    public @NonNull EntityDimensions getDefaultDimensions(@NonNull Pose pose) {
        if (this.isBaby()) {
            if (BABY_BASE_DIMENSIONS == null) {
                BABY_BASE_DIMENSIONS = EntityTypeRegistry.MOOBLOOM.getDimensions().scale(0.5F).withEyeHeight(0.665F);
            }
            return BABY_BASE_DIMENSIONS;
        }
        return super.getDefaultDimensions(pose);
    }
}