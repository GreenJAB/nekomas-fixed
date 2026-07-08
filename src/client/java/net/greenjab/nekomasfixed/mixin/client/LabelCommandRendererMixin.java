package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NameTagFeatureRenderer.class)
public class LabelCommandRendererMixin {

    @ModifyArg(method = "renderTranslucent", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4fc;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V"), index = 4)
    private boolean fixLight(boolean shadow, @Local SubmitNodeStorage.NameTagSubmit labelCommand) {
        if (labelCommand.distanceToCameraSq() == 100.6789){
            return true;
        }
        return shadow;
    }
}