package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidMobRenderer.class)
public class BipedEntityRendererMixin {

    @ModifyExpressionValue(method="extractHumanoidRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;getChargeDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"))
    private static int slingshotFasterPulltime(int original, @Local(argsOnly = true) LivingEntity entity) {
       if (entity.getUseItem().is(ItemRegistry.SLINGSHOT)) {
           return original/2;
       }
       return original;
    }
}
