package net.greenjab.nekomasfixed.registry.recipe.book;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeBookGroup;

import java.util.List;

public class KilnRecipeBookGroup implements RecipeBookGroup {
    public static final KilnRecipeBookGroup KILN_SEARCH = new KilnRecipeBookGroup(true);
    public static final KilnRecipeBookGroup KILN_MISC = new KilnRecipeBookGroup(false);

    private final boolean isSearch;

    private KilnRecipeBookGroup(boolean isSearch) {

        this.isSearch = isSearch;
    }

    public static List<RecipeBookGroup> getGroups() {
        return ImmutableList.of(KILN_SEARCH, KILN_MISC);
    }


    public boolean isSearch() {
        return this.isSearch;
    }


    public List<ItemStack> getIcons() {
        if (this == KILN_SEARCH) {
            return ImmutableList.of(new ItemStack(Items.COMPASS));
        }
        return ImmutableList.of(new ItemStack(Items.BRICK));
    }
}