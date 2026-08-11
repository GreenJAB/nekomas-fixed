package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.entity.model.CustomMinecartModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.object.cart.MinecartModel;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(AbstractMinecartRenderer.class)
public class AbstractMinecartEntityRendererMixin {

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/client/model/geom/ModelPart;)Lnet/minecraft/client/model/object/cart/MinecartModel;"))
    private static MinecartModel useCustomMinecartModel(ModelPart root) {
        return new CustomMinecartModel(root);
    }
}