package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.entity.model.CustomMinecartEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.AbstractMinecartEntityRenderer;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(AbstractMinecartEntityRenderer.class)
public class AbstractMinecartEntityRendererMixin {

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/client/model/ModelPart;)Lnet/minecraft/client/render/entity/model/MinecartEntityModel;"))
    private static MinecartEntityModel useCustomMinecartModel(ModelPart modelPart) {
        return new CustomMinecartEntityModel(modelPart);
    }
}