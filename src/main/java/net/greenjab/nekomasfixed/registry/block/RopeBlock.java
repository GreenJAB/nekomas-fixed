package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;

import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

import java.util.Objects;

public class RopeBlock extends  Block  {
    public static final BooleanProperty IS_CONNECTED = BooleanProperty.of("is_connected");
    public static final BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");

    public RopeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(IS_CONNECTED, false).with(WATERLOGGED, false));
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_CONNECTED, WATERLOGGED);
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        BlockState blockState2 = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if(!blockState.isAir() && !blockState2.get(IS_CONNECTED, true)){
            return blockState2.cycle(IS_CONNECTED);
        }else if(!blockState.isAir() && blockState2.get(IS_CONNECTED, false)){
            return blockState2.with(IS_CONNECTED, true);
        }
        else{
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            boolean bl = fluidState.getFluid() == Fluids.WATER;
            return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, bl);
        }
    }
}
