package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jspecify.annotations.NonNull;


public class KilnRecipe extends AbstractCookingRecipe {
    public KilnRecipe(String string, CookingBookCategory cookingRecipeCategory, Ingredient ingredient, ItemStack itemStack, float f, int i) {
        super(string, cookingRecipeCategory, ingredient, itemStack, f, i);
    }

    protected @NonNull Item furnaceIcon() {
        return ItemRegistry.KILN;
    }

    public @NonNull RecipeSerializer<KilnRecipe> getSerializer() {
        return RecipeRegistry.KILNING_RECIPE_SERIALIZER;
    }

    public @NonNull RecipeType<KilnRecipe> getType() {
        return RecipeRegistry.KILN;
    }

    public @NonNull RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case BLOCKS -> RecipeRegistry.KILNING_BLOCK;
            case FOOD, MISC -> RecipeRegistry.KILNING_MISC;
        };
    }
}
