package net.greenjab.nekomasfixed.mixin.client;


import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BedBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedBlockEntityRenderer.class)
public class BedBlockEntityRendererMixin {

    @Inject(method = "updateRenderState", at = @At("TAIL"))
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
                            Identifier.of("nekomasfixed","entity/bed/amber")
                    )
            );
        }
    }
}