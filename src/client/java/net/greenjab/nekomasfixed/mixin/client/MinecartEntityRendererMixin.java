package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.entity.state.CustomMinecartEntityRenderState;
import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.client.render.entity.state.MinecartEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartEntityRenderer.class)
public class MinecartEntityRendererMixin {
    @Inject(method = "createRenderState()Lnet/minecraft/client/render/entity/state/MinecartEntityRenderState;", at = @At(value = "HEAD"), cancellable = true)
    private static void useCustomeMinecartRenderState(CallbackInfoReturnable<MinecartEntityRenderState> cir) {
        cir.setReturnValue(new CustomMinecartEntityRenderState());
    }
}