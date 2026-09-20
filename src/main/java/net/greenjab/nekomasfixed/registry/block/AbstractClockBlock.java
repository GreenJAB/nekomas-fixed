package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class AbstractClockBlock extends BaseEntityBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AbstractClockBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    public abstract @NonNull MapCodec<? extends AbstractClockBlock> codec();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(POWERED, ctx.getLevel().hasNeighborSignal(ctx.getClickedPos()));
    }

    @Override
    protected @NonNull ItemInteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof ClockBlockEntity clockBlockEntity && !hand.equals(InteractionHand.OFF_HAND)) {
            clockBlockEntity.setChanged();
            if (state.is(BlockRegistry.CLOCK)) {
                if (stack.is(Items.BELL)) {
                    if (!clockBlockEntity.hasBell()) {
                        clockBlockEntity.setBell(true);
                        stack.shrink(1);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
                if (stack.is(Items.SHEARS)) {
                    if (clockBlockEntity.hasBell()) {
                        clockBlockEntity.setBell(false);
                        clockBlockEntity.setTimer(-60);
                        stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, Items.BELL.getDefaultInstance());
                        itemEntity.setDefaultPickUpDelay();
                        level.addFreshEntity(itemEntity);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
                if (clockBlockEntity.hasBell()) {
                    int timer = clockBlockEntity.getTimer();
                    if (timer < 0) timer = 0;
                    timer += player.isShiftKeyDown() ? 1200 : 100;
                    if (timer > 12000) timer = 12000;
                    clockBlockEntity.setTimer(timer);
                } else {
                    clockBlockEntity.setShowsTime(!clockBlockEntity.getShowsTime());
                }
            } else {
                clockBlockEntity.setShowsTime(!clockBlockEntity.getShowsTime());
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

@Override
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new ClockBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY,
                level.isClientSide() ? ClockBlockEntity::clientTick : ClockBlockEntity::tick);
    }

    @Override
    protected int getSignal(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return direction == Direction.UP ? state.getSignal(level, pos, direction) : 0;
    }

    @Override
    protected boolean isSignalSource(@NonNull BlockState state) {
        return true;
    }

    // Drawn entirely by the block-entity renderer, so the empty block model must not render.
    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public void setPower(Level level, BlockPos pos, BlockState state, boolean power) {
        state = state.setValue(AbstractClockBlock.POWERED, power);
        level.setBlock(pos, state, Block.UPDATE_ALL);
        updateNeighbors(state, level, pos);
    }

    public void updateNeighbors(BlockState state, Level level, BlockPos pos) {
        Direction direction = Direction.DOWN;
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(pos.relative(direction), this);
    }

    @Override
    protected void onRemove(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState newState, boolean movedByPiston) {
        if (!movedByPiston && state.getValue(POWERED)) {
            this.updateNeighbors(state.setValue(POWERED, false), level, pos);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (state.getValue(POWERED)) {
            addParticle(state, level, pos, random);
            addParticle(state, level, pos, random);
            addParticle(state, level, pos, random);
        }
    }

    public void addParticle(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.4;
        double e = pos.getY() + 0.4 + (random.nextDouble() - 0.5) * 0.2;
        double f = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.4;
        level.addParticle(DustParticleOptions.REDSTONE, d, e, f, 0.0, 0.0, 0.0);
    }

    @Override
    protected boolean hasAnalogOutputSignal(@NonNull BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos) {
        return (int) (((level.getDayTime() + 5000) % 12000) / 1000) + 1;
    }

    @Override
    protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
        return false;
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState state, @NonNull Direction direction, @NonNull BlockState neighborState, @NonNull LevelAccessor level, @NonNull BlockPos pos, @NonNull BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canSurvive(state, level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader level, BlockPos pos) {
        return canSupportCenter(level, pos.below(), Direction.UP);
    }
}