package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class PyrotechnicsTableScreenHandler extends ScreenHandler {

    private final Inventory input = new SimpleInventory(4);
    private final Inventory output = new SimpleInventory(1);
    private final Property selectedPattern = Property.create();

    public PyrotechnicsTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public PyrotechnicsTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerRegistry.PYROTECHNICS_TABLE_HANDLER, syncId);

        this.addProperty(selectedPattern);

        // 2. THE TABLE SLOTS
        this.addSlot(new Slot(this.input, 0, 8, 16)); // Dye
        this.addSlot(new Slot(this.input, 1, 8, 35)); // Dye 2
        this.addSlot(new Slot(this.input, 2, 8, 54)); // Paper
        this.addSlot(new Slot(this.input, 3, 8, 73)); // Gunpowder

        this.addSlot(new Slot(this.output, 0, 143, 70) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        int m;
        int l;
        // Inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 104 + m * 18));
            }
        }
        // Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 162));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(slotIndex);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (slotIndex < 5) {
                if (!this.insertItem(itemStack2, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, 4, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public int getSelectedPattern() {
        return this.selectedPattern.get();
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id >= 0 && id < 16) {
            this.selectedPattern.set(id);
            this.updateOutput();
            return true;
        }
        return false;
    }

    // Placeholder for the crafting logic later
    private void updateOutput() {

    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.dropInventory(player, this.input);
    }
}
