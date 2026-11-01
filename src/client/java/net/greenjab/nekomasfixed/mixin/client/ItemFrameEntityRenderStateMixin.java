package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.target_access.ItemFrameAccess;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemFrameEntityRenderState.class)
public class ItemFrameEntityRenderStateMixin implements ItemFrameAccess {
    @Unique
    public boolean nekomasfixed$clear;

    @Override
    public boolean nekomasfixed$isClear() {
        return nekomasfixed$clear;
    }

    @Override
    public void nekomasfixed$setClear(boolean clear) {
        nekomasfixed$clear = clear;
    }
}
