package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.CustomBedTextureHolder;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.MessyBedAccessor;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.state.BedRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedRenderer.class)
public class BedRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/BedBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/BedRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
            at = @At("TAIL"))
    private void detectCustomBed(
            BedBlockEntity blockEntity,
            BedRenderState state,
            float partialTicks,
            Vec3 cameraPosition,
            ModelFeatureRenderer.CrumblingOverlay breakProgress,
            CallbackInfo ci) {

        String bed = null;
        if (blockEntity.getBlockState().getBlock() == BlockRegistry.AMBER_BED) bed = "amber";
        else if (blockEntity.getBlockState().getBlock() == BlockRegistry.AQUA_BED) bed = "aqua";
        else if (blockEntity.getBlockState().getBlock() == BlockRegistry.MAROON_BED) bed = "maroon";
        else if (blockEntity.getBlockState().getBlock() == BlockRegistry.INDIGO_BED) bed = "indigo";
        if (blockEntity.getBlockState().getValue(MessyBedAccessor.IS_MESSY)) {
            if (bed==null) bed = blockEntity.getColor().getName();
            bed += "_messy";
        }
        if (bed!=null) ((CustomBedTextureHolder) state).nekomasfixed$setCustomTexture(
                new SpriteId(Sheets.BED_SHEET, NekomasFixed.id("entity/bed/"+bed)));
    }

    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BedRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(value = "STORE"), ordinal = 0)
    private SpriteId replaceTexture(SpriteId sprite, BedRenderState state) {
        SpriteId custom = ((CustomBedTextureHolder) state).nekomasfixed$getCustomTexture();
        if (custom != null) return custom;
        return sprite;
    }
}