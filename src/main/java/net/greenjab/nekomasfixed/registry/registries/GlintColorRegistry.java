package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.GlintColor;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.GlintColors;
import net.greenjab.nekomasfixed.util.ModColors;
import net.greenjab.nekomasfixed.util.TreasureGlints;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.DyeColor;

import java.util.*;

public class GlintColorRegistry {
    public static final Map<AllDyes, ResourceKey<GlintColor>> GLINT_COLORS;
    public static final Map<TreasureGlints, ResourceKey<GlintColor>> TREASURE_GLINTS;
    static {
        Map<AllDyes, ResourceKey<GlintColor>> map = new EnumMap<>(AllDyes.class);
        for (AllDyes dye : AllDyes.values()) {
            map.put(dye, ResourceKey.create(GlintColor.REGISTRY_KEY, NekomasFixed.id(dye.getSerializedName())));
        }
        GLINT_COLORS = Collections.unmodifiableMap(map);
        Map<TreasureGlints, ResourceKey<GlintColor>> treasureMap = new EnumMap<>(TreasureGlints.class);
        for (TreasureGlints glint : TreasureGlints.values()) {
            treasureMap.put(glint, ResourceKey.create(GlintColor.REGISTRY_KEY, NekomasFixed.id(glint.getSerializedName())));
        }
        TREASURE_GLINTS = Collections.unmodifiableMap(treasureMap);
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
        for (AllDyes dye : AllDyes.values()) {
            ResourceKey<GlintColor> key = GLINT_COLORS.get(dye);
            int rawColor = colorFor(dye);
            MutableComponent description = Component.translatable("glint.nekomasfixed." + dye.getSerializedName()).withColor(tooltipColor(rawColor));
            GlintColor color = new GlintColor(List.of(rawColor), 80, description, Optional.empty());
            context.register(key, color);
        }
        for (TreasureGlints glint : TreasureGlints.values()) {
            ResourceKey<GlintColor> key = TREASURE_GLINTS.get(glint);
            AllDyes[] dyes = glint.getDyes();
            List<Integer> colors = new ArrayList<>(Arrays.stream(dyes).map(GlintColorRegistry::colorFor).toList());
            for (int extra : glint.getExtraColors()) {
                colors.add(ARGB.opaque(extra));
            }
            MutableComponent description = Component.translatable("glint.nekomasfixed." + glint.getSerializedName()).withColor(tooltipColor(colors.getFirst()));
            GlintColor color = new GlintColor(colors, glint.getCycleTicks(), description, Optional.ofNullable(glint.getTexture()).map(NekomasFixed::id));
            context.register(key, color);
        }
    }

    public static void registerGlintColors() {
        DynamicRegistries.registerSynced(GlintColor.REGISTRY_KEY, GlintColor.DIRECT_CODEC);
    }

    public static int tooltipColor(int color) {
        return GlintColors.lift(color, GlintColors.TOOLTIP_FLOOR, GlintColors.TOOLTIP_FLOOR_GRAY);
    }
}
