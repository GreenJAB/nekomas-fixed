package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.BundleTooltipData;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.CopyComponentsLootFunction;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;


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
}