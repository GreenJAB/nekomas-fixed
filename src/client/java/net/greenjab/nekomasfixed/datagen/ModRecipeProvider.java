package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

/**
 * Generates the colour-suite recipes + their unlock advancements.
 * Bricks use vanilla shaped/stonecutting builders (main's brick recipes are a
 * plain-to-coloured craft, not a recolour). Spotted wool/carpet + froglights
 * use the mod's custom `nekomasfixed:recolour` type (RecolourRecipeBuilder).
 */
public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private static Item dyeItem(AllDyes dye) {
        return switch (dye) {
            case WHITE -> Items.WHITE_DYE;
            case ORANGE -> Items.ORANGE_DYE;
            case MAGENTA -> Items.MAGENTA_DYE;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
            case YELLOW -> Items.YELLOW_DYE;
            case LIME -> Items.LIME_DYE;
            case PINK -> Items.PINK_DYE;
            case GRAY -> Items.GRAY_DYE;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
            case CYAN -> Items.CYAN_DYE;
            case PURPLE -> Items.PURPLE_DYE;
            case BLUE -> Items.BLUE_DYE;
            case BROWN -> Items.BROWN_DYE;
            case GREEN -> Items.GREEN_DYE;
            case RED -> Items.RED_DYE;
            case BLACK -> Items.BLACK_DYE;
            case AMBER -> ItemRegistry.AMBER_DYE;
            case AQUA -> ItemRegistry.AQUA_DYE;
            case INDIGO -> ItemRegistry.INDIGO_DYE;
            case MAROON -> ItemRegistry.MAROON_DYE;
        };
    }

    private void generateBaobabRecipes(RecipeOutput output) {
        // Baobab wood-family recipes (vanilla-shaped builders).
        Item planks = ItemRegistry.BAOBAB_PLANKS;
        Item log = ItemRegistry.BAOBAB_LOG;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .requires(ModTags.BAOBAB_LOGS_ITEM).group("planks")
                .unlockedBy("has_log", has(log))
                .save(output, NekomasFixed.id("baobab_planks"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_WOOD, 3)
                .pattern("##").pattern("##").define('#', log)
                .group("bark").unlockedBy("has_log", has(log))
                .save(output, NekomasFixed.id("baobab_wood"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.STRIPPED_BAOBAB_WOOD, 3)
                .pattern("##").pattern("##").define('#', ItemRegistry.STRIPPED_BAOBAB_LOG)
                .group("bark").unlockedBy("has_log", has(ItemRegistry.STRIPPED_BAOBAB_LOG))
                .save(output, NekomasFixed.id("stripped_baobab_wood"));

        baobabSimple(output);
    }

    private void baobabSimple(RecipeOutput output) {
        Item planks = ItemRegistry.BAOBAB_PLANKS;
        Item log = ItemRegistry.BAOBAB_LOG;
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ItemRegistry.BAOBAB_BUTTON)
                .requires(planks).group("wooden_button")
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_button"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BAOBAB_DOOR, 3)
                .pattern("##").pattern("##").pattern("##").define('#', planks)
                .group("door").unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_door"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.BAOBAB_FENCE, 3)
                .pattern("W#W").pattern("W#W").define('#', Items.STICK).define('W', planks)
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_fence"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BAOBAB_FENCE_GATE)
                .pattern("#W#").pattern("#W#").define('#', Items.STICK).define('W', planks)
                .group("fence_gate").unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_fence_gate"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_SLAB, 6)
                .pattern("###").define('#', planks).group("wooden_slab")
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_slab"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.BAOBAB_STAIRS, 4)
                .pattern("#  ").pattern("## ").pattern("###").define('#', planks).group("wooden_stairs")
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_stairs"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BAOBAB_TRAPDOOR, 2)
                .pattern("###").pattern("###").define('#', planks).group("wooden_trapdoor")
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_trapdoor"));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BAOBAB_PRESSURE_PLATE)
                .pattern("##").define('#', planks).group("wooden_pressure_plate")
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_pressure_plate"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.BAOBAB_SIGN, 3)
                .pattern("###").pattern("###").pattern(" X ").define('#', planks).define('X', Items.STICK)
                .group("sign").unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_sign"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.BAOBAB_HANGING_SIGN, 6)
                .pattern("X X").pattern("###").pattern("###").define('#', log).define('X', Items.CHAIN)
                .group("hanging_sign").unlockedBy("has_log", has(log))
                .save(output, NekomasFixed.id("baobab_hanging_sign"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.BAOBAB_BOAT)
                .pattern("# #").pattern("###").define('#', planks).group("boat")
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_boat"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.BAOBAB_CHEST_BOAT)
                .requires(Items.CHEST).requires(planks).group("chest_boat")
                .unlockedBy("has_planks", has(planks))
                .save(output, NekomasFixed.id("baobab_chest_boat"));
    }

    private void generateHollowLogRecipes(RecipeOutput output) {
        // Each hollow log/stem/block crafts back into that wood's planks.
        // Vanilla woods give vanilla planks; baobab gives the mod's baobab planks.
        record Pair(Item hollow, Item planks) {
        }
        Pair[] pairs = {
                new Pair(ItemRegistry.HOLLOW_OAK_LOG, Items.OAK_PLANKS),
                new Pair(ItemRegistry.HOLLOW_SPRUCE_LOG, Items.SPRUCE_PLANKS),
                new Pair(ItemRegistry.HOLLOW_BIRCH_LOG, Items.BIRCH_PLANKS),
                new Pair(ItemRegistry.HOLLOW_JUNGLE_LOG, Items.JUNGLE_PLANKS),
                new Pair(ItemRegistry.HOLLOW_ACACIA_LOG, Items.ACACIA_PLANKS),
                new Pair(ItemRegistry.HOLLOW_DARK_OAK_LOG, Items.DARK_OAK_PLANKS),
                new Pair(ItemRegistry.HOLLOW_MANGROVE_LOG, Items.MANGROVE_PLANKS),
                new Pair(ItemRegistry.HOLLOW_CHERRY_LOG, Items.CHERRY_PLANKS),
                new Pair(ItemRegistry.HOLLOW_BAMBOO_BLOCK, Items.BAMBOO_PLANKS),
                new Pair(ItemRegistry.HOLLOW_CRIMSON_STEM, Items.CRIMSON_PLANKS),
                new Pair(ItemRegistry.HOLLOW_WARPED_STEM, Items.WARPED_PLANKS),
                new Pair(ItemRegistry.HOLLOW_BAOBAB_LOG, ItemRegistry.BAOBAB_PLANKS)
        };
        for (Pair p : pairs) {
            String wood = BuiltInRegistries.ITEM.getKey(p.planks()).getPath().replace("_planks", "");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, p.planks())
                    .requires(p.hollow()).group("planks")
                    .unlockedBy("has_hollow_log", has(p.hollow()))
                    .save(output, NekomasFixed.id(wood + "_planks_from_hollow_log"));
        }
    }

    private void generateStrippedHollowLogRecipes(RecipeOutput output) {
        // Stripped hollow logs craft back into that wood's planks, mirroring the
        // unstripped ones (upstream #74 added no recipes; these are port-added).
        record Pair(Item hollow, Item planks) {
        }
        Pair[] pairs = {
                new Pair(ItemRegistry.HOLLOW_STRIPPED_OAK_LOG, Items.OAK_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_SPRUCE_LOG, Items.SPRUCE_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_BIRCH_LOG, Items.BIRCH_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_JUNGLE_LOG, Items.JUNGLE_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_ACACIA_LOG, Items.ACACIA_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG, Items.DARK_OAK_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_MANGROVE_LOG, Items.MANGROVE_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_CHERRY_LOG, Items.CHERRY_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK, Items.BAMBOO_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_CRIMSON_STEM, Items.CRIMSON_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_WARPED_STEM, Items.WARPED_PLANKS),
                new Pair(ItemRegistry.HOLLOW_STRIPPED_BAOBAB_LOG, ItemRegistry.BAOBAB_PLANKS)
        };
        for (Pair p : pairs) {
            String wood = BuiltInRegistries.ITEM.getKey(p.planks()).getPath().replace("_planks", "");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, p.planks())
                    .requires(p.hollow()).group("planks")
                    .unlockedBy("has_hollow_log", has(p.hollow()))
                    .save(output, NekomasFixed.id(wood + "_planks_from_stripped_hollow_log"));
        }
    }

    private void generateCakesAndDyes(RecipeOutput output) {
        // Cakes: all share the ADA/BEB/CCC shaped pattern; only the flavour item (D) differs.
        // RecipeCategory.FOOD puts the advancement under food/ and, via
        // RecipeBuilder.determineBookCategory, writes the recipe category as misc
        // (matching the committed output). Cakes have no loot (drop nothing on mine).
        record Cake(Item result, Item flavour) {
        }
        Cake[] cakes = {
                new Cake(ItemRegistry.APPLE_CAKE, Items.APPLE),
                new Cake(ItemRegistry.BEETROOT_CAKE, Items.BEETROOT),
                new Cake(ItemRegistry.CHOCOLATE_CAKE, Items.COCOA_BEANS),
                new Cake(ItemRegistry.COOKIE_CAKE, Items.COOKIE),
                new Cake(ItemRegistry.GLOWBERRY_CAKE, Items.GLOW_BERRIES),
                new Cake(ItemRegistry.PAN_CAKE, Items.HONEY_BOTTLE),
                new Cake(ItemRegistry.SWEETBERRY_CAKE, Items.SWEET_BERRIES),
                new Cake(ItemRegistry.VANILLA_CAKE, Items.MILK_BUCKET)
        };
        for (Cake c : cakes) {
            String name = BuiltInRegistries.ITEM.getKey(c.result()).getPath();
            ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, c.result())
                    .pattern("ADA").pattern("BEB").pattern("CCC")
                    .define('A', Items.MILK_BUCKET).define('B', Items.SUGAR)
                    .define('C', Items.WHEAT).define('D', c.flavour()).define('E', Items.EGG)
                    .unlockedBy("has_ingredients", has(Items.SUGAR))
                    .save(output, NekomasFixed.id(name));
        }

        // Ancient dyes: amber/aqua mix a base dye with white; indigo/maroon come from flowers.
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.AMBER_DYE, 2)
                .requires(ItemRegistry.MAROON_DYE).requires(Items.WHITE_DYE)
                .group("amber_dye").unlockedBy("has_dye", has(ItemRegistry.MAROON_DYE))
                .save(output, NekomasFixed.id("amber_dye"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.AQUA_DYE, 2)
                .requires(ItemRegistry.INDIGO_DYE).requires(Items.WHITE_DYE)
                .group("aqua_dye").unlockedBy("has_dye", has(ItemRegistry.INDIGO_DYE))
                .save(output, NekomasFixed.id("aqua_dye"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.INDIGO_DYE, 2)
                .requires(Items.PITCHER_PLANT)
                .group("indigo_dye").unlockedBy("has_pitcher_plant", has(Items.PITCHER_PLANT))
                .save(output, NekomasFixed.id("indigo_dye"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MAROON_DYE, 2)
                .requires(Items.TORCHFLOWER)
                .group("maroon_dye").unlockedBy("has_torchflower", has(Items.TORCHFLOWER))
                .save(output, NekomasFixed.id("maroon_dye"));

        // Pearl block: four pearls craft into one block.
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PEARL_BLOCK)
                .pattern("##").pattern("##").define('#', ItemRegistry.PEARL)
                .unlockedBy("has_pearl", has(ItemRegistry.PEARL))
                .save(output, NekomasFixed.id("pearl_block"));

        // Single-item recipes: glow torch and glistering melon (misc category).
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GLOW_TORCH, 4)
                .pattern("X").pattern("#").define('X', Items.GLOW_INK_SAC).define('#', Items.STICK)
                .unlockedBy("has_glow_ink_sac", has(Items.GLOW_INK_SAC))
                .save(output, NekomasFixed.id("glow_torch"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GLISTERING_MELON)
                .pattern("###").pattern("###").pattern("###").define('#', Items.GLISTERING_MELON_SLICE)
                .unlockedBy("has_glistering_melon_slice", has(Items.GLISTERING_MELON_SLICE))
                .save(output, NekomasFixed.id("glistering_melon"));

        // Redstone Striker: flint + gold + redstone.
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.REDSTONE_STRIKER)
                .pattern("RG").pattern("FR")
                .define('R', Items.REDSTONE).define('G', Items.GOLD_INGOT).define('F', Items.FLINT)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(output, NekomasFixed.id("redstone_striker"));

        // NOTE: the vanilla cake / flower-dye recipe overrides (result air) stay hand-written
        // in resources. Fabric's recipe builders reject an air result ("Item must not be
        // minecraft:air"), so datagen can't express those void-result overrides.
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        generateBaobabRecipes(output);
        generateHollowLogRecipes(output);
        generateStrippedHollowLogRecipes(output);
        generateCakesAndDyes(output);
        generateTurtleRecipes(output);
        generateSlingshotRecipe(output);

        for (AllDyes dye : AllDyes.values()) {
            Item dyeItem = dyeItem(dye);
            Item bricks = BlockDyeMap.BRICKS.get(dye).asItem();
            Item slab = BlockDyeMap.BRICK_SLAB.get(dye).asItem();
            Item stairs = BlockDyeMap.BRICK_STAIRS.get(dye).asItem();
            Item wall = BlockDyeMap.BRICK_WALL.get(dye).asItem();
            String c = dye.getSerializedName();

            ring(output, c + "_bricks_dyed", "dyed_bricks_dyed", Items.BRICKS, dyeItem,
                    bricks, 8, "has_plain_bricks", Items.BRICKS, "has_needed_dye", dyeItem);

            slabBuild(output, c + "_brick_slab", "dyed_brick_slab", bricks, slab, 6, "has_blocks", bricks);
            stonecutting(output, c + "_brick_slab_from_" + c + "_bricks_stonecutting", bricks, slab, 2, "has_blocks", bricks);
            ring(output, c + "_brick_slab_dyed", "dyed_brick_slab_dyed", Items.BRICK_SLAB, dyeItem,
                    slab, 8, "has_slab", Items.BRICK_SLAB, "has_needed_dye", dyeItem);

            stairsBuild(output, c + "_brick_stairs", "dyed_brick_stairs", bricks, stairs, "has_bricks", bricks);
            stonecutting(output, c + "_brick_stairs_from_" + c + "_bricks_stonecutting", bricks, stairs, 1, "has_bricks", bricks);
            ring(output, c + "_brick_stairs_dyed", "dyed_brick_stairs_dyed", Items.BRICK_STAIRS, dyeItem,
                    stairs, 8, "has_stairs", Items.BRICK_STAIRS, "has_needed_dye", dyeItem);

            wallBuild(output, c + "_brick_wall", "dyed_brick_wall", bricks, wall, "has_bricks", bricks);
            stonecutting(output, c + "_brick_wall_from_" + c + "_bricks_stonecutting", bricks, wall, 1, "has_bricks", bricks);
            ring(output, c + "_brick_wall_dyed", "dyed_brick_wall_dyed", Items.BRICK_WALL, dyeItem,
                    wall, 8, "has_walls", Items.BRICK_WALL, "has_needed_dye", dyeItem);

            recolour(output, "dye_" + c + "_spotted_wool", "spotted_wool",
                    ModTags.SPOTTED_WOOL_ITEM, dyeItem, BlockDyeMap.SPOTTED_WOOL.get(dye).asItem(),
                    RecipeCategory.BUILDING_BLOCKS, CraftingBookCategory.BUILDING, "has_needed_dye", has(dyeItem));
            recolour(output, "dye_" + c + "_spotted_carpet", "spotted_carpet",
                    ModTags.SPOTTED_CARPET_ITEM, dyeItem, BlockDyeMap.SPOTTED_CARPET.get(dye).asItem(),
                    RecipeCategory.DECORATIONS, CraftingBookCategory.BUILDING, "has_needed_dye", has(dyeItem));

            // Blame Akshaj
            carpetCraft(output, c + "_spotted_carpet",
                    BlockDyeMap.SPOTTED_WOOL.get(dye).asItem(),
                    BlockDyeMap.SPOTTED_CARPET.get(dye).asItem());
        }
        generateAncientWoolCarpet(output);
        generateAncientGlass(output);
        generateAncientCandle(output);
        generateAncientBed(output);
        generateAncientShulkerBox(output);
        generateAncientConcreteTerracotta(output);
        generateAncientGlazedTerracotta(output);

        BlockDyeMap.FROGLIGHT.forEach((dye, block) ->
                recolour(output, "dye_" + BuiltInRegistries.BLOCK.getKey(block).getPath(), "froglight",
                        ModTags.FROGLIGHTS_ITEM, dyeItem(dye), block.asItem(),
                        RecipeCategory.DECORATIONS, CraftingBookCategory.MISC, "has_item", has(ModTags.FROGLIGHTS_ITEM)));
    }

    private void generateTurtleRecipes(RecipeOutput output) {
        Item scute = Items.TURTLE_SCUTE;
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.TURTLE_CHESTPLATE)
                .pattern("# #").pattern("###").pattern("###").define('#', scute)
                .unlockedBy("has_turtle_scute", has(scute))
                .save(output, NekomasFixed.id("turtle_chestplate"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.TURTLE_LEGGINGS)
                .pattern("###").pattern("# #").pattern("# #").define('#', scute)
                .unlockedBy("has_turtle_scute", has(scute))
                .save(output, NekomasFixed.id("turtle_leggings"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.TURTLE_BOOTS)
                .pattern("# #").pattern("# #").define('#', scute)
                .unlockedBy("has_turtle_scute", has(scute))
                .save(output, NekomasFixed.id("turtle_boots"));
    }

    private void generateSlingshotRecipe(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SLINGSHOT)
                .pattern(" X")
                .pattern("#$")
                .define('X', Items.STRING)
                .define('#', Items.STICK)
                .define('$', Items.LEATHER)
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(output, NekomasFixed.id("slingshot"));
    }

    private void generateAncientWoolCarpet(RecipeOutput output) {
        // Ancient-dye plain wool/carpet. Recolour from the vanilla wool / wool_carpets
        // item tags + the ancient dye; carpet also has a base 2-wools craft (like vanilla).
        recolour(output, "dye_amber_wool", "wool", ItemTags.WOOL, ItemRegistry.AMBER_DYE, ItemRegistry.AMBER_WOOL,
                RecipeCategory.BUILDING_BLOCKS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.AMBER_DYE));
        recolour(output, "dye_aqua_wool", "wool", ItemTags.WOOL, ItemRegistry.AQUA_DYE, ItemRegistry.AQUA_WOOL,
                RecipeCategory.BUILDING_BLOCKS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.AQUA_DYE));
        recolour(output, "dye_indigo_wool", "wool", ItemTags.WOOL, ItemRegistry.INDIGO_DYE, ItemRegistry.INDIGO_WOOL,
                RecipeCategory.BUILDING_BLOCKS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.INDIGO_DYE));
        recolour(output, "dye_maroon_wool", "wool", ItemTags.WOOL, ItemRegistry.MAROON_DYE, ItemRegistry.MAROON_WOOL,
                RecipeCategory.BUILDING_BLOCKS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.MAROON_DYE));

        recolour(output, "dye_amber_carpet", "wool_carpets", ItemTags.WOOL_CARPETS, ItemRegistry.AMBER_DYE, ItemRegistry.AMBER_CARPET,
                RecipeCategory.DECORATIONS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.AMBER_DYE));
        recolour(output, "dye_aqua_carpet", "wool_carpets", ItemTags.WOOL_CARPETS, ItemRegistry.AQUA_DYE, ItemRegistry.AQUA_CARPET,
                RecipeCategory.DECORATIONS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.AQUA_DYE));
        recolour(output, "dye_indigo_carpet", "wool_carpets", ItemTags.WOOL_CARPETS, ItemRegistry.INDIGO_DYE, ItemRegistry.INDIGO_CARPET,
                RecipeCategory.DECORATIONS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.INDIGO_DYE));
        recolour(output, "dye_maroon_carpet", "wool_carpets", ItemTags.WOOL_CARPETS, ItemRegistry.MAROON_DYE, ItemRegistry.MAROON_CARPET,
                RecipeCategory.DECORATIONS, CraftingBookCategory.BUILDING, "has_needed_dye", has(ItemRegistry.MAROON_DYE));

        carpetCraft(output, "amber_carpet", ItemRegistry.AMBER_WOOL, ItemRegistry.AMBER_CARPET);
        carpetCraft(output, "aqua_carpet", ItemRegistry.AQUA_WOOL, ItemRegistry.AQUA_CARPET);
        carpetCraft(output, "indigo_carpet", ItemRegistry.INDIGO_WOOL, ItemRegistry.INDIGO_CARPET);
        carpetCraft(output, "maroon_carpet", ItemRegistry.MAROON_WOOL, ItemRegistry.MAROON_CARPET);
    }

    // Ancient-colour stained glass + panes. Three recipes per colour, mirroring main:
    //   {c}_stained_glass                     : dye-ring around glass     -> 8 block
    //   {c}_stained_glass_pane                : 6 coloured blocks          -> 16 pane
    //   {c}_stained_glass_pane_from_glass_pane: dye-ring around glass_pane -> 8 pane
    private void generateAncientGlass(RecipeOutput output) {
        record GlassSet(Item glass, Item pane, Item dye, Item glassPane) {
        }
        GlassSet[] sets = {
                new GlassSet(ItemRegistry.AMBER_STAINED_GLASS, ItemRegistry.AMBER_STAINED_GLASS_PANE, ItemRegistry.AMBER_DYE, Items.GLASS_PANE),
                new GlassSet(ItemRegistry.AQUA_STAINED_GLASS, ItemRegistry.AQUA_STAINED_GLASS_PANE, ItemRegistry.AQUA_DYE, Items.GLASS_PANE),
                new GlassSet(ItemRegistry.INDIGO_STAINED_GLASS, ItemRegistry.INDIGO_STAINED_GLASS_PANE, ItemRegistry.INDIGO_DYE, Items.GLASS_PANE),
                new GlassSet(ItemRegistry.MAROON_STAINED_GLASS, ItemRegistry.MAROON_STAINED_GLASS_PANE, ItemRegistry.MAROON_DYE, Items.GLASS_PANE),
        };
        for (GlassSet s : sets) {
            String name = BuiltInRegistries.ITEM.getKey(s.glass()).getPath(); // e.g. amber_stained_glass
            ring(output, name, "stained_glass", Items.GLASS, s.dye(), s.glass(), 8,
                    "has_glass", Items.GLASS, "has_needed_dye", s.dye());
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, s.pane(), 16)
                    .pattern("###").pattern("###").define('#', s.glass())
                    .group("stained_glass_pane").unlockedBy("has_stained_glass", has(s.glass()))
                    .save(output, NekomasFixed.id(name + "_pane"));
            ring(output, name + "_pane_from_glass_pane", "stained_glass_pane", Items.GLASS_PANE, s.dye(), s.pane(), 8,
                    "has_glass_pane", Items.GLASS_PANE, "has_needed_dye", s.dye());
        }
    }

    // Ancient-colour candles: dye + vanilla candle -> {c}_candle (unshaped, group dyed_candle).
    private void generateAncientCandle(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.AMBER_CANDLE)
                .requires(ItemRegistry.AMBER_DYE).requires(Items.CANDLE)
                .group("dyed_candle").unlockedBy("has_needed_dye", has(ItemRegistry.AMBER_DYE))
                .save(output, NekomasFixed.id("amber_candle"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.AQUA_CANDLE)
                .requires(ItemRegistry.AQUA_DYE).requires(Items.CANDLE)
                .group("dyed_candle").unlockedBy("has_needed_dye", has(ItemRegistry.AQUA_DYE))
                .save(output, NekomasFixed.id("aqua_candle"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.INDIGO_CANDLE)
                .requires(ItemRegistry.INDIGO_DYE).requires(Items.CANDLE)
                .group("dyed_candle").unlockedBy("has_needed_dye", has(ItemRegistry.INDIGO_DYE))
                .save(output, NekomasFixed.id("indigo_candle"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.MAROON_CANDLE)
                .requires(ItemRegistry.MAROON_DYE).requires(Items.CANDLE)
                .group("dyed_candle").unlockedBy("has_needed_dye", has(ItemRegistry.MAROON_DYE))
                .save(output, NekomasFixed.id("maroon_candle"));
    }

    // Ancient-colour beds: a plain 3-wool + planks craft (like vanilla beds) plus a recolour
    // of any bed (#minecraft:beds) with the ancient dye. Main used crafting_transmute for the
    // latter; 1.21.1 uses the mod's recolour recipe instead.
    private void generateAncientBed(RecipeOutput output) {
        record BedSet(Item bed, Item wool, Item dye) {
        }
        BedSet[] sets = {
                new BedSet(ItemRegistry.AMBER_BED, ItemRegistry.AMBER_WOOL, ItemRegistry.AMBER_DYE),
                new BedSet(ItemRegistry.AQUA_BED, ItemRegistry.AQUA_WOOL, ItemRegistry.AQUA_DYE),
                new BedSet(ItemRegistry.INDIGO_BED, ItemRegistry.INDIGO_WOOL, ItemRegistry.INDIGO_DYE),
                new BedSet(ItemRegistry.MAROON_BED, ItemRegistry.MAROON_WOOL, ItemRegistry.MAROON_DYE),
        };
        for (BedSet s : sets) {
            String name = BuiltInRegistries.ITEM.getKey(s.bed()).getPath(); // e.g. amber_bed
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, s.bed())
                    .pattern("DDD").pattern("###")
                    .define('D', s.wool()).define('#', ItemTags.PLANKS)
                    .group("bed").unlockedBy("has_wool", has(s.wool()))
                    .save(output, NekomasFixed.id(name));
            recolour(output, "dye_" + name, "bed_dye", ItemTags.BEDS, s.dye(), s.bed(),
                    RecipeCategory.DECORATIONS, CraftingBookCategory.MISC, "has_dye", has(s.dye()));
        }
    }


    // Ancient-colour shulker boxes: recolour any shulker box (#minecraft:shulker_boxes)
    // with the ancient dye. Main used crafting_transmute; 1.21.1 uses the mod's recolour recipe.
    // (1.21.1 has ItemTags.SHULKER_BOXES absent, so reference the vanilla tag by name.)
    private void generateAncientShulkerBox(RecipeOutput output) {
        TagKey<Item> shulkerTag = TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("shulker_boxes"));
        record ShulkerSet(Item box, Item dye) {
        }
        ShulkerSet[] sets = {
                new ShulkerSet(ItemRegistry.AMBER_SHULKER_BOX, ItemRegistry.AMBER_DYE),
                new ShulkerSet(ItemRegistry.AQUA_SHULKER_BOX, ItemRegistry.AQUA_DYE),
                new ShulkerSet(ItemRegistry.INDIGO_SHULKER_BOX, ItemRegistry.INDIGO_DYE),
                new ShulkerSet(ItemRegistry.MAROON_SHULKER_BOX, ItemRegistry.MAROON_DYE),
        };
        for (ShulkerSet s : sets) {
            String name = BuiltInRegistries.ITEM.getKey(s.box()).getPath(); // e.g. amber_shulker_box
            recolour(output, name, "shulker_box_dye", shulkerTag, s.dye(), s.box(),
                    RecipeCategory.DECORATIONS, CraftingBookCategory.MISC, "has_item", has(s.dye()));
        }
    }

    // Ancient-dye terracotta: plain terracotta dye-ring -> 8 baked terracotta (as main).
    // Ancient-dye concrete powder: dye + sand + gravel shapeless -> 8 powder (no concrete
    // craft; concrete is the hardened form that powder turns into on water contact).
    private void generateAncientConcreteTerracotta(RecipeOutput output) {
        record StoneSet(Item terracotta, Item powder, Item dye) {
        }
        StoneSet[] sets = {
                new StoneSet(ItemRegistry.AMBER_TERRACOTTA, ItemRegistry.AMBER_CONCRETE_POWDER, ItemRegistry.AMBER_DYE),
                new StoneSet(ItemRegistry.AQUA_TERRACOTTA, ItemRegistry.AQUA_CONCRETE_POWDER, ItemRegistry.AQUA_DYE),
                new StoneSet(ItemRegistry.INDIGO_TERRACOTTA, ItemRegistry.INDIGO_CONCRETE_POWDER, ItemRegistry.INDIGO_DYE),
                new StoneSet(ItemRegistry.MAROON_TERRACOTTA, ItemRegistry.MAROON_CONCRETE_POWDER, ItemRegistry.MAROON_DYE),
        };
        for (StoneSet s : sets) {
            String name = BuiltInRegistries.ITEM.getKey(s.terracotta()).getPath(); // e.g. amber_terracotta
            ring(output, name, "dyed_terracotta", Items.TERRACOTTA, s.dye(), s.terracotta(), 8,
                    "has_terracotta", Items.TERRACOTTA, "has_needed_dye", s.dye());
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, s.powder(), 8)
                    .requires(s.dye())
                    .requires(Items.SAND).requires(Items.SAND).requires(Items.SAND).requires(Items.SAND)
                    .requires(Items.GRAVEL).requires(Items.GRAVEL).requires(Items.GRAVEL).requires(Items.GRAVEL)
                    .group("concrete_powder")
                    .unlockedBy("has_sand", has(Items.SAND))
                    .unlockedBy("has_gravel", has(Items.GRAVEL))
                    .save(output, NekomasFixed.id(BuiltInRegistries.ITEM.getKey(s.powder()).getPath()));
        }
    }

    // Ancient-dye glazed terracotta: smelt the baked terracotta to glaze it (vanilla-style,
    // 0.1 xp / 200 ticks / blocks category). Unlocks on the baked terracotta item, matching main.
    private void generateAncientGlazedTerracotta(RecipeOutput output) {
        record GlazeSet(Item baked, Item glazed) {
        }
        GlazeSet[] sets = {
                new GlazeSet(ItemRegistry.AMBER_TERRACOTTA, ItemRegistry.AMBER_GLAZED_TERRACOTTA),
                new GlazeSet(ItemRegistry.AQUA_TERRACOTTA, ItemRegistry.AQUA_GLAZED_TERRACOTTA),
                new GlazeSet(ItemRegistry.INDIGO_TERRACOTTA, ItemRegistry.INDIGO_GLAZED_TERRACOTTA),
                new GlazeSet(ItemRegistry.MAROON_TERRACOTTA, ItemRegistry.MAROON_GLAZED_TERRACOTTA),
        };
        for (GlazeSet s : sets) {
            String name = BuiltInRegistries.ITEM.getKey(s.glazed()).getPath(); // e.g. amber_glazed_terracotta
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(s.baked()), RecipeCategory.BUILDING_BLOCKS,
                            s.glazed(), 0.1F, 200)
                    .unlockedBy("has_baked_terracotta", has(s.baked()))
                    .save(output, NekomasFixed.id(name));
        }
    }

    private void carpetCraft(RecipeOutput output, String rid, Item wool, Item carpet) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, carpet, 3)
                .pattern("##").define('#', wool)
                .group("carpet").unlockedBy("has_wool", has(wool))
                .save(output, NekomasFixed.id(rid));
    }

    private void ring(RecipeOutput output, String rid, String group, Item outside, Item inside,
                      Item result, int count, String cn1, Item c1, String cn2, Item c2) {
        // Dye-ring crafts unlock on the input block OR the dye (consistent across
        // all shapes; the base-bricks committed advancement already used both).
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, count)
                .pattern("###")
                .pattern("#D#")
                .pattern("###")
                .define('#', outside)
                .define('D', inside)
                .group(group)
                .unlockedBy(cn1, has(c1))
                .unlockedBy(cn2, has(c2));
        builder.save(output, NekomasFixed.id(rid));
    }

    private void slabBuild(RecipeOutput output, String rid, String group, Item bricks, Item slab,
                           int count, String crit, Item critItem) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, count)
                .pattern("###")
                .define('#', bricks)
                .group(group)
                .unlockedBy(crit, has(critItem));
        builder.save(output, NekomasFixed.id(rid));
    }

    private void stairsBuild(RecipeOutput output, String rid, String group, Item bricks, Item stairs,
                             String crit, Item critItem) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', bricks)
                .group(group)
                .unlockedBy(crit, has(critItem));
        builder.save(output, NekomasFixed.id(rid));
    }

    private void wallBuild(RecipeOutput output, String rid, String group, Item bricks, Item wall,
                           String crit, Item critItem) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .pattern("###")
                .pattern("###")
                .define('#', bricks)
                .group(group)
                .unlockedBy(crit, has(critItem));
        builder.save(output, NekomasFixed.id(rid));
    }

    private void stonecutting(RecipeOutput output, String rid, Item input, Item result, int count,
                              String crit, Item critItem) {
        SingleItemRecipeBuilder builder = count == 1
                ? SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, result)
                : SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, result, count);
        builder.unlockedBy(crit, has(critItem));
        builder.save(output, NekomasFixed.id(rid));
    }

    private void recolour(RecipeOutput output, String rid, String group,
                          net.minecraft.tags.TagKey<net.minecraft.world.item.Item> inputTag, Item material,
                          Item result, RecipeCategory category, CraftingBookCategory bookCategory,
                          String criterionName, Criterion<?> criterion) {
        RecolourRecipeBuilder.recolour(category, bookCategory,
                        Ingredient.of(inputTag), Ingredient.of(material), new ItemStack(result), group)
                .unlockedBy(criterionName, criterion)
                .save(output, NekomasFixed.id(rid));
    }
}
