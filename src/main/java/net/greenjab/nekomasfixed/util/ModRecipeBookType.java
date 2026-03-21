package net.greenjab.nekomasfixed.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookGroup;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ModRecipeBookType extends RecipeBookCategory implements RecipeBookGroup {
    private final List<RecipeBookCategory> categories;

    public ModRecipeBookType(final RecipeBookCategory... categories) {
        this.categories = List.of(categories);
    }

    public List<RecipeBookCategory> getCategories() {
        return this.categories;
    }
}