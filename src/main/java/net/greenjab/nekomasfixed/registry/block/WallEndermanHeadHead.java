package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.OrientationHelper;
import net.minecraft.world.block.WireOrientation;

import java.util.Map;

public class WallEndermanHeadHead extends AbstractEndermanHeadBlock {
	public static final MapCodec<WallEndermanHeadHead> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					createSettingsCodec()
			).apply(instance, WallEndermanHeadHead::new)
	);
	public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
	private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION = VoxelShapes.createHorizontalFacingShapeMap(Block.createCuboidZShape(8.0, 8.0, 16.0));
	private static final Map<Direction, VoxelShape> SHAPES_POWERED_BY_DIRECTION = VoxelShapes.createHorizontalFacingShapeMap(Block.createCuboidZShape(8.0, 13.0,8.0, 16.0));

	@Override
	public MapCodec<? extends WallEndermanHeadHead> getCodec() {
		return CODEC;
	}

	public WallEndermanHeadHead(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		if (!moved && state.get(POWER)>0) {
			this.updateNeighbors(state, world, pos);
		}
	}

	@Override
	protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return direction == state.get(FACING) ? state.getWeakRedstonePower(world, pos, direction) : 0;
	}

	@Override
	public void updateNeighbors(BlockState state, World world, BlockPos pos) {
		Direction direction = state.get(FACING).getOpposite();
		WireOrientation wireOrientation = OrientationHelper.getEmissionOrientation(
				world, direction, Direction.UP
		);
		world.updateNeighborsAlways(pos, this, wireOrientation);
		world.updateNeighborsAlways(pos.offset(direction), this, wireOrientation);
	}


	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(POWER)>0?SHAPES_POWERED_BY_DIRECTION.get(state.get(FACING)):SHAPES_BY_DIRECTION.get(state.get(FACING));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = super.getPlacementState(ctx);
		BlockView blockView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		Direction[] directions = ctx.getPlacementDirections();

		for (Direction direction : directions) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction2 = direction.getOpposite();
				blockState = blockState.with(FACING, direction2);
				if (!blockView.getBlockState(blockPos.offset(direction)).canReplace(ctx)) {
					return blockState;
				}
			}
		}

		return null;
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING);
	}
}
