package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

public class Drenched extends AbstractSkeleton {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Drenched.class, EntityDataSerializers.INT);
    boolean targetingUnderwater;

    public Drenched(EntityType<? extends Drenched> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new Drenched.DrenchedMoveControl(this);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createDrenchedAttributes() {
        return Skeleton.createAttributes().add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected @NonNull PathNavigation createNavigation(@NonNull Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new Drenched.WanderAroundOnSurfaceGoal(this, 1.0));
        //this.goalSelector.add(2, new DrenchedEntity.AnchorAttackGoal(this, 1.0, 40, 10.0F));
        this.goalSelector.addGoal(2, new Drenched.DrenchedAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new Drenched.LeaveWaterGoal(this, 1.0));
        this.goalSelector.addGoal(6, new Drenched.TargetAboveWaterGoal(this, 1.0, this.level().getSeaLevel()));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
        this.targetSelector
                .addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (target, _) -> this.canDrenchedAttackTarget(target)));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Axolotl.class, true, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor level, @NonNull DifficultyInstance difficulty, @NonNull EntitySpawnReason spawnReason, SpawnGroupData entityData) {
        entityData = super.finalizeSpawn(level, difficulty, spawnReason, entityData);
        this.setVariant(this.random.nextInt(3));
        if (this.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty() && level.getRandom().nextFloat() < 0.03F) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(getClam(level.getRandom().nextFloat())));
            this.setGuaranteedDrop(EquipmentSlot.OFFHAND);
        }
        return entityData;
    }

    private Item getClam(float rarity) {
        if (rarity>0.5) return ItemRegistry.CLAM;
        if (rarity>0.25) return ItemRegistry.CLAM_BLUE;
        if (rarity>0.125) return ItemRegistry.CLAM_PINK;
        if (rarity>0.0625) return ItemRegistry.CLAM_PURPLE;
        return ItemRegistry.CLAM;
    }

    public static boolean canSpawn(EntityType<Drenched> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        if (!level.getFluidState(pos.below()).is(FluidTags.WATER) && !EntitySpawnReason.isSpawner(spawnReason)) return false;
        Holder<Biome> registryEntry = level.getBiome(pos);
        boolean bl = level.getDifficulty() != Difficulty.PEACEFUL
                && (EntitySpawnReason.ignoresLightRequirements(spawnReason) || isDarkEnoughToSpawn(level, pos, random))
                && (EntitySpawnReason.isSpawner(spawnReason) || level.getFluidState(pos).is(FluidTags.WATER));
        if (!bl || !EntitySpawnReason.isSpawner(spawnReason) && spawnReason != EntitySpawnReason.REINFORCEMENT) {
            return registryEntry.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
                    ? random.nextInt(15) == 0 && bl
                    : random.nextInt(40) == 0 && isValidSpawnDepth(level, pos) && bl;
        } else {
            return true;
        }
    }

    private static boolean isValidSpawnDepth(LevelAccessor level, BlockPos pos) {
        return pos.getY() < level.getSeaLevel() - 5;
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, @NonNull DifficultyInstance localDifficulty) {
        if (random.nextFloat() > 0.9) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.ANCHOR));
        }
    }

    @Override
    public @NonNull SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }
    @Override
    protected @NonNull SoundEvent getSwimSound() {
        return SoundEvents.DROWNED_SWIM;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }
    @Override
    protected @NonNull SoundEvent getHurtSound(@NonNull DamageSource source) {
        return SoundEvents.SKELETON_HURT;
    }
    @Override
    protected @NonNull SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    public boolean checkSpawnObstruction(final LevelReader level) {
        return level.isUnobstructed(this);
    }

    public boolean canDrenchedAttackTarget(@Nullable LivingEntity target) {
        return target != null && (!this.level().isBrightOutside() || target.isInWater());
    }

    @Override
    public boolean isPushedByFluid() {
        return !this.isSwimming();
    }

    boolean isTargetingUnderwater() {
        if (this.targetingUnderwater) return true;
        LivingEntity livingEntity = this.getTarget();
        return livingEntity != null && livingEntity.isInWater();
    }

    @Override
    protected void travelInWater(@NonNull Vec3 movementInput, double gravity, boolean falling, double y) {
        if (this.isUnderWater() && this.isTargetingUnderwater()) {
            this.moveRelative(0.01F, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travelInWater(movementInput, gravity, falling, y);
        }
    }

    @Override
    public void updateSwimming() {
        if (!this.level().isClientSide()) this.setSwimming(this.isEffectiveAi() && this.isUnderWater() && this.isTargetingUnderwater());
    }

    @Override
    public boolean isVisuallySwimming() {
        return this.isSwimming() && !this.isPassenger();
    }

    protected boolean hasFinishedCurrentPath() {
        Path path = this.getNavigation().getPath();
        if (path != null) {
            BlockPos blockPos = path.getTarget();
            double d = this.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            return d < 4.0;
        }
        return false;
    }

    public void setTargetingUnderwater(boolean targetingUnderwater) {
        this.targetingUnderwater = targetingUnderwater;
    }

    static class DrenchedAttackGoal extends MeleeAttackGoal {
        private final Drenched drenched;

        public DrenchedAttackGoal(Drenched drenched, double speed, boolean pauseWhenMobIdle) {
            super(drenched, speed, pauseWhenMobIdle);
            this.drenched = drenched;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.drenched.canDrenchedAttackTarget(this.drenched.getTarget());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.drenched.canDrenchedAttackTarget(this.drenched.getTarget());
        }
    }

    static class DrenchedMoveControl extends MoveControl {
        private final Drenched drenched;

        public DrenchedMoveControl(Drenched drenched) {
            super(drenched);
            this.drenched = drenched;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.drenched.getTarget();
            if (this.drenched.isTargetingUnderwater() && this.drenched.isInWater()) {
                if (livingEntity != null && livingEntity.getY() > this.drenched.getY() || this.drenched.targetingUnderwater) {
                    this.drenched.setDeltaMovement(this.drenched.getDeltaMovement().add(0.0, 0.002, 0.0));
                }

                if (this.operation != MoveControl.Operation.MOVE_TO || this.drenched.getNavigation().isDone()) {
                    this.drenched.setSpeed(0.0F);
                    return;
                }

                double d = this.wantedX - this.drenched.getX();
                double e = this.wantedY - this.drenched.getY();
                double f = this.wantedZ - this.drenched.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                e /= g;
                float h = (float)(Mth.atan2(f, d) * 180.0F / (float)Math.PI) - 90.0F;
                this.drenched.setYRot(this.rotlerp(this.drenched.getYRot(), h, 90.0F));
                this.drenched.yBodyRot = this.drenched.getYRot();
                float i = (float)(this.speedModifier * this.drenched.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float j = Mth.lerp(0.125F, this.drenched.getSpeed(), i);
                this.drenched.setSpeed(j);
                this.drenched.setDeltaMovement(this.drenched.getDeltaMovement().add(j * d * 0.005, j * e * 0.1, j * f * 0.005));
            } else {
                if (!this.drenched.onGround()) {
                    this.drenched.setDeltaMovement(this.drenched.getDeltaMovement().add(0.0, -0.008, 0.0));
                }

                super.tick();
            }
        }
    }

    static class LeaveWaterGoal extends MoveToBlockGoal {
        private final Drenched drenched;

        public LeaveWaterGoal(Drenched drenched, double speed) {
            super(drenched, speed, 8, 2);
            this.drenched = drenched;
        }

        @Override
        public boolean canUse() {
            return super.canUse()
                    && !this.drenched.level().isBrightOutside()
                    && this.drenched.isInWater()
                    && this.drenched.getY() >= this.drenched.level().getSeaLevel() - 3;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            BlockPos blockPos = pos.above();
            return level.isEmptyBlock(blockPos) && level.isEmptyBlock(blockPos.above()) && level.getBlockState(pos).entityCanStandOn(level, pos, this.drenched);
        }

        @Override
        public void start() {
            this.drenched.setTargetingUnderwater(false);
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
        }
    }

    static class TargetAboveWaterGoal extends Goal {
        private final Drenched drenched;
        private final double speed;
        private final int minY;
        private boolean foundTarget;

        public TargetAboveWaterGoal(Drenched drenched, double speed, int minY) {
            this.drenched = drenched;
            this.speed = speed;
            this.minY = minY;
        }

        @Override
        public boolean canUse() {
            return !this.drenched.level().isBrightOutside() && this.drenched.isInWater() && this.drenched.getY() < this.minY - 2;
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse() && !this.foundTarget;
        }

        @Override
        public void tick() {
            if (this.drenched.getY() < this.minY - 1 && (this.drenched.getNavigation().isDone() || this.drenched.hasFinishedCurrentPath())) {
                Vec3 vec3d = DefaultRandomPos.getPosTowards(this.drenched, 4, 8, new Vec3(this.drenched.getX(), this.minY - 1, this.drenched.getZ()), (float) (Math.PI / 2));
                if (vec3d == null) {
                    this.foundTarget = true;
                    return;
                }

                this.drenched.getNavigation().moveTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
            }
        }

        @Override
        public void start() {
            this.drenched.setTargetingUnderwater(true);
            this.foundTarget = false;
        }

        @Override
        public void stop() {
            this.drenched.setTargetingUnderwater(false);
        }
    }

    /*static class AnchorAttackGoal extends ProjectileAttackGoal {
        private final DrenchedEntity drenched;

        public AnchorAttackGoal(RangedAttackMob rangedAttackMob, double d, int i, float f) {
            super(rangedAttackMob, d, i, f);
            this.drenched = (DrenchedEntity)rangedAttackMob;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && this.drenched.getMainHandStack().isOf(ItemRegistry.ANCHOR);
        }

        @Override
        public void start() {
            super.start();
            this.drenched.setAttacking(true);
            this.drenched.setCurrentHand(Hand.MAIN_HAND);
        }

        @Override
        public void stop() {
            super.stop();
            this.drenched.clearActiveItem();
            this.drenched.setAttacking(false);
        }
    }*/

    static class WanderAroundOnSurfaceGoal extends Goal {
        private final PathfinderMob mob;
        private double x;
        private double y;
        private double z;
        private final double speed;
        private final Level level;

        public WanderAroundOnSurfaceGoal(PathfinderMob mob, double speed) {
            this.mob = mob;
            this.speed = speed;
            this.level = mob.level();
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!this.level.isBrightOutside()) {
                return false;
            } else if (this.mob.isInWater()) {
                return false;
            } else {
                Vec3 vec3d = this.getWanderTarget();
                if (vec3d == null) {
                    return false;
                } else {
                    this.x = vec3d.x;
                    this.y = vec3d.y;
                    this.z = vec3d.z;
                    return true;
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return !this.mob.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.mob.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
        }

        @Nullable
        private Vec3 getWanderTarget() {
            RandomSource random = this.mob.getRandom();
            BlockPos blockPos = this.mob.blockPosition();

            for (int i = 0; i < 10; i++) {
                BlockPos blockPos2 = blockPos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
                if (this.level.getBlockState(blockPos2).is(Blocks.WATER)) {
                    return Vec3.atBottomCenterOf(blockPos2);
                }
            }

            return null;
        }
    }
}