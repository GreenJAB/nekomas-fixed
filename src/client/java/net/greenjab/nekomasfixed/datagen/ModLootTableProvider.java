package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.block.BedBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        BlockDyeMap.BRICKS.values().forEach(this::addDrop);
        BlockDyeMap.BRICK_SLAB.values().forEach(this::addDrop);
        BlockDyeMap.BRICK_STAIRS.values().forEach(this::addDrop);
        BlockDyeMap.BRICK_WALL.values().forEach(this::addDrop);
        BlockDyeMap.SPOTTED_WOOL.values().forEach(this::addDrop);
        BlockDyeMap.SPOTTED_CARPET.values().forEach(this::addDrop);
        BlockDyeMap.GLOWING_WOOL.values().forEach(this::addDrop);
        BlockDyeMap.GLOWING_CARPET.values().forEach(this::addDrop);
        BlockDyeMap.GLOWING_BED.values().forEach(block -> addDrop(block, dropsWithProperty(block, BedBlock.PART, BedPart.HEAD)));
        BlockDyeMap.GLOWING_BANNER.values().forEach(block -> addDrop(block, bannerDrops(block)));
    }
}