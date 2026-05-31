package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import org.jspecify.annotations.NonNull;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.BAOBAB_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.BAOBAB_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.WHITE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.ORANGE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.MAGENTA_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.LIGHT_BLUE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.YELLOW_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.LIME_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.PINK_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.GRAY_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.LIGHT_GRAY_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.CYAN_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.PURPLE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.BLUE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.BROWN_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.GREEN_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.RED_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.BLACK_BRICKS);
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.MOOBLOOM_SPAWN_EGG, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.REDSTONE_STRIKER, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.SUS_SPIDER_SPAWN_EGG, Models.GENERATED);

        itemModelGenerator.register(ItemRegistry.WHITE_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.ORANGE_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.MAGENTA_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.LIGHT_BLUE_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.YELLOW_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.LIME_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PINK_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GRAY_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.LIGHT_GRAY_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.CYAN_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PURPLE_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.BLUE_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.BROWN_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GREEN_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.RED_DYED_BRUSH, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.BLACK_DYED_BRUSH, Models.GENERATED);
    }

}