package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HollowLogBlockEntityRenderState extends BlockEntityRenderState {
    //public BlockState blockState = Blocks.AIR.defaultBlockState();
    public BlockModelRenderState displayBlockModel = new BlockModelRenderState();
}