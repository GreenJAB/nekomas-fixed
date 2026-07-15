package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(NameTagFeatureRenderer.class)
public class NameTagFeatureRendererMixin {

    @ModifyArgs(method = "prepareText", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/gui/Font;prepareText(Lnet/minecraft/util/FormattedCharSequence;FFIZZI)Lnet/minecraft/client/gui/Font$PreparedText;"))
    private static void fixNametagForNumberParticle(Args args, @Local NameTagFeatureRenderer.Submit nameTag) {
        if (nameTag.lightCoords() == 16516350) {
            args.set(4, true);
            args.set(6, 0);
        }
    }
}