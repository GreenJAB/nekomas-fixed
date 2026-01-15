package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

	@Inject(method="hasGlint", at = @At(value = "HEAD"), cancellable = true)
	private void throwableBrick(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.hasChangedComponent(OtherRegistry.STORED_TIME)) cir.setReturnValue(true);
	}

}