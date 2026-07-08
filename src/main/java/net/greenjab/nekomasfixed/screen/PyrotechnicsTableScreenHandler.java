package net.greenjab.nekomasfixed.screen;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PyrotechnicsTableScreenHandler extends AbstractContainerMenu {

    private final Container input = new SimpleContainer(14){
        @Override
        public void setChanged() {
            super.setChanged();
            slotsChanged(this);
        }
    };
    private final Container output = new SimpleContainer(1);
    private final DataSlot selectedPattern = DataSlot.standalone();

    public PyrotechnicsTableScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    public PyrotechnicsTableScreenHandler(int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(ScreenHandlerRegistry.PYROTECHNICS_TABLE_HANDLER, syncId);
        this.addDataSlot(selectedPattern);

        // dyes / stars
        this.addSlot(new Slot(this.input, 0, 8, 16)
        { @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return stack.is(Items.FIREWORK_STAR)||stack.getItem() instanceof DyeItem;}});
        this.addSlot(new Slot(this.input, 1, 8+18, 16)
        {   @Override public boolean isActive() {
                return slots.get(0).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                    : slots.get(0).getItem().is(Items.FIREWORK_STAR) &&stack.is(Items.FIREWORK_STAR);}});
        this.addSlot(new Slot(this.input, 2, 8+18*2, 16)
        {   @Override public boolean isActive() {
                return slots.get(1).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getItem().is(Items.FIREWORK_STAR) &&stack.is(Items.FIREWORK_STAR);}});
        this.addSlot(new Slot(this.input, 3, 8+18*3, 16)
        {   @Override public boolean isActive() {
                return slots.get(2).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getItem().is(Items.FIREWORK_STAR) &&stack.is(Items.FIREWORK_STAR);}});
        this.addSlot(new Slot(this.input, 4, 8+18*4, 16)
        {   @Override public boolean isActive() {
                return slots.get(3).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getItem().is(Items.FIREWORK_STAR) &&stack.is(Items.FIREWORK_STAR);}});
        // fade / gunpowder
        this.addSlot(new Slot(this.input, 5, 8, 35)
        {   @Override public boolean isActive() {
                return slots.get(0).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getItem().is(Items.FIREWORK_STAR) && stack.is(Items.GUNPOWDER);}});
        this.addSlot(new Slot(this.input, 6, 8+18, 35)
        {   @Override public boolean isActive() {
                return slots.get(5).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getItem().is(Items.FIREWORK_STAR) && stack.is(Items.GUNPOWDER);}});
        this.addSlot(new Slot(this.input, 7, 8+18*2, 35)
        {   @Override public boolean isActive() {
                return slots.get(6).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                    : slots.get(0).getItem().is(Items.FIREWORK_STAR) && stack.is(Items.GUNPOWDER);}});
        this.addSlot(new Slot(this.input, 8, 8+18*3, 35)
        {   @Override public boolean isActive() {
                return (slots.get(7).hasItem() && slots.get(0).getItem().getItem() instanceof DyeItem) || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem && stack.getItem() instanceof DyeItem;}});
        this.addSlot(new Slot(this.input, 9, 8+18*4, 35)
        {   @Override public boolean isActive() {
                return slots.get(8).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem && stack.getItem() instanceof DyeItem;}});
        // shape
        this.addSlot(new Slot(this.input, 10, 80, 54)
        {   @Override public boolean isActive() {
                return slots.get(0).getItem().getItem() instanceof DyeItem || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return isActive() && (stack.is(Items.FIRE_CHARGE)|| stack.is(Items.GOLD_NUGGET)|| stack.is(Items.CREEPER_BANNER_PATTERN)|| stack.is(Items.FEATHER));}});
        // glowstone
        this.addSlot(new Slot(this.input, 11, 80-18, 73)
        {   @Override public boolean isActive() {
                return slots.get(0).getItem().getItem() instanceof DyeItem || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return isActive() && (stack.is(Items.GLOWSTONE_DUST));}});
        // diamond
        this.addSlot(new Slot(this.input, 12, 80, 73)
        {   @Override public boolean isActive() {
                return slots.get(0).getItem().getItem() instanceof DyeItem || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return isActive() && (stack.is(Items.DIAMOND));}});
        // gunpowder / paper
        this.addSlot(new Slot(this.input, 13, 99, 73)
        {    @Override public boolean isActive() {
                return slots.get(0).hasItem() || this.hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return slots.get(0).getItem().getItem() instanceof DyeItem ? stack.is(Items.GUNPOWDER)
                    : slots.get(0).getItem().is(Items.FIREWORK_STAR) && stack.is(Items.PAPER);}});

        this.addSlot(new Slot(this.output, 0, 152, 73) {
            @Override public boolean isActive() {
                return slots.get(0).hasItem();}
            @Override public boolean mayPlace(@NonNull ItemStack stack) {
                return false;}
            @Override public void onTake(final @NonNull Player player, final @NonNull ItemStack carried) {
                slots.stream().filter(slot -> slot.container==input).forEach(slot -> slot.remove(slot.getItem().is(Items.CREEPER_BANNER_PATTERN)?0:1));}
        });

        for (int m = 0; m < 3; ++m)
            for (int l = 0; l < 9; ++l) this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 104 + m * 18));
        for (int m = 0; m < 9; ++m) this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 162));
    }

    @Override
    public void slotsChanged(@NonNull Container inventory) {
        super.slotsChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
            this.broadcastChanges();
        }
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (slotIndex < 15) {
                if (!this.moveItemStackTo(itemStack2, 15, 51, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, 14, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            slot.onTake(player, itemStack2);
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return true;
    }

    public int getSelectedPattern() {
        return this.selectedPattern.get();
    }

    @Override
    public boolean clickMenuButton(@NonNull Player player, int id) {
        if (id >= 0 && id < 8) {
            this.selectedPattern.set(id);
            return true;
        }
        return false;
    }

    private static final Map<Item, FireworkExplosion.Shape> TYPE_MODIFIER_MAP = Map.of(
            Items.FIRE_CHARGE,
            FireworkExplosion.Shape.LARGE_BALL,
            Items.FEATHER,
            FireworkExplosion.Shape.BURST,
            Items.GOLD_NUGGET,
            FireworkExplosion.Shape.STAR,
            Items.CREEPER_BANNER_PATTERN,
            FireworkExplosion.Shape.CREEPER
    );

    // Placeholder for the crafting logic later
    private void updateResult() {
        ItemStack newOutput = ItemStack.EMPTY;
        if (slots.get(0).hasItem()) {
            if (slots.get(0).getItem().getItem() instanceof DyeItem && slots.get(13).getItem().is(Items.GUNPOWDER)) {
                if (slots.stream().filter(slot -> slot.getContainerSlot()<10 && slot.hasItem() && slot.container==input).allMatch(slot -> slot.getItem().getItem() instanceof DyeItem)) {
                    FireworkExplosion.Shape type = TYPE_MODIFIER_MAP.getOrDefault(slots.get(10).getItem().getItem(), FireworkExplosion.Shape.SMALL_BALL);
                    boolean twinkle = slots.get(11).getItem().is(Items.GLOWSTONE_DUST);
                    boolean trail = slots.get(12).getItem().is(Items.DIAMOND);
                    IntList colorList = new IntArrayList();
                    slots.stream().filter(slot -> slot.getContainerSlot()<5 && slot.hasItem() && slot.container==input).forEach(slot -> {
                        Item item = slot.getItem().getItem();
                        if (item.equals(ItemRegistry.AMBER_DYE)) colorList.add(ModColors.AMBER.getColor());
                        else if (item.equals(ItemRegistry.AQUA_DYE)) colorList.add(ModColors.AQUA.getColor());
                        else if (item.equals(ItemRegistry.MAROON_DYE)) colorList.add(ModColors.MAROON.getColor());
                        else if (item.equals(ItemRegistry.INDIGO_DYE)) colorList.add(ModColors.INDIGO.getColor());
                        else colorList.add(item.components().getOrDefault(DataComponents.DYE, DyeColor.WHITE).getFireworkColor());});
                    IntList fadeList = new IntArrayList();
                    slots.stream().filter(slot -> slot.getContainerSlot()>=5 && slot.getContainerSlot()<10 && slot.hasItem() && slot.container==input).forEach(slot -> {
                        Item item = slot.getItem().getItem();
                        if (item.equals(ItemRegistry.AMBER_DYE)) fadeList.add(ModColors.AMBER.getColor());
                        else if (item.equals(ItemRegistry.AQUA_DYE)) fadeList.add(ModColors.AQUA.getColor());
                        else if (item.equals(ItemRegistry.MAROON_DYE)) fadeList.add(ModColors.MAROON.getColor());
                        else if (item.equals(ItemRegistry.INDIGO_DYE)) fadeList.add(ModColors.INDIGO.getColor());
                        else fadeList.add(item.components().getOrDefault(DataComponents.DYE, DyeColor.WHITE).getFireworkColor());});

                    newOutput = new ItemStack(Items.FIREWORK_STAR);
                    newOutput.set(DataComponents.FIREWORK_EXPLOSION, new FireworkExplosion(type, colorList, fadeList, trail, twinkle));
                }
            } else if (slots.get(0).getItem().is(Items.FIREWORK_STAR) && slots.get(13).getItem().is(Items.PAPER)) {
                if (slots.stream().filter(slot -> slot.getContainerSlot()<5 && slot.hasItem() && slot.container==input).allMatch(slot -> slot.getItem().is(Items.FIREWORK_STAR))) {
                    if (slots.stream().filter(slot -> slot.getContainerSlot() >= 5 && slot.getContainerSlot() < 10 && slot.hasItem() && slot.container==input).allMatch(slot -> slot.getItem().is(Items.GUNPOWDER))) {
                        if (!slots.get(10).hasItem()&&!slots.get(11).hasItem()&&!slots.get(12).hasItem()){
                            int i = Math.toIntExact((slots.stream().filter(slot -> slot.getContainerSlot() >= 5 && slot.getContainerSlot() < 10 && slot.hasItem() && slot.container==input).count()));
                            if (i>0) {
                                List<FireworkExplosion> list = new ArrayList<>();
                                slots.stream().filter(slot -> slot.getContainerSlot() < 5 && slot.hasItem() && slot.container == input).forEach(slot -> {
                                    FireworkExplosion fireworkExplosionComponent = slot.getItem().get(DataComponents.FIREWORK_EXPLOSION);
                                    if (fireworkExplosionComponent != null) list.add(fireworkExplosionComponent);});

                                newOutput = new ItemStack(Items.FIREWORK_ROCKET, 5);
                                newOutput.set(DataComponents.FIREWORKS, new Fireworks(i, list));
                            }
                        }
                    }
                }
            }
        }
        output.setItem(0, newOutput);
    }

    @Override
    public void removed(@NonNull Player player) {
        super.removed(player);
        this.clearContainer(player, this.input);
    }
}
