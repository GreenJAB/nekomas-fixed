package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.other.BannerEffects;
import net.greenjab.nekomasfixed.target_access.BannerAccess;
import net.minecraft.client.render.block.entity.state.BannerBlockEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BannerBlockEntityRenderState.class)
public class BannerBlockEntityRenderStateMixin implements BannerAccess {
    @Unique
    public BannerEffects nekomasfixed$effects;

    @Override
    public BannerEffects nekomasfixed$getBannerEffects() {
        return nekomasfixed$effects;
    }

    @Override
    public void nekomasfixed$setBannerEffects(BannerEffects effects) {
        nekomasfixed$effects = effects;
    }
}
