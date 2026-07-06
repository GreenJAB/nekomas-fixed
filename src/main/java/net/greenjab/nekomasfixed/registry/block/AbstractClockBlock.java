package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class AbstractClockBlock extends BaseEntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	@Override
	public abstract @NonNull MapCodec<? extends AbstractClockBlock> codec();

	public AbstractClockBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(POWERED, ctx.getLevel().hasNeighborSignal(ctx.getClickedPos()));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

	@Override
	protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof ClockBlockEntity clockBlockEntity && !hand.equals(InteractionHand.OFF_HAND)) {
			clockBlockEntity.setChanged();
			if (state.is(BlockRegistry.CLOCK)){
				if (stack.is(Items.BELL)) {
					if (!clockBlockEntity.hasBell()) {
						clockBlockEntity.setBell(true);
						stack.shrink(1);
					}
					return InteractionResult.SUCCESS;
				}
				if (stack.is(Items.SHEARS)) {
					if (clockBlockEntity.hasBell()) {
						clockBlockEntity.setBell(false);
						clockBlockEntity.setTimer(-60);
						stack.hurtWithoutBreaking(1, player);
						ItemEntity itemEntity = new ItemEntity(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5 , Items.BELL.getDefaultInstance());
						itemEntity.setDefaultPickUpDelay();
						world.addFreshEntity(itemEntity);
					}
					return InteractionResult.SUCCESS;
				}
				if (clockBlockEntity.hasBell()) {
					int timer = clockBlockEntity.getTimer();
					if (timer < 0) timer = 0;
					timer += player.isShiftKeyDown() ? 1200 : 100;
					if (timer > 12000) timer = 12000;
					clockBlockEntity.setTimer(timer);
					return InteractionResult.SUCCESS;
				} else {
					clockBlockEntity.setShowsTime(!clockBlockEntity.getShowsTime());
				}
			} else {
				clockBlockEntity.setShowsTime(!clockBlockEntity.getShowsTime());
			}
			return InteractionResult.PASS;
		}
		return InteractionResult.PASS;

	}

	@Override
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return new ClockBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, @NonNull BlockState state, @NonNull BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, world.isClientSide()?ClockBlockEntity::clientTick:ClockBlockEntity::tick);
	}

	@Override
	protected int getSignal(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull Direction direction) {
		return state.getValue(POWERED) ? 15 : 0;
	}

	@Override
	protected int getDirectSignal(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull Direction direction) {
		return direction == Direction.UP ? state.getSignal(world, pos, direction) : 0;
	}

	@Override
	protected boolean isSignalSource(@NonNull BlockState state) {
		return true;
	}

	public void setPower(Level world, BlockPos pos,BlockState state,boolean power) {
		state = state.setValue(AbstractClockBlock.POWERED, power);
		world.setBlock(pos, state, Block.UPDATE_ALL);
		updateNeighbors(state, world, pos);
	}
	public void updateNeighbors(BlockState state, Level world, BlockPos pos) {
		Direction direction = Direction.DOWN;
		Orientation wireOrientation = ExperimentalRedstoneUtils.initialOrientation(
				world, direction, Direction.UP
		);
		world.updateNeighborsAt(pos, this, wireOrientation);
		world.updateNeighborsAt(pos.relative(direction), this, wireOrientation);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, @NonNull ServerLevel world, @NonNull BlockPos pos, boolean moved) {
		if (state.getValue(POWERED)) {
			this.updateNeighbors(state.setValue(POWERED, false), world, pos);
		}
	}

	@Override
	public void animateTick(BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull RandomSource random) {
		if (state.getValue(POWERED)) {
			addParticle(state, world, pos, random);
			addParticle(state, world, pos, random);
			addParticle(state, world, pos, random);
		}
	}

	public void addParticle(BlockState state, Level world, BlockPos pos, RandomSource random) {
		double d = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.4;
		double e = pos.getY() + 0.4 + (random.nextDouble() - 0.5) * 0.2;
		double f = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.4;
		world.addParticle(DustParticleOptions.REDSTONE, d, e, f, 0.0, 0.0, 0.0);
	}

	@Override
	protected boolean hasAnalogOutputSignal(@NonNull BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(@NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull Direction direction) {
		return (int)(((world.getDayTime()+5000)%12000)/1000)+1;
	}

	@Override
	protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
		return false;
	}

	@Override
	protected @NonNull BlockState updateShape(
            @NonNull BlockState state,
            @NonNull LevelReader world,
            @NonNull ScheduledTickAccess tickView,
            @NonNull BlockPos pos,
            @NonNull Direction direction,
            @NonNull BlockPos neighborPos,
            @NonNull BlockState neighborState,
            @NonNull RandomSource random
	) {
		return direction == Direction.DOWN && !this.canSurvive(state, world, pos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader world, BlockPos pos) {
		return canSupportCenter(world, pos.below(), Direction.UP);
	}
}
