package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.quiver.QuiverTooltip;
import net.greenjab.nekomasfixed.registry.other.AnimalTooltipData;
import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	@Inject(method = "getTooltipImage", at = @At("HEAD"), cancellable = true)
	private void UseNewContainerComponentTooltip(CallbackInfoReturnable<Optional<TooltipComponent>> cir){
        ItemStack itemStack = (ItemStack)(Object) this;
		if (itemStack.getComponents().has(DataComponents.CONTAINER)) {
			TooltipDisplay tooltipDisplayComponent = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
			cir.setReturnValue(!tooltipDisplayComponent.shows(DataComponents.CONTAINER)
					? Optional.empty()
					: Optional.ofNullable(itemStack.get(DataComponents.CONTAINER)).map(ContainerTooltipData::new));
		} else if (itemStack.getComponents().has(ComponentRegistry.ANIMAL)) {
			TooltipDisplay tooltipDisplayComponent = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
			cir.setReturnValue(!tooltipDisplayComponent.shows(ComponentRegistry.ANIMAL)
					? Optional.empty()
					: Optional.ofNullable(itemStack.get(ComponentRegistry.ANIMAL)).map(AnimalTooltipData::new));
		}
		else if (itemStack.getComponents().has(ComponentRegistry.QUIVER_CONTENTS)) {
			TooltipDisplay tooltipDisplayComponent = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
			cir.setReturnValue(!tooltipDisplayComponent.shows(ComponentRegistry.QUIVER_CONTENTS)
					? Optional.empty()
					: Optional.ofNullable(itemStack.get(ComponentRegistry.QUIVER_CONTENTS)).map(QuiverTooltip::new));
		}
	}

	@Inject(method = "addDetailsToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 0))
	private void addTooltips(Item.TooltipContext context, TooltipDisplay display, Player player,
	                         TooltipFlag tooltipFlag, Consumer<Component> builder, CallbackInfo ci) {
		ItemStack stack = (ItemStack)(Object)this;
		stack.addToTooltip(ComponentRegistry.ANIMAL, context, display, builder, tooltipFlag);
		stack.addToTooltip(ComponentRegistry.STORED_TIME, context, display, builder, tooltipFlag);
		stack.addToTooltip(ComponentRegistry.QUIVER_CONTENTS, context, display, builder, tooltipFlag);
		stack.addToTooltip(ComponentRegistry.COMBO_MULTIPLIER, context, display, builder, tooltipFlag);
		stack.addToTooltip(ComponentRegistry.POT_FACE_BLOCK, context, display, builder, tooltipFlag);
		stack.addToTooltip(ComponentRegistry.POT_ENGRAVED_DECORATIONS, context, display, builder, tooltipFlag);
	}

	@Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"))
	private void useBlockItem(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		ItemStack stack = (ItemStack)(Object)this;
		if (stack.is(ModTags.CLAMTAG)) {
			int c = stack.getOrDefault(ComponentRegistry.CLAM_STATE, 0)>0?0:1;
			if (c>0&&stack.hasNonDefault(DataComponents.CONTAINER)) {
				if (!stack.get(DataComponents.CONTAINER).copyOne().isEmpty()) c++;
			}
			stack.set(ComponentRegistry.CLAM_STATE, c);
		}
		if (stack.is(Items.CLOCK)) {
			if (stack.getComponents().has(ComponentRegistry.STORED_TIME)) {
				stack.remove(ComponentRegistry.STORED_TIME);
			} else {
				stack.set(ComponentRegistry.STORED_TIME, new StoredTimeComponent((int) ((level.getOverworldClockTime() + 6000) % 24000)));
			}
			player.swing(hand);
		}
	}

	@Inject(method = "hasFoil", at = @At("HEAD"), cancellable = true)
	private void lightningGlint(CallbackInfoReturnable<Boolean> cir){
		ItemStack stack = (ItemStack)(Object)this;
		Optional<Holder<Potion>> optional = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion();
		if (optional.isPresent()) {
			if (optional.get() == ItemRegistry.LIGHTNING) {
				cir.setReturnValue(true);
			}
		}
	}
}