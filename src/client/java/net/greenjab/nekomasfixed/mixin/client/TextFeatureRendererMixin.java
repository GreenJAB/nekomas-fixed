package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.feature.TextFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TextFeatureRenderer.class)
public class TextFeatureRendererMixin {

    @ModifyArgs(
            method = "renderText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;prepareText(Lnet/minecraft/util/FormattedCharSequence;FFIZZI)Lnet/minecraft/client/gui/Font$PreparedText;"
            )
    )
    private static void fixNametagForNumberParticle(
            Args args,
            @Local(argsOnly = true) TextFeatureRenderer.Content.Text content
    ) {
        // content is a public static record, so it resolves cleanly without accessors
        if (content.color() == 16516350) {
            args.set(4, true); // dropShadow = true
            args.set(6, 0);    // backgroundColor = 0
        }
    }
}