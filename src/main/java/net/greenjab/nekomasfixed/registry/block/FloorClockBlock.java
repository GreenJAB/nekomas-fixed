package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class FloorClockBlock extends AbstractClockBlock {
	public static final MapCodec<FloorClockBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				createSettingsCodec()
			).apply(instance, FloorClockBlock::new)
	);
	public static final int MAX_ROTATION_INDEX = RotationPropertyHelper.getMax();
	private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
	public static final IntProperty ROTATION = Properties.ROTATION;
	private static final VoxelShape SHAPE = Block.createColumnShape(8.0, 0.0, 8.0);

	@Override
	public MapCodec<? extends FloorClockBlock> getCodec() {
		return CODEC;
	}

	public FloorClockBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(ROTATION, 0));
	}

	/*@Override
	protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return 15;//direction == Direction.UP ? state.getWeakRedstonePower(world, pos, direction) : 0;
	}*/

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw()));
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(ROTATION, rotation.rotate(state.get(ROTATION), MAX_ROTATIONS));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.with(ROTATION, mirror.mirror(state.get(ROTATION), MAX_ROTATIONS));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ROTATION);
	}
}
