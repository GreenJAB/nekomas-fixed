package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.registry.block.FletchingTableBlock;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Items;

public class FletchingScreen extends AbstractContainerScreen<FletchingMenu> {

    public FletchingScreen(FletchingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }


//    @Override
//    protected ScreenPosition getRecipeBookButtonPosition() {
//        return new ScreenPosition(this.leftPos + 5, this.height / 2 - 49);
//    }
}
