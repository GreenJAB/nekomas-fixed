package net.greenjab.nekomasfixed.util;

import java.util.List;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public class ModRecipeBookType extends RecipeBookCategory implements ExtendedRecipeBookCategory {
    private final List<RecipeBookCategory> categories;

    public ModRecipeBookType(final RecipeBookCategory... categories) {
        this.categories = List.of(categories);
    }

    public List<RecipeBookCategory> getCategories() {
        return this.categories;
    }
}