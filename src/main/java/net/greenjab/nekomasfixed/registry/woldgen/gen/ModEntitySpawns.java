package net.greenjab.nekomasfixed.registry.woldgen.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {
    public static void addSpawns(){
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.PLAINS), SpawnGroup.MONSTER,
                EntityTypeRegistry.SUS_SPIDER,  30, 1, 2);
        SpawnRestriction.register(EntityTypeRegistry.SUS_SPIDER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDarkUnderSky);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS), SpawnGroup.CREATURE,
                EntityTypeRegistry.MOOBLOOM, 30, 1, 2);
    }
}
