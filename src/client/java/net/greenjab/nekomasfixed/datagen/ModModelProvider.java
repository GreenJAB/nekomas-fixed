package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.predicates.QuiverPredicate;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.DataComponentPredicateRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.render.block.item.TerracottaDecoratedPotSpecialRenderer;

import net.greenjab.nekomasfixed.render.special.TippedArrowTint;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ComponentMatches;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;


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

        blockStateModelGenerator.createCampfires(BlockRegistry.SULFUR_CAMPFIRE);
        blockStateModelGenerator.createNormalTorch(BlockRegistry.SULFUR_TORCH, BlockRegistry.WALL_SULFUR_TORCH);

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


       registerTippedArrowCustom(itemModelGenerator);

        registerQuiver(itemModelGenerator);


    }

    private void registerTippedArrowCustom(ItemModelGenerators gen) {
        Item item = ItemRegistry.TIPPED_ARROW_CUSTOM;

        Identifier model = gen.generateLayeredItem(
                item,
                new Material(NekomasFixed.id("item/custom_tipped_arrow_base")),
                new Material(NekomasFixed.id("item/custom_tipped_arrow_head")));

        gen.itemModelOutput.accept(item, ItemModelUtils.tintedModel(
                model,
                ItemModelUtils.constantTint(-1),
                new TippedArrowTint()));
    }

    private void registerQuiver(ItemModelGenerators generators){
        DataComponentPredicate.Single<QuiverPredicate> single =
                new DataComponentPredicate.Single<>(DataComponentPredicateRegistry.HAS_ARROWS, new QuiverPredicate());

        ItemModel.Unbaked filledModel = ItemModelUtils.plainModel(
                generators.createFlatItemModel(ItemRegistry.QUIVER, ModelTemplates.FLAT_ITEM)
        );

        ItemModel.Unbaked emptyModel = ItemModelUtils.plainModel(
                generators.createFlatItemModel(ItemRegistry.QUIVER, "_empty", ModelTemplates.FLAT_ITEM)
        );

        generators.generateBooleanDispatch(
                ItemRegistry.QUIVER,
                new ComponentMatches(single),
                filledModel,
                emptyModel
        );


    }




    public void registerSlab(BlockModelGenerators blockStateModelGenerator, Block block, Block slab) {
        TextureMapping textureMap = TextureMapping.cube(block);
        MultiVariant weightedVariant = plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant2 = plainVariant(ModelTemplates.SLAB_TOP.create(slab, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant3 = plainVariant(
                ModelTemplates.CUBE_COLUMN.createWithOverride(slab, "_double", textureMap, blockStateModelGenerator.modelOutput)
        );

        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, weightedVariant, weightedVariant2, weightedVariant3));
    }

    public void registerStairs(BlockModelGenerators blockStateModelGenerator, Block block, Block stairs) {
        TextureMapping textureMap = TextureMapping.cube(block);
        MultiVariant weightedVariant = plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant2 = plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant3 = plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, weightedVariant, weightedVariant2, weightedVariant3));
    }


    public void registerWall(BlockModelGenerators blockStateModelGenerator, Block block, Block wall) {
        TextureMapping textureMap = TextureMapping.cube(block);
        MultiVariant weightedVariant = plainVariant(ModelTemplates.WALL_POST.create(wall, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant2 = plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, textureMap, blockStateModelGenerator.modelOutput));
        MultiVariant weightedVariant3 = plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, textureMap, blockStateModelGenerator.modelOutput));
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createWall(wall, weightedVariant, weightedVariant2, weightedVariant3));
        Identifier identifier = ModelTemplates.WALL_INVENTORY.create(wall, textureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.registerSimpleItemModel(wall, identifier);
    }

}