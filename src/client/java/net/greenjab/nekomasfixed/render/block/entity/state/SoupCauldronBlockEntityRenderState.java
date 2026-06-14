package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

import java.util.Collections;
import java.util.List;

public class SoupCauldronBlockEntityRenderState extends BlockEntityRenderState {
    public List<ItemRenderState> inputItems = Collections.emptyList();
    public float animationTime;
    public float stirProgress;
    public boolean hasStirred = false;

}
