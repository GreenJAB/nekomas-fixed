//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.util.ModRecipeType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.book.RecipeBookCategories;
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
        return ModRecipeType.KILNING;
    }

    public RecipeBookCategory getRecipeBookCategory() {
        RecipeBookCategory var10000;
        switch (this.getCategory()) {
            case BLOCKS:
                var10000 = RecipeBookCategories.BLAST_FURNACE_BLOCKS;
                break;
            case FOOD:
            case MISC:
                var10000 = RecipeBookCategories.BLAST_FURNACE_MISC;
                break;
            default:
                try {
                    throw new Exception();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }

        return var10000;
    }
}
