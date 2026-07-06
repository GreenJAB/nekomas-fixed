package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class FloorEndermanHeadHead extends AbstractEndermanHeadBlock {
	public static final MapCodec<FloorEndermanHeadHead> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					propertiesCodec()
			).apply(instance, FloorEndermanHeadHead::new)
	);
	public static final int MAX_ROTATION_INDEX = RotationSegment.getMaxSegmentIndex();
	private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	private static final VoxelShape SHAPE = Block.column(8.0, 0.0, 8.0);
	private static final VoxelShape SHAPE_POWERED = Block.column(8.0, 0.0, 13.0);

	@Override
	public @NonNull MapCodec<? extends FloorEndermanHeadHead> codec() {
		return CODEC;
	}

	public FloorEndermanHeadHead(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(ROTATION, 0));
	}

	@Override
	protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return state.getValue(POWER)>0?SHAPE_POWERED:SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(@NonNull BlockPlaceContext ctx) {
		return super.getStateForPlacement(ctx).setValue(ROTATION, RotationSegment.convertToSegment(ctx.getRotation()));
	}

	@Override
	protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), MAX_ROTATIONS));
	}

	@Override
	protected @NonNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), MAX_ROTATIONS));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ROTATION);
	}
}