package net.greenjab.nekomasfixed.registry.item.quiver;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class QuiverContents implements TooltipComponent {
    public static final QuiverContents EMPTY = new QuiverContents(List.of());
    public static final Codec<QuiverContents> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, QuiverContents> STREAM_CODEC;

    private static final int NO_STACK_INDEX = -1;
    public static final int NO_SELECTED_ITEM_INDEX = -1;

    private final List<ItemStackTemplate> items;
    public static final int MAX_ARROWS = 9 * 64;
    private final int selectedItem;

    private QuiverContents(final List<ItemStackTemplate> items, final int selectedItem) {
        this.items = items;
        this.selectedItem = selectedItem;
    }

    public QuiverContents(final List<ItemStackTemplate> items) {
        this(items, -1);
    }


    public static boolean canItemBeInQuiver(final ItemStack itemToAdd) {
        return !itemToAdd.isEmpty() && itemToAdd.getItem() instanceof ArrowItem && itemToAdd.getItem().canFitInsideContainerItems();
    }

    public int getNumberOfItemsToShow() {
        int numberOfItemStacks = this.size();
        int availableItemsToShow = numberOfItemStacks > 12 ? 11 : 12;
        int itemsOnNonFullRow = numberOfItemStacks % 4;
        int emptySpaceOnNonFullRow = itemsOnNonFullRow == 0 ? 0 : 4 - itemsOnNonFullRow;
        return Math.min(numberOfItemStacks, availableItemsToShow - emptySpaceOnNonFullRow);
    }

    public Stream<ItemStack> itemCopyStream() {
        return this.items.stream().map(ItemStackTemplate::create);
    }

    public List<ItemStackTemplate> items() {
        return this.items;
    }

    public int size() {
        return this.items.size();
    }


    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public int getSelectedItemIndex() {
        return this.selectedItem;
    }

    public @Nullable ItemStackTemplate getSelectedItem() {
        return this.selectedItem == -1 ? null : this.items.get(this.selectedItem);
    }

    //weight for quivers
    public int getTotalArrowCount() {
        int total = 0;

        for (ItemStackTemplate stack : this.items) {
            total += stack.count();
        }

        return total;
    }

    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof QuiverContents) {
            QuiverContents contents = (QuiverContents)obj;
            return this.items.equals(contents.items);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.items.hashCode();
    }

    public String toString() {
        return "QuiverContents" + String.valueOf(this.items);
    }

    static {
        CODEC = ItemStackTemplate.CODEC.listOf().xmap(QuiverContents::new, (contents) -> contents.items);
        STREAM_CODEC = ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()).map(QuiverContents::new, (contents) -> contents.items);
    }

    public static class Mutable {
        private final List<ItemStack> items;
        private int selectedItem;

        public Mutable(final QuiverContents contents) {

                this.items = new ArrayList(contents.items.size());

                for(ItemStackTemplate item : contents.items) {
                    this.items.add(item.create());
                }

                this.selectedItem = contents.selectedItem;


        }

        public QuiverContents.Mutable clearItems() {
            this.items.clear();
            this.selectedItem = -1;
            return this;
        }

        private int findStackIndex(final ItemStack itemsToAdd) {
            if (!itemsToAdd.isStackable()) {
                return -1;
            } else {
                for(int i = 0; i < this.items.size(); ++i) {
                    if (ItemStack.isSameItemSameComponents((ItemStack)this.items.get(i), itemsToAdd)) {
                        return i;
                    }
                }

                return -1;
            }
        }


        public int getCountOfStack(final ItemStack itemToSearch){
            int count = 0;
            for(ItemStack item : items){
                if(item.is(itemToSearch.getItem())){
                    count += item.getCount() ;
                }
            }
            return count;
        }



        public int tryInsert(final ItemStack itemsToAdd) {
            if (!QuiverContents.canItemBeInQuiver(itemsToAdd)) {
                return 0;
            }
            int currentAmount = 0;

            for (ItemStack stack : this.items) {
                currentAmount += stack.getCount();
            }
            int remainingSpace = MAX_ARROWS - currentAmount;
            if (remainingSpace <= 0) {
                return 0;
            }

            int amountToAdd = Math.min(itemsToAdd.getCount(), remainingSpace);

            if (amountToAdd <= 0) {
                return 0;
            }
            int stackIndex = this.findStackIndex(itemsToAdd);
            if (stackIndex != -1) {
                ItemStack removedStack = this.items.remove(stackIndex);

                ItemStack mergedStack = removedStack.copyWithCount(removedStack.getCount() + amountToAdd);

                itemsToAdd.shrink(amountToAdd);
                this.items.add(0, mergedStack);
            } else {
                this.items.add(0, itemsToAdd.split(amountToAdd));
            }

            return amountToAdd;
        }



        public int tryTransfer(final Slot slot, final Player player) {
            ItemStack other = slot.getItem();

            int maxAmount = other.getCount();
            return QuiverContents.canItemBeInQuiver(other) && other.getItem() instanceof ArrowItem ? this.tryInsert(slot.safeTake(other.getCount(), maxAmount, player)) : 0;

        }

        public void toggleSelectedItem(final int selectedItem) {
            this.selectedItem = this.selectedItem != selectedItem && !this.indexIsOutsideAllowedBounds(selectedItem) ? selectedItem : -1;
        }

        private boolean indexIsOutsideAllowedBounds(final int selectedItem) {
            return selectedItem < 0 || selectedItem >= this.items.size();
        }

        public @Nullable ItemStack removeOne() {
            if (this.items.isEmpty()) {
                return null;
            } else {
                int removeIndex = this.indexIsOutsideAllowedBounds(this.selectedItem) ? 0 : this.selectedItem;
                ItemStack stack = ((ItemStack)this.items.remove(removeIndex)).copy();
                this.toggleSelectedItem(-1);
                return stack;
            }
        }


        public QuiverContents toImmutable() {
            ImmutableList.Builder<ItemStackTemplate> builder = ImmutableList.builder();

            for(ItemStack item : this.items) {
                builder.add(ItemStackTemplate.fromNonEmptyStack(item));
            }

            return new QuiverContents(builder.build(), this.selectedItem);
        }
    }
}
