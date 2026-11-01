package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.target_access.SheepAccess;
import net.greenjab.nekomasfixed.util.SpottedRenderStateAccess;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.client.render.entity.state.SheepEntityRenderState;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntityRenderer.class)
public abstract class SheepEntityRendererMixin {

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/passive/SheepEntity;Lnet/minecraft/client/render/entity/state/SheepEntityRenderState;F)V",
            at = @At("RETURN")
    )
    private void updateRenderState(SheepEntity sheep, SheepEntityRenderState state, float tickDelta, CallbackInfo ci) {
        ((SpottedRenderStateAccess) state).nekomasfixed$setSpottedState(((SpottedSheepAccess) sheep).nekomasfixed$isSpotted());
        ((SheepAccess)state).nekomasfixed$setGlowing(((SheepAccess)sheep).nekomasfixed$isGlowing());
    }
}