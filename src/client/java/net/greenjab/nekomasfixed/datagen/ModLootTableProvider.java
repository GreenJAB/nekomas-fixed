package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDropWithSilkTouch(BlockRegistry.GEYSER);

        addDrop(BlockRegistry.WHITE_BRICKS);
        addDrop(BlockRegistry.LIGHT_GRAY_BRICKS);
        addDrop(BlockRegistry.GRAY_BRICKS);
        addDrop(BlockRegistry.BLACK_BRICKS);
        addDrop(BlockRegistry.BROWN_BRICKS);
        addDrop(BlockRegistry.RED_BRICKS);
        addDrop(BlockRegistry.ORANGE_BRICKS);
        addDrop(BlockRegistry.YELLOW_BRICKS);
        addDrop(BlockRegistry.LIME_BRICKS);
        addDrop(BlockRegistry.GREEN_BRICKS);
        addDrop(BlockRegistry.CYAN_BRICKS);
        addDrop(BlockRegistry.LIGHT_BLUE_BRICKS);
        addDrop(BlockRegistry.BLUE_BRICKS);
        addDrop(BlockRegistry.PURPLE_BRICKS);
        addDrop(BlockRegistry.MAGENTA_BRICKS);
        addDrop(BlockRegistry.PINK_BRICKS);

        addDrop(BlockRegistry.TERMITE_BLOCK);
        addDrop(BlockRegistry.TERMITE_HIVE);
        addDrop(BlockRegistry.BAOBAB_WOOD);
        addDrop(BlockRegistry.BAOBAB_PLANKS);
    }
}