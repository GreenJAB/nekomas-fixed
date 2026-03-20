package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeBookCategories {

    public static RecipeBookCategory KILNING_MISC;

    public static void init() {
        KILNING_MISC = Registry.register(
                Registries.RECIPE_BOOK_CATEGORY,
                NekomasFixed.id("kilning_misc"),
                new RecipeBookCategory()
        );
    }
}