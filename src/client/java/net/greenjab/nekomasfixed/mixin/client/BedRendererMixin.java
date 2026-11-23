package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.MessyBedAccessor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// BedRenderer picks the sheet from the bed's DyeColor (Sheets.BED_TEXTURES), so
// ancient beds would show their representational colour. Route them to the
// custom entity/bed sprite instead.
@Mixin(BedRenderer.class)
public class BedRendererMixin {

    @ModifyVariable(
            method = "render(Lnet/minecraft/world/level/block/entity/BedBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At("STORE"),
            ordinal = 0
    )
    private Material nekomasfixed$swapAncientBedTexture(
            Material material, BedBlockEntity blockEntity, float partialTick,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (material.atlasLocation().equals(Sheets.BED_SHEET)) {
            var state = blockEntity.getBlockState();
            var block = state.getBlock();
            var color = block == BlockRegistry.AMBER_BED ? "amber"
                    : block == BlockRegistry.AQUA_BED ? "aqua"
                    : block == BlockRegistry.INDIGO_BED ? "indigo"
                    : block == BlockRegistry.MAROON_BED ? "maroon"
                    : blockEntity.getColor().getName();
            boolean ancient = block == BlockRegistry.AMBER_BED || block == BlockRegistry.AQUA_BED
                    || block == BlockRegistry.INDIGO_BED || block == BlockRegistry.MAROON_BED;
            if (state.getValue(MessyBedAccessor.MESSY)) {
                return new Material(Sheets.BED_SHEET, NekomasFixed.id("entity/bed/" + color + "_messy"));
            }
            if (ancient) {
                return new Material(Sheets.BED_SHEET, NekomasFixed.id("entity/bed/" + color));
            }
        }
        return material;
    }
}