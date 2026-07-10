package net.greenjab.nekomasfixed.registry.entity;

import io.netty.buffer.ByteBuf;
import net.greenjab.nekomasfixed.registry.block.TermitehiveBlock;
import net.greenjab.nekomasfixed.registry.block.entity.TermitehiveBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.HollowLogType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.CustomTrackedDataHandlerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.IntFunction;

public class Termite extends Monster {
    public final AnimationState swipeAnimationState = new AnimationState();
    private static final EntityDataAccessor<Termite.State> STATE;
    private BlockPos moundPosition = null;

    public Termite(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EnterMoundGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GoToNearestMound(this, 0.4d, 32));
        this.goalSelector.addGoal(2, new SearchForLogGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.4d));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 0.6F, false));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, State.IDLING);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 2d)
                .add(Attributes.ATTACK_SPEED, 1.6d)
                .add(Attributes.ATTACK_KNOCKBACK, 0.2d)
                .add(Attributes.MOVEMENT_SPEED, 0.4d)
                .add(Attributes.SAFE_FALL_DISTANCE, 2d)
                .add(Attributes.STEP_HEIGHT, 1d);
    }

    @Override
    public boolean doHurtTarget(@NonNull ServerLevel level, @NonNull Entity target) {
        this.setState(State.SWIPING);
        return super.doHurtTarget(level, target);
    }

    @Override
    public void die(@NonNull DamageSource damageSource) {
        super.die(damageSource);
    }

    private void setState(State state) {
        this.entityData.set(STATE, state);
    }

    @Override
    public void onSyncedDataUpdated(@NonNull EntityDataAccessor<?> data) {
        if (STATE.equals(data)) {
            if (this.entityData.get(STATE) == State.SWIPING) {
                this.swipeAnimationState.start(this.tickCount);
            } else {
                this.swipeAnimationState.stop();
            }
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(data);
    }

    boolean canEnterMound() {
        return this.getTarget() == null && this.level().isDarkOutside();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.entityData.get(STATE) == State.SWIPING) {
            if (swipeAnimationState.getTimeInMillis(this.tickCount)>1000) {
                this.setState(State.IDLING);
            }
        }
    }

    public TermitehiveBlockEntity getMound(){
        if (this.getMoundPosition()==null) return null;
        if (this.level().getBlockEntity(this.getMoundPosition()) instanceof TermitehiveBlockEntity blockEntity) return blockEntity;
        return null;
    }

    public BlockPos getMoundPosition() {
        return moundPosition;
    }

    public BlockPos findNearestMound(){
        Optional<BlockPos> blockPos = BlockPos.findClosestMatch(
                this.blockPosition(),
                16,
                8,
                pos -> this.level().getBlockState(pos).is(BlockRegistry.TERMITE_HIVE)
        );
        return blockPos.orElse(null);
    }

    static {
        STATE = SynchedEntityData.defineId(Termite.class, CustomTrackedDataHandlerRegistry.TERMITE_STATE);
    }

    private static class GoToNearestMound extends MoveToBlockGoal {
        private final Termite termite;
        public GoToNearestMound(Termite mob, double speed, int range) {
            super(mob, speed, range);
            this.termite = mob;
        }

        @Override
        protected boolean isValidTarget(@NonNull LevelReader level, @NonNull BlockPos pos) {
            return level.getBlockState(pos).is(BlockRegistry.TERMITE_HIVE);
        }

        @Override
        public boolean canUse() {
            if (this.termite.level().isDarkOutside() && this.termite.getMoundPosition()!=null) {
                BlockState state = this.termite.level().getBlockState(this.termite.getMoundPosition());
                return state.is(BlockRegistry.TERMITE_HIVE) && state.getValue(TermitehiveBlock.TERMITES) < 2;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.isReachedTarget() && this.termite.level().isDarkOutside() && this.termite.getMoundPosition()!=null) {
                BlockState state = this.termite.level().getBlockState(this.termite.getMoundPosition());
                return state.is(BlockRegistry.TERMITE_HIVE) && state.getValue(TermitehiveBlock.TERMITES) < 2;
            }
            return false;
        }

        @Override
        public void start() {
            Optional<BlockPos> target = BlockPos.findClosestMatch(
                    termite.blockPosition(),
                    5, 5,
                    pos -> {
                        BlockState state = termite.level().getBlockState(pos);
                        return state.is(BlockRegistry.TERMITE_HIVE)
                                && state.getValue(TermitehiveBlock.TERMITES) < 2;
                    }
            );

            target.ifPresent(pos -> {
                blockPos = pos;
                termite.getNavigation().moveTo(
                        pos.getX(), pos.getY(), pos.getZ(), 0.4
                );
            });
        }
    }

    private class EnterMoundGoal extends Goal {
        private final Termite termite;
        EnterMoundGoal(Termite mob){
            this.termite = mob;
        }
        @Override
        public boolean canUse() {
            moundPosition = findNearestMound();

            if (moundPosition == null) return false;

            TermitehiveBlockEntity hive = Termite.this.getMound();
            return hive != null
                    && !hive.isFullOfTermites()
                    && Termite.this.canEnterMound();
        }

        @Override
        public void start() {
            termite.getNavigation().moveTo(moundPosition.getX(), moundPosition.getY(), moundPosition.getZ(), 0.4);
        }

        @Override
        public boolean canContinueToUse() {
            if (moundPosition==null) return false;
            TermitehiveBlockEntity hive = Termite.this.getMound();
            if (hive == null || hive.isFullOfTermites()) return false;
            return Termite.this.distanceToSqr(
                    moundPosition.getX() + 0.5,
                    moundPosition.getY() + 0.5,
                    moundPosition.getZ() + 0.5
            ) > 4.0;
        }

        @Override
        public void tick() {
            if (moundPosition==null) return;
            double dist = Termite.this.distanceToSqr(
                    moundPosition.getX() + 0.5,
                    moundPosition.getY() + 0.5,
                    moundPosition.getZ() + 0.5);

            if (dist <= 4.0) {
                TermitehiveBlockEntity hive = Termite.this.getMound();
                if (hive != null ) hive.tryEnterMound(Termite.this);
            }
        }
    }

    private static class SearchForLogGoal extends Goal {
        private final Termite termite;
        private BlockPos targetPos;
        private int running;

        public SearchForLogGoal(Termite termite) {
            this.termite = termite;
        }

        @Override
        public boolean canUse() {
            return termite.getRandom().nextInt(40) == 0
                    && termite.level().isBrightOutside();
        }

        @Override
        public void start() {
            this.running = 0;
            Optional<BlockPos> target = BlockPos.findClosestMatch(
                    termite.blockPosition(),
                    16,
                    8,
                    pos -> termite.level().getBlockState(pos).is(BlockTags.LOGS)
            );

            target.ifPresent(pos -> {
                this.targetPos = pos;
                termite.getNavigation().moveTo(
                        pos.getX(), pos.getY(), pos.getZ(), 0.4
                );
            });
        }

        @Override
        public void tick() {
            if (targetPos == null) return;
            if (termite.blockPosition().closerThan(targetPos, 1.5)) {
                BlockState state = termite.level().getBlockState(targetPos);
                BlockState newState = HollowLogType.getHollowState(state);
                if (newState != Blocks.AIR.defaultBlockState())
                    termite.level().setBlockAndUpdate(
                            targetPos,
                            newState
                    );

                this.stop();
            }
        }

        @Override
        public boolean canContinueToUse() {
            return running<200 && targetPos != null;
        }

        @Override
        public void stop() {
            this.running = -1;
            this.targetPos = null;
        }
    }

    public enum State {
        IDLING(0),
        SWIPING(1);

        public static final IntFunction<State> INDEX_TO_VALUE = ByIdMap.continuous(State::getIndex, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, State> PACKET_CODEC = ByteBufCodecs.idMapper(INDEX_TO_VALUE, State::getIndex);
        private final int index;

        State(final int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
