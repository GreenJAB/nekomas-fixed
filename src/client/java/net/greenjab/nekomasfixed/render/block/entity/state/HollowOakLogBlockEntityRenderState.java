package net.greenjab.nekomasfixed.render.block.entity.state;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;

public class HollowOakLogBlockEntityRenderState extends BlockEntityRenderState {
    public BlockState blockState;

    public HollowOakLogBlockEntityRenderState() {
        this.blockState = BlockRegistry.BOABAB_LOG.getDefaultState();
    }
}
