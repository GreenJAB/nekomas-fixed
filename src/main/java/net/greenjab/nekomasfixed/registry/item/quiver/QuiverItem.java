package net.greenjab.nekomasfixed.registry.item.quiver;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.math.Fraction;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuiverItem extends Item {
    public static final int MAX_SHOWN_GRID_ITEMS_X = 4;
    public static final int MAX_SHOWN_GRID_ITEMS_Y = 3;
    public static final int MAX_SHOWN_GRID_ITEMS = 12;
    public static final int OVERFLOWING_MAX_SHOWN_GRID_ITEMS = 11;
    private static final int FULL_BAR_COLOR = ARGB.colorFromFloat(1.0F, 1.0F, 0.33F, 0.33F);
    private static final int BAR_COLOR = ARGB.colorFromFloat(1.0F, 0.44F, 0.53F, 1.0F);
    private static final int TICKS_AFTER_FIRST_THROW = 10;
    private static final int TICKS_BETWEEN_THROWS = 2;
    private static final int TICKS_MAX_THROW_DURATION = 200;

    public QuiverItem(Properties properties) {
        super(properties);
    }


    private static int getWeightSafe(QuiverContents contents){
        return contents.getTotalArrowCount();
    }


    public static float getFullnessDisplay(final ItemStack itemStack) {
        QuiverContents contents = (QuiverContents)itemStack.getOrDefault(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return getWeightSafe(contents);
    }

    public boolean overrideStackedOnOther(final ItemStack self, final Slot slot, final ClickAction clickAction, final Player player) {
        QuiverContents initialContents = (QuiverContents)self.get(ComponentRegistry.QUIVER_CONTENTS);
        if (initialContents == null) {
            return false;
        } else {
            ItemStack other = slot.getItem();
            QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);
            if (clickAction == ClickAction.PRIMARY && !other.isEmpty() && contents.toImmutable().doesItemHasPlace(self)) {
                if (contents.tryTransfer(slot, player) > 0) {
                    playInsertSound(player);
                } else {
                    playInsertFailSound(player);
                }

                self.set(ComponentRegistry.QUIVER_CONTENTS, contents.toImmutable());
                this.broadcastChangesOnContainerMenu(player);
                return true;
            } else if (clickAction == ClickAction.SECONDARY && other.isEmpty()) {
                ItemStack itemStack = contents.removeOne();
                if (itemStack != null) {
                    ItemStack remainder = slot.safeInsert(itemStack);
                    if (remainder.getCount() > 0) {
                        contents.tryInsert(remainder);
                    } else {
                        playRemoveOneSound(player);
                    }
                }

                self.set(ComponentRegistry.QUIVER_CONTENTS, contents.toImmutable());
                this.broadcastChangesOnContainerMenu(player);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean overrideOtherStackedOnMe(final ItemStack self, final ItemStack other, final Slot slot, final ClickAction clickAction, final Player player, final SlotAccess carriedItem) {
        if (clickAction == ClickAction.PRIMARY && other.isEmpty()) {
            toggleSelectedItem(self, -1);
            return false;
        } else {
            QuiverContents initialContents = (QuiverContents)self.get(ComponentRegistry.QUIVER_CONTENTS);
            if (initialContents == null) {
                return false;
            } else {
                QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);
                if (clickAction == ClickAction.PRIMARY && !other.isEmpty()) {
                    if (slot.allowModification(player) && contents.tryInsert(other) > 0) {
                        playInsertSound(player);
                    } else {
                        playInsertFailSound(player);
                    }

                    self.set(ComponentRegistry.QUIVER_CONTENTS, contents.toImmutable());
                    this.broadcastChangesOnContainerMenu(player);
                    return true;
                } else if (clickAction == ClickAction.SECONDARY && other.isEmpty()) {
                    if (slot.allowModification(player)) {
                        ItemStack removed = contents.removeOne();
                        if (removed != null) {
                            playRemoveOneSound(player);
                            carriedItem.set(removed);
                        }
                    }

                    self.set(ComponentRegistry.QUIVER_CONTENTS, contents.toImmutable());
                    this.broadcastChangesOnContainerMenu(player);
                    return true;
                } else {
                    toggleSelectedItem(self, -1);
                    return false;
                }
            }
        }
    }

    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.SUCCESS;
    }

    private void dropContent(final Level level, final Player player, final ItemStack itemStack) {
        if (this.dropContent(itemStack, player)) {
            playDropContentsSound(level, player);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

    }


    public boolean isBarVisible(final ItemStack stack) {
        QuiverContents contents = (QuiverContents)stack.getOrDefault(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return getWeightSafe(contents) > 0;
    }

    public int getBarWidth(final ItemStack stack) {
        QuiverContents contents = (QuiverContents)stack.getOrDefault(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return Math.min(1 + Mth.mulAndTruncate(Fraction.getFraction((double) getWeightSafe(contents) /contents.MAX_ARROWS), 12), 13);
    }

    public int getBarColor(final ItemStack stack) {
        QuiverContents contents = (QuiverContents)stack.getOrDefault(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return getWeightSafe(contents) == QuiverContents.MAX_ARROWS ? FULL_BAR_COLOR : BAR_COLOR;
    }

    public static void toggleSelectedItem(final ItemStack stack, final int selectedItem) {
        QuiverContents initialContents = (QuiverContents)stack.get(ComponentRegistry.QUIVER_CONTENTS);
        if (initialContents != null) {
            QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);
            contents.toggleSelectedItem(selectedItem);
            stack.set(ComponentRegistry.QUIVER_CONTENTS, contents.toImmutable());
        }
    }

    public static int getSelectedItemIndex(final ItemStack stack) {
        return ((QuiverContents)stack.getOrDefault(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY)).getSelectedItemIndex();
    }

    public static @Nullable ItemStack getSelectedItem(final ItemStack stack) {
        return ((QuiverContents)stack.getOrDefault(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY)).getSelectedItem();
    }

    public static int getNumberOfItemsToShow(final ItemStack stack) {
        QuiverContents contents = (QuiverContents)stack.getOrDefault(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY);
        return contents.getNumberOfItemsToShow();
    }

    private boolean dropContent(final ItemStack bundle, final Player player) {
        QuiverContents contents = (QuiverContents)bundle.get(ComponentRegistry.QUIVER_CONTENTS);
        if (contents != null && !contents.isEmpty()) {
            Optional<ItemStack> itemStack = removeOneItemFromBundle(bundle, player, contents);
            if (itemStack.isPresent()) {
                player.drop((ItemStack)itemStack.get(), true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static Optional<ItemStack> removeOneItemFromBundle(final ItemStack self, final Player player, final QuiverContents initialContents) {
        QuiverContents.Mutable contents = new QuiverContents.Mutable(initialContents);
        ItemStack removed = contents.removeOne();
        if (removed != null) {
            playRemoveOneSound(player);
            self.set(ComponentRegistry.QUIVER_CONTENTS, contents.toImmutable());
            return Optional.of(removed);
        } else {
            return Optional.empty();
        }
    }

    public void onUseTick(final Level level, final LivingEntity livingEntity, final ItemStack itemStack, final int ticksRemaining) {
        if (livingEntity instanceof Player player) {
            int useDuration = this.getUseDuration(itemStack, livingEntity);
            boolean isFirstTick = ticksRemaining == useDuration;
            if (isFirstTick || ticksRemaining < useDuration - 10 && ticksRemaining % 2 == 0) {
                this.dropContent(level, player, itemStack);
            }
        }

    }

    public int getUseDuration(final ItemStack itemStack, final LivingEntity entity) {
        return 200;
    }

    public ItemUseAnimation getUseAnimation(final ItemStack itemStack) {
        return ItemUseAnimation.BUNDLE;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack bundle) {
        QuiverContents contents = bundle.get(ComponentRegistry.QUIVER_CONTENTS);
        if (contents == null || contents.isEmpty()) {
            return Optional.empty();
        }
        List<ItemStack> items = contents.itemCopyStream().collect(Collectors.toList());
        return Optional.of(new net.greenjab.nekomasfixed.registry.item.quiver.QuiverTooltip(contents));
    }

    public void onDestroyed(final ItemEntity entity) {
        QuiverContents contents = (QuiverContents)entity.getItem().get(ComponentRegistry.QUIVER_CONTENTS);
        if (contents != null) {
            entity.getItem().set(ComponentRegistry.QUIVER_CONTENTS, QuiverContents.EMPTY);
            ItemUtils.onContainerDestroyed(entity, contents.itemCopyStream());
        }
    }

    private static void playRemoveOneSound(final Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertSound(final Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertFailSound(final Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT_FAIL, 1.0F, 1.0F);
    }

    private static void playDropContentsSound(final Level level, final Entity entity) {
        level.playSound((Entity)null, entity.blockPosition(), SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.PLAYERS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void broadcastChangesOnContainerMenu(final Player player) {
        AbstractContainerMenu containerMenu = player.containerMenu;
        if (containerMenu != null) {
            containerMenu.slotsChanged(player.getInventory());
        }

    }
}
