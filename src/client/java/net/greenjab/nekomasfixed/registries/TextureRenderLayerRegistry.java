package net.greenjab.nekomasfixed.registries;

import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class TextureRenderLayerRegistry {
    public static Map<DyeColor, SpriteIdentifier> MESSY_BED_TEXTURES = new HashMap<>();

    public static final SpriteIdentifier WHITE_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/white_messy")
    );

    public static final SpriteIdentifier ORANGE_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/orange_messy")
    );

    public static final SpriteIdentifier MAGENTA_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/magenta_messy")
    );

    public static final SpriteIdentifier LIGHT_BLUE_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/light_blue_messy")
    );

    public static final SpriteIdentifier YELLOW_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/yellow_messy")
    );

    public static final SpriteIdentifier LIME_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/lime_messy")
    );

    public static final SpriteIdentifier PINK_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/pink_messy")
    );

    public static final SpriteIdentifier GRAY_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/gray_messy")
    );

    public static final SpriteIdentifier LIGHT_GRAY_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/light_gray_messy")
    );

    public static final SpriteIdentifier CYAN_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/cyan_messy")
    );

    public static final SpriteIdentifier PURPLE_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/purple_messy")
    );

    public static final SpriteIdentifier BLUE_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/blue_messy")
    );

    public static final SpriteIdentifier BROWN_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/brown_messy")
    );

    public static final SpriteIdentifier GREEN_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/green_messy")
    );

    public static final SpriteIdentifier RED_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/red_messy")
    );

    public static final SpriteIdentifier BLACK_MESSY_BED = new SpriteIdentifier(
            TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
            Identifier.of("nekomasfixed", "entity/bed/black_messy")
    );
    public static void init(){
        System.out.println("Registering Mod Texture Render Layers");
    }

    static{
        MESSY_BED_TEXTURES.put(DyeColor.WHITE, WHITE_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.ORANGE, ORANGE_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.MAGENTA, MAGENTA_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.LIGHT_BLUE, LIGHT_BLUE_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.YELLOW, YELLOW_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.LIME, LIME_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.PINK, PINK_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.GRAY, GRAY_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.LIGHT_GRAY, LIGHT_GRAY_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.CYAN, CYAN_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.PURPLE, PURPLE_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.BLUE, BLUE_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.BROWN, BROWN_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.GREEN, GREEN_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.RED, RED_MESSY_BED);
        MESSY_BED_TEXTURES.put(DyeColor.BLACK, BLACK_MESSY_BED);
    }
}
