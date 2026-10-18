package net.greenjab.nekomasfixed.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class RecipeOption extends Option<Boolean>{
    private final ItemLike itemRecipe;

    public RecipeOption(String name, ItemLike item, Boolean defaultValue) {
        super(name, defaultValue);
        this.itemRecipe = item;
    }

    public Item getRecipe(){
        return this.itemRecipe.asItem();
    }
}
