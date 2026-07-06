package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.CustomBedTextureHolder;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.MessyBedAccessor;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.state.BedRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedRenderer.class)
public class BedBlockEntityRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/BedBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/BedRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
            at = @At("TAIL"))
    private void detectCustomBed(
            BedBlockEntity bedBlockEntity,
            BedRenderState state,
            float tickDelta,
            Vec3 cameraPos,
            ModelFeatureRenderer.CrumblingOverlay overlay,
            CallbackInfo ci) {

        String bed = null;
        if (bedBlockEntity.getBlockState().getBlock() == BlockRegistry.AMBER_BED) bed = "amber";
        else if (bedBlockEntity.getBlockState().getBlock() == BlockRegistry.AQUA_BED) bed = "aqua";
        else if (bedBlockEntity.getBlockState().getBlock() == BlockRegistry.MAROON_BED) bed = "maroon";
        else if (bedBlockEntity.getBlockState().getBlock() == BlockRegistry.INDIGO_BED) bed = "indigo";
        if (bedBlockEntity.getBlockState().getValue(MessyBedAccessor.IS_MESSY)) {
            if (bed==null) bed = bedBlockEntity.getColor().getName();
            bed += "_messy";
        }
        if (bed!=null) ((CustomBedTextureHolder) state).nekomasfixed$setCustomTexture(
                new Material(Sheets.BED_SHEET, NekomasFixed.id("entity/bed/"+bed)));
    }

    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BedRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At(value = "STORE"), ordinal = 0)
    private Material replaceTexture(Material original, BedRenderState state) {
        Material custom = ((CustomBedTextureHolder) state).nekomasfixed$getCustomTexture();
        if (custom != null) return custom;
        return original;
    }
}