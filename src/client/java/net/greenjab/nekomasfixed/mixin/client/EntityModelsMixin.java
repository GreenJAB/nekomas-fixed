package net.greenjab.nekomasfixed.mixin.client;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.entity.model.BasePlateEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.BigBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.HugeBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyEntityModel;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityModels.class)
public class EntityModelsMixin {

    @Inject(method = "getModels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/SignBlockEntityRenderer;getTexturedModelData(Z)Lnet/minecraft/client/model/TexturedModelData;", ordinal = 0))
    private static void addClamModel(CallbackInfoReturnable<Map<EntityModelLayer, TexturedModelData>> cir, @Local ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder) {
        builder.put(EntityModelLayerRegistry.CLAM, ClamBlockModel.getTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_ACACIA_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_BAMBOO_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_BIRCH_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_CHERRY_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_DARK_OAK_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_JUNGLE_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_MANGROVE_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_OAK_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_PALE_OAK_BOAT, BigBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIG_SPRUCE_BOAT, BigBoatEntityModel.getChestTexturedModelData());

        builder.put(EntityModelLayerRegistry.HUGE_ACACIA_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_BAMBOO_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_BIRCH_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_CHERRY_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_DARK_OAK_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_JUNGLE_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_MANGROVE_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_OAK_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_PALE_OAK_BOAT, HugeBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.HUGE_SPRUCE_BOAT, HugeBoatEntityModel.getChestTexturedModelData());

        builder.put(EntityModelLayerRegistry.TARGET_DUMMY, TargetDummyEntityModel.getTexturedModelData());
        EquipmentModelData<TexturedModelData> equipmentModelData6 = TargetDummyEntityModel.getEquipmentModelData(new Dilation(1.0F), new Dilation(0.5F));
        EntityModelLayerRegistry.TARGET_DUMMY_EQUIPMENT.addTo(equipmentModelData6, builder);
        builder.put(EntityModelLayerRegistry.TARGET_DUMMY_BASE, BasePlateEntityModel.getTexturedModelData());
    }
}