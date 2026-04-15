package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import org.jspecify.annotations.Nullable;

import static net.minecraft.util.math.Direction.NORTH;

public class GoatHornBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<GoatHornBlock> CODEC = createCodec(GoatHornBlock::new);
    public static final Property<Boolean> WATERLOGGED = Properties.WATERLOGGED;

    public GoatHornBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, NORTH)
                .with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        if(ctx.getWorld().getBlockState(ctx.getBlockPos().down()).isAir() || ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isAir())return null;
        return this.getDefaultState().with(HorizontalFacingBlock.FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
