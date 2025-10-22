package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.OrientationHelper;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class WallGlowTorchBlock extends GlowTorchBlock {
	public static final MapCodec<WallGlowTorchBlock> CODEC = createCodec(WallGlowTorchBlock::new);
	public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty WATERLOGGED = GlowTorchBlock.WATERLOGGED;

	@Override
	public MapCodec<WallGlowTorchBlock> getCodec() {
		return CODEC;
	}

	public WallGlowTorchBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, true));
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return WallTorchBlock.getBoundingShape(state);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return WallTorchBlock.canPlaceAt(world, pos, state.get(FACING));
	}

	@Override
	protected BlockState getStateForNeighborUpdate(
			BlockState state,
			WorldView world,
			ScheduledTickView tickView,
			BlockPos pos,
			Direction direction,
			BlockPos neighborPos,
			BlockState neighborState,
			Random random
	) {
		if ((Boolean)state.get(WATERLOGGED)) {
			tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = Blocks.WALL_TORCH.getPlacementState(ctx);
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		return blockState == null ? null : this.getDefaultState().with(FACING, (Direction)blockState.get(FACING)).with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (world.random.nextInt(4)!=0) return;
		if ((Boolean)state.get(WATERLOGGED)) {
			Direction direction = ((Direction)state.get(FACING)).getOpposite();
			double d = 0.27;
			double e = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * direction.getOffsetX();
			double f = pos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2 + 0.22;
			double g = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * direction.getOffsetZ();
			world.addParticleClient(ParticleTypes.SCRAPE, e, f, g, 0.0, 0.0, 0.0);
		}
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
		builder.add(FACING, WATERLOGGED);
	}

}
