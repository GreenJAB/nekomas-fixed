package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentRenderer.class)
@Environment(EnvType.CLIENT)
public class EquipmentRendererMixin {

    @Unique private static final EquipmentModel turtleArmorModel = createHumanoidOnlyModel("turtle_scute");
    @Unique private static final EquipmentModel netheriteCrownModel = createHumanoidOnlyModel("netherite_crown");
    @Unique private static final EquipmentModel copperCrownModel = createHumanoidOnlyModel("copper_crown");
    @Unique private static final EquipmentModel ironCrownModel = createHumanoidOnlyModel("iron_crown");
    @Unique private static final EquipmentModel goldenCrownModel = createHumanoidOnlyModel("golden_crown");
    @Unique private static final EquipmentModel diamondCrownModel = createHumanoidOnlyModel("diamond_crown");

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/equipment/EquipmentModelLoader;get(Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/client/render/entity/equipment/EquipmentModel;"
    ))
    private EquipmentModel useNewArmorModel(EquipmentModel original, @Local(argsOnly = true) RegistryKey<EquipmentAsset> assetKey) {
        if (assetKey.getValue().toString().toLowerCase().contains("turtle_scute")) return turtleArmorModel;
        if(assetKey.getValue().toString().toLowerCase().contains("netherite_crown")) return netheriteCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("copper_crown")) return copperCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("iron_crown")) return ironCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("golden_crown")) return goldenCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("diamond_crown")) return diamondCrownModel;
        return original;
    }

    @Unique
    private static EquipmentModel createHumanoidOnlyModel(String id) {
        return EquipmentModel.builder()
                .addHumanoidLayers(Identifier.ofVanilla(id))
                .build();
    }

}
