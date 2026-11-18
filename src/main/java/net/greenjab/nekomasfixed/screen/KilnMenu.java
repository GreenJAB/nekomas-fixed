package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;

public class KilnMenu extends AbstractFurnaceMenu {
    public KilnMenu(int id, Inventory inventory) {
        super(ScreenHandlerRegistry.KILN, RecipeRegistry.KILN, RecipeBookType.FURNACE, id, inventory);
    }

    public KilnMenu(int id, Inventory inventory, Container container, ContainerData data) {
        super(ScreenHandlerRegistry.KILN, RecipeRegistry.KILN, RecipeBookType.FURNACE, id, inventory, container, data);
    }
}
