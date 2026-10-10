package net.greenjab.nekomasfixed.registry.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.worldgen.feature.ClamFeature;
import net.greenjab.nekomasfixed.registry.worldgen.feature.GeyserBlockFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class ModWorldGeneration {

    @SuppressWarnings("unused")
    public static final Feature<CountConfiguration> CLAM_FEATURE =
            Registry.register(BuiltInRegistries.FEATURE, NekomasFixed.id("clam"),
                    new ClamFeature(CountConfiguration.CODEC));

    @SuppressWarnings("unused")
    public static final GeyserBlockFeature GEYSER_FEATURE =
            Registry.register(BuiltInRegistries.FEATURE, NekomasFixed.id("geyser_feature"),
                    new GeyserBlockFeature(SimpleBlockConfiguration.CODEC));

    public static void generateModWorldGen() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.BAOBAB_PLACED_KEY);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.WARM_OCEAN),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.CLAM_PLACED_KEY);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST, Biomes.NETHER_WASTES),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                ModPlacedFeatures.GEYSER_PLACED_KEY);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.GHOST_PEPPER_SHRUB_KEY);
    }
}
