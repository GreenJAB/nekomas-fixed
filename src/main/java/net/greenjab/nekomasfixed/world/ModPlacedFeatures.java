package net.greenjab.nekomasfixed.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> BAOBAB_PLACED_KEY = registerKey("baobab");
    public static final RegistryKey<PlacedFeature> CLAM_PLACED_KEY = registerKey("clam");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("nekomasfixed", name));
    }
}
