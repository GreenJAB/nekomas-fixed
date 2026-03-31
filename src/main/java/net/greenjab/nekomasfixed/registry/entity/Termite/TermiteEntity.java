package net.greenjab.nekomasfixed.registry.entity.Termite;

import net.greenjab.nekomasfixed.registry.entity.TermiteHiveBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class TermiteEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState swipeAnimationState = new AnimationState();
    int idleAnimationTimeout = 0;
    public Optional<Boolean> isInMound = Optional.of(false);
    private Optional<BlockPos> moundPosition = findNearestMound(this).isEmpty() ? Optional.empty() : findNearestMound(this);
    SearchForLogGoal searchForLogGoal;

    public TermiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new EnterMoundGoal());
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(4, new GoToNearestMound(this, 0.4d, 32));
        this.searchForLogGoal = new SearchForLogGoal(this);
        this.goalSelector.add(2, this.searchForLogGoal);
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.4d));
        this.goalSelector.add(3, new LookAtEntityGoal(this, net.minecraft.entity.player.PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 0.6F, false));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.ATTACK_DAMAGE, 2d)
                .add(EntityAttributes.ATTACK_SPEED, 1.6d)
                .add(EntityAttributes.ATTACK_KNOCKBACK, 0.2d)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.4d)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 2d)
                .add(EntityAttributes.STEP_HEIGHT, 1d);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        world.sendEntityStatus(this, (byte)10);
        this.playSound(SoundEvents.ENTITY_SILVERFISH_AMBIENT, 10.0F, this.getSoundPitch());
        return super.tryAttack(world, target);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 10) {
            this.swipeAnimationState.start(this.age);
        } else {
            super.handleStatus(status);
        }
    }

    boolean canEnterMound() {
        return !this.searchForLogGoal.isRunning() && this.getTarget() == null && this.getEntityWorld().isNight();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("IsInMound", this.isInMound.get());

        if (this.moundPosition.isPresent()) {
            BlockPos pos = this.moundPosition.get();
            nbt.putInt("MoundX", pos.getX());
            nbt.putInt("MoundY", pos.getY());
            nbt.putInt("MoundZ", pos.getZ());
            nbt.putBoolean("HasMoundPosition", true);
        } else {
            nbt.putBoolean("HasMoundPosition", false);
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.isInMound = nbt.getBoolean("IsInMound");

        if (nbt.getBoolean("HasMoundPosition").get() && nbt.getBoolean("HasMoundPosition").isPresent() ) {
            int x = nbt.getInt("MoundX").get();
            int y = nbt.getInt("MoundY").get();
            int z = nbt.getInt("MoundZ").get();
            this.moundPosition = Optional.of(new BlockPos(x, y, z));
        } else {
            this.moundPosition = Optional.empty();
        }
    }

    public void setReachedHome(boolean b){
        this.isInMound = Optional.of(b);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getEntityWorld().isClient()) {

            if (this.getVelocity().horizontalLengthSquared() > 0.0001) {
                runAnimationState.startIfNotRunning(this.age);
                idleAnimationState.stop();
            } else {
                idleAnimationState.startIfNotRunning(this.age);
                runAnimationState.stop();
            }
        }
    }

    public TermiteHiveBlockEntity getMound(){
        if(this.getMoundPosition().isEmpty()){return null;}
        return (TermiteHiveBlockEntity) this.getEntityWorld().getBlockEntity(this.getMoundPosition().get());
    }

    public Optional<BlockPos> getMoundPosition() {
        return moundPosition;
    }

    public void setMoundPosition(Optional<BlockPos> pos) {
        this.moundPosition = pos;
    }

    public static Optional<BlockPos> findNearestMound(TermiteEntity termiteEntity){
        return BlockPos.findClosest(
               termiteEntity.getBlockPos(),
               16,
               8,
               pos -> termiteEntity.getEntityWorld().getBlockState(pos).isOf(BlockRegistry.TERMITE_HIVE)
       );
    }


    //find the nearest mound and go towards it
    private static class GoToNearestMound extends MoveToTargetPosGoal {
        private final TermiteEntity termiteEntity;
        public GoToNearestMound(TermiteEntity mob, double speed, int range) {
            super(mob, speed, range);
            this.termiteEntity = mob;
        }

        @Override
        protected boolean isTargetPos(@NonNull WorldView world, BlockPos pos) {
            return world.getBlockState(pos).isOf(BlockRegistry.TERMITE_HIVE);
        }

        @Override
        public boolean canStart() {
            return this.termiteEntity.getEntityWorld().isNight() && this.termiteEntity.getMoundPosition().isPresent();
        }

        @Override
        public boolean shouldContinue() {
            return !this.hasReached();
        }

        @Override
        public void start() {
            Optional<BlockPos> target = termiteEntity.getMoundPosition();

            if(target.isPresent()){
                target.ifPresent(pos -> {
                    targetPos = pos;
                    termiteEntity.getNavigation().startMovingTo(
                            pos.getX(), pos.getY(), pos.getZ(), 0.4
                    );
                });

            }


        }
    }


    private class EnterMoundGoal extends Goal {

        public boolean canTermiteStart() {
            if (TermiteEntity.this.moundPosition.isPresent()
                    && TermiteEntity.this.canEnterMound()
                    && TermiteEntity.this.squaredDistanceTo(
                    TermiteEntity.this.moundPosition.get().getX() + 0.5,
                    TermiteEntity.this.moundPosition.get().getY() + 0.5,
                    TermiteEntity.this.moundPosition.get().getZ() + 0.5
            ) < 4.0) {
                TermiteHiveBlockEntity termiteHiveBlockEntity = TermiteEntity.this.getMound();
                if (termiteHiveBlockEntity != null) {
                    if (!termiteHiveBlockEntity.isFullOfTermites()) {
                        return true;
                    }

                    TermiteEntity.this.moundPosition = Optional.empty();
                }
            }

            return false;
        }

        public boolean canTermiteContinue() {
            return false;
        }

        @Override
        public boolean canStart() {
            return canTermiteStart();
        }

        public void start() {
            TermiteHiveBlockEntity termiteHiveBlockEntity = TermiteEntity.this.getMound();
            if (termiteHiveBlockEntity != null) {
                termiteHiveBlockEntity.tryEnterMound(TermiteEntity.this);
            }
            TermiteEntity.this.setReachedHome(true);

        }
    }



    //custom goal for fetching a tree!!!
    private static class SearchForLogGoal extends Goal {
        private final TermiteEntity termiteEntity;
        private BlockPos targetPos;
        private boolean running;

        public SearchForLogGoal(TermiteEntity termiteEntity) {
            this.termiteEntity = termiteEntity;
        }

        @Override
        public boolean canStart() {
            return termiteEntity.isInMound.filter(aBoolean -> termiteEntity.getRandom().nextInt(40) == 0 && !aBoolean).isPresent();
        }

        boolean isRunning() {
            return this.running;
        }

        @Override
        public void stop(){this.running = false;}

        @Override
        public void start() {
            this.running = true;
            Optional<BlockPos> target = BlockPos.findClosest(
                    termiteEntity.getBlockPos(),
                    16,
                    8,
                    pos -> termiteEntity.getEntityWorld().getBlockState(pos).isIn(BlockTags.LOGS)
            );

            target.ifPresent(pos -> {
                targetPos = pos;
                termiteEntity.getNavigation().startMovingTo(
                        pos.getX(), pos.getY(), pos.getZ(), 0.4
                );
            });
        }

        @Override
        public boolean shouldContinue() {
            return !termiteEntity.getNavigation().isIdle();
        }

        public BlockPos getTargetPos() {
            return targetPos;
        }

        public void setTargetPos(BlockPos targetPos) {
            this.targetPos = targetPos;
        }
    }

}
