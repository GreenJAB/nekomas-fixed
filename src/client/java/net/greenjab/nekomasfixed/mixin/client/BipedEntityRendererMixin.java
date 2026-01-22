package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BipedEntityRenderer.class)
public class BipedEntityRendererMixin {

    @ModifyExpressionValue(method="updateBipedRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;getPullTime(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)I"))
    private static int slingshotFasterPulltime(int original, @Local(argsOnly = true) LivingEntity entity) {
       if (entity.getActiveItem().isOf(ItemRegistry.SLINGSHOT)) {
           return original/2;
       }
       return original;
    }
}
