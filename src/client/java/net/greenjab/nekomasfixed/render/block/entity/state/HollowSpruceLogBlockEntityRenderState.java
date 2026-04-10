package net.greenjab.nekomasfixed.render.block.entity.state;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;

public class HollowSpruceLogBlockEntityRenderState extends BlockEntityRenderState {
    public BlockState blockState;

    public HollowSpruceLogBlockEntityRenderState() {
        this.blockState = Blocks.AIR.getDefaultState();
    }
}