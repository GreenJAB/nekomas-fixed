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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        boolean connected = world.getBlockState(pos.up()).isOf(this);
        return this.getDefaultState().with(IS_CONNECTED, connected);
    }
}
