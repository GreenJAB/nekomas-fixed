package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.recipe.CustomTippedArrowRecipe;
import net.greenjab.nekomasfixed.registry.recipe.KilnRecipe;
import net.greenjab.nekomasfixed.util.ModRecipeBookType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.*;

public class RecipeRegistry {

    public static void registerRecipes() {
        System.out.println("Registering Mod Recipes");
    }

    public static final ResourceKey<RecipePropertySet> KILN_INPUT = registerRecipePropertySet("kiln_input");
    public static final ResourceKey<RecipePropertySet> FLETCHING_INPUT = registerRecipePropertySet("fletching_input");
    private static ResourceKey<RecipePropertySet> registerRecipePropertySet(String id) {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, NekomasFixed.id(id));
    }

    public static final RecipeType<KilnRecipe> KILN = registerRecipeType("kiln");
    public static final RecipeType<CustomTippedArrowRecipe> CUSTOM_TIPPED_ARROW_RECIPE = registerRecipeType("custom_tipped_arrow");

    static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String id) {
        return Registry.register(
                BuiltInRegistries.RECIPE_TYPE,
                NekomasFixed.id(id),
                new RecipeType<>() {
                    @Override
                    public String toString() {
                        return "nekomasfixed:" + id;
                    }
                }
        );
    }

    public static RecipeBookCategory KILNING_BLOCK = Registry.register(
            BuiltInRegistries.RECIPE_BOOK_CATEGORY,
            NekomasFixed.id("kilning_block"),
            new RecipeBookCategory()
    );
    public static RecipeBookCategory KILNING_MISC = Registry.register(
            BuiltInRegistries.RECIPE_BOOK_CATEGORY,
            NekomasFixed.id("kilning_misc"),
            new RecipeBookCategory()
    );
    public static RecipeBookCategory FLETCHING_QUIVER = Registry.register(
            BuiltInRegistries.RECIPE_BOOK_CATEGORY,
            NekomasFixed.id("fletching_quiver"),
            new RecipeBookCategory()
    );
   public static final ModRecipeBookType KILNING = new ModRecipeBookType(RecipeRegistry.KILNING_BLOCK, RecipeRegistry.KILNING_MISC);
   public static final ModRecipeBookType FLETCHING = new ModRecipeBookType(RecipeRegistry.FLETCHING_QUIVER);
}