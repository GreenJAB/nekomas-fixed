package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SubmitNodeCollection.class)
public class NameTagFeatureRendererStorageMixin {

    @ModifyArgs(method = "submitNameTag", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/renderer/feature/NameTagFeatureRenderer$Submit;<init>(Lorg/joml/Matrix4fc;FFLnet/minecraft/network/chat/Component;IIILnet/minecraft/client/gui/Font$DisplayMode;)V"))
    private void fixLight(Args args, @Local(ordinal = 1, argsOnly = true) int lightCoords) {
        if (ARGB.red(lightCoords) == 254 && ARGB.green(lightCoords) == 255 && ARGB.blue(lightCoords) == 255) {
            args.set(4, 16516350);
            args.set(5, lightCoords);
        }
    }
}