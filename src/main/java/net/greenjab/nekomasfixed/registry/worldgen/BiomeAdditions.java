package net.greenjab.nekomasfixed.registry.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.registry.entity.DrenchedEntity;
import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpiderEntity;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class BiomeAdditions {
    public static void addSpawns(){

        SpawnPlacements.register(EntityTypeRegistry.WILDFIRE, SpawnPlacementTypes.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WildfireEntity::canSpawn);
        SpawnPlacements.register(EntityTypeRegistry.SUSPICIOUS_SPIDER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SuspiciousSpiderEntity::canSpawn);
        SpawnPlacements.register(EntityTypeRegistry.RIME, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkSurfaceMonstersSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.DERELICT, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkSurfaceMonstersSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.DRENCHED, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DrenchedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES, Biomes.PLAINS), MobCategory.MONSTER,
                EntityTypeRegistry.SUSPICIOUS_SPIDER,  30, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModTags.SPAWNS_RIME), MobCategory.MONSTER,
                EntityTypeRegistry.RIME,  100, 4, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), MobCategory.MONSTER,
                EntityTypeRegistry.DERELICT,  100, 4, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OCEAN), MobCategory.MONSTER,
                EntityTypeRegistry.DRENCHED,  5, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS), MobCategory.MONSTER,
                EntityTypeRegistry.DRENCHED,  5, 1, 2);


        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW), MobCategory.CREATURE,
                EntityTypeRegistry.MOOBLOOM, 30, 1, 2);

    }
}
