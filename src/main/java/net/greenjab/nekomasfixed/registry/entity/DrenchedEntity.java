package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

public class DrenchedEntity extends AbstractSkeletonEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(DrenchedEntity.class, TrackedDataHandlerRegistry.INTEGER);
    boolean targetingUnderwater;

    public DrenchedEntity(EntityType<? extends DrenchedEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new DrenchedEntity.DrenchedMoveControl(this);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    public static DefaultAttributeContainer.Builder createDrenchedAttributes() {
        return SkeletonEntity.createAbstractSkeletonAttributes().add(EntityAttributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousSwimNavigation(this, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new DrenchedEntity.WanderAroundOnSurfaceGoal(this, 1.0));
        //this.goalSelector.add(2, new DrenchedEntity.AnchorAttackGoal(this, 1.0, 40, 10.0F));
        this.goalSelector.add(2, new DrenchedEntity.DrenchedAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new DrenchedEntity.LeaveWaterGoal(this, 1.0));
        this.goalSelector.add(6, new DrenchedEntity.TargetAboveWaterGoal(this, 1.0, this.getEntityWorld().getSeaLevel()));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1.0));
        this.targetSelector
                .add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, (target, world) -> this.canDrenchedAttackTarget(target)));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MerchantEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, AxolotlEntity.class, true, false));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);
        this.setVariant(this.random.nextInt(3));
        if (this.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty() && world.getRandom().nextFloat() < 0.03F) {
            this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(getClam(world.getRandom().nextFloat())));
            this.setDropGuaranteed(EquipmentSlot.OFFHAND);
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

    public static boolean canSpawn(EntityType<DrenchedEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (!world.getFluidState(pos.down()).isIn(FluidTags.WATER) && !SpawnReason.isAnySpawner(spawnReason)) {
            return false;
        } else {
            RegistryEntry<Biome> registryEntry = world.getBiome(pos);
            boolean bl = world.getDifficulty() != Difficulty.PEACEFUL
                    && (SpawnReason.isTrialSpawner(spawnReason) || isSpawnDark(world, pos, random))
                    && (SpawnReason.isAnySpawner(spawnReason) || world.getFluidState(pos).isIn(FluidTags.WATER));
            if (!bl || !SpawnReason.isAnySpawner(spawnReason) && spawnReason != SpawnReason.REINFORCEMENT) {
                return registryEntry.isIn(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
                        ? random.nextInt(15) == 0 && bl
                        : random.nextInt(40) == 0 && isValidSpawnDepth(world, pos) && bl;
            } else {
                return true;
            }
        }
    }

    private static boolean isValidSpawnDepth(WorldAccess world, BlockPos pos) {
        return pos.getY() < world.getSeaLevel() - 5;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
    }

    public int getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if (random.nextFloat() > 0.9) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.ANCHOR));
        }
    }

    @Override
    public SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }
    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_DROWNED_SWIM;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    public boolean canDrenchedAttackTarget(@Nullable LivingEntity target) {
        return target != null && (!this.getEntityWorld().isDay() || target.isTouchingWater());
    }

    @Override
    public boolean isPushedByFluids() {
        return !this.isSwimming();
    }

    boolean isTargetingUnderwater() {
        if (this.targetingUnderwater) {
            return true;
        } else {
            LivingEntity livingEntity = this.getTarget();
            return livingEntity != null && livingEntity.isTouchingWater();
        }
    }

    @Override
    protected void travelInWater(Vec3d movementInput, double gravity, boolean falling, double y) {
        if (this.isSubmergedInWater() && this.isTargetingUnderwater()) {
            this.updateVelocity(0.01F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
        } else {
            super.travelInWater(movementInput, gravity, falling, y);
        }
    }

    @Override
    public void updateSwimming() {
        if (!this.getEntityWorld().isClient()) {
            this.setSwimming(this.canActVoluntarily() && this.isSubmergedInWater() && this.isTargetingUnderwater());
        }
    }

    @Override
    public boolean isInSwimmingPose() {
        return this.isSwimming() && !this.hasVehicle();
    }

    protected boolean hasFinishedCurrentPath() {
        Path path = this.getNavigation().getCurrentPath();
        if (path != null) {
            BlockPos blockPos = path.getTarget();
            if (blockPos != null) {
                double d = this.squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                return d < 4.0;
            }
        }
        return false;
    }

    public void setTargetingUnderwater(boolean targetingUnderwater) {
        this.targetingUnderwater = targetingUnderwater;
    }

    static class DrenchedAttackGoal extends MeleeAttackGoal {
        private final DrenchedEntity drenched;

        public DrenchedAttackGoal(DrenchedEntity drenched, double speed, boolean pauseWhenMobIdle) {
            super(drenched, speed, pauseWhenMobIdle);
            this.drenched = drenched;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && this.drenched.canDrenchedAttackTarget(this.drenched.getTarget());
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.drenched.canDrenchedAttackTarget(this.drenched.getTarget());
        }
    }

    static class DrenchedMoveControl extends MoveControl {
        private final DrenchedEntity drenched;

        public DrenchedMoveControl(DrenchedEntity drenched) {
            super(drenched);
            this.drenched = drenched;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.drenched.getTarget();
            if (this.drenched.isTargetingUnderwater() && this.drenched.isTouchingWater()) {
                if (livingEntity != null && livingEntity.getY() > this.drenched.getY() || this.drenched.targetingUnderwater) {
                    this.drenched.setVelocity(this.drenched.getVelocity().add(0.0, 0.002, 0.0));
                }

                if (this.state != MoveControl.State.MOVE_TO || this.drenched.getNavigation().isIdle()) {
                    this.drenched.setMovementSpeed(0.0F);
                    return;
                }

                double d = this.targetX - this.drenched.getX();
                double e = this.targetY - this.drenched.getY();
                double f = this.targetZ - this.drenched.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                e /= g;
                float h = (float)(MathHelper.atan2(f, d) * 180.0F / (float)Math.PI) - 90.0F;
                this.drenched.setYaw(this.wrapDegrees(this.drenched.getYaw(), h, 90.0F));
                this.drenched.bodyYaw = this.drenched.getYaw();
                float i = (float)(this.speed * this.drenched.getAttributeValue(EntityAttributes.MOVEMENT_SPEED));
                float j = MathHelper.lerp(0.125F, this.drenched.getMovementSpeed(), i);
                this.drenched.setMovementSpeed(j);
                this.drenched.setVelocity(this.drenched.getVelocity().add(j * d * 0.005, j * e * 0.1, j * f * 0.005));
            } else {
                if (!this.drenched.isOnGround()) {
                    this.drenched.setVelocity(this.drenched.getVelocity().add(0.0, -0.008, 0.0));
                }

                super.tick();
            }
        }
    }

    static class LeaveWaterGoal extends MoveToTargetPosGoal {
        private final DrenchedEntity drenched;

        public LeaveWaterGoal(DrenchedEntity drenched, double speed) {
            super(drenched, speed, 8, 2);
            this.drenched = drenched;
        }

        @Override
        public boolean canStart() {
            return super.canStart()
                    && !this.drenched.getEntityWorld().isDay()
                    && this.drenched.isTouchingWater()
                    && this.drenched.getY() >= this.drenched.getEntityWorld().getSeaLevel() - 3;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue();
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockPos blockPos = pos.up();
            return world.isAir(blockPos) && world.isAir(blockPos.up()) && world.getBlockState(pos).hasSolidTopSurface(world, pos, this.drenched);
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
        private final DrenchedEntity drenched;
        private final double speed;
        private final int minY;
        private boolean foundTarget;

        public TargetAboveWaterGoal(DrenchedEntity drenched, double speed, int minY) {
            this.drenched = drenched;
            this.speed = speed;
            this.minY = minY;
        }

        @Override
        public boolean canStart() {
            return !this.drenched.getEntityWorld().isDay() && this.drenched.isTouchingWater() && this.drenched.getY() < this.minY - 2;
        }

        @Override
        public boolean shouldContinue() {
            return this.canStart() && !this.foundTarget;
        }

        @Override
        public void tick() {
            if (this.drenched.getY() < this.minY - 1 && (this.drenched.getNavigation().isIdle() || this.drenched.hasFinishedCurrentPath())) {
                Vec3d vec3d = NoPenaltyTargeting.findTo(this.drenched, 4, 8, new Vec3d(this.drenched.getX(), this.minY - 1, this.drenched.getZ()), (float) (Math.PI / 2));
                if (vec3d == null) {
                    this.foundTarget = true;
                    return;
                }

                this.drenched.getNavigation().startMovingTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
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
        private final PathAwareEntity mob;
        private double x;
        private double y;
        private double z;
        private final double speed;
        private final World world;

        public WanderAroundOnSurfaceGoal(PathAwareEntity mob, double speed) {
            this.mob = mob;
            this.speed = speed;
            this.world = mob.getEntityWorld();
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (!this.world.isDay()) {
                return false;
            } else if (this.mob.isTouchingWater()) {
                return false;
            } else {
                Vec3d vec3d = this.getWanderTarget();
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
        public boolean shouldContinue() {
            return !this.mob.getNavigation().isIdle();
        }

        @Override
        public void start() {
            this.mob.getNavigation().startMovingTo(this.x, this.y, this.z, this.speed);
        }

        @Nullable
        private Vec3d getWanderTarget() {
            Random random = this.mob.getRandom();
            BlockPos blockPos = this.mob.getBlockPos();

            for (int i = 0; i < 10; i++) {
                BlockPos blockPos2 = blockPos.add(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
                if (this.world.getBlockState(blockPos2).isOf(Blocks.WATER)) {
                    return Vec3d.ofBottomCenter(blockPos2);
                }
            }

            return null;
        }
    }
}