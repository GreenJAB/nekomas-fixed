package net.greenjab.nekomasfixed.registry.entity.Termite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.rule.GameRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TermiteEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    int idleAnimationTimeout = 0;

    public TermiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new PowderSnowJumpGoal(this, this.getEntityWorld()));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.4d));
        this.goalSelector.add(2, new SearchForLogGoal(this));
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
        private final TermiteEntity termtieEntity;
        private int delay;

        public SearchForLogGoal(TermiteEntity termiteEntity) {
            this.termtieEntity = termiteEntity;
        }

        public void onHurt() {
            if (this.delay == 0) {
                this.delay = this.getTickCount(20);
            }

        }

        public boolean canStart() {
            return this.delay > 0;
        }

        private static boolean chunkContainsBlock(Chunk chunk) {
            BlockPos.Mutable pos = new BlockPos.Mutable();

            int startX = chunk.getPos().getStartX();
            int startZ = chunk.getPos().getStartZ();

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = chunk.getBottomY(); y < chunk.getHeight(); y++) {

                        pos.set(startX + x, y, startZ + z);

                        if (chunk.getBlockState(pos).getBlock().getDefaultState().isIn(BlockTags.LOGS) ) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private static ArrayList<BlockPos> getAllPositions(Chunk chunk) {
            ArrayList<BlockPos> allPositions = new ArrayList<>();
            BlockPos.Mutable pos = new BlockPos.Mutable();

            int startX = chunk.getPos().getStartX();
            int startZ = chunk.getPos().getStartZ();

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = chunk.getBottomY(); y < chunk.getHeight(); y++) {

                        pos.set(startX + x, y, startZ + z);

                        if (chunk.getBlockState(pos).isIn(BlockTags.LOGS)) {
                            allPositions.add(pos.toImmutable());
                        }
                    }
                }
            }
            return allPositions;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        public void tick() {
            --this.delay;

            if (this.delay <= 0) {
                Optional<BlockPos> target = BlockPos.findClosest(
                        termtieEntity.getBlockPos(),
                        16,
                        8,
                        pos -> termtieEntity.getEntityWorld().getBlockState(pos).isIn(BlockTags.LOGS)
                );
                System.out.println(target + " nearest log");

                target.ifPresent(pos -> termtieEntity.getNavigation().startMovingTo(
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        0.4
                ));
            }
        }


    }

}
