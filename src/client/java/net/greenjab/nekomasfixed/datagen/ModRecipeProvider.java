package net.greenjab.nekomasfixed.datagen;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ItemDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import org.jspecify.annotations.NonNull;
import java.util.ArrayList;
import java.util.EnumMap;
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
                        .input(ModTags.BAOBAB_LOGS)
                        .criterion(hasItem(ItemRegistry.BAOBAB_LOG), conditionsFromItem(ItemRegistry.BAOBAB_LOG)).offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_WOOD, 3)
                        .pattern("##")
                        .pattern("##")
                        .input('#', ItemRegistry.BAOBAB_LOG)
                        .criterion(hasItem(ItemRegistry.BAOBAB_LOG), conditionsFromItem(ItemRegistry.BAOBAB_LOG))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.STRIPPED_BAOBAB_WOOD, 3)
                        .pattern("##")
                        .pattern("##")
                        .input('#', ItemRegistry.STRIPPED_BAOBAB_LOG)
                        .criterion(hasItem(ItemRegistry.STRIPPED_BAOBAB_LOG), conditionsFromItem(ItemRegistry.STRIPPED_BAOBAB_LOG))
                        .offerTo(exporter);

                offerBoatRecipe(ItemRegistry.BAOBAB_BOAT, ItemRegistry.BAOBAB_PLANKS);
                offerChestBoatRecipe(ItemRegistry.BAOBAB_CHEST_BOAT, ItemRegistry.BAOBAB_PLANKS);
                offerShelfRecipe(ItemRegistry.BAOBAB_SHELF, ItemRegistry.STRIPPED_BAOBAB_LOG);
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
                        Pair.of(Items.MANGROVE_PLANKS, ItemRegistry.HOLLOW_MANGROVE_LOG),
                        Pair.of(Items.CHERRY_PLANKS, ItemRegistry.HOLLOW_CHERRY_LOG),
                        Pair.of(Items.PALE_OAK_PLANKS, ItemRegistry.HOLLOW_PALE_OAK_LOG),
                        Pair.of(Items.BAMBOO_PLANKS, ItemRegistry.HOLLOW_BAMBOO_BLOCK),
                        Pair.of(Items.CRIMSON_PLANKS, ItemRegistry.HOLLOW_CRIMSON_STEM),
                        Pair.of(Items.WARPED_PLANKS, ItemRegistry.HOLLOW_WARPED_STEM),
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

                createShaped(RecipeCategory.MISC, ItemRegistry.GLOW_TORCH, 4)
                        .criterion(hasItem(Items.GLOW_INK_SAC), conditionsFromItem(Items.GLOW_INK_SAC))
                        .input('g', Items.GLOW_INK_SAC)
                        .input('/', Items.STICK)
                        .pattern("g")
                        .pattern("/")
                        .offerTo(exporter);
                createShapeless(RecipeCategory.DECORATIONS, ItemRegistry.CLEAR_ITEM_FRAME)
                        .criterion(hasItem(Items.PHANTOM_MEMBRANE), conditionsFromItem(Items.PHANTOM_MEMBRANE))
                        .input(Items.ITEM_FRAME)
                        .input(Items.PHANTOM_MEMBRANE)
                        .offerTo(exporter);

                // Regular wool from dying
                createDyeRecipe(BlockDyeMap.WOOL, ModTags.GLOWING_WOOL_ITEMS, RecipeCategory.BUILDING_BLOCKS, "dye_glowing_wool");
                // Glowing wool from regular ones
                createLightRecipe(BlockDyeMap.GLOWING_WOOL, BlockDyeMap.WOOL, RecipeCategory.BUILDING_BLOCKS, "light_wool");


                // Crafting glowing carpets
                BlockDyeMap.GLOWING_CARPET.forEach(((color, block) -> {
                    ItemConvertible wool = BlockDyeMap.GLOWING_WOOL.get(color);
                    createShaped(RecipeCategory.DECORATIONS, block, 3)
                            .criterion(hasItem(wool), conditionsFromItem(wool))
                            .group("glowing_carpet")
                            .input('w', wool)
                            .pattern("ww")
                            .offerTo(exporter);
                }));
                // Regular carpet from dying
                createDyeRecipe(BlockDyeMap.CARPET, ModTags.GLOWING_WOOL_CARPET_ITEMS, RecipeCategory.MISC, "dye_glowing_wool_carpet");
                // Glowing carpets from regular ones
                createLightRecipe(BlockDyeMap.GLOWING_CARPET, BlockDyeMap.CARPET, RecipeCategory.MISC, "light_wool_carpet");

                //TODO uncomment when DyeColor is extended
