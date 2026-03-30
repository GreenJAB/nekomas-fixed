package net.greenjab.nekomasfixed.registry.entity.Termite;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class TermiteEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState swipeAnimationState = new AnimationState();
    int idleAnimationTimeout = 0;

    public TermiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new PowderSnowJumpGoal(this, this.getEntityWorld()));
        this.goalSelector.add(2, new SearchForLogGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.4d));
        this.goalSelector.add(3, new LookAtEntityGoal(this, net.minecraft.entity.player.PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 0.6F, false));
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

    //custom goal for fetching on trees!!!
    static class SearchForLogGoal extends Goal {
        private final TermiteEntity termiteEntity;
        private BlockPos targetPos;

        public SearchForLogGoal(TermiteEntity termiteEntity) {
            this.termiteEntity = termiteEntity;
        }

        @Override
        public boolean canStart() {
            return termiteEntity.getRandom().nextInt(40) == 0;
        }

        @Override
        public void start() {
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
