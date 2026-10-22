package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.item.FlowerCrownItem;
import net.greenjab.nekomasfixed.registry.item.GoatHornHelmetItem;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidMobRenderer.class)
public class HumanoidMobRendererMixin {

    @ModifyExpressionValue(method="extractHumanoidRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;getChargeDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"))
    private static int slingshotFasterPullTime(int original, @Local(argsOnly = true) LivingEntity entity) {
        if (entity.getUseItem().is(ItemRegistry.SLINGSHOT)) {
            return original / 2;
        }
        return original;
    }

    @WrapOperation(method="extractHumanoidRenderState",
            at=@At(value="INVOKE",
                    target="Lnet/minecraft/client/renderer/entity/HumanoidMobRenderer;getEquipmentIfRenderable(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack keepCustomHelmetsInHeadEquipment(LivingEntity entity, EquipmentSlot slot,
                                                              Operation<ItemStack> original) {
        ItemStack result = original.call(entity, slot);
        if (slot == EquipmentSlot.HEAD && result.isEmpty()) {
            ItemStack headItem = entity.getItemBySlot(EquipmentSlot.HEAD);
            Item itemType = headItem.getItem();
            if (itemType instanceof FlowerCrownItem || itemType instanceof GoatHornHelmetItem) {
                return headItem.copy();
            }
        }
        return result;
    }
}
