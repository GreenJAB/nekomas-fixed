package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.block.entity.state.ClamBlockEntityRenderState;
import net.minecraft.client.resources.model.Material;

import static net.minecraft.client.renderer.Sheets.CHEST_MAPPER;

public class TextureRegistry {

    public static final Material CLAM_SPRITE = new Material(CHEST_MAPPER.sheet(), NekomasFixed.id("clam").withPrefix(CHEST_MAPPER.prefix() + "/"));
    public static final Material CLAM_BLUE_SPRITE = new Material(CHEST_MAPPER.sheet(), NekomasFixed.id("clam_blue").withPrefix(CHEST_MAPPER.prefix() + "/"));
    public static final Material CLAM_PINK_SPRITE = new Material(CHEST_MAPPER.sheet(), NekomasFixed.id("clam_pink").withPrefix(CHEST_MAPPER.prefix() + "/"));
    public static final Material CLAM_PURPLE_SPRITE = new Material(CHEST_MAPPER.sheet(), NekomasFixed.id("clam_purple").withPrefix(CHEST_MAPPER.prefix() + "/"));

    public static Material getClamTextureId(ClamBlockEntityRenderState.Variant variant) {
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
