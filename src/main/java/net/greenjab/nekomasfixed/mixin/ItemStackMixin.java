package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.AnimalTooltipData;
import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
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
		} else if (itemStack.getComponents().contains(OtherRegistry.ANIMAL)) {
			TooltipDisplayComponent tooltipDisplayComponent = itemStack.getOrDefault(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);
			cir.setReturnValue(!tooltipDisplayComponent.shouldDisplay(OtherRegistry.ANIMAL)
					? Optional.empty()
					: Optional.ofNullable(itemStack.get(OtherRegistry.ANIMAL)).map(AnimalTooltipData::new));
		}
	}

	@Inject(method = "appendTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendComponentTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/component/type/TooltipDisplayComponent;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", ordinal = 0))
	private void addTooltips(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player,
								TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
		ItemStack stack = (ItemStack)(Object)this;
		stack.appendComponentTooltip(OtherRegistry.ANIMAL, context, displayComponent, textConsumer, type);
		stack.appendComponentTooltip(OtherRegistry.STORED_TIME, context, displayComponent, textConsumer, type);
	}

	@Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
	private void useBlockItem(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack stack = (ItemStack)(Object)this;
		if (stack.isIn(OtherRegistry.CLAMTAG)) {
			int c = stack.getOrDefault(OtherRegistry.CLAM_STATE, 0)>0?0:1;
			if (c>0&&stack.hasChangedComponent(DataComponentTypes.CONTAINER)) {
				if (!stack.get(DataComponentTypes.CONTAINER).copyFirstStack().isEmpty()) c++;
			}
			stack.set(OtherRegistry.CLAM_STATE, c);
		}
		if (stack.isOf(Items.CLOCK)) {
			if (stack.getComponents().contains(OtherRegistry.STORED_TIME)) {
				stack.remove(OtherRegistry.STORED_TIME);
			} else {
				stack.set(OtherRegistry.STORED_TIME, new StoredTimeComponent((int) ((world.getTimeOfDay() + 6000) % 24000)));
			}
		}
	}


}