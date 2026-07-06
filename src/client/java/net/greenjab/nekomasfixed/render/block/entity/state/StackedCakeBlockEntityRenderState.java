package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class StackedCakeBlockEntityRenderState extends BlockEntityRenderState {
    public BlockState LAYER_2_STATE = Blocks.AIR.defaultBlockState();
    public BlockState LAYER_3_STATE = Blocks.AIR.defaultBlockState();
    public BlockState CANDLE_STATE = Blocks.AIR.defaultBlockState();
}
