package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.target_access.SheepAccess;
import net.greenjab.nekomasfixed.util.SpottedRenderStateAccess;
import net.minecraft.client.render.entity.state.SheepEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SheepEntityRenderState.class)
public abstract class SheepEntityRenderStateMixin implements SheepAccess, SpottedRenderStateAccess {
    @Unique
    private boolean spottedState;
    @Unique
    public boolean nekomasfixed$glowing = false;

    @Override
    public boolean nekomasfixed$isSpottedState() {
        return this.spottedState;
    }

    @Override
    public void nekomasfixed$setSpottedState(boolean spotted) {
        this.spottedState = spotted;
    }

    @Override
    public boolean nekomasfixed$isGlowing() {
        return nekomasfixed$glowing;
    }

    @Override
    public void nekomasfixed$setGlowing(boolean glowing) {
        nekomasfixed$glowing = glowing;
    }

}