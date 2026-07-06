package net.greenjab.nekomasfixed.registry.worldgen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BAOBAB_KEY = registerKey("baobab");
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NekomasFixed.id(name));
    }

}
