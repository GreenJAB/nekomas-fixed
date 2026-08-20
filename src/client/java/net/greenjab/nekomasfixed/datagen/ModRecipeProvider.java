package net.greenjab.nekomasfixed.datagen;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ItemDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider wrapperLookup, @NonNull RecipeOutput recipeExporter) {
        return new RecipeProvider(wrapperLookup, recipeExporter) {

            @Override
            public void buildRecipes() {
                shapeless(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_PLANKS, 4)
                        .requires(ModTags.BAOBAB_LOGS)
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_LOG), has(ItemRegistry.BAOBAB_LOG)).save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_WOOD, 3)
                        .pattern("##")
                        .pattern("##")
                        .define('#', ItemRegistry.BAOBAB_LOG)
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_LOG), has(ItemRegistry.BAOBAB_LOG))
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.STRIPPED_BAOBAB_WOOD, 3)
                        .pattern("##")
                        .pattern("##")
                        .define('#', ItemRegistry.STRIPPED_BAOBAB_LOG)
                        .unlockedBy(getHasName(ItemRegistry.STRIPPED_BAOBAB_LOG), has(ItemRegistry.STRIPPED_BAOBAB_LOG))
                        .save(output);

                woodenBoat(ItemRegistry.BAOBAB_BOAT, ItemRegistry.BAOBAB_PLANKS);
                chestBoat(ItemRegistry.BAOBAB_CHEST_BOAT, ItemRegistry.BAOBAB_PLANKS);
                shelf(ItemRegistry.BAOBAB_SHELF, ItemRegistry.STRIPPED_BAOBAB_LOG);

                fenceBuilder(ItemRegistry.BAOBAB_FENCE, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                fenceGateBuilder(ItemRegistry.BAOBAB_FENCE_GATE, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                buttonBuilder(ItemRegistry.BAOBAB_BUTTON, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                doorBuilder(ItemRegistry.BAOBAB_DOOR, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                trapdoorBuilder(ItemRegistry.BAOBAB_TRAPDOOR, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                pressurePlateBuilder(RecipeCategory.REDSTONE, ItemRegistry.BAOBAB_PRESSURE_PLATE, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                signBuilder(ItemRegistry.BAOBAB_SIGN, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                hangingSignBuilder(ItemRegistry.BAOBAB_HANGING_SIGN, Ingredient.of(ItemRegistry.BAOBAB_LOG))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_LOG), has(ItemRegistry.BAOBAB_LOG))
                        .save(output);
                slabBuilder(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_SLAB, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);
                stairBuilder(ItemRegistry.BAOBAB_STAIRS, Ingredient.of(ItemRegistry.BAOBAB_PLANKS))
                        .unlockedBy(getHasName(ItemRegistry.BAOBAB_PLANKS), has(ItemRegistry.BAOBAB_PLANKS))
                        .save(output);


                for (AllDyes colour : AllDyes.values()){
                    createRingRecipe(RecipeCategory.MISC, ItemDyeMap.DYE.get(colour), Items.BRUSH, ItemDyeMap.BRUSH.get(colour), "dyed_brush", 1)
                            .save(output);
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICKS, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICKS.get(colour).asItem(), "dyed_bricks_dyed", 8)
                            .save(output, BlockDyeMap.BRICKS.get(colour).asItem() + "_dyed");
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICK_SLAB, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICK_SLAB.get(colour).asItem(), "dyed_brick_slab_dyed", 8)
                            .save(output, BlockDyeMap.BRICK_SLAB.get(colour).asItem() + "_dyed");
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICK_STAIRS, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICK_STAIRS.get(colour).asItem(), "dyed_brick_stairs_dyed", 8)
                            .save(output, BlockDyeMap.BRICK_STAIRS.get(colour).asItem() + "_dyed");
                    createRingRecipe(RecipeCategory.BUILDING_BLOCKS, Items.BRICK_WALL, ItemDyeMap.DYE.get(colour), BlockDyeMap.BRICK_WALL.get(colour).asItem(), "dyed_brick_wall_dyed", 8)
                            .save(output, BlockDyeMap.BRICK_WALL.get(colour).asItem() + "_dyed");
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_SLAB.get(colour).asItem(), BlockDyeMap.BRICKS.get(colour).asItem(), 2);
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_STAIRS.get(colour).asItem(), BlockDyeMap.BRICKS.get(colour).asItem());
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_WALL.get(colour).asItem(), BlockDyeMap.BRICKS.get(colour).asItem());
                    slabBuilder(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_SLAB.get(colour).asItem(), Ingredient.of(BlockDyeMap.BRICKS.get(colour).asItem())).group("dyed_brick_slab").unlockedBy(getHasName(BlockDyeMap.BRICKS.get(colour).asItem()), this.has(BlockDyeMap.BRICKS.get(colour).asItem())).save(this.output);
                    stairBuilder(BlockDyeMap.BRICK_STAIRS.get(colour).asItem(), Ingredient.of(BlockDyeMap.BRICKS.get(colour).asItem())).group("dyed_brick_stairs").unlockedBy(getHasName(BlockDyeMap.BRICKS.get(colour).asItem()), this.has(BlockDyeMap.BRICKS.get(colour).asItem())).save(this.output);
                    wallBuilder(RecipeCategory.BUILDING_BLOCKS, BlockDyeMap.BRICK_WALL.get(colour).asItem(), Ingredient.of(BlockDyeMap.BRICKS.get(colour).asItem())).group("dyed_brick_wall").unlockedBy(getHasName(BlockDyeMap.BRICKS.get(colour).asItem()), this.has(BlockDyeMap.BRICKS.get(colour).asItem())).save(this.output);
                }
                ArrayList<Item> spotted_wool = new ArrayList<>();
                BlockDyeMap.SPOTTED_WOOL.values().forEach(e->spotted_wool.add(e.asItem()));
                this.colorItemWithDye(ItemDyeMap.DYE.values().stream().toList(), spotted_wool, "spotted_wool", RecipeCategory.BUILDING_BLOCKS);

                ArrayList<Item> spotted_carpet = new ArrayList<>();
                BlockDyeMap.SPOTTED_CARPET.values().forEach(e->spotted_carpet.add(e.asItem()));
                this.colorItemWithDye(ItemDyeMap.DYE.values().stream().toList(), spotted_carpet, "spotted_carpet_dye", RecipeCategory.DECORATIONS);

                List<Pair<Item,Item>> hollows = List.of(
                        Pair.of(Items.OAK_PLANKS, ItemRegistry.HOLLOW_OAK_LOG),
                        Pair.of(Items.SPRUCE_PLANKS, ItemRegistry.HOLLOW_SPRUCE_LOG),
                        Pair.of(Items.BIRCH_PLANKS, ItemRegistry.HOLLOW_BIRCH_LOG),
                        Pair.of(Items.JUNGLE_PLANKS, ItemRegistry.HOLLOW_JUNGLE_LOG),
                        Pair.of(Items.ACACIA_PLANKS, ItemRegistry.HOLLOW_ACACIA_LOG),
                        Pair.of(Items.DARK_OAK_PLANKS, ItemRegistry.HOLLOW_DARK_OAK_LOG),
                        Pair.of(Items.MANGROVE_PLANKS, ItemRegistry.HOLLOW_MANGROVE_LOG),
                        Pair.of(Items.CHERRY_PLANKS, ItemRegistry.HOLLOW_CHERRY_LOG),
                        Pair.of(Items.PALE_OAK_PLANKS, ItemRegistry.HOLLOW_PALE_OAK_LOG),
                        Pair.of(Items.BAMBOO_PLANKS, ItemRegistry.HOLLOW_BAMBOO_BLOCK),
                        Pair.of(Items.CRIMSON_PLANKS, ItemRegistry.HOLLOW_CRIMSON_STEM),
                        Pair.of(Items.WARPED_PLANKS, ItemRegistry.HOLLOW_WARPED_STEM),
                        Pair.of(ItemRegistry.BAOBAB_PLANKS, ItemRegistry.HOLLOW_BAOBAB_LOG));
                for (Pair<Item,Item> hollow : hollows){
                    shapeless(RecipeCategory.BUILDING_BLOCKS, hollow.getFirst(), 1)
                            .requires(hollow.getSecond())
                            .unlockedBy(getHasName(hollow.getSecond()), has(hollow.getSecond()))
                            .save(output, hollow.getFirst() + "_from_hollow_log");
                }


                shaped(RecipeCategory.TOOLS, ItemRegistry.REDSTONE_STRIKER, 1)
                        .pattern("RG")
                        .pattern("FR")
                        .define('R', Items.REDSTONE)
                        .define('G', Items.GOLD_INGOT)
                        .define('F', Items.FLINT)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                        .unlockedBy(getHasName(Items.FLINT), has(Items.FLINT))
                        .save(output);

            }

            private ShapedRecipeBuilder createRingRecipe(RecipeCategory category, Item outside,  Item inside, Item result, String group, int num) {
                return shaped(category, result, num)
                        .pattern("###")
                        .pattern("#D#")
                        .pattern("###")
                        .define('#', outside)
                        .define('D', inside)
                        .group(group)
                        .unlockedBy(getHasName(outside), has(outside))
                        .unlockedBy(getHasName(inside), has(inside));
            }
        };
    }

    @Override
    public @NonNull String getName() {
        return "NekomasFixed Recipes";
    }
}