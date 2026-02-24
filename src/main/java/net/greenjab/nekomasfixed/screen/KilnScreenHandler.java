package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class KilnScreenHandler extends AbstractFurnaceScreenHandler {

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4));
    }

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory,
                             Inventory inventory, PropertyDelegate propertyDelegate) {
        super(
                ScreenHandlerRegistry.KILN_SCREEN_HANDLER,
                RecipeRegistry.KILN_RECIPE_TYPE,
                null,
                RecipeBookType.FURNACE,
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return super.quickMove(player, slot);
    }

    @Override
    protected boolean isSmeltable(ItemStack itemStack) {
        return super.isSmeltable(itemStack);
    }
}