package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.entity.model.*;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.object.cart.MinecartModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartModel.class)
public class MinecartEntityModelMixin {
    @Inject(method = "createBodyLayer", at = @At(value = "HEAD"), cancellable = true)
    private static void useCustomMinecartModel(CallbackInfoReturnable<LayerDefinition> cir) {
        cir.setReturnValue(CustomMinecartEntityModel.getTexturedModelData());
    }
}