//                // Crafting glowing beds
//                BlockDyeMap.GLOWING_BED.forEach(((color, block) -> {
//                    ItemConvertible wool = BlockDyeMap.GLOWING_WOOL.get(color);
//                    createShaped(RecipeCategory.DECORATIONS, block)
//                            .criterion(hasItem(wool), conditionsFromItem(wool))
//                            .group("glowing_bed")
//                            .input('p', ItemTags.PLANKS)
//                            .input('w', wool)
//                            .pattern("www")
//                            .pattern("ppp")
//                            .offerTo(exporter);
//                }));
//                // Regular bed from dying
//                createDyeRecipe(BlockDyeMap.BED, ModTags.GLOWING_BED_ITEMS, RecipeCategory.MISC, "dye_glowing_bed");
//                // Glowing beds from regular ones
//                createLightRecipe(BlockDyeMap.GLOWING_BED, BlockDyeMap.BED, RecipeCategory.MISC, "light_bed");
//
//
//                // Crafting glowing banners
//                BlockDyeMap.GLOWING_BANNER.forEach(((color, block) -> {
//                    ItemConvertible wool = BlockDyeMap.GLOWING_WOOL.get(color);
//                    createShaped(RecipeCategory.DECORATIONS, block)
//                            .criterion(hasItem(wool), conditionsFromItem(wool))
//                            .group("glowing_banner")
//                            .input('/', Items.STICK)
//                            .input('w', wool)
//                            .pattern("www")
//                            .pattern("www")
//                            .pattern(" / ")
//                            .offerTo(exporter);
//                }));

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

            private void createDyeRecipe(EnumMap<AllDyes, Block> blockDyeMap, TagKey<Item> glowingBlocks, RecipeCategory category, String group) {
                blockDyeMap.forEach((color, block) -> {
                    createShapeless(category, block)
                            .criterion("has_needed_dye", conditionsFromItem(ItemDyeMap.DYE.get(color)))
                            .group(group)
                            .input(ItemDyeMap.DYE.get(color))
                            .input(glowingBlocks)
                            .offerTo(exporter, prefixedKey(block, "dye_glowing_"));
                });
            }

            private void createLightRecipe(EnumMap<AllDyes, Block> glowingBlockDyeMap, EnumMap<AllDyes, Block> regularBlockDyeMap, RecipeCategory category, String group) {
                glowingBlockDyeMap.forEach((color, block) -> {
                    Block regular = regularBlockDyeMap.get(color);
                    createShapeless(category, block)
                            .criterion("has_glow_ink_sac", conditionsFromItem(Items.GLOW_INK_SAC))
                            .group(group)
                            .input(Items.GLOW_INK_SAC)
                            .input(regular)
                            .offerTo(exporter, prefixedKey(regular, "light_"));
                });
            }
        };
    }

    private static RegistryKey<Recipe<?>> prefixedKey(Block block, String prefix) {
        return RegistryKey.of(RegistryKeys.RECIPE, NekomasFixed.id(Registries.BLOCK.getId(block).getPath()).withPrefixedPath(prefix));
    }

    @Override
    public String getName() {
        return "NekomasFixed Recipes";
    }
}