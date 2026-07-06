package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.EndermanHeadBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class AbstractEndermanHeadBlock extends BaseEntityBlock {
	public static final IntegerProperty POWER = BlockStateProperties.POWER;

	@Override
	public abstract @NonNull MapCodec<? extends AbstractEndermanHeadBlock> codec();

	public AbstractEndermanHeadBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));
	}

	@Override
	public BlockState getStateForPlacement(@NonNull BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(POWER, 0);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWER);
	}

	@Override
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return new EndermanHeadBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, @NonNull BlockState state, @NonNull BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, world.isClientSide()? null: EndermanHeadBlockEntity::tick);
	}

	@Override
	protected int getSignal(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull Direction direction) {
		return state.getValue(POWER);
	}

	@Override
	protected int getDirectSignal(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull Direction direction) {
		return direction == Direction.UP ? state.getSignal(world, pos, direction) : 0;
	}

	@Override
	protected boolean isSignalSource(@NonNull BlockState state) {
		return true;
	}

	public void setPower(Level world, BlockPos pos,BlockState state, int power) {
		state = state.setValue(AbstractEndermanHeadBlock.POWER, power);
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
		if (state.getValue(POWER)>0) {
			this.updateNeighbors(state.setValue(POWER, 0), world, pos);
		}
	}

	@Override
	protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
		return false;
	}
}
