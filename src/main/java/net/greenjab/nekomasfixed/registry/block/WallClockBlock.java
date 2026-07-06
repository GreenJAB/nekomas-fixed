package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

public class WallClockBlock extends AbstractClockBlock {
	public static final MapCodec<WallClockBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				propertiesCodec()
			).apply(instance, WallClockBlock::new)
	);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION = Shapes.rotateHorizontal(Block.boxZ(14.0, 15.0, 16.0));

	@Override
	public @NonNull MapCodec<? extends WallClockBlock> codec() {
		return CODEC;
	}

	public WallClockBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, @NonNull ServerLevel world, @NonNull BlockPos pos, boolean moved) {
		if (!moved && state.getValue(POWERED)) {
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

	public void addParticle(BlockState state,Level world, BlockPos pos, RandomSource random) {
		Direction dir = state.getValue(FACING);
		double d = pos.getX() + 0.5 + (dir.getAxis()==Direction.Axis.Z?(random.nextDouble() - 0.5) * 0.4 : -dir.getStepX()*0.4);
		double e = pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 0.4;
		double f = pos.getZ() + 0.5 + (dir.getAxis()==Direction.Axis.X?(random.nextDouble() - 0.5) * 0.4 : -dir.getStepZ()*0.4);
		world.addParticle(DustParticleOptions.REDSTONE, d, e, f, 0.0, 0.0, 0.0);
	}

	@Override
	protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return SHAPES_BY_DIRECTION.get(state.getValue(FACING));
	}

	@Override
	protected boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader world, BlockPos pos) {
		return canPlaceAt(world, pos, state.getValue(FACING));
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
		return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : state;
	}

	public static boolean canPlaceAt(LevelReader world, BlockPos pos, Direction facing) {
		BlockPos blockPos = pos.relative(facing.getOpposite());
		BlockState blockState = world.getBlockState(blockPos);
		return blockState.isFaceSturdy(world, blockPos, facing);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
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
