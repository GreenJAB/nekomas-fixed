package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.util.SpottedRenderStateAccess;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.state.SheepRenderState;
import net.minecraft.world.entity.animal.sheep.Sheep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepRenderer.class)
public abstract class SheepRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/animal/sheep/Sheep;Lnet/minecraft/client/renderer/entity/state/SheepRenderState;F)V",
            at = @At("RETURN")
    )
    private void extractSpottedData(Sheep entity, SheepRenderState state, float partialTicks, CallbackInfo ci) {
        SpottedRenderStateAccess stateAccess = (SpottedRenderStateAccess) state;
        SpottedSheepAccess sheepAccess = (SpottedSheepAccess) entity;
        stateAccess.nekomasfixed$setSpottedState(sheepAccess.nekomasfixed$isSpotted());
    }
}