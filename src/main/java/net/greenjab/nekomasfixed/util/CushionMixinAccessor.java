package net.greenjab.nekomasfixed.util;

import net.minecraft.world.item.ItemStack;

public interface CushionMixinAccessor {
    String nekomas$getModColor();
    void nekomas$setModColor(String color);
    default ItemStack nekomas$asCushionItemStack() {
        return ItemStack.EMPTY;
    }
}