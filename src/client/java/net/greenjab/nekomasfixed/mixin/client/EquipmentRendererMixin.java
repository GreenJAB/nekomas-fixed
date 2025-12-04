package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixedClient;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentRenderer.class)
@Environment(EnvType.CLIENT)
public class EquipmentRendererMixin {

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/equipment/EquipmentModelLoader;get(Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/client/render/entity/equipment/EquipmentModel;"
    ))
    private EquipmentModel useNewArmorModel(EquipmentModel original, @Local(argsOnly = true) RegistryKey<EquipmentAsset> assetKey, @Local(argsOnly = true)
                                            ItemStack stack, @Local(argsOnly = true) EquipmentModel.LayerType layerType) {
        if (assetKey.getValue().toString().toLowerCase().contains("turtle_scute")) return NekomasFixedClient.turtleArmorModel;
        return original;
    }

}
