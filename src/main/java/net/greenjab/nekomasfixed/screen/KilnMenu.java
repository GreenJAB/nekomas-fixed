package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;

public class KilnMenu extends AbstractFurnaceMenu {

    public KilnMenu(final int containerId, final Inventory inventory) {
        super(ScreenHandlerRegistry.KILN, RecipeRegistry.KILN_INPUT, RecipeBookType.FURNACE, containerId, inventory);
    }

    public KilnMenu(final int containerId, final Inventory inventory, final Container container, final ContainerData data) {
        super(ScreenHandlerRegistry.KILN, RecipeRegistry.KILN_INPUT, RecipeBookType.FURNACE, containerId, inventory, container, data);
    }
}