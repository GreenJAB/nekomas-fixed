package net.greenjab.nekomasfixed.registry.entity.Termite;

import io.netty.buffer.ByteBuf;
import net.greenjab.nekomasfixed.registry.block.TermiteHiveBlock;
import net.greenjab.nekomasfixed.registry.block.entity.TermiteHiveBlockEntity;
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
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.IntFunction;

public class TermiteEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState swipeAnimationState = new AnimationState();
    private static final TrackedData<TermiteEntity.State> STATE;
    private int swipeStartTick = -1;
    public Optional<Boolean> isInMound = Optional.of(false);
    private Optional<BlockPos> moundPosition = findNearestMound(this).isEmpty() ? Optional.empty() : findNearestMound(this);

    SearchForLogGoal searchForLogGoal;

    public TermiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new EnterMoundGoal());
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

//    @Override
//    public boolean isAttacking() {
//        this.setState(State.IDLING);
//        return super.isAttacking();
//    }

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

//    @Override
//    public void handleStatus(byte status) {
//        if (status == 10) {
//            this.swipeAnimationState.startIfNotRunning(this.age);
//        } else {
//            super.handleStatus(status);
//        }
//    }

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

        if (nbt.getBoolean("HasMoundPosition", false)) {
            int x = nbt.getInt("MoundX",0);
            int y = nbt.getInt("MoundY",0);
            int z = nbt.getInt("MoundZ",0);
            this.moundPosition = Optional.of(new BlockPos(x, y, z));
        } else {
            this.moundPosition = Optional.empty();
        }
    }

    public void setReachedHome(boolean b){
        this.isInMound = Optional.of(b);
    }

    @Override
    public void tick() {
        super.tick();


        if (this.dataTracker.get(STATE) == State.SWIPING) {
            if (this.age - swipeStartTick > 20) {
                this.setState(State.IDLING);
            }
        }
//        if (this.getEntityWorld().isClient()) {
//
//            if (this.getVelocity().horizontalLengthSquared() > 0.0001) {
//                runAnimationState.startIfNotRunning(this.age);
//                idleAnimationState.stop();
//            } else {
//                idleAnimationState.startIfNotRunning(this.age);
//                runAnimationState.stop();
//            }
//        }
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

    static {
        STATE = DataTracker.registerData(TermiteEntity.class, CustomTrackedDataHandlerRegistry.TERMITE_STATE);
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
            return this.termiteEntity.getEntityWorld().isNight()
                    && this.termiteEntity.getMoundPosition().isPresent()
                    && this.termiteEntity.getEntityWorld().getBlockState(this.termiteEntity.getMoundPosition().get()).get(TermiteHiveBlock.TERMITES) < 2;
        }

        @Override
        public boolean shouldContinue() {
            return !this.hasReached() || this.termiteEntity.getEntityWorld().getBlockState(this.termiteEntity.getMoundPosition().get()).get(TermiteHiveBlock.TERMITES) == 2;
        }

        @Override
        public void start() {
            Optional<BlockPos> target = BlockPos.findClosest(
                    termiteEntity.getBlockPos(),
                    16,
                    8,
                    pos -> termiteEntity.getEntityWorld().getBlockState(pos).isOf(BlockRegistry.TERMITE_HIVE)
            );

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
            TermiteEntity.this.moundPosition = findNearestMound(TermiteEntity.this);
            if (TermiteEntity.this.moundPosition.isPresent()
                    && TermiteEntity.this.canEnterMound()
                    && TermiteEntity.this.squaredDistanceTo(
                    TermiteEntity.this.moundPosition.get().getX() + 2f,
                    TermiteEntity.this.moundPosition.get().getY() + 2f,
                    TermiteEntity.this.moundPosition.get().getZ() + 2f
            ) < 4.0 ) {
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

        @Override
        public boolean canStart() {
            return canTermiteStart();
        }

        @Override
        public void start() {
            TermiteHiveBlockEntity hive = TermiteEntity.this.getMound();

            if (hive != null && !hive.isFullOfTermites()) {
                if (hive.tryEnterMound(TermiteEntity.this)) {
                    TermiteEntity.this.discard();
                }
            }
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
            return termiteEntity.getRandom().nextInt(40) == 0
                    && !termiteEntity.isInMound.get()
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

    public static enum State {
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
