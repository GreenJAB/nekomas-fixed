package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.target_access.SignAccess;
import net.minecraft.client.render.block.entity.state.SignBlockEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SignBlockEntityRenderState.class)
public class SignBlockEntityRenderStateMixin implements SignAccess {
    @Unique
    private boolean nekomasfixed$hideBackground = false;

    @Override
    public boolean nekomasfixed$isBackgroundHidden() {
        return nekomasfixed$hideBackground;
    }

    @Override
    public void nekomasfixed$setHideBackground(boolean hideBackground) {
        nekomasfixed$hideBackground = hideBackground;
    }
}
