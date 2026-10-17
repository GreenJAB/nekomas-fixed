package net.greenjab.nekomasfixed.registry.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;

public class BiomeAdditions {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW),
                MobCategory.CREATURE, EntityTypeRegistry.MOOBLOOM, 30, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), MobCategory.MONSTER,
                EntityTypeRegistry.DERELICT, 100, 4, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OCEAN), MobCategory.MONSTER,
                EntityTypeRegistry.DRENCHED, 5, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS), MobCategory.MONSTER,
                EntityTypeRegistry.DRENCHED, 100, 1, 2);
    }
}