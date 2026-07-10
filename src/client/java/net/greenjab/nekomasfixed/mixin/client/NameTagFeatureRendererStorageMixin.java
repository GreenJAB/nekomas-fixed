package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(NameTagFeatureRenderer.Storage.class)
public class NameTagFeatureRendererStorageMixin {

    @ModifyExpressionValue(method = "add", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/state/OptionsRenderState;getBackgroundOpacity(F)F"))
    private float removeNumberParticleBackground(float original, @Local(ordinal = 0, argsOnly = true) double distanceToCameraSq) {
        if (distanceToCameraSq == 100.6789){
            return 0f;
        }
        return original;
    }

    @ModifyArgs(method = "add", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;<init>(Lorg/joml/Matrix4fc;FFLnet/minecraft/network/chat/Component;IIID)V"))
    private void fixLight(Args args, @Local(ordinal = 1, argsOnly = true) int lightCoords) {
        if ((Double)args.get(7) == 100.6789){
            args.set(4, 15728880);
            args.set(5, lightCoords);
        }
    }
}