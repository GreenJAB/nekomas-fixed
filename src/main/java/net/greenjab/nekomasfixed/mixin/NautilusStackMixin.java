package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class NautilusStackMixin {

    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void forceUnstackableIfFull(CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        if (stack.isOf(BlockRegistry.NAUTILUS_BLOCK.asItem()) ||
                stack.isOf(BlockRegistry.ZOMBIE_NAUTILUS_BLOCK.asItem()) ||
                stack.isOf(BlockRegistry.CORAL_NAUTILUS_BLOCK.asItem())) {

            AnimalComponent data = stack.get(OtherRegistry.ANIMAL);

            if (data != null && !data.animal().isEmpty()) {
                cir.setReturnValue(1);
            }
        }
    }
}