package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.other.ArmorGlint;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.render.other.TintedGlint;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentLayerRenderer.class)
@Environment(EnvType.CLIENT)
public class EquipmentLayerRendererMixin {

    @WrapOperation(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;armorCutoutNoCullGlint(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType armorCutoutNoCullGlint(Identifier texture, Operation<RenderType> original, @Local(argsOnly = true) EquipmentClientInfo.LayerType layerType, @Local(argsOnly = true) ItemStack stack) {
        if(layerType == EquipmentClientInfo.LayerType.WINGS) {
            return original.call(texture);
        }
        ArmorGlint glint = stack.get(ComponentRegistry.ARMOR_GLINT);
        if (glint == null) {
            return original.call(texture);
        }
        int color = glint.color().value().color();
        RenderType tinted = TintedGlint.armorLayer(texture, color);
        return tinted != null ? tinted : original.call(texture);
    }

    @WrapOperation(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;trimmedArmorGlint()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType trimmedArmorGlint(Operation<RenderType> original, @Local(argsOnly = true) EquipmentClientInfo.LayerType layerType, @Local(argsOnly = true) ItemStack stack) {
        if(layerType == EquipmentClientInfo.LayerType.WINGS) {
            return original.call();
        }
        ArmorGlint glint = stack.get(ComponentRegistry.ARMOR_GLINT);
        if (glint == null) {
            return original.call();
        }
        int color = glint.color().value().color();
        RenderType tinted = TintedGlint.trim(color);
        return tinted != null ? tinted : original.call();
    }

    @Unique private static final EquipmentClientInfo turtleArmorModel = createHumanoidOnlyModel("turtle_scute");
    @Unique private static final EquipmentClientInfo netheriteCrownModel = createHumanoidOnlyModel("netherite_crown");
    @Unique private static final EquipmentClientInfo copperCrownModel = createHumanoidOnlyModel("copper_crown");
    @Unique private static final EquipmentClientInfo ironCrownModel = createHumanoidOnlyModel("iron_crown");
    @Unique private static final EquipmentClientInfo goldenCrownModel = createHumanoidOnlyModel("golden_crown");
    @Unique private static final EquipmentClientInfo diamondCrownModel = createHumanoidOnlyModel("diamond_crown");

    @ModifyExpressionValue(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/EquipmentAssetManager;get(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/client/resources/model/EquipmentClientInfo;"
    ))
    private EquipmentClientInfo useNewArmorModel(EquipmentClientInfo original, @Local(argsOnly = true) ResourceKey<EquipmentAsset> equipmentAssetId) {
        if (equipmentAssetId.identifier().toString().toLowerCase().contains("turtle_scute")) return turtleArmorModel;
        if(equipmentAssetId.identifier().toString().toLowerCase().contains("netherite_crown")) return netheriteCrownModel;
        if(equipmentAssetId.identifier().toString().toLowerCase().contains("copper_crown")) return copperCrownModel;
        if(equipmentAssetId.identifier().toString().toLowerCase().contains("iron_crown")) return ironCrownModel;
        if(equipmentAssetId.identifier().toString().toLowerCase().contains("golden_crown")) return goldenCrownModel;
        if(equipmentAssetId.identifier().toString().toLowerCase().contains("diamond_crown")) return diamondCrownModel;
        return original;
    }

    @Unique
    private static EquipmentClientInfo createHumanoidOnlyModel(String id) {
        return EquipmentClientInfo.builder()
                .addHumanoidLayers(Identifier.withDefaultNamespace(id))
                .build();
    }
}
