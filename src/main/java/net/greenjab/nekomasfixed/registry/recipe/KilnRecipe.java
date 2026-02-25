package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class KilnRecipe extends AbstractCookingRecipe {

    public KilnRecipe(String group, CookingRecipeCategory category, Ingredient ingredient,
                      ItemStack result, float experience, int cookingTime) {
        super(
                group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public RecipeSerializer<? extends AbstractCookingRecipe> getSerializer() {
        return RecipeRegistry.KILN_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends AbstractCookingRecipe> getType() {
        return RecipeRegistry.KILN_RECIPE_TYPE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    @Override
    protected Item getCookerItem() {
        return null;
    }


    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        return this.ingredient().test(input.item());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.result().copy();
    }


    public boolean fits(int width, int height) {
        return true;
    }


    public ItemStack getResult() {
        return this.result();
    }


    public Ingredient getIngredient() {
        return this.ingredient();
    }
}