package net.greenjab.nekomasfixed.registry.entity.Moobloom;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.cow.AbstractCow;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class MoobloomEntity extends Cow {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS;
    private ItemStack LastFlowerEaten = ItemStack.EMPTY;
    private int flowerRegrowTimer = 20 * 60 * 5;
    public static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(MoobloomEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(MoobloomEntity.class, EntityDataSerializers.BOOLEAN);

    public MoobloomEntity(EntityType<? extends Cow> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public @NonNull SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor world, @NonNull DifficultyInstance difficulty, @NonNull EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        this.entityData.set(VARIANT, MoobloomEntityVariants.getRandomVariant().path);

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

    public static AttributeSupplier.@NonNull Builder createAttributes(){
        return AbstractCow.createAttributes();
    }

    public void setLastFlowerEaten(ItemStack stack) {
        LastFlowerEaten = stack;
    }
    public ItemStack getLastFlowerEaten() {
        return LastFlowerEaten;
    }

    @Override
    protected void addAdditionalSaveData(@NonNull ValueOutput view) {
        super.addAdditionalSaveData(view);
        view.putBoolean("Sheared", this.entityData.get(SHEARED));
        view.putInt("FlowerRegrowTimer", this.flowerRegrowTimer);
        view.putString("VariantPath", this.entityData.get(VARIANT));

        if (!LastFlowerEaten.isEmpty()) {
            view.store("Item", ItemStack.CODEC, LastFlowerEaten);
        }
    }

    @Override
    protected void readAdditionalSaveData(@NonNull ValueInput view) {
        super.readAdditionalSaveData(view);
        this.setSheared(view.getBooleanOr("Sheared", false));
        this.flowerRegrowTimer = view.getIntOr("FlowerRegrowTimer", 20 * 60 * 5);
        this.entityData.set(VARIANT, view.getStringOr("VariantPath", "ancient_cow_1"));

        LastFlowerEaten = view.read("Item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    @Override
    public @NonNull InteractionResult mobInteract(Player player, @NonNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.SHEARS) && !this.isBaby()) {
            Level var5 = this.level();
            if (var5 instanceof ServerLevel serverWorld) {
                if (this.isShearable()) {
                    this.sheared(serverWorld, SoundSource.PLAYERS, itemStack);
                    this.gameEvent(GameEvent.SHEAR, player);
                    itemStack.hurtAndBreak(1, player, hand.asEquipmentSlot());
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
            return InteractionResult.SUCCESS;
        } else if (itemStack.is(Items.BOWL) && !this.isBaby()) {
            Level world = this.level();
            if (!world.isClientSide() && world instanceof ServerLevel) {
                ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
                stew.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects(List.of(MoobloomEntityVariants.fromPath(this.entityData.get(VARIANT)).effect)));
                player.getItemInHand(InteractionHand.MAIN_HAND).consume(1, player);
                player.handleExtraItemsCreatedOnUse( stew);
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    public void sheared(ServerLevel world, SoundSource shearedSoundCategory, ItemStack shears) {
        world.playSound(null, this, SoundEvents.SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);

            for(int i = 0; i < shears.getCount(); ++i) {
                ItemEntity itemEntity = this.spawnAtLocation(world, MoobloomEntityVariants.fromPath(this.entityData.get(VARIANT)).flower, 1.0F);
                if (itemEntity != null) {
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
                }
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
    public MoobloomEntity getBreedOffspring(@NonNull ServerLevel world, @NonNull AgeableMob other) {
        MoobloomEntity child = EntityTypeRegistry.MOOBLOOM.create(world, EntitySpawnReason.BREEDING);
        assert child != null;

        MoobloomEntityVariants thisVariant = MoobloomEntityVariants.fromPath(this.entityData.get(VARIANT));
        String result = thisVariant.path;
        if (other instanceof MoobloomEntity mate) {
            MoobloomEntityVariants secondVariant = MoobloomEntityVariants.fromPath(mate.getEntityData().get(VARIANT));
            MoobloomEntityVariants flowerVariant = MoobloomEntityVariants.fromFlower(this.LastFlowerEaten.getItem());
            MoobloomEntityVariants flowerVariant2 = MoobloomEntityVariants.fromFlower(mate.getLastFlowerEaten().getItem());
            double random = world.getRandom().nextFloat();
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
        child.getEntityData().set(VARIANT, result);
        child.getEntityData().set(SHEARED, true);
        return child;
    }

    public void setSheared(boolean val){
        this.entityData.set(SHEARED, val);
        this.flowerRegrowTimer = 20 * 60 * 5;}

    public boolean isShearable(){return !this.entityData.get(SHEARED);}

    public void regrowFlowers(){
        this.flowerRegrowTimer = 20 * 60 * 5;
        this.setSheared(false);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.MOOBLOOM_FLOWERS);
    }

    @Override
    public void customServerAiStep(@NonNull ServerLevel world) {
        super.customServerAiStep(world);

        if (this.entityData.get(SHEARED)) {
            if (this.flowerRegrowTimer > 0) {
                this.flowerRegrowTimer--;
            }
            if (this.flowerRegrowTimer <= 0) {
                this.regrowFlowers();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {

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
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    static {
        BABY_BASE_DIMENSIONS = EntityTypeRegistry.MOOBLOOM.getDimensions().scale(0.5F).withEyeHeight(0.665F);
    }
}
