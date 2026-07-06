package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class WallEndermanHeadHead extends AbstractEndermanHeadBlock {
	public static final MapCodec<WallEndermanHeadHead> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					propertiesCodec()
			).apply(instance, WallEndermanHeadHead::new)
	);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION = Shapes.rotateHorizontal(Block.boxZ(8.0, 8.0, 16.0));
	private static final Map<Direction, VoxelShape> SHAPES_POWERED_BY_DIRECTION = Shapes.rotateHorizontal(Block.boxZ(8.0, 13.0,8.0, 16.0));

	@Override
	public @NonNull MapCodec<? extends WallEndermanHeadHead> codec() {
		return CODEC;
	}

	public WallEndermanHeadHead(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, @NonNull ServerLevel world, @NonNull BlockPos pos, boolean moved) {
		if (!moved && state.getValue(POWER)>0) {
			this.updateNeighbors(state, world, pos);
		}
	}

	@Override
	protected int getDirectSignal(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull Direction direction) {
		return direction == state.getValue(FACING) ? state.getSignal(world, pos, direction) : 0;
	}

	@Override
	public void updateNeighbors(BlockState state, Level world, BlockPos pos) {
		Direction direction = state.getValue(FACING).getOpposite();
		Orientation wireOrientation = ExperimentalRedstoneUtils.initialOrientation(
				world, direction, Direction.UP
		);
		world.updateNeighborsAt(pos, this, wireOrientation);
		world.updateNeighborsAt(pos.relative(direction), this, wireOrientation);
	}


	@Override
	protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return state.getValue(POWER)>0?SHAPES_POWERED_BY_DIRECTION.get(state.getValue(FACING)):SHAPES_BY_DIRECTION.get(state.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(@NonNull BlockPlaceContext ctx) {
		BlockState blockState = super.getStateForPlacement(ctx);
		BlockGetter blockView = ctx.getLevel();
		BlockPos blockPos = ctx.getClickedPos();
		Direction[] directions = ctx.getNearestLookingDirections();

		for (Direction direction : directions) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction2 = direction.getOpposite();
				blockState = blockState.setValue(FACING, direction2);
				if (!blockView.getBlockState(blockPos.relative(direction)).canBeReplaced(ctx)) {
					return blockState;
				}
			}
		}

		return null;
	}

	@Override
	protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected @NonNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}
}
