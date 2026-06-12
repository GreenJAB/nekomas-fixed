package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.NonNull;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.BAOBAB_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.BAOBAB_WOOD);
        registerSlab(blockStateModelGenerator, BlockRegistry.BAOBAB_PLANKS, BlockRegistry.BAOBAB_SLAB);
        registerStairs(blockStateModelGenerator, BlockRegistry.BAOBAB_PLANKS, BlockRegistry.BAOBAB_STAIRS);

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



        registerSlab(blockStateModelGenerator, BlockRegistry.WHITE_BRICKS, BlockRegistry.WHITE_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.ORANGE_BRICKS, BlockRegistry.ORANGE_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.MAGENTA_BRICKS, BlockRegistry.MAGENTA_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.LIGHT_BLUE_BRICKS, BlockRegistry.LIGHT_BLUE_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.YELLOW_BRICKS, BlockRegistry.YELLOW_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.LIME_BRICKS, BlockRegistry.LIME_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.PINK_BRICKS, BlockRegistry.PINK_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.GRAY_BRICKS, BlockRegistry.GRAY_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.LIGHT_GRAY_BRICKS, BlockRegistry.LIGHT_GRAY_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.CYAN_BRICKS, BlockRegistry.CYAN_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.PURPLE_BRICKS, BlockRegistry.PURPLE_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.BLUE_BRICKS, BlockRegistry.BLUE_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.BROWN_BRICKS, BlockRegistry.BROWN_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.GREEN_BRICKS, BlockRegistry.GREEN_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.RED_BRICKS, BlockRegistry.RED_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.BLACK_BRICKS, BlockRegistry.BLACK_BRICK_SLAB);

        registerStairs(blockStateModelGenerator, BlockRegistry.WHITE_BRICKS, BlockRegistry.WHITE_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.ORANGE_BRICKS, BlockRegistry.ORANGE_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.MAGENTA_BRICKS, BlockRegistry.MAGENTA_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.LIGHT_BLUE_BRICKS, BlockRegistry.LIGHT_BLUE_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.YELLOW_BRICKS, BlockRegistry.YELLOW_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.LIME_BRICKS, BlockRegistry.LIME_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.PINK_BRICKS, BlockRegistry.PINK_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.GRAY_BRICKS, BlockRegistry.GRAY_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.LIGHT_GRAY_BRICKS, BlockRegistry.LIGHT_GRAY_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.CYAN_BRICKS, BlockRegistry.CYAN_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.PURPLE_BRICKS, BlockRegistry.PURPLE_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.BLUE_BRICKS, BlockRegistry.BLUE_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.BROWN_BRICKS, BlockRegistry.BROWN_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.GREEN_BRICKS, BlockRegistry.GREEN_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.RED_BRICKS, BlockRegistry.RED_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.BLACK_BRICKS, BlockRegistry.BLACK_BRICK_STAIRS);

        registerWall(blockStateModelGenerator, BlockRegistry.WHITE_BRICKS, BlockRegistry.WHITE_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.ORANGE_BRICKS, BlockRegistry.ORANGE_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.MAGENTA_BRICKS, BlockRegistry.MAGENTA_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.LIGHT_BLUE_BRICKS, BlockRegistry.LIGHT_BLUE_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.YELLOW_BRICKS, BlockRegistry.YELLOW_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.LIME_BRICKS, BlockRegistry.LIME_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.PINK_BRICKS, BlockRegistry.PINK_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.GRAY_BRICKS, BlockRegistry.GRAY_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.LIGHT_GRAY_BRICKS, BlockRegistry.LIGHT_GRAY_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.CYAN_BRICKS, BlockRegistry.CYAN_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.PURPLE_BRICKS, BlockRegistry.PURPLE_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.BLUE_BRICKS, BlockRegistry.BLUE_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.BROWN_BRICKS, BlockRegistry.BROWN_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.GREEN_BRICKS, BlockRegistry.GREEN_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.RED_BRICKS, BlockRegistry.RED_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.BLACK_BRICKS, BlockRegistry.BLACK_BRICK_WALL);
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

    public void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block block, Block slab) {
        TextureMap textureMap = TextureMap.all(block);
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(Models.SLAB.upload(slab, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = BlockStateModelGenerator.createWeightedVariant(Models.SLAB_TOP.upload(slab, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant3 = BlockStateModelGenerator.createWeightedVariant(
                Models.CUBE_COLUMN.uploadWithoutVariant(slab, "_double", textureMap, blockStateModelGenerator.modelCollector)
        );

        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slab, weightedVariant, weightedVariant2, weightedVariant3));
    }

    public void registerStairs(BlockStateModelGenerator blockStateModelGenerator, Block block, Block stairs) {
        TextureMap textureMap = TextureMap.all(block);
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(Models.INNER_STAIRS.upload(stairs, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = BlockStateModelGenerator.createWeightedVariant(Models.STAIRS.upload(stairs, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant3 = BlockStateModelGenerator.createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, textureMap, blockStateModelGenerator.modelCollector));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairs, weightedVariant, weightedVariant2, weightedVariant3));
    }


    public void registerWall(BlockStateModelGenerator blockStateModelGenerator, Block block, Block wall) {
        TextureMap textureMap = TextureMap.all(block);
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant3 = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, textureMap, blockStateModelGenerator.modelCollector));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(wall, weightedVariant, weightedVariant2, weightedVariant3));
        Identifier identifier = Models.WALL_INVENTORY.upload(wall, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.registerParentedItemModel(wall, identifier);
    }

}