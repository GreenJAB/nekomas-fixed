package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class KilnScreenHandler extends AbstractFurnaceMenu {

    public KilnScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public KilnScreenHandler(int syncId, Inventory playerInventory,
                             Container inventory, ContainerData propertyDelegate) {
        super(
                ScreenHandlerRegistry.KILN_SCREEN_HANDLER,
                RecipeRegistry.KILN,
                RecipeRegistry.KILN_INPUT,
                RecipeBookType.FURNACE,
                syncId,
                playerInventory,
                inventory,
                propertyDelegate
        );
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slot) {
        return super.quickMoveStack(player, slot);
    }

    @Override
    protected boolean canSmelt(@NonNull ItemStack itemStack) {
        return super.canSmelt(itemStack);
    }
}