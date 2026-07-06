package net.greenjab.nekomasfixed.registry.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.worldgen.feature.ClamFeature;
import net.greenjab.nekomasfixed.registry.worldgen.feature.GeyserBlockFeature;
import net.greenjab.nekomasfixed.registry.worldgen.feature.TermiteMoundFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.BAOBAB_PLACED_KEY);
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA, Biomes.DESERT),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.MOUND_PLACED_KEY);
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST, Biomes.NETHER_WASTES),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.GEYSER_PLACED_KEY);

        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CLAM_PLACED_KEY);
    }

    public static final Feature<CountConfiguration> CLAM_FEATURE = registerFeature("clam", new ClamFeature(CountConfiguration.CODEC));
    public static final TermiteMoundFeature MOUND_FEATURE = registerFeature("mound", new TermiteMoundFeature(SimpleBlockConfiguration.CODEC));
    public static final GeyserBlockFeature GEYSER_FEATURE = registerFeature("geyser_feature", new GeyserBlockFeature(SimpleBlockConfiguration.CODEC));
    private static <C extends FeatureConfiguration, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(BuiltInRegistries.FEATURE, NekomasFixed.id(name), feature);
    }
}
