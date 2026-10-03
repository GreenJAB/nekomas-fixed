package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootSubProvider {
    public ModLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        BlockDyeMap.BRICK_SLAB.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_STAIRS.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_WALL.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_WOOL.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_CARPET.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_WOOL_SLABS.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_WOOL_STAIRS.values().forEach(this::dropSelf);
        dropSelf(BlockRegistry.AMBER_WOOL_SLAB);
        dropSelf(BlockRegistry.AQUA_WOOL_SLAB);
        dropSelf(BlockRegistry.INDIGO_WOOL_SLAB);
        dropSelf(BlockRegistry.MAROON_WOOL_SLAB);
        dropSelf(BlockRegistry.AMBER_WOOL_STAIRS);
        dropSelf(BlockRegistry.AQUA_WOOL_STAIRS);
        dropSelf(BlockRegistry.INDIGO_WOOL_STAIRS);
        dropSelf(BlockRegistry.MAROON_WOOL_STAIRS);
        dropSelf(BlockRegistry.AMBER_CONCRETE_SLAB);
        dropSelf(BlockRegistry.AQUA_CONCRETE_SLAB);
        dropSelf(BlockRegistry.INDIGO_CONCRETE_SLAB);
        dropSelf(BlockRegistry.MAROON_CONCRETE_SLAB);
        dropSelf(BlockRegistry.AMBER_CONCRETE_STAIRS);
        dropSelf(BlockRegistry.AQUA_CONCRETE_STAIRS);
        dropSelf(BlockRegistry.INDIGO_CONCRETE_STAIRS);
        dropSelf(BlockRegistry.MAROON_CONCRETE_STAIRS);
    }
}