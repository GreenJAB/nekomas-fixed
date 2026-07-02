package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;

public class HollowLogBlockEntityRenderState extends BlockEntityRenderState {
    public BlockState blockState = Blocks.AIR.getDefaultState();
}