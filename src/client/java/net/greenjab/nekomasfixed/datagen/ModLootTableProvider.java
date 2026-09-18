package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.greenjab.nekomasfixed.registry.block.HollowLogBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootSubProvider {
    public ModLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        BlockDyeMap.BRICKS.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_SLAB.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_STAIRS.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_WALL.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_WOOL.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_CARPET.values().forEach(this::dropSelf);

        dropSelf(BlockRegistry.HOLLOW_STRIPPED_OAK_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_PALE_OAK_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_BAOBAB_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_BIRCH_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_ACACIA_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_JUNGLE_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_SPRUCE_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_CRIMSON_STEM);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_WARPED_STEM);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_CHERRY_LOG);
        dropSelf(BlockRegistry.HOLLOW_STRIPPED_MANGROVE_LOG);
    }


}