package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.command.LabelCommandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LabelCommandRenderer.Commands.class)
public class LabelCommandRendererCommandMixin {

    @ModifyExpressionValue(method = "add", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getTextBackgroundOpacity(F)F"))
    private float removeNumberParticleBackground(float original, @Local(ordinal = 0, argsOnly = true) double dist) {
        if (dist == 100.6789){
            return 0f;
        }
        return original;
    }

    @ModifyArgs(method = "add", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/render/command/OrderedRenderCommandQueueImpl$LabelCommand;<init>(Lorg/joml/Matrix4f;FFLnet/minecraft/text/Text;IIID)V"))
    private void fixLight(Args args, @Local(ordinal = 1, argsOnly = true) int color) {
        if ((Double)args.get(7) == 100.6789){
            args.set(4, 15728880);
            args.set(5, color);
        }
    }
}