package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.book.RecipeBookCategory;


public class KilnRecipe extends AbstractCookingRecipe {
    public KilnRecipe(String string, CookingRecipeCategory cookingRecipeCategory, Ingredient ingredient, ItemStack itemStack, float f, int i) {
        super(string, cookingRecipeCategory, ingredient, itemStack, f, i);
    }

    protected Item getCookerItem() {
        return ItemRegistry.KILN;
    }

    public RecipeSerializer<KilnRecipe> getSerializer() {
        return RecipeRegistry.KILNING_RECIPE_SERIALIZER;
    }

    public RecipeType<KilnRecipe> getType() {
        return RecipeRegistry.KILN;
    }

    public RecipeBookCategory getRecipeBookCategory() {
        return switch (this.getCategory()) {
            case BLOCKS -> RecipeRegistry.KILNING_BLOCK;
            case FOOD, MISC -> RecipeRegistry.KILNING_MISC;
        };
    }
}
