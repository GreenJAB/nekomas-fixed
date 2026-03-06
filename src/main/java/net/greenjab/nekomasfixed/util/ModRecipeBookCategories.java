package net.greenjab.nekomasfixed.util;

import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeBookCategories {
    public static final RecipeBookCategory KILNING_MISC = register("kilning_misc");

    private static RecipeBookCategory register(String id) {
        return (RecipeBookCategory) Registry.register(Registries.RECIPE_BOOK_CATEGORY, id, new RecipeBookCategory());
    }
}
