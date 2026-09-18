package net.greenjab.nekomasfixed.registry.worldgen;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.worldgen.feature.ClamFeature;
import net.greenjab.nekomasfixed.registry.worldgen.feature.GeyserBlockFeature;
import net.greenjab.nekomasfixed.registry.worldgen.feature.TermiteMoundFeature;
import net.greenjab.nekomasfixed.registry.worldgen.tree.BaobabTreeDecorator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.BAOBAB_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA, Biomes.DESERT),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModFeatures.MOUND_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST, Biomes.NETHER_WASTES),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModFeatures.GEYSER_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.CLAM_KEY);
    }

    public static final MapCodec<ClamFeature> CLAM_FEATURE = registerFeature("clam", ClamFeature.MAP_CODEC);
    public static final MapCodec<TermiteMoundFeature> MOUND_FEATURE = registerFeature("mound", TermiteMoundFeature.MAP_CODEC);
    public static final MapCodec<GeyserBlockFeature> GEYSER_FEATURE = registerFeature("geyser_feature", GeyserBlockFeature.MAP_CODEC);
    @SuppressWarnings("unchecked")
    private static <T, M extends MapCodec<T>> M registerFeature(String name, M codec) {
        return (M) Registry.register((Registry) BuiltInRegistries.FEATURE_TYPE, NekomasFixed.id(name), codec);
    }
}