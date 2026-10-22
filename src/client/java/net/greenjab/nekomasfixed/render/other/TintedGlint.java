/*
 * The glint texture tinting in build() is adapted from Glint Everything(26.2)
 * (https://github.com/dev-limucc/glint-everything) by Limucc-dev, used under the MIT License:
 *
 * Copyright (c) 2026 Limucc-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.greenjab.nekomasfixed.render.other;

import com.mojang.blaze3d.platform.NativeImage;
import net.greenjab.nekomasfixed.util.GlintColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.TextureTransform;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class TintedGlint {
    private static final Logger LOGGER = LoggerFactory.getLogger("nekomasfixed/TintedGlint");

    private static final Identifier VANILLA_ITEM = Identifier.withDefaultNamespace("textures/misc/enchanted_glint_item.png");
    private static final Identifier VANILLA_ARMOR = Identifier.withDefaultNamespace("textures/misc/enchanted_glint_armor.png");

    private record TexKey(boolean armor, int color, Identifier base) {}
    private static final Map<TexKey, Identifier> CACHE = new HashMap<>();

    private record LayerKey(Identifier layerTexture, int color, Identifier base) {}
    private static final Map<LayerKey, RenderType> ARMOR_LAYER_TYPES = new HashMap<>();
    private static final Map<TexKey, RenderType> TRIM_TYPES = new HashMap<>();
    private record ItemKey(boolean translucent, boolean special, Identifier atlas, int color, Identifier base) {}
    private static final Map<ItemKey, RenderType> ITEM_TYPES = new HashMap<>();

    // Gamma
    private static final double GAMMA = 0.75;
    // Glint Strength
    private static final float GAIN = 1.2f;

    private TintedGlint() {}

    private static String baseTag(Identifier base) {
        return base == null ? "" : "_" + Integer.toHexString(base.hashCode());
    }

    public static RenderType itemGlint(boolean translucent, boolean special, Identifier atlas, int color, Identifier base) {
        ItemKey key = new ItemKey(translucent, special, atlas, color, base);
        RenderType cached = ITEM_TYPES.get(key);
        if (cached != null) return cached;

        Identifier glintTexture = texture(false, color, base);
        if (glintTexture == null) return null;

        RenderSetup.RenderSetupBuilder builder;
        if (translucent) {
            builder = RenderSetup.builder(special
                    ? RenderPipelines.ITEM_TRANSLUCENT_GLINT_SPECIAL
                    : RenderPipelines.ITEM_TRANSLUCENT_GLINT);
            builder.setOitPipelines(special
                    ? RenderPipelines.OIT_ITEM_GLINT_SPECIAL
                    : RenderPipelines.OIT_ITEM_GLINT);
            builder.sortOnUpload();
        } else {
            builder = RenderSetup.builder(special
                    ? RenderPipelines.ITEM_CUTOUT_GLINT_SPECIAL
                    : RenderPipelines.ITEM_CUTOUT_GLINT);
        }

        RenderSetup setup = builder
                .withTexture("Sampler0", atlas)
                .withTexture("GlintSampler", glintTexture)
                .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();

        RenderType type = RenderType.create(
                "nekomasfixed_item_glint_"
                        + (translucent ? "t" : "c") + (special ? "s" : "n") + "_"
                        + Integer.toHexString(color) + "_" + atlas.hashCode() + baseTag(base),
                setup);

        ITEM_TYPES.put(key, type);
        return type;
    }

    public static Identifier texture(boolean armor, int color, Identifier base) {
        TexKey key = new TexKey(armor, color, base);
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }

        Identifier result = build(armor, color, base);
        CACHE.put(key, result);
        return result;
    }

    public static RenderType armorLayer(Identifier layerTexture, int color, Identifier base) {
        LayerKey key = new LayerKey(layerTexture, color, base);

        RenderType cached = ARMOR_LAYER_TYPES.get(key);
        if (cached != null) return cached;

        Identifier glintTexture = texture(true, color, base);
        if (glintTexture == null) return null;

        RenderSetup setup = RenderSetup.builder(RenderPipelines.ARMOR_CUTOUT_NO_CULL_GLINT)
                .withTexture("Sampler0", layerTexture)
                .withTexture("GlintSampler", glintTexture)
                .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                .useLightmap()
                .useOverlay()
                .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .affectsCrumbling()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup();

        RenderType type = RenderType.create(
                "nekomasfixed_armor_glint_" + Integer.toHexString(color) + "_" + layerTexture.hashCode() + baseTag(base),
                setup);

        ARMOR_LAYER_TYPES.put(key, type);
        return type;
    }

    public static RenderType trim(int color, Identifier base) {
        TexKey key = new TexKey(true, color, base);
        RenderType cached = TRIM_TYPES.get(key);
        if (cached != null) return cached;

        Identifier glintTexture = texture(true, color, base);
        if (glintTexture == null) return null;

        RenderSetup setup = RenderSetup.builder(RenderPipelines.GLINT)
                .withTexture("Sampler0", glintTexture)
                .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .withForcedSolidModelPhase()
                .createRenderSetup();

        RenderType type = RenderType.create("nekomasfixed_trim_glint_" + Integer.toHexString(color) + baseTag(base), setup);
        TRIM_TYPES.put(key, type);
        return type;
    }

    private static Identifier build(boolean armor, int color, Identifier base) {
        Minecraft mc = Minecraft.getInstance();

        int tint = GlintColors.lift(color, GlintColors.GLINT_FLOOR, GlintColors.GLINT_FLOOR_GRAY);
        int tintA = tint >>> 24 & 0xFF;
        int tintR = tint >> 16 & 0xFF;
        int tintG = tint >> 8 & 0xFF;
        int tintB = tint & 0xFF;

        Identifier source = base != null ? base : (armor ? VANILLA_ARMOR : VANILLA_ITEM);

        try (InputStream in = mc.getResourceManager().getResourceOrThrow(source).open()) {
            NativeImage image = NativeImage.read(in);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getPixel(x, y);
                    int rawBrightness = Math.max(pixel >> 16 & 0xFF,
                            Math.max(pixel >> 8 & 0xFF, pixel & 0xFF));
                    int brightness = Math.min(255,
                            Math.round((float) Math.pow(rawBrightness / 255.0, GAMMA) * 255f * GAIN));

                    int a = (pixel >>> 24 & 0xFF) * tintA / 255;
                    int r = brightness * tintR / 255;
                    int g = brightness * tintG / 255;
                    int b = brightness * tintB / 255;

                    image.setPixel(x, y, a << 24 | r << 16 | g << 8 | b);
                }
            }

            Identifier id = Identifier.fromNamespaceAndPath("nekomasfixed",
                    "tinted_glint/" + (armor ? "armor_" : "item_") + Integer.toHexString(color) + baseTag(base));
            mc.getTextureManager().register(id,
                    new DynamicTexture(() -> "nekomasfixed tinted glint", image));
            return id;
        } catch (Exception e) {
            LOGGER.warn("Could not build tinted glint texture for color {} (base {})", Integer.toHexString(color), base, e);
            return null;
        }
    }

    public static void clearCache() {
        Minecraft mc = Minecraft.getInstance();
        for (Identifier id : CACHE.values()) {
            if (id != null) {
                mc.getTextureManager().release(id);
            }
        }
        CACHE.clear();
        ARMOR_LAYER_TYPES.clear();
        TRIM_TYPES.clear();
        ITEM_TYPES.clear();
    }
}