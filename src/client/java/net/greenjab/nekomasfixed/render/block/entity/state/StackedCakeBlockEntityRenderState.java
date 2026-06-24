package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;

import java.util.Stack;

public class StackedCakeBlockEntityRenderState extends BlockEntityRenderState {
    public BlockState LAYER_2_STATE = Blocks.AIR.getDefaultState();
    public BlockState LAYER_3_STATE = Blocks.AIR.getDefaultState();
    public BlockState CANDLE_STATE = Blocks.AIR.getDefaultState();
}
