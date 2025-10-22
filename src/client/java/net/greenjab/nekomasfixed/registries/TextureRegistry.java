package net.greenjab.nekomasfixed.registries;

import net.fabricmc.fabric.api.client.rendering.v1.AtlasSourceRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.enums.ClamType;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.state.ClamBlockEntityRenderState;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.state.ChestBlockEntityRenderState;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.SpriteMapper;
import net.minecraft.util.Identifier;

import static net.minecraft.client.render.TexturedRenderLayers.CHEST_SPRITE_MAPPER;

public class TextureRegistry {

   public static final SpriteIdentifier CLAM_SPRITE = new SpriteIdentifier(CHEST_SPRITE_MAPPER.sheet(), NekomasFixed.id("clam").withPrefixedPath(CHEST_SPRITE_MAPPER.prefix() + "/"));
   public static final SpriteIdentifier CLAM_BLUE_SPRITE = new SpriteIdentifier(CHEST_SPRITE_MAPPER.sheet(), NekomasFixed.id("clam_blue").withPrefixedPath(CHEST_SPRITE_MAPPER.prefix() + "/"));
   public static final SpriteIdentifier CLAM_PINK_SPRITE = new SpriteIdentifier(CHEST_SPRITE_MAPPER.sheet(), NekomasFixed.id("clam_pink").withPrefixedPath(CHEST_SPRITE_MAPPER.prefix() + "/"));
   public static final SpriteIdentifier CLAM_PURPLE_SPRITE = new SpriteIdentifier(CHEST_SPRITE_MAPPER.sheet(), NekomasFixed.id("clam_purple").withPrefixedPath(CHEST_SPRITE_MAPPER.prefix() + "/"));

    public static SpriteIdentifier getClamTextureId(ClamBlockEntityRenderState.Variant variant) {
        return switch (variant) {
            case BLUE -> CLAM_BLUE_SPRITE;
            case PINK -> CLAM_PINK_SPRITE;
            case PURPLE -> CLAM_PURPLE_SPRITE;
            default -> CLAM_SPRITE;
        };
    }

    public static void registerTextureRegistry() {
        System.out.println("register TextureRegistry");
    }
}
