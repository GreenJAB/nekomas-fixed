package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.fabricmc.fabric.mixin.client.rendering.HumanoidArmorLayerMixin;
import net.greenjab.nekomasfixed.registry.item.quiver.QuiverContents;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.render.block.item.TerracottaDecoratedPotSpecialRenderer;
import net.greenjab.nekomasfixed.util.QuiverComponentChangeProperty;
import net.greenjab.nekomasfixed.util.QuiverListState;
import net.minecraft.client.data.*;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ComponentMatches;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.special.DecoratedPotSpecialRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockModelGenerators blockStateModelGenerator) {

        blockStateModelGenerator.createTrivialCube(BlockRegistry.WHITE_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.ORANGE_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.MAGENTA_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.LIGHT_BLUE_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.YELLOW_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.LIME_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.PINK_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.GRAY_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.LIGHT_GRAY_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.CYAN_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.PURPLE_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.BLUE_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.BROWN_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.GREEN_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.RED_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.BLACK_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.AMBER_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.AQUA_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.INDIGO_BRICKS);
        blockStateModelGenerator.createTrivialCube(BlockRegistry.MAROON_BRICKS);
        blockStateModelGenerator.createFloorFireModels(BlockRegistry.SULFUR_FIRE);
        blockStateModelGenerator.createTopFireModels(BlockRegistry.SULFUR_FIRE);
        blockStateModelGenerator.createSideFireModels(BlockRegistry.SULFUR_FIRE);
        blockStateModelGenerator.createLantern(BlockRegistry.SULFUR_LANTERN);
        blockStateModelGenerator.createAxisAlignedPillarBlock(BlockRegistry.GOLD_CHAIN, TexturedModel.CHAIN);

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
        registerSlab(blockStateModelGenerator, BlockRegistry.AMBER_BRICKS, BlockRegistry.AMBER_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.AQUA_BRICKS, BlockRegistry.AQUA_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.INDIGO_BRICKS, BlockRegistry.INDIGO_BRICK_SLAB);
        registerSlab(blockStateModelGenerator, BlockRegistry.MAROON_BRICKS, BlockRegistry.MAROON_BRICK_SLAB);

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
        registerStairs(blockStateModelGenerator, BlockRegistry.AMBER_BRICKS, BlockRegistry.AMBER_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.AQUA_BRICKS, BlockRegistry.AQUA_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.INDIGO_BRICKS, BlockRegistry.INDIGO_BRICK_STAIRS);
        registerStairs(blockStateModelGenerator, BlockRegistry.MAROON_BRICKS, BlockRegistry.MAROON_BRICK_STAIRS);

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
        registerWall(blockStateModelGenerator, BlockRegistry.AMBER_BRICKS, BlockRegistry.AMBER_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.AQUA_BRICKS, BlockRegistry.AQUA_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.INDIGO_BRICKS, BlockRegistry.INDIGO_BRICK_WALL);
        registerWall(blockStateModelGenerator, BlockRegistry.MAROON_BRICKS, BlockRegistry.MAROON_BRICK_WALL);

        blockStateModelGenerator.createParticleOnlyBlock(BlockRegistry.TERRACOTTA_DECORATED_POT, Blocks.TERRACOTTA);
        blockStateModelGenerator.
                generateSimpleSpecialItemModel(BlockRegistry.TERRACOTTA_DECORATED_POT, Optional.empty(), new TerracottaDecoratedPotSpecialRenderer.Unbaked());


    }


    @Override
    public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ItemRegistry.WHITE_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.ORANGE_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.MAGENTA_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.LIGHT_BLUE_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.YELLOW_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.LIME_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.PINK_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.GRAY_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.LIGHT_GRAY_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.CYAN_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.PURPLE_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.BLUE_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.BROWN_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.GREEN_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.RED_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.BLACK_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.AMBER_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.AQUA_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.INDIGO_DYED_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.MAROON_DYED_BRUSH, ModelTemplates.FLAT_ITEM);



        itemModelGenerator.generateFlatItem(ItemRegistry.QUIVER, ModelTemplates.FLAT_ITEM);


    }


    public void registerSlab(BlockModelGenerators blockStateModelGenerator, Block block, Block slab) {
        TextureMapping textureMap = TextureMapping.cube(block);
        MultiVariant weightedVariant = BlockModelGenerators.plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant2 = BlockModelGenerators.plainVariant(ModelTemplates.SLAB_TOP.create(slab, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant3 = BlockModelGenerators.plainVariant(
                ModelTemplates.CUBE_COLUMN.createWithOverride(slab, "_double", textureMap, blockStateModelGenerator.modelOutput)
        );

        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, weightedVariant, weightedVariant2, weightedVariant3));
    }

    public void registerStairs(BlockModelGenerators blockStateModelGenerator, Block block, Block stairs) {
        TextureMapping textureMap = TextureMapping.cube(block);
        MultiVariant weightedVariant = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant2 = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant3 = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, weightedVariant, weightedVariant2, weightedVariant3));
    }


    public void registerWall(BlockModelGenerators blockStateModelGenerator, Block block, Block wall) {
        TextureMapping textureMap = TextureMapping.cube(block);
        MultiVariant weightedVariant = BlockModelGenerators.plainVariant(ModelTemplates.WALL_POST.create(wall, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant2 = BlockModelGenerators.plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant3 = BlockModelGenerators.plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, textureMap, blockStateModelGenerator.modelOutput));
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createWall(wall, weightedVariant, weightedVariant2, weightedVariant3));
        Identifier identifier = ModelTemplates.WALL_INVENTORY.create(wall, textureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.registerSimpleItemModel(wall, identifier);
    }

}