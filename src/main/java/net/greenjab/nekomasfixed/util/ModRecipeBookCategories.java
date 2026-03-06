package net.greenjab.nekomasfixed.util;

import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeBookCategories {

    public static RecipeBookCategory KILNING_MISC;

    public static void init() {
        KILNING_MISC = Registry.register(
                Registries.RECIPE_BOOK_CATEGORY,
                Identifier.of("nekomasfixed", "kilning_misc"),
                new RecipeBookCategory()
        );
    }
}