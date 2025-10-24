package net.greenjab.nekomasfixed.mixin.client;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.entity.model.MegaBoatEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityModels.class)
public class EntityModelsMixin {

    @Inject(method = "getModels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/SignBlockEntityRenderer;getTexturedModelData(Z)Lnet/minecraft/client/model/TexturedModelData;", ordinal = 0))
    private static void addClamModel(CallbackInfoReturnable<Map<EntityModelLayer, TexturedModelData>> cir, @Local ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder) {
        builder.put(EntityModelLayerRegistry.CLAM, ClamBlockModel.getTexturedModelData());
        builder.put(EntityModelLayerRegistry.ACACIA_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BAMBOO_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.BIRCH_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.CHERRY_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.DARK_OAK_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.JUNGLE_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.MANGROVE_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.OAK_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.PALE_OAK_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
        builder.put(EntityModelLayerRegistry.SPRUCE_MEGA_BOAT, MegaBoatEntityModel.getChestTexturedModelData());
    }
}