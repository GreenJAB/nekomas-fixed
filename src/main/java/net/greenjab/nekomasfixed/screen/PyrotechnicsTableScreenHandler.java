package net.greenjab.nekomasfixed.screen;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PyrotechnicsTableScreenHandler extends ScreenHandler {

    private final Inventory input = new SimpleInventory(14){
        @Override
        public void markDirty() {
            super.markDirty();
            onContentChanged(this);
        }
    };
    private final Inventory output = new SimpleInventory(1);
    private final Property selectedPattern = Property.create();

    public PyrotechnicsTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public PyrotechnicsTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerRegistry.PYROTECHNICS_TABLE_HANDLER, syncId);
        this.addProperty(selectedPattern);

        // dyes / stars
        this.addSlot(new Slot(this.input, 0, 8, 16)
        { @Override public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.FIREWORK_STAR)||stack.getItem() instanceof DyeItem;}});
        this.addSlot(new Slot(this.input, 1, 8+18, 16)
        {   @Override public boolean isEnabled() {
                return slots.get(0).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                    : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) &&stack.isOf(Items.FIREWORK_STAR);}});
        this.addSlot(new Slot(this.input, 2, 8+18*2, 16)
        {   @Override public boolean isEnabled() {
                return slots.get(1).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) &&stack.isOf(Items.FIREWORK_STAR);}});
        this.addSlot(new Slot(this.input, 3, 8+18*3, 16)
        {   @Override public boolean isEnabled() {
                return slots.get(2).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) &&stack.isOf(Items.FIREWORK_STAR);}});
        this.addSlot(new Slot(this.input, 4, 8+18*4, 16)
        {   @Override public boolean isEnabled() {
                return slots.get(3).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) &&stack.isOf(Items.FIREWORK_STAR);}});
        // fade / gunpowder
        this.addSlot(new Slot(this.input, 5, 8, 35)
        {   @Override public boolean isEnabled() {
                return slots.get(0).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) && stack.isOf(Items.GUNPOWDER);}});
        this.addSlot(new Slot(this.input, 6, 8+18, 35)
        {   @Override public boolean isEnabled() {
                return slots.get(5).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                        : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) && stack.isOf(Items.GUNPOWDER);}});
        this.addSlot(new Slot(this.input, 7, 8+18*2, 35)
        {   @Override public boolean isEnabled() {
                return slots.get(6).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.getItem() instanceof DyeItem
                    : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) && stack.isOf(Items.GUNPOWDER);}});
        this.addSlot(new Slot(this.input, 8, 8+18*3, 35)
        {   @Override public boolean isEnabled() {
                return (slots.get(7).hasStack() && slots.get(0).getStack().getItem() instanceof DyeItem) || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem && stack.getItem() instanceof DyeItem;}});
        this.addSlot(new Slot(this.input, 9, 8+18*4, 35)
        {   @Override public boolean isEnabled() {
                return slots.get(8).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem && stack.getItem() instanceof DyeItem;}});
        // shape
        this.addSlot(new Slot(this.input, 10, 80, 54)
        {   @Override public boolean isEnabled() {
                return slots.get(0).getStack().getItem() instanceof DyeItem || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return isEnabled() && (stack.isOf(Items.FIRE_CHARGE)|| stack.isOf(Items.GOLD_NUGGET)|| stack.isOf(Items.CREEPER_BANNER_PATTERN)|| stack.isOf(Items.FEATHER));}});
        // glowstone
        this.addSlot(new Slot(this.input, 11, 80-18, 73)
        {   @Override public boolean isEnabled() {
                return slots.get(0).getStack().getItem() instanceof DyeItem || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return isEnabled() && (stack.isOf(Items.GLOWSTONE_DUST));}});
        // diamond
        this.addSlot(new Slot(this.input, 12, 80, 73)
        {   @Override public boolean isEnabled() {
                return slots.get(0).getStack().getItem() instanceof DyeItem || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return isEnabled() && (stack.isOf(Items.DIAMOND));}});
        // gunpowder / paper
        this.addSlot(new Slot(this.input, 13, 99, 73)
        {    @Override public boolean isEnabled() {
                return slots.get(0).hasStack() || this.hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return slots.get(0).getStack().getItem() instanceof DyeItem ? stack.isOf(Items.GUNPOWDER)
                    : slots.get(0).getStack().isOf(Items.FIREWORK_STAR) && stack.isOf(Items.PAPER);}});

        this.addSlot(new Slot(this.output, 0, 152, 73) {
            @Override public boolean isEnabled() {
                return slots.get(0).hasStack();}
            @Override public boolean canInsert(ItemStack stack) {
                return false;}
            @Override public void onTakeItem(final PlayerEntity player, final ItemStack carried) {
                slots.stream().filter(slot -> slot.inventory==input).forEach(slot -> slot.takeStack(slot.getStack().isOf(Items.CREEPER_BANNER_PATTERN)?0:1));}
        });

        for (int m = 0; m < 3; ++m)
            for (int l = 0; l < 9; ++l) this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 104 + m * 18));
        for (int m = 0; m < 9; ++m) this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 162));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
            this.sendContentUpdates();
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (slotIndex < 15) {
                if (!this.insertItem(itemStack2, 15, 51, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, 14, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            slot.onTakeItem(player, itemStack2);
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
        if (id >= 0 && id < 8) {
            this.selectedPattern.set(id);
            return true;
        }
        return false;
    }

    private static final Map<Item, FireworkExplosionComponent.Type> TYPE_MODIFIER_MAP = Map.of(
            Items.FIRE_CHARGE,
            FireworkExplosionComponent.Type.LARGE_BALL,
            Items.FEATHER,
            FireworkExplosionComponent.Type.BURST,
            Items.GOLD_NUGGET,
            FireworkExplosionComponent.Type.STAR,
            Items.CREEPER_BANNER_PATTERN,
            FireworkExplosionComponent.Type.CREEPER
    );

    // Placeholder for the crafting logic later
    private void updateResult() {
        ItemStack newOutput = ItemStack.EMPTY;
        if (slots.get(0).hasStack()) {
            if (slots.get(0).getStack().getItem() instanceof DyeItem && slots.get(13).getStack().isOf(Items.GUNPOWDER)) {
                if (slots.stream().filter(slot -> slot.getIndex()<10 && slot.hasStack() && slot.inventory==input).allMatch(slot -> slot.getStack().getItem() instanceof DyeItem)) {
                    FireworkExplosionComponent.Type type = TYPE_MODIFIER_MAP.getOrDefault(slots.get(10).getStack().getItem(), FireworkExplosionComponent.Type.SMALL_BALL);
                    boolean twinkle = slots.get(11).getStack().isOf(Items.GLOWSTONE_DUST);
                    boolean trail = slots.get(12).getStack().isOf(Items.DIAMOND);
                    IntList colorList = new IntArrayList();
                    slots.stream().filter(slot -> slot.getIndex()<5 && slot.hasStack() && slot.inventory==input).forEach(slot -> {
                        Item item = slot.getStack().getItem();
                        if (item.equals(ItemRegistry.AMBER_DYE)) colorList.add(ModColors.AMBER.color());
                        else if (item.equals(ItemRegistry.AQUA_DYE)) colorList.add(ModColors.AQUA.color());
                        else if (item.equals(ItemRegistry.MAROON_DYE)) colorList.add(ModColors.MAROON.color());
                        else if (item.equals(ItemRegistry.INDIGO_DYE)) colorList.add(ModColors.INDIGO.color());
                        else colorList.add(((DyeItem)item).getColor().getFireworkColor());});
                    IntList fadeList = new IntArrayList();
                    slots.stream().filter(slot -> slot.getIndex()>=5 && slot.getIndex()<10 && slot.hasStack() && slot.inventory==input).forEach(slot -> {
                        Item item = slot.getStack().getItem();
                        if (item.equals(ItemRegistry.AMBER_DYE)) fadeList.add(ModColors.AMBER.color());
                        else if (item.equals(ItemRegistry.AQUA_DYE)) fadeList.add(ModColors.AQUA.color());
                        else if (item.equals(ItemRegistry.MAROON_DYE)) fadeList.add(ModColors.MAROON.color());
                        else if (item.equals(ItemRegistry.INDIGO_DYE)) fadeList.add(ModColors.INDIGO.color());
                        else fadeList.add(((DyeItem)item).getColor().getFireworkColor());});

                    newOutput = new ItemStack(Items.FIREWORK_STAR);
                    newOutput.set(DataComponentTypes.FIREWORK_EXPLOSION, new FireworkExplosionComponent(type, colorList, fadeList, trail, twinkle));
                }
            } else if (slots.get(0).getStack().isOf(Items.FIREWORK_STAR) && slots.get(13).getStack().isOf(Items.PAPER)) {
                if (slots.stream().filter(slot -> slot.getIndex()<5 && slot.hasStack() && slot.inventory==input).allMatch(slot -> slot.getStack().isOf(Items.FIREWORK_STAR))) {
                    if (slots.stream().filter(slot -> slot.getIndex() >= 5 && slot.getIndex() < 10 && slot.hasStack() && slot.inventory==input).allMatch(slot -> slot.getStack().isOf(Items.GUNPOWDER))) {
                        if (!slots.get(10).hasStack()&&!slots.get(11).hasStack()&&!slots.get(12).hasStack()){
                            int i = Math.toIntExact((slots.stream().filter(slot -> slot.getIndex() >= 5 && slot.getIndex() < 10 && slot.hasStack() && slot.inventory==input).count()));
                            if (i>0) {
                                List<FireworkExplosionComponent> list = new ArrayList<>();
                                slots.stream().filter(slot -> slot.getIndex() < 5 && slot.hasStack() && slot.inventory == input).forEach(slot -> {
                                    FireworkExplosionComponent fireworkExplosionComponent = slot.getStack().get(DataComponentTypes.FIREWORK_EXPLOSION);
                                    if (fireworkExplosionComponent != null) list.add(fireworkExplosionComponent);});

                                newOutput = new ItemStack(Items.FIREWORK_ROCKET, 5);
                                newOutput.set(DataComponentTypes.FIREWORKS, new FireworksComponent(i, list));
                            }
                        }
                    }
                }
            }
        }
        output.setStack(0, newOutput);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.dropInventory(player, this.input);
    }
}
