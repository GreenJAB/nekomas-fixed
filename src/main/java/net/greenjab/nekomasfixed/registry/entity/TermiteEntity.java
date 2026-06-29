package net.greenjab.nekomasfixed.registry.entity;

import io.netty.buffer.ByteBuf;
import net.greenjab.nekomasfixed.registry.block.TermitehiveBlock;
import net.greenjab.nekomasfixed.registry.block.entity.TermitehiveBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.HollowLogType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.CustomTrackedDataHandlerRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.IntFunction;

public class TermiteEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swipeAnimationState = new AnimationState();
    private static final TrackedData<TermiteEntity.State> STATE;
    private int swipeStartTick = -1;
    private BlockPos moundPosition = null;

    SearchForLogGoal searchForLogGoal;

    public TermiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new EnterMoundGoal(this));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new GoToNearestMound(this, 0.4d, 32));
        this.searchForLogGoal = new SearchForLogGoal(this);
        this.goalSelector.add(2, this.searchForLogGoal);
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.4d));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 0.6F, false));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(STATE, State.IDLING);
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
        this.setState(State.IDLING);
        this.setState(State.SWIPING);
        this.swipeStartTick = this.age;
        return super.tryAttack(world, target);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.startState(State.IDLING);
        super.onDeath(damageSource);
    }

    private void setState(State state) {
        this.dataTracker.set(STATE, state);
    }


    public void startState(TermiteEntity.State state) {
        switch (state.ordinal()) {
            case 0 -> this.setState(State.IDLING);
            case 1 -> this.setState(State.SWIPING);
        }
    }

    private void stopAnimations(){
        this.idleAnimationState.stop();
        this.swipeAnimationState.stop();
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (STATE.equals(data)) {
            State state = this.dataTracker.get(STATE);

            if (state == State.SWIPING) {
                this.swipeAnimationState.stop();
                this.swipeAnimationState.startIfNotRunning(this.age);
            }

            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }

    boolean canEnterMound() {
        return !this.searchForLogGoal.isRunning() && this.getTarget() == null && this.getEntityWorld().isNight();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.dataTracker.get(STATE) == State.SWIPING) {
            if (this.age - swipeStartTick > 20) {
                this.setState(State.IDLING);
            }
        }
    }

    public TermitehiveBlockEntity getMound(){
        if (this.getMoundPosition()==null) return null;
        if (this.getEntityWorld().getBlockEntity(this.getMoundPosition()) instanceof TermitehiveBlockEntity blockEntity) return blockEntity;
        return null;
    }

    public BlockPos getMoundPosition() {
        return moundPosition;
    }

    public BlockPos findNearestMound(){
        Optional<BlockPos> blockPos = BlockPos.findClosest(
                this.getBlockPos(),
                16,
                8,
                pos -> this.getEntityWorld().getBlockState(pos).isOf(BlockRegistry.TERMITE_HIVE)
        );
        return blockPos.orElse(null);
    }

    static {
        STATE = DataTracker.registerData(TermiteEntity.class, CustomTrackedDataHandlerRegistry.TERMITE_STATE);
    }

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
            if (this.termiteEntity.getEntityWorld().isNight() && this.termiteEntity.getMoundPosition()!=null) {
                BlockState state = this.termiteEntity.getEntityWorld().getBlockState(this.termiteEntity.getMoundPosition());
                return state.isOf(BlockRegistry.TERMITE_HIVE) && state.get(TermitehiveBlock.TERMITES) < 2;
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            if (!this.hasReached() && this.termiteEntity.getEntityWorld().isNight() && this.termiteEntity.getMoundPosition()!=null) {
                BlockState state = this.termiteEntity.getEntityWorld().getBlockState(this.termiteEntity.getMoundPosition());
                return state.isOf(BlockRegistry.TERMITE_HIVE) && state.get(TermitehiveBlock.TERMITES) < 2;
            }
            return false;
        }

        @Override
        public void start() {
            Optional<BlockPos> target = BlockPos.findClosest(
                    termiteEntity.getBlockPos(),
                    5, 5,
                    pos -> {
                        BlockState state = termiteEntity.getEntityWorld().getBlockState(pos);
                        return state.isOf(BlockRegistry.TERMITE_HIVE)
                                && state.get(TermitehiveBlock.TERMITES) < 2;
                    }
            );

            target.ifPresent(pos -> {
                targetPos = pos;
                termiteEntity.getNavigation().startMovingTo(
                        pos.getX(), pos.getY(), pos.getZ(), 0.4
                );
            });
        }
    }

    private class EnterMoundGoal extends Goal {
        private final TermiteEntity termiteEntity;
        EnterMoundGoal(TermiteEntity mob){
            this.termiteEntity = mob;
        }
        @Override
        public boolean canStart() {
            moundPosition = findNearestMound();

            if (moundPosition == null) return false;

            TermitehiveBlockEntity hive = TermiteEntity.this.getMound();
            return hive != null
                    && !hive.isFullOfTermites()
                    && TermiteEntity.this.canEnterMound();
        }

        @Override
        public void start() {
            termiteEntity.getNavigation().startMovingTo(moundPosition.getX(), moundPosition.getY(), moundPosition.getZ(), 0.4);
        }

        @Override
        public boolean shouldContinue() {
            if (moundPosition==null) return false;
            TermitehiveBlockEntity hive = TermiteEntity.this.getMound();
            if (hive == null || hive.isFullOfTermites()) return false;
            return TermiteEntity.this.squaredDistanceTo(
                    moundPosition.getX() + 0.5,
                    moundPosition.getY() + 0.5,
                    moundPosition.getZ() + 0.5
            ) > 4.0;
        }

        @Override
        public void tick() {
            if (moundPosition==null) return;
            double dist = TermiteEntity.this.squaredDistanceTo(
                    moundPosition.getX() + 0.5,
                    moundPosition.getY() + 0.5,
                    moundPosition.getZ() + 0.5);

            if (dist <= 4.0) {
                TermitehiveBlockEntity hive = TermiteEntity.this.getMound();
                if (hive != null ) hive.tryEnterMound(TermiteEntity.this);
            }
        }
    }

    private static class SearchForLogGoal extends Goal {
        private final TermiteEntity termiteEntity;
        private BlockPos targetPos;
        private boolean running;

        public SearchForLogGoal(TermiteEntity termiteEntity) {
            this.termiteEntity = termiteEntity;
        }

        @Override
        public boolean canStart() {
            return termiteEntity.getRandom().nextInt(40) == 0
                    && termiteEntity.getEntityWorld().isDay();
        }

        boolean isRunning() {
            return this.running;
        }

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
                this.targetPos = pos;
                termiteEntity.getNavigation().startMovingTo(
                        pos.getX(), pos.getY(), pos.getZ(), 0.4
                );
            });
        }

        @Override
        public void tick() {
            if (targetPos == null) return;
            if (termiteEntity.getBlockPos().isWithinDistance(targetPos, 1.5)) {
                BlockState state = termiteEntity.getEntityWorld().getBlockState(targetPos);
                BlockState newState = HollowLogType.getHollowState(state);
                if (newState != Blocks.AIR.getDefaultState())
                    termiteEntity.getEntityWorld().setBlockState(
                            targetPos,
                            newState
                    );

                this.stop();
            }
        }

        @Override
        public boolean shouldContinue() {
            return running && targetPos != null;
        }

        @Override
        public void stop() {
            this.running = false;
            this.targetPos = null;
        }
    }

    public enum State {
        IDLING(0),
        SWIPING(1);

        public static final IntFunction<State> INDEX_TO_VALUE = ValueLists.createIndexToValueFunction(State::getIndex, values(), ValueLists.OutOfBoundsHandling.ZERO);
        public static final PacketCodec<ByteBuf, State> PACKET_CODEC = PacketCodecs.indexed(INDEX_TO_VALUE, State::getIndex);
        private final int index;

        State(final int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }

}
