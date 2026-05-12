package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.entity.model.*;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartEntityModel.class)
public class MinecartEntityModelMixin {
    @Inject(method = "getTexturedModelData", at = @At(value = "HEAD"), cancellable = true)
    private static void addClamModel(CallbackInfoReturnable<TexturedModelData> cir) {
        cir.setReturnValue(CustomMinecartEntityModel.getTexturedModelData());
    }
}