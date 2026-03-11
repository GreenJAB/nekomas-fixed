package net.greenjab.nekomasfixed.mixin.client;

import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeadFeatureRenderer.class)
public class HeadFeatureRendererMixin {

    @Inject(method = "translate(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/entity/feature/HeadFeatureRenderer$HeadTransformation;)V",
            at = @At("HEAD"), cancellable = true)
    private static void customTranslate(MatrixStack matrices, HeadFeatureRenderer.HeadTransformation transformation, CallbackInfo cir) {
        System.out.println("[NekomasFixed] translate called with yOffset: " + transformation.yOffset());

        matrices.translate(0.2F, -0.99F + transformation.yOffset() + (15F / 16F), 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.scale(1.99F, -0.99F, -0.99F);

        cir.cancel();
    }


}