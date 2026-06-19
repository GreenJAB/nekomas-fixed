package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;

import java.util.Stack;

public class StackedCakeBlockEntityRenderState extends BlockEntityRenderState {
    public Stack<BlockState> STACKED_CAKES = new Stack<>();
    public BlockState CANDLE_STATE = Blocks.AIR.getDefaultState();
}
