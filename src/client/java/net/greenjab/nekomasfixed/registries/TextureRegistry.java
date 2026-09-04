package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.block.entity.state.ClamBlockEntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;

import static net.minecraft.client.renderer.Sheets.CHEST_MAPPER;

public class TextureRegistry {

    public static final SpriteId CLAM_SPRITE = new SpriteId(CHEST_MAPPER.sheet(), NekomasFixed.id("clam").withPrefix(CHEST_MAPPER.prefix() + "/"));
    public static final SpriteId CLAM_BLUE_SPRITE = new SpriteId(CHEST_MAPPER.sheet(), NekomasFixed.id("clam_blue").withPrefix(CHEST_MAPPER.prefix() + "/"));
    public static final SpriteId CLAM_PINK_SPRITE = new SpriteId(CHEST_MAPPER.sheet(), NekomasFixed.id("clam_pink").withPrefix(CHEST_MAPPER.prefix() + "/"));
    public static final SpriteId CLAM_PURPLE_SPRITE = new SpriteId(CHEST_MAPPER.sheet(), NekomasFixed.id("clam_purple").withPrefix(CHEST_MAPPER.prefix() + "/"));

    public static SpriteId getClamTextureId(ClamBlockEntityRenderState.Variant variant) {
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
