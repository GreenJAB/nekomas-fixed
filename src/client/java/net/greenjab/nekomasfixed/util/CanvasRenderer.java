package net.greenjab.nekomasfixed.util;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.stream.IntStream;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public class CanvasRenderer {
    public static void renderCanvas(
            PoseStack matrices,
            MultiBufferSource vertexConsumers,
            int light,
            int overlay,
            ModelPart canvas,
            ModelPart patternPart,
            Material baseSprite,
            boolean isBanner,
            DyeColor dye,
            BannerPatternLayers patterns,
            boolean glint,
            BannerEffects effects,
            boolean isWall
    ) {
        if (effects.isBackgroundHidden() && isBanner) {
            canvas.visible = false;
            if (isWall) {
                matrices.translate(0.0F, 0.0F, 0.2F);
            }
        }
        canvas.render(matrices, baseSprite.buffer(vertexConsumers, RenderType::entitySolid, glint), light, overlay, dye.getTextureDiffuseColor());

        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternLayers.Layer layer = patterns.layers().get(i);

            int color = layer.color().getTextureDiffuseColor();
            if (effects.isGlowing()) {
                float[] ch = new float[]{FastColor.ARGB32.red(color) / 256f, FastColor.ARGB32.green(color) / 256f, FastColor.ARGB32.blue(color) / 256f};
                IntStream.range(0, 3).forEach(j -> {
                    ch[j] *= GlobalVariables.COLOR_VALUE_MULTIPLIER;
                    if (ch[j] > 1.0f) {
                        ch[j] = 1.0f;
                    }
                });
                color = FastColor.ARGB32.colorFromFloat(1.0f, ch[0], ch[1], ch[2]);
            }

            Material spriteIdentifier = isBanner ? Sheets.getBannerMaterial(layer.pattern()) : Sheets.getShieldMaterial(layer.pattern());
            patternPart.render(matrices, spriteIdentifier.buffer(vertexConsumers, RenderType::itemEntityTranslucentCull), effects.isGlowing() ? GlobalVariables.GLOW_STRENGTH : light, overlay, color);
        }
    }
}
