package net.greenjab.nekomasfixed.registry.woldgen.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildFireEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {
    public static void addSpawns(){

        SpawnRestriction.register(EntityTypeRegistry.WILD_FIRE, SpawnLocationTypes.IN_LAVA, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WildFireEntity::canSpawn);
        SpawnRestriction.register(EntityTypeRegistry.SUS_SPIDER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDarkUnderSky);
        SpawnRestriction.register(EntityTypeRegistry.SNOW_ZOMBIE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDarkUnderSky);
        SpawnRestriction.register(EntityTypeRegistry.JUNGLE_ZOMBIE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDarkUnderSky);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.PLAINS), SpawnGroup.MONSTER,
                EntityTypeRegistry.SUS_SPIDER,  30, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModTags.SPAWNS_SNOW_ZOMBIE), SpawnGroup.MONSTER,
                EntityTypeRegistry.SNOW_ZOMBIE,  80, 4, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.MONSTER,
                EntityTypeRegistry.JUNGLE_ZOMBIE,  80, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OCEAN), SpawnGroup.MONSTER,
                EntityTypeRegistry.DRENCHED,  2, 1, 2);


        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.MEADOW), SpawnGroup.CREATURE,
                EntityTypeRegistry.MOOBLOOM, 30, 1, 2);

    }
}
