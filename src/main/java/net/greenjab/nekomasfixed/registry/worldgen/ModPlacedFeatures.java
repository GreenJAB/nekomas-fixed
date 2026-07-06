package net.greenjab.nekomasfixed.registry.worldgen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BAOBAB_PLACED_KEY = registerKey("baobab");
    public static final ResourceKey<PlacedFeature> CLAM_PLACED_KEY = registerKey("clam");
    public static final ResourceKey<PlacedFeature> MOUND_PLACED_KEY = registerKey("mound");
    public static final ResourceKey<PlacedFeature> GEYSER_PLACED_KEY = registerKey("geyser_feature");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NekomasFixed.id(name));
    }
}
