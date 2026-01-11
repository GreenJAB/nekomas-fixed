package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.command.LabelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LabelCommandRenderer.class)
public class LabelCommandRendererMixin {

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)V"), index = 4)
    private boolean fixLight(boolean shadow, @Local OrderedRenderCommandQueueImpl.LabelCommand labelCommand) {
        if (labelCommand.distanceToCameraSq() == 100.6789){
            return true;
        }
        return shadow;
    }
}