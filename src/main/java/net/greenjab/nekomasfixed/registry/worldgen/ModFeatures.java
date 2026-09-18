package net.greenjab.nekomasfixed.registry.worldgen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ModFeatures {
    public static final ResourceKey<Feature> BAOBAB_KEY = registerKey("baobab");
    public static final ResourceKey<PlacedFeature> BAOBAB_PLACED_KEY = registerPlacedKey("baobab");
    public static final ResourceKey<PlacedFeature> CLAM_KEY = registerPlacedKey("clam");
    public static final ResourceKey<PlacedFeature> MOUND_KEY = registerPlacedKey("mound");
    public static final ResourceKey<PlacedFeature> GEYSER_KEY = registerPlacedKey("geyser_feature");

    public static ResourceKey<PlacedFeature> registerPlacedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NekomasFixed.id(name));
    }

    public static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(Registries.FEATURE, NekomasFixed.id(name));
    }
}