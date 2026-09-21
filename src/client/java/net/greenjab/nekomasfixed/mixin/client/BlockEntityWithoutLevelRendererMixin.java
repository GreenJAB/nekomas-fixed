package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.BannerEffects;
import net.greenjab.nekomasfixed.util.CanvasRenderer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

// renderByItem maps the item's DyeColor to a vanilla prebuilt entity, so the shulker
// icon showed vanilla. Swap in an entity carrying our block so ShulkerBoxRendererMixin
// applies the custom texture.
@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRendererMixin {

    @Unique
    private static final ShulkerBoxBlockEntity AMBER = shulker(DyeColor.YELLOW, BlockRegistry.AMBER_SHULKER_BOX);
    @Unique
    private static final ShulkerBoxBlockEntity AQUA = shulker(DyeColor.LIGHT_BLUE, BlockRegistry.AQUA_SHULKER_BOX);
    @Unique
    private static final ShulkerBoxBlockEntity INDIGO = shulker(DyeColor.MAGENTA, BlockRegistry.INDIGO_SHULKER_BOX);
    @Unique
    private static final ShulkerBoxBlockEntity MAROON = shulker(DyeColor.RED, BlockRegistry.MAROON_SHULKER_BOX);

    @Unique
    private static ShulkerBoxBlockEntity shulker(DyeColor color, Block block) {
        return new ShulkerBoxBlockEntity(color, BlockPos.ZERO, block.defaultBlockState());
    }

    @ModifyVariable(
            method = "renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At("STORE"),
            ordinal = 0
    )
    private BlockEntity nekomasfixed$customAncientShulkerItemEntity(
            BlockEntity blockEntity, ItemStack stack, ItemDisplayContext displayContext,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = stack.getItem();
        if (item instanceof BlockItem blockItem) {
            Block b = blockItem.getBlock();
            if (b == BlockRegistry.AMBER_SHULKER_BOX) return AMBER;
            if (b == BlockRegistry.AQUA_SHULKER_BOX) return AQUA;
            if (b == BlockRegistry.INDIGO_SHULKER_BOX) return INDIGO;
            if (b == BlockRegistry.MAROON_SHULKER_BOX) return MAROON;
        }
        return blockEntity;
    }

    @Redirect(
            method = "renderByItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/BannerRenderer;renderPatterns(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/resources/model/Material;ZLnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/level/block/entity/BannerPatternLayers;Z)V")
    )
    private void renderCanvas(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ModelPart canvas, Material baseSprite, boolean isBanner, DyeColor color, BannerPatternLayers patterns, boolean glint, ItemStack stack) {
        // Render banner on shield
        BannerEffects effects = stack.getOrDefault(ComponentRegistry.BANNER_EFFECTS, BannerEffects.EMPTY);
        if (effects.isBackgroundHidden()) {
            baseSprite = ModelBakery.NO_PATTERN_SHIELD;
        }
        CanvasRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, canvas, canvas, baseSprite, isBanner, color, patterns, glint, effects, false);
    }
}