package net.greenjab.nekomasfixed.registry.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;

public class BiomeAdditions {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW),
                MobCategory.CREATURE, EntityTypeRegistry.MOOBLOOM, 30, 1, 2);
    }
}