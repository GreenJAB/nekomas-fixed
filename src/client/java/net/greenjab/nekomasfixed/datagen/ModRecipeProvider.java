package net.greenjab.nekomasfixed.datagen;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ItemDyeMap;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipe.CraftingRecipeJsonBuilder.getItemId;

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


                for (AllDyes colour : AllDyes.values()){
                    createRingRecipe(RecipeCategory.MISC, ItemDyeMap.DYE.get(colour), Items.BRUSH, ItemDyeMap.BRUSH.get(colour), "dyed_brush", 1)
                            .offerTo(exporter);
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICKS, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICKS.get(colour).asItem(), "dyed_bricks_dyed", 8)
                            .offerTo(exporter, getItemId(BlockDyeMap.BRICKS.get(colour).asItem()) + "_dyed");
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICK_SLAB, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICK_SLAB.get(colour).asItem(), "dyed_brick_slab_dyed", 8)
                            .offerTo(exporter, getItemId(BlockDyeMap.BRICK_SLAB.get(colour).asItem()) + "_dyed");
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICK_STAIRS, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICK_STAIRS.get(colour).asItem(), "dyed_brick_stairs_dyed", 8)
                            .offerTo(exporter, getItemId(BlockDyeMap.BRICK_STAIRS.get(colour).asItem()) + "_dyed");
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICK_WALL, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICK_WALL.get(colour).asItem(), "dyed_brick_wall_dyed", 8)
                            .offerTo(exporter, getItemId(BlockDyeMap.BRICK_WALL.get(colour).asItem()) + "_dyed");
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_SLAB.get(colour).asItem(), BlockDyeMap.BRICKS.get(colour).asItem(), 2);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_STAIRS.get(colour).asItem(), BlockDyeMap.BRICKS.get(colour).asItem());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_WALL.get(colour).asItem(), BlockDyeMap.BRICKS.get(colour).asItem());
                    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_SLAB.get(colour).asItem(), Ingredient.ofItem(BlockDyeMap.BRICKS.get(colour).asItem())).group("dyed_brick_slab").criterion(hasItem(BlockDyeMap.BRICKS.get(colour).asItem()), this.conditionsFromItem(BlockDyeMap.BRICKS.get(colour).asItem())).offerTo(this.exporter);
                    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_STAIRS.get(colour).asItem(), Ingredient.ofItem(BlockDyeMap.BRICKS.get(colour).asItem())).group("dyed_brick_stairs").criterion(hasItem(BlockDyeMap.BRICKS.get(colour).asItem()), this.conditionsFromItem(BlockDyeMap.BRICKS.get(colour).asItem())).offerTo(this.exporter);
                    getWallRecipe(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_WALL.get(colour).asItem(), Ingredient.ofItem(BlockDyeMap.BRICKS.get(colour).asItem())).group("dyed_brick_wall").criterion(hasItem(BlockDyeMap.BRICKS.get(colour).asItem()), this.conditionsFromItem(BlockDyeMap.BRICKS.get(colour).asItem())).offerTo(this.exporter);
                }
                ArrayList<Item> spotted_wool = new ArrayList<>();
                BlockDyeMap.SPOTTED_WOOL.values().forEach(e->spotted_wool.add(e.asItem()));
                this.offerDyeableRecipes(ItemDyeMap.DYE.values().stream().toList(), spotted_wool, "spotted_wool", RecipeCategory.BUILDING_BLOCKS);

                ArrayList<Item> spotted_carpet = new ArrayList<>();
                BlockDyeMap.SPOTTED_CARPET.values().forEach(e->spotted_carpet.add(e.asItem()));
                this.offerDyeableRecipes(ItemDyeMap.DYE.values().stream().toList(), spotted_carpet, "spotted_carpet_dye", RecipeCategory.DECORATIONS);

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
                        Pair.of(Items.WARPED_PLANKS, ItemRegistry.HOLLOW_WARPED_LOG),
                        Pair.of(ItemRegistry.BAOBAB_PLANKS, ItemRegistry.HOLLOW_BAOBAB_LOG));
                for (Pair<Item,Item> hollow : hollows){
                    createShapeless(RecipeCategory.BUILDING_BLOCKS, hollow.getFirst(), 1)
                            .input(hollow.getSecond())
                            .criterion(hasItem(hollow.getSecond()), conditionsFromItem(hollow.getSecond()))
                            .offerTo(exporter, getItemId(hollow.getFirst()) + "_from_hollow_log");
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

            private ShapedRecipeJsonBuilder createRingRecipe(RecipeCategory category, Item outside,  Item inside, Item result, String group, int num) {
                return createShaped(category, result, num)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .input('#', outside)
                        .input('D', inside)
                        .group(group)
                        .criterion(hasItem(outside), conditionsFromItem(outside))
                        .criterion(hasItem(inside), conditionsFromItem(inside));
            }
        };
    }

    @Override
    public String getName() {
        return "NekomasFixed Recipes";
    }
}