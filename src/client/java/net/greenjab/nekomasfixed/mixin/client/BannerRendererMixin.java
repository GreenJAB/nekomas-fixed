package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.client.model.object.banner.BannerModel;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.renderer.blockentity.BannerRenderer.submitPatterns;

@Mixin(value = BannerRenderer.class, priority = 800)
public class BannerRendererMixin {
    @Inject(method = "submitPatternLayer", at = @At("HEAD"), cancellable = true)
    private static <S> void submitPatternLayer(
            SpriteGetter sprites, PoseStack poseStack, OrderedSubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, Model<S> model, S state, SpriteId sprite, DyeColor color, CallbackInfo ci
    ) {
        int diffuseColor = color.getTextureDiffuseColor();

        Identifier id = sprite.texture();
        if(id.getPath().contains("amber")){
            diffuseColor = ModColors.AMBER.getColor();
        }
        else if(id.getPath().contains("aqua")){
            diffuseColor = ModColors.AQUA.getColor();
        }
        else if(id.getPath().contains("maroon")){
            diffuseColor = ModColors.MAROON.getColor();
        }
        else if(id.getPath().contains("indigo")){
            diffuseColor = ModColors.INDIGO.getColor();
        }
        submitNodeCollector.submitModel(
                model, state, poseStack, sprite.renderType(RenderTypes::bannerPattern), lightCoords, overlayCoords, diffuseColor, sprites.get(sprite), 0
        );
        ci.cancel();
    }

    @Inject(method = "submitBanner", at = @At("HEAD"), cancellable = true)
    private static void submitBanner(
            SpriteGetter sprites, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, BannerModel model, BannerFlagModel flagModel, float phase, DyeColor baseColor, BannerPatternLayers patterns, ModelFeatureRenderer.CrumblingOverlay breakProgress, int outlineColor, CallbackInfo ci
    ) {
        SpriteId sprite = Sheets.BANNER_BASE;
        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, lightCoords, overlayCoords, -1, sprite, sprites, outlineColor);
        submitNodeCollector.submitModel(flagModel, phase, poseStack, lightCoords, overlayCoords, -1, sprite, sprites, outlineColor);
        submitPatterns(sprites, poseStack, submitNodeCollector, lightCoords, overlayCoords, flagModel, phase, true, baseColor, patterns);
        ci.cancel();
    }
}
