package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class StackedCakeBlockEntityRenderState extends BlockEntityRenderState {
    public BlockModelRenderState displayBlockModel_layer_2 = new BlockModelRenderState();
    public BlockModelRenderState displayBlockModel_layer_3 = new BlockModelRenderState();
    public BlockModelRenderState displayBlockModel_candle = new BlockModelRenderState();
}
