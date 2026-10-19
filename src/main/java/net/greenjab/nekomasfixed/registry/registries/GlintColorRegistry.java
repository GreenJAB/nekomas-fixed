package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.GlintColor;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.DyeColor;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class GlintColorRegistry {
    public static final Map<AllDyes, ResourceKey<GlintColor>> GLINT_COLORS;
    static {
        Map<AllDyes, ResourceKey<GlintColor>> map = new EnumMap<>(AllDyes.class);
        for (AllDyes dye : AllDyes.values()) {
            map.put(dye, ResourceKey.create(GlintColor.REGISTRY_KEY, NekomasFixed.id(dye.getSerializedName())));
        }
        GLINT_COLORS = Collections.unmodifiableMap(map);
    }

    private static int colorFor(AllDyes dye) {
        DyeColor vanilla = DyeColor.byName(dye.getSerializedName(), null);
        if (vanilla != null) {
            return ARGB.opaque(vanilla.getFireworkColor());
        }
        return switch (dye) {
            case AMBER -> ARGB.opaque(ModColors.AMBER.getColor());
            case AQUA -> ARGB.opaque(ModColors.AQUA.getColor());
            case INDIGO -> ARGB.opaque(ModColors.INDIGO.getColor());
            case MAROON -> ARGB.opaque(ModColors.MAROON.getColor());
            default -> throw new IllegalStateException();
        };
    }

    public static void bootstrap(BootstrapContext<GlintColor> context) {
        for(AllDyes dye : AllDyes.values()) {
            ResourceKey<GlintColor> key = GLINT_COLORS.get(dye);
            GlintColor color = new GlintColor(colorFor(dye), Component.translatable("glint.nekomasfixed." + dye.getSerializedName()));
            context.register(key, color);
        }
    }

    public static void registerGlintColors() {
        DynamicRegistries.registerSynced(GlintColor.REGISTRY_KEY, GlintColor.DIRECT_CODEC);
    }
}
