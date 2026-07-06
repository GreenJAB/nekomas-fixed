package net.greenjab.nekomasfixed.render.block.entity.state;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class SoupCauldronBlockEntityRenderState extends BlockEntityRenderState {
    public List<ItemStackRenderState> inputItems = Collections.emptyList();
    public int tint;
    public float animationTime;
    public float stirProgress;
}
