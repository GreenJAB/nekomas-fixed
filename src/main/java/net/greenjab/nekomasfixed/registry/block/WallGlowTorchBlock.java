package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class WallGlowTorchBlock extends GlowTorchBlock {
	public static final MapCodec<WallGlowTorchBlock> CODEC = simpleCodec(WallGlowTorchBlock::new);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = GlowTorchBlock.WATERLOGGED;

	@Override
	public @NonNull MapCodec<WallGlowTorchBlock> codec() {
		return CODEC;
	}

	public WallGlowTorchBlock(BlockBehaviour.Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, true));
	}

	@Override
	protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return WallTorchBlock.getShape(state);
	}

	@Override
	protected boolean canSurvive(BlockState state, @NonNull LevelReader world, @NonNull BlockPos pos) {
		return WallTorchBlock.canSurvive(world, pos, state.getValue(FACING));
	}

	@Override
	protected @NonNull BlockState updateShape(
            BlockState state,
            @NonNull LevelReader world,
            @NonNull ScheduledTickAccess tickView,
            @NonNull BlockPos pos,
            @NonNull Direction direction,
            @NonNull BlockPos neighborPos,
            @NonNull BlockState neighborState,
            @NonNull RandomSource random
	) {
		if (state.getValue(WATERLOGGED)) {
			tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : state;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState blockState = Blocks.WALL_TORCH.getStateForPlacement(ctx);
		FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
		return blockState == null ? null : this.defaultBlockState().setValue(FACING, blockState.getValue(FACING)).setValue(WATERLOGGED, fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
	}

	@Override
	public void animateTick(@NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull RandomSource random) {
		if (world.getRandom().nextInt(4)!=0) return;
		if (state.getValue(WATERLOGGED)) {
			Direction direction = (state.getValue(FACING)).getOpposite();
			double e = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * direction.getStepX();
			double f = pos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2 + 0.22;
			double g = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * direction.getStepZ();
			world.addParticle(ParticleTypes.SCRAPE, e, f, g, 0.0, 0.0, 0.0);
		}
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
		builder.add(FACING, WATERLOGGED);
	}

}
