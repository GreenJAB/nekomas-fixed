package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class GlowTorchBlock extends BaseTorchBlock implements SimpleWaterloggedBlock {
	public static final MapCodec<GlowTorchBlock> CODEC = simpleCodec(GlowTorchBlock::new);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	@Override
	public @NonNull MapCodec<? extends GlowTorchBlock> codec() {
		return CODEC;
	}

	public GlowTorchBlock(BlockBehaviour.Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
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
		return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected @NonNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
		return this.defaultBlockState().setValue(WATERLOGGED, fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
	}

	@Override
	public void animateTick(@NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull RandomSource random) {
		if (world.random.nextInt(4)!=0) return;
		if (state.getValue(WATERLOGGED)) {
			double d = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
			double e = pos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2;
			double f = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
			world.addParticle(ParticleTypes.GLOW, d, e, f, 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
}
