package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	@Inject(method = "getTooltipData", at = @At("HEAD"), cancellable = true)
	private void UseNewContainerComponentTooltip(CallbackInfoReturnable<Optional<TooltipData>> cir){
        ItemStack itemStack = (ItemStack)(Object) this;
		if (itemStack.getComponents().contains(DataComponentTypes.CONTAINER)) {
			TooltipDisplayComponent tooltipDisplayComponent = itemStack.getOrDefault(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);
			cir.setReturnValue(!tooltipDisplayComponent.shouldDisplay(DataComponentTypes.CONTAINER)
					? Optional.empty()
					: Optional.ofNullable(itemStack.get(DataComponentTypes.CONTAINER)).map(ContainerTooltipData::new));
		}
	}

	@Inject(method = "appendTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendComponentTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/component/type/TooltipDisplayComponent;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", ordinal = 0))
	private void addBaitTooltip(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player,
								TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
		ItemStack stack = (ItemStack)(Object)this;
		stack.appendComponentTooltip(OtherRegistry.ANIMAL, context, displayComponent, textConsumer, type);
	}
}