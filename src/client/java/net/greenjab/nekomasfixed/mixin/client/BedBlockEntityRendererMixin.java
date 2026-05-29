package net.greenjab.nekomasfixed.mixin.client;


import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.CustomBedTextureHolder;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BedBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedBlockEntityRenderer.class)
public class BedBlockEntityRendererMixin {

    @Inject(method = "updateRenderState(Lnet/minecraft/block/entity/BedBlockEntity;Lnet/minecraft/client/render/block/entity/state/BedBlockEntityRenderState;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V", at = @At("TAIL"))
    private void detectCustomBed(
            BedBlockEntity bedBlockEntity,
            BedBlockEntityRenderState state,
            float tickDelta,
            Vec3d cameraPos,
            ModelCommandRenderer.CrumblingOverlayCommand overlay,
            CallbackInfo ci) {

        if (bedBlockEntity.getCachedState().getBlock() == BlockRegistry.AMBER_BED) {

            ((CustomBedTextureHolder) state).nekomasfixed$setCustomTexture(
                    new SpriteIdentifier(
                            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
                            NekomasFixed.id("entity/bed/amber")
                    )
            );
        }
        if (bedBlockEntity.getCachedState().getBlock() == BlockRegistry.AQUA_BED) {

            ((CustomBedTextureHolder) state).nekomasfixed$setCustomTexture(
                    new SpriteIdentifier(
                            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
                            NekomasFixed.id("entity/bed/aqua")
                    )
            );
        }
        if (bedBlockEntity.getCachedState().getBlock() == BlockRegistry.CRIMSON_BED) {

            ((CustomBedTextureHolder) state).nekomasfixed$setCustomTexture(
                    new SpriteIdentifier(
                            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
                            NekomasFixed.id("entity/bed/crimson")
                    )
            );
        }
        if (bedBlockEntity.getCachedState().getBlock() == BlockRegistry.INDIGO_BED) {
            ((CustomBedTextureHolder) state).nekomasfixed$setCustomTexture(
                    new SpriteIdentifier(
                            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
                            NekomasFixed.id("entity/bed/indigo")
                    )
            );
        }

    }

    @ModifyVariable(
            method = "render(Lnet/minecraft/client/render/block/entity/state/BedBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private SpriteIdentifier replaceTexture(SpriteIdentifier original, BedBlockEntityRenderState state) {

        SpriteIdentifier custom =
                ((CustomBedTextureHolder) state).nekomasfixed$getCustomTexture();

        if (custom != null) {
            return custom;
        }

        return original;
    }
}