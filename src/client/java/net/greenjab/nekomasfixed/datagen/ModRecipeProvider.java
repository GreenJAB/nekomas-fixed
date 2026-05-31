package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;

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

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.WHITE_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.WHITE_DYE)
                        .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.ORANGE_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.ORANGE_DYE)
                        .criterion(hasItem(Items.ORANGE_DYE), conditionsFromItem(Items.ORANGE_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.MAGENTA_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.MAGENTA_DYE)
                        .criterion(hasItem(Items.MAGENTA_DYE), conditionsFromItem(Items.MAGENTA_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.LIGHT_BLUE_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.LIGHT_BLUE_DYE)
                        .criterion(hasItem(Items.LIGHT_BLUE_DYE), conditionsFromItem(Items.LIGHT_BLUE_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.YELLOW_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.YELLOW_DYE)
                        .criterion(hasItem(Items.YELLOW_DYE), conditionsFromItem(Items.YELLOW_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.LIME_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.LIME_DYE)
                        .criterion(hasItem(Items.LIME_DYE), conditionsFromItem(Items.LIME_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.PINK_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.PINK_DYE)
                        .criterion(hasItem(Items.PINK_DYE), conditionsFromItem(Items.PINK_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.GRAY_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.GRAY_DYE)
                        .criterion(hasItem(Items.GRAY_DYE), conditionsFromItem(Items.GRAY_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.LIGHT_GRAY_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.LIGHT_GRAY_DYE)
                        .criterion(hasItem(Items.LIGHT_GRAY_DYE), conditionsFromItem(Items.LIGHT_GRAY_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.CYAN_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.CYAN_DYE)
                        .criterion(hasItem(Items.CYAN_DYE), conditionsFromItem(Items.CYAN_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.PURPLE_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.PURPLE_DYE)
                        .criterion(hasItem(Items.PURPLE_DYE), conditionsFromItem(Items.PURPLE_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BLUE_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.BLUE_DYE)
                        .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BROWN_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.BROWN_DYE)
                        .criterion(hasItem(Items.BROWN_DYE), conditionsFromItem(Items.BROWN_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.GREEN_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.GREEN_DYE)
                        .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.RED_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.RED_DYE)
                        .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BLACK_BRICKS, 8)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', Items.BRICKS)
                        .input('D', Items.BLACK_DYE)
                        .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                        .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

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


                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.OAK_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_OAK_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_OAK_LOG), conditionsFromItem(ItemRegistry.HOLLOW_OAK_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.SPRUCE_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_SPRUCE_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_SPRUCE_LOG), conditionsFromItem(ItemRegistry.HOLLOW_SPRUCE_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.BIRCH_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_BIRCH_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_BIRCH_LOG), conditionsFromItem(ItemRegistry.HOLLOW_BIRCH_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.JUNGLE_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_JUNGLE_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_JUNGLE_LOG), conditionsFromItem(ItemRegistry.HOLLOW_JUNGLE_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.ACACIA_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_ACACIA_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_ACACIA_LOG), conditionsFromItem(ItemRegistry.HOLLOW_ACACIA_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.DARK_OAK_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_DARK_OAK_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_DARK_OAK_LOG), conditionsFromItem(ItemRegistry.HOLLOW_DARK_OAK_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.CHERRY_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_CHERRY_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_CHERRY_LOG), conditionsFromItem(ItemRegistry.HOLLOW_CHERRY_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.PALE_OAK_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_PALE_OAK_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_PALE_OAK_LOG), conditionsFromItem(ItemRegistry.HOLLOW_PALE_OAK_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.CRIMSON_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_CRIMSON_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_CRIMSON_LOG), conditionsFromItem(ItemRegistry.HOLLOW_CRIMSON_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.WARPED_PLANKS, 1)
                        .input(ItemRegistry.HOLLOW_WARPED_LOG)
                        .criterion(hasItem(ItemRegistry.HOLLOW_WARPED_LOG), conditionsFromItem(ItemRegistry.HOLLOW_WARPED_LOG))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.WHITE_DYED_BRUSH, 1)
                        .input(Items.WHITE_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.ORANGE_DYED_BRUSH, 1)
                        .input(Items.ORANGE_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.ORANGE_DYE), conditionsFromItem(Items.ORANGE_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.MAGENTA_DYED_BRUSH, 1)
                        .input(Items.MAGENTA_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.MAGENTA_DYE), conditionsFromItem(Items.MAGENTA_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.LIGHT_BLUE_DYED_BRUSH, 1)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.LIGHT_BLUE_DYE), conditionsFromItem(Items.LIGHT_BLUE_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.YELLOW_DYED_BRUSH, 1)
                        .input(Items.YELLOW_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.YELLOW_DYE), conditionsFromItem(Items.YELLOW_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.LIME_DYED_BRUSH, 1)
                        .input(Items.LIME_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.LIME_DYE), conditionsFromItem(Items.LIME_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.PINK_DYED_BRUSH, 1)
                        .input(Items.PINK_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.PINK_DYE), conditionsFromItem(Items.PINK_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.GRAY_DYED_BRUSH, 1)
                        .input(Items.GRAY_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.GRAY_DYE), conditionsFromItem(Items.GRAY_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.LIGHT_GRAY_DYED_BRUSH, 1)
                        .input(Items.LIGHT_GRAY_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.LIGHT_GRAY_DYE), conditionsFromItem(Items.LIGHT_GRAY_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.CYAN_DYED_BRUSH, 1)
                        .input(Items.CYAN_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.CYAN_DYE), conditionsFromItem(Items.CYAN_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.PURPLE_DYED_BRUSH, 1)
                        .input(Items.PURPLE_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.PURPLE_DYE), conditionsFromItem(Items.PURPLE_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.BLUE_DYED_BRUSH, 1)
                        .input(Items.BLUE_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.BROWN_DYED_BRUSH, 1)
                        .input(Items.BROWN_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.BROWN_DYE), conditionsFromItem(Items.BROWN_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.GREEN_DYED_BRUSH, 1)
                        .input(Items.GREEN_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.RED_DYED_BRUSH, 1)
                        .input(Items.RED_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);

                createShapeless(RecipeCategory.MISC, ItemRegistry.BLACK_DYED_BRUSH, 1)
                        .input(Items.BLACK_DYE)
                        .input(Items.BRUSH)
                        .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                        .criterion(hasItem(Items.BRUSH), conditionsFromItem(Items.BRUSH))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "NekomasFixed Recipes";
    }
}