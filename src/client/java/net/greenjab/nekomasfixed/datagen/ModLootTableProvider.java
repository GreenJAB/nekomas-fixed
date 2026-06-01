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

        addDrop(BlockRegistry.WHITE_BRICK_SLAB);
        addDrop(BlockRegistry.LIGHT_GRAY_BRICK_SLAB);
        addDrop(BlockRegistry.GRAY_BRICK_SLAB);
        addDrop(BlockRegistry.BLACK_BRICK_SLAB);
        addDrop(BlockRegistry.BROWN_BRICK_SLAB);
        addDrop(BlockRegistry.RED_BRICK_SLAB);
        addDrop(BlockRegistry.ORANGE_BRICK_SLAB);
        addDrop(BlockRegistry.YELLOW_BRICK_SLAB);
        addDrop(BlockRegistry.LIME_BRICK_SLAB);
        addDrop(BlockRegistry.GREEN_BRICK_SLAB);
        addDrop(BlockRegistry.CYAN_BRICK_SLAB);
        addDrop(BlockRegistry.LIGHT_BLUE_BRICK_SLAB);
        addDrop(BlockRegistry.BLUE_BRICK_SLAB);
        addDrop(BlockRegistry.PURPLE_BRICK_SLAB);
        addDrop(BlockRegistry.MAGENTA_BRICK_SLAB);
        addDrop(BlockRegistry.PINK_BRICK_SLAB);

        addDrop(BlockRegistry.WHITE_BRICK_STAIRS);
        addDrop(BlockRegistry.LIGHT_GRAY_BRICK_STAIRS);
        addDrop(BlockRegistry.GRAY_BRICK_STAIRS);
        addDrop(BlockRegistry.BLACK_BRICK_STAIRS);
        addDrop(BlockRegistry.BROWN_BRICK_STAIRS);
        addDrop(BlockRegistry.RED_BRICK_STAIRS);
        addDrop(BlockRegistry.ORANGE_BRICK_STAIRS);
        addDrop(BlockRegistry.YELLOW_BRICK_STAIRS);
        addDrop(BlockRegistry.LIME_BRICK_STAIRS);
        addDrop(BlockRegistry.GREEN_BRICK_STAIRS);
        addDrop(BlockRegistry.CYAN_BRICK_STAIRS);
        addDrop(BlockRegistry.LIGHT_BLUE_BRICK_STAIRS);
        addDrop(BlockRegistry.BLUE_BRICK_STAIRS);
        addDrop(BlockRegistry.PURPLE_BRICK_STAIRS);
        addDrop(BlockRegistry.MAGENTA_BRICK_STAIRS);
        addDrop(BlockRegistry.PINK_BRICK_STAIRS);

        addDrop(BlockRegistry.WHITE_BRICK_WALL);
        addDrop(BlockRegistry.LIGHT_GRAY_BRICK_WALL);
        addDrop(BlockRegistry.GRAY_BRICK_WALL);
        addDrop(BlockRegistry.BLACK_BRICK_WALL);
        addDrop(BlockRegistry.BROWN_BRICK_WALL);
        addDrop(BlockRegistry.RED_BRICK_WALL);
        addDrop(BlockRegistry.ORANGE_BRICK_WALL);
        addDrop(BlockRegistry.YELLOW_BRICK_WALL);
        addDrop(BlockRegistry.LIME_BRICK_WALL);
        addDrop(BlockRegistry.GREEN_BRICK_WALL);
        addDrop(BlockRegistry.CYAN_BRICK_WALL);
        addDrop(BlockRegistry.LIGHT_BLUE_BRICK_WALL);
        addDrop(BlockRegistry.BLUE_BRICK_WALL);
        addDrop(BlockRegistry.PURPLE_BRICK_WALL);
        addDrop(BlockRegistry.MAGENTA_BRICK_WALL);
        addDrop(BlockRegistry.PINK_BRICK_WALL);

        addDrop(BlockRegistry.TERMITE_BLOCK);
        addDrop(BlockRegistry.TERMITE_HIVE);
        addDrop(BlockRegistry.BAOBAB_WOOD);
        addDrop(BlockRegistry.BAOBAB_PLANKS);
    }
}