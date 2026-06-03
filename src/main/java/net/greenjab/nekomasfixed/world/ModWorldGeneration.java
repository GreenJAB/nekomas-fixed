package net.greenjab.nekomasfixed.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.ClamFeature;
import net.greenjab.nekomasfixed.registry.woldgen.gen.ModEntitySpawns;
import net.greenjab.nekomasfixed.world.feature.GeyserBlockFeature;
import net.greenjab.nekomasfixed.world.feature.TermiteMoundFeature;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BAOBAB_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.DESERT),
                GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.MOUND_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST, BiomeKeys.NETHER_WASTES),
                GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.GEYSER_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLAM_PLACED_KEY);

        ModEntitySpawns.addSpawns();
    }

    public static final Feature<CountConfig> CLAM_FEATURE = registerFeature("clam", new ClamFeature(CountConfig.CODEC));
    public static final TermiteMoundFeature MOUND_FEATURE = registerFeature("mound", new TermiteMoundFeature(SimpleBlockFeatureConfig.CODEC));
    public static final GeyserBlockFeature GEYSER_FEATURE = registerFeature("geyser_feature", new GeyserBlockFeature(SimpleBlockFeatureConfig.CODEC));
    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registries.FEATURE, NekomasFixed.id(name), feature);
    }
}
