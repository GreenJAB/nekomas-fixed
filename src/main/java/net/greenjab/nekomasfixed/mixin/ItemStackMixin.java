package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getTooltipImage", at = @At("HEAD"), cancellable = true)
    private void useContainerTooltip(CallbackInfoReturnable<Optional<TooltipComponent>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (!stack.has(DataComponents.HIDE_TOOLTIP) && stack.has(DataComponents.CONTAINER)) {
            ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
            // Only show the container tooltip when the clam actually holds an item.
            if (contents != null && contents.nonEmptyItems().iterator().hasNext()) {
                cir.setReturnValue(Optional.of(new ContainerTooltipData(contents)));
            }
        }
    }

    // The Lightning potion glints like an enchanted item.
    @Inject(method = "hasFoil", at = @At("HEAD"), cancellable = true)
    private void lightningGlint(CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        Optional<Holder<Potion>> potion = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion();
        if (potion.isPresent() && potion.get() == ItemRegistry.LIGHTNING) {
            cir.setReturnValue(true);
        }
        // A clock carrying a stored_time glints to show it has been set.
        if (stack.has(ComponentRegistry.STORED_TIME)) {
            cir.setReturnValue(true);
        }
    }

    // Clock tooltip line showing the stored HH:MM once a time has been captured.
    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void addStoredTimeTooltip(Item.TooltipContext context, Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir) {
        StoredTimeComponent stored = ((ItemStack) (Object) this).get(ComponentRegistry.STORED_TIME);
        if (stored != null) {
            stored.addToTooltip(context, cir.getReturnValue()::add, flag);
        }
    }

    // Right-clicking a clock (while not placing it) captures/clears the current daytime as stored_time.
    @Inject(method = "use", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/item/Item;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;"))
    private void useClockItem(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.is(Items.CLOCK)) {
            if (stack.has(ComponentRegistry.STORED_TIME)) {
                stack.remove(ComponentRegistry.STORED_TIME);
            } else {
                stack.set(ComponentRegistry.STORED_TIME, new StoredTimeComponent((int) ((level.getDayTime() + 6000) % 24000)));
            }
            player.swing(hand);
        }
    }

    // Add echoing layers tooltip to item.
    @Inject(
            method = "getTooltipLines",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V",
                    shift = At.Shift.AFTER
            ),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/core/component/DataComponents;TRIM:Lnet/minecraft/core/component/DataComponentType;"
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/core/component/DataComponents;STORED_ENCHANTMENTS:Lnet/minecraft/core/component/DataComponentType;"
                    )
            )
    )
    private void appendEchoingEffectsTooltip(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, @Local Consumer<Component> consumer) {
        EchoingLayer.appendTooltip((ItemStack)(Object)this, tooltipContext, consumer, tooltipFlag);
    }
}
