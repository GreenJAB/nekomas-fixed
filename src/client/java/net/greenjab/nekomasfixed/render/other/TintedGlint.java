package net.greenjab.nekomasfixed.render.other;

import com.mojang.blaze3d.platform.NativeImage;
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

    private static final Map<Long, Identifier> CACHE = new HashMap<>();
    private record LayerKey(Identifier layerTexture, int color) {}
    private static final Map<LayerKey, RenderType> ARMOR_LAYER_TYPES = new HashMap<>();
    private static final Map<Integer, RenderType> TRIM_TYPES = new HashMap<>();

    private TintedGlint() {}

    public static Identifier texture(boolean armor, int color) {
        long key = ((long) color << 8) | (armor ? 1 : 0);
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }

        Identifier result = build(armor, color);
        CACHE.put(key, result);
        return result;
    }

    public static RenderType armorLayer(Identifier layerTexture, int color) {
        LayerKey key = new LayerKey(layerTexture, color);

        RenderType cached = ARMOR_LAYER_TYPES.get(key);
        if (cached != null) return cached;

        Identifier glintTexture = texture(true, color);
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
                "nekomasfixed_armor_glint_" + Integer.toHexString(color) + "_" + layerTexture.hashCode(),
                setup);

        ARMOR_LAYER_TYPES.put(key, type);
        return type;
    }

    public static RenderType trim(int color) {
        RenderType cached = TRIM_TYPES.get(color);
        if (cached != null) return cached;

        Identifier glintTexture = texture(true, color);
        if (glintTexture == null) return null;

        RenderSetup setup = RenderSetup.builder(RenderPipelines.GLINT)
                .withTexture("Sampler0", glintTexture)
                .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .withForcedSolidModelPhase()
                .createRenderSetup();

        RenderType type = RenderType.create("nekomasfixed_trim_glint_" + Integer.toHexString(color), setup);
        TRIM_TYPES.put(color, type);
        return type;
    }

    private static Identifier build(boolean armor, int color) {
        Minecraft mc = Minecraft.getInstance();

        int colorA = color >>> 24 & 0xFF;
        int colorR = color >> 16 & 0xFF;
        int colorG = color >> 8 & 0xFF;
        int colorB = color & 0xFF;

        try (InputStream in = mc.getResourceManager()
                .getResourceOrThrow(armor ? VANILLA_ARMOR : VANILLA_ITEM).open()) {
            NativeImage image = NativeImage.read(in);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getPixel(x, y);

                    int brightness = Math.max(pixel >> 16 & 0xFF,
                            Math.max(pixel >> 8 & 0xFF, pixel & 0xFF));

                    int a = (pixel >>> 24 & 0xFF) * colorA / 255;
                    int r = brightness * colorR / 255;
                    int g = brightness * colorG / 255;
                    int b = brightness * colorB / 255;


                    image.setPixel(x, y, a << 24 | r << 16 | g << 8 | b);
                }
            }

            Identifier id = Identifier.fromNamespaceAndPath("nekomasfixed",
                    "tinted_glint/" + (armor ? "armor_" : "item_") + Integer.toHexString(color));
            mc.getTextureManager().register(id,
                    new DynamicTexture(() -> "nekomasfixed tinted glint", image));
            return id;
        } catch (Exception e) {
            LOGGER.warn("Could not build tinted glint texture for color {}", Integer.toHexString(color), e);
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
    }
}