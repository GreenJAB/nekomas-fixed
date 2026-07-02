package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.AnimalTooltipData;
import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
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
		} else if (itemStack.getComponents().contains(ComponentRegistry.ANIMAL)) {
			TooltipDisplayComponent tooltipDisplayComponent = itemStack.getOrDefault(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);
			cir.setReturnValue(!tooltipDisplayComponent.shouldDisplay(ComponentRegistry.ANIMAL)
					? Optional.empty()
					: Optional.ofNullable(itemStack.get(ComponentRegistry.ANIMAL)).map(AnimalTooltipData::new));
		}
	}

	@Inject(method = "appendTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendComponentTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/component/type/TooltipDisplayComponent;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", ordinal = 0))
	private void addTooltips(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player,
								TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
		ItemStack stack = (ItemStack)(Object)this;
		stack.appendComponentTooltip(ComponentRegistry.ANIMAL, context, displayComponent, textConsumer, type);
		stack.appendComponentTooltip(ComponentRegistry.STORED_TIME, context, displayComponent, textConsumer, type);
		stack.appendComponentTooltip(ComponentRegistry.COMBO_MULTIPLIER, context, displayComponent, textConsumer, type);
	}

	@Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
	private void useBlockItem(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack stack = (ItemStack)(Object)this;
		if (stack.isIn(ModTags.CLAMTAG)) {
			int c = stack.getOrDefault(ComponentRegistry.CLAM_STATE, 0)>0?0:1;
			if (c>0&&stack.hasChangedComponent(DataComponentTypes.CONTAINER)) {
				if (!stack.get(DataComponentTypes.CONTAINER).copyFirstStack().isEmpty()) c++;
			}
			stack.set(ComponentRegistry.CLAM_STATE, c);
		}
		if (stack.isOf(Items.CLOCK)) {
			if (stack.getComponents().contains(ComponentRegistry.STORED_TIME)) {
				stack.remove(ComponentRegistry.STORED_TIME);
			} else {
				stack.set(ComponentRegistry.STORED_TIME, new StoredTimeComponent((int) ((world.getTimeOfDay() + 6000) % 24000)));
			}
		}
	}

	@Inject(method = "hasGlint", at = @At("HEAD"), cancellable = true)
	private void lightningGlint(CallbackInfoReturnable<Boolean> cir){
		ItemStack stack = (ItemStack)(Object)this;
		Optional<RegistryEntry<Potion>> optional = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).potion();
		if (optional.isPresent()) {
			if (optional.get() == ItemRegistry.LIGHTNING) {
				cir.setReturnValue(true);
			}
		}
	}
}