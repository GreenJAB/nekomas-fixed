package net.greenjab.nekomasfixed.datagen;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeGenerator getRecipeGenerator(RegistryWrapper.@NonNull WrapperLookup wrapperLookup, @NonNull RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {

            @Override
            public void generate() {
                createShapeless(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_PLANKS, 4)
                        .input(ItemRegistry.BAOBAB_LOG)
                        .criterion(hasItem(ItemRegistry.BAOBAB_LOG), conditionsFromItem(ItemRegistry.BAOBAB_LOG)).offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_WOOD, 3)
                        .pattern("##")
                        .pattern("##")
                        .input('#', ItemRegistry.BAOBAB_LOG)
                        .criterion(hasItem(ItemRegistry.BAOBAB_LOG), conditionsFromItem(ItemRegistry.BAOBAB_LOG))
                        .offerTo(exporter);

                offerBoatRecipe(ItemRegistry.BAOBAB_BOAT, ItemRegistry.BAOBAB_PLANKS);
                offerChestBoatRecipe(ItemRegistry.BAOBAB_CHEST_BOAT, ItemRegistry.BAOBAB_PLANKS);
                offerShelfRecipe(ItemRegistry.BAOBAB_SHELF, ItemRegistry.BAOBAB_LOG);
                offerHangingSignRecipe(ItemRegistry.BAOBAB_HANGING_SIGN, ItemRegistry.BAOBAB_LOG);

                createFenceRecipe(ItemRegistry.BAOBAB_FENCE, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createFenceGateRecipe(ItemRegistry.BAOBAB_FENCE_GATE, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createButtonRecipe(ItemRegistry.BAOBAB_BUTTON, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createDoorRecipe(ItemRegistry.BAOBAB_DOOR, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createTrapdoorRecipe(ItemRegistry.BAOBAB_TRAPDOOR, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createPressurePlateRecipe(RecipeCategory.REDSTONE, ItemRegistry.BAOBAB_PRESSURE_PLATE, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createSignRecipe(ItemRegistry.BAOBAB_SIGN, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_SLAB, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);
                createStairsRecipe(ItemRegistry.BAOBAB_STAIRS, Ingredient.ofItem(ItemRegistry.BAOBAB_PLANKS))
                        .criterion(hasItem(ItemRegistry.BAOBAB_PLANKS), conditionsFromItem(ItemRegistry.BAOBAB_PLANKS))
                        .offerTo(exporter);



                List< List<Item>> colours = List.of(
                        List.of(Items.WHITE_DYE, ItemRegistry.WHITE_DYED_BRUSH, ItemRegistry.WHITE_BRICKS, ItemRegistry.WHITE_BRICK_SLAB, ItemRegistry.WHITE_BRICK_STAIRS, ItemRegistry.WHITE_BRICK_WALL),
                        List.of(Items.ORANGE_DYE, ItemRegistry.ORANGE_DYED_BRUSH, ItemRegistry.ORANGE_BRICKS, ItemRegistry.ORANGE_BRICK_SLAB, ItemRegistry.ORANGE_BRICK_STAIRS, ItemRegistry.ORANGE_BRICK_WALL),
                        List.of(Items.MAGENTA_DYE, ItemRegistry.MAGENTA_DYED_BRUSH, ItemRegistry.MAGENTA_BRICKS, ItemRegistry.MAGENTA_BRICK_SLAB, ItemRegistry.MAGENTA_BRICK_STAIRS, ItemRegistry.MAGENTA_BRICK_WALL),
                        List.of(Items.LIGHT_BLUE_DYE, ItemRegistry.LIGHT_BLUE_DYED_BRUSH, ItemRegistry.LIGHT_BLUE_BRICKS, ItemRegistry.LIGHT_BLUE_BRICK_SLAB, ItemRegistry.LIGHT_BLUE_BRICK_STAIRS, ItemRegistry.LIGHT_BLUE_BRICK_WALL),
                        List.of(Items.YELLOW_DYE, ItemRegistry.YELLOW_DYED_BRUSH, ItemRegistry.YELLOW_BRICKS, ItemRegistry.YELLOW_BRICK_SLAB, ItemRegistry.YELLOW_BRICK_STAIRS, ItemRegistry.YELLOW_BRICK_WALL),
                        List.of(Items.LIME_DYE, ItemRegistry.LIME_DYED_BRUSH, ItemRegistry.LIME_BRICKS, ItemRegistry.LIME_BRICK_SLAB, ItemRegistry.LIME_BRICK_STAIRS, ItemRegistry.LIME_BRICK_WALL),
                        List.of(Items.PINK_DYE, ItemRegistry.PINK_DYED_BRUSH, ItemRegistry.PINK_BRICKS, ItemRegistry.PINK_BRICK_SLAB, ItemRegistry.PINK_BRICK_STAIRS, ItemRegistry.PINK_BRICK_WALL),
                        List.of(Items.GRAY_DYE, ItemRegistry.GRAY_DYED_BRUSH, ItemRegistry.GRAY_BRICKS, ItemRegistry.GRAY_BRICK_SLAB, ItemRegistry.GRAY_BRICK_STAIRS, ItemRegistry.GRAY_BRICK_WALL),
                        List.of(Items.LIGHT_GRAY_DYE, ItemRegistry.LIGHT_GRAY_DYED_BRUSH, ItemRegistry.LIGHT_GRAY_BRICKS, ItemRegistry.LIGHT_GRAY_BRICK_SLAB, ItemRegistry.LIGHT_GRAY_BRICK_STAIRS, ItemRegistry.LIGHT_GRAY_BRICK_WALL),
                        List.of(Items.CYAN_DYE, ItemRegistry.CYAN_DYED_BRUSH, ItemRegistry.CYAN_BRICKS, ItemRegistry.CYAN_BRICK_SLAB, ItemRegistry.CYAN_BRICK_STAIRS, ItemRegistry.CYAN_BRICK_WALL),
                        List.of(Items.PURPLE_DYE, ItemRegistry.PURPLE_DYED_BRUSH, ItemRegistry.PURPLE_BRICKS, ItemRegistry.PURPLE_BRICK_SLAB, ItemRegistry.PURPLE_BRICK_STAIRS, ItemRegistry.PURPLE_BRICK_WALL),
                        List.of(Items.BLUE_DYE, ItemRegistry.BLUE_DYED_BRUSH, ItemRegistry.BLUE_BRICKS, ItemRegistry.BLUE_BRICK_SLAB, ItemRegistry.BLUE_BRICK_STAIRS, ItemRegistry.BLUE_BRICK_WALL),
                        List.of(Items.BROWN_DYE, ItemRegistry.BROWN_DYED_BRUSH, ItemRegistry.BROWN_BRICKS, ItemRegistry.BROWN_BRICK_SLAB, ItemRegistry.BROWN_BRICK_STAIRS, ItemRegistry.BROWN_BRICK_WALL),
                        List.of(Items.GREEN_DYE, ItemRegistry.GREEN_DYED_BRUSH, ItemRegistry.GREEN_BRICKS, ItemRegistry.GREEN_BRICK_SLAB, ItemRegistry.GREEN_BRICK_STAIRS, ItemRegistry.GREEN_BRICK_WALL),
                        List.of(Items.RED_DYE, ItemRegistry.RED_DYED_BRUSH, ItemRegistry.RED_BRICKS, ItemRegistry.RED_BRICK_SLAB, ItemRegistry.RED_BRICK_STAIRS, ItemRegistry.RED_BRICK_WALL),
                        List.of(Items.BLACK_DYE, ItemRegistry.BLACK_DYED_BRUSH, ItemRegistry.BLACK_BRICKS, ItemRegistry.BLACK_BRICK_SLAB, ItemRegistry.BLACK_BRICK_STAIRS, ItemRegistry.BLACK_BRICK_WALL));
                
                for (List<Item> colour : colours){
                    createShapeless(RecipeCategory.MISC, colour.get(1), 1)
                            .input(colour.get(0))
                            .input(Items.BRUSH)
                            .criterion(hasItem(colour.get(0)), conditionsFromItem(colour.get(0)))
                            .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                            .offerTo(exporter);
                    createShaped(RecipeCategory.BUILDING_BLOCKS, colour.get(2), 8)
                            .pattern("###")
                            .pattern("#D#")
                            .pattern("###")
                            .input('#', Items.BRICKS)
                            .input('D', colour.get(0))
                            .criterion(hasItem(colour.get(0)), conditionsFromItem(colour.get(0)))
                            .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                            .offerTo(exporter);
                    createShaped(RecipeCategory.BUILDING_BLOCKS, colour.get(3), 8)
                            .pattern("###")
                            .pattern("#D#")
                            .pattern("###")
                            .input('#', Items.BRICK_SLAB)
                            .input('D', colour.get(0))
                            .criterion(hasItem(colour.get(0)), conditionsFromItem(colour.get(0)))
                            .criterion(hasItem(Items.BRICK_SLAB), conditionsFromItem(Items.BRICK_SLAB))
                            .offerTo(exporter);
                    createShaped(RecipeCategory.BUILDING_BLOCKS, colour.get(4), 8)
                            .pattern("###")
                            .pattern("#D#")
                            .pattern("###")
                            .input('#', Items.BRICK_STAIRS)
                            .input('D', colour.get(0))
                            .criterion(hasItem(colour.get(0)), conditionsFromItem(colour.get(0)))
                            .criterion(hasItem(Items.BRICK_STAIRS), conditionsFromItem(Items.BRICK_STAIRS))
                            .offerTo(exporter);
                    createShaped(RecipeCategory.BUILDING_BLOCKS, colour.get(5), 8)
                            .pattern("###")
                            .pattern("#D#")
                            .pattern("###")
                            .input('#', Items.BRICK_WALL)
                            .input('D', colour.get(0))
                            .criterion(hasItem(colour.get(0)), conditionsFromItem(colour.get(0)))
                            .criterion(hasItem(Items.BRICK_WALL), conditionsFromItem(Items.BRICK_WALL))
                            .offerTo(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, colour.get(3), colour.get(2), 2);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, colour.get(4), colour.get(2));
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, colour.get(5), colour.get(2));
                    //offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, colour.get(3), colour.get(2));
                    //createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, colour.get(4), Ingredient.ofItem(colour.get(2))).criterion(hasItem(colour.get(2)), this.conditionsFromItem(colour.get(2))).offerTo(this.exporter);
                    //offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, colour.get(5), colour.get(2));
                }

                List<Pair<Item,Item>> hollows = List.of(
                        Pair.of(Items.OAK_PLANKS, ItemRegistry.HOLLOW_OAK_LOG),
                        Pair.of(Items.SPRUCE_PLANKS, ItemRegistry.HOLLOW_SPRUCE_LOG),
                        Pair.of(Items.BIRCH_PLANKS, ItemRegistry.HOLLOW_BIRCH_LOG),
                        Pair.of(Items.JUNGLE_PLANKS, ItemRegistry.HOLLOW_JUNGLE_LOG),
                        Pair.of(Items.ACACIA_PLANKS, ItemRegistry.HOLLOW_ACACIA_LOG),
                        Pair.of(Items.DARK_OAK_PLANKS, ItemRegistry.HOLLOW_DARK_OAK_LOG),
                        Pair.of(Items.CHERRY_PLANKS, ItemRegistry.HOLLOW_CHERRY_LOG),
                        Pair.of(Items.PALE_OAK_PLANKS, ItemRegistry.HOLLOW_PALE_OAK_LOG),
                        Pair.of(Items.CRIMSON_PLANKS, ItemRegistry.HOLLOW_CRIMSON_LOG),
                        Pair.of(Items.WARPED_PLANKS, ItemRegistry.HOLLOW_WARPED_LOG));
                for (Pair<Item,Item> hollow : hollows){
                    createShapeless(RecipeCategory.BUILDING_BLOCKS, hollow.getFirst(), 1)
                            .input(hollow.getSecond())
                            .criterion(hasItem(hollow.getSecond()), conditionsFromItem(hollow.getSecond()))
                            .offerTo(exporter);
                }


                createShaped(RecipeCategory.TOOLS, ItemRegistry.REDSTONE_STRIKER, 1)
                        .pattern("RG")
                        .pattern("FR")
                        .input('R', Items.REDSTONE)
                        .input('G', Items.GOLD_INGOT)
                        .input('F', Items.FLINT)
                        .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                        .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                        .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT))
                        .offerTo(exporter);

            }
        };
    }

    @Override
    public String getName() {
        return "NekomasFixed Recipes";
    }
}