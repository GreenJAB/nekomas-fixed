package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.SickleItem;
import net.greenjab.nekomasfixed.registry.item.SlingshotItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = {"isPrimaryItem", "isAcceptableItem", "isSupportedItem"}, at = @At(value = "HEAD"), cancellable = true)
    private void otherChecks(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment)(Object)this;
        Item item = stack.getItem();
        if (item instanceof SickleItem) {
            cir.setReturnValue(enchantment.isAcceptableItem(Items.DIAMOND_SWORD.getDefaultStack()) && enchantment.getMaxLevel()!=5);
            cir.cancel();
        }
        if (item instanceof SlingshotItem) {
            cir.setReturnValue(enchantment.isAcceptableItem(Items.FLINT_AND_STEEL.getDefaultStack())
                            || enchantment.description().copyContentOnly().toString().contains("multishot")
                            || enchantment.description().copyContentOnly().toString().contains("power")
                            || enchantment.description().copyContentOnly().toString().contains("punch")
                            || enchantment.description().copyContentOnly().toString().contains("shatter")
            );
            cir.cancel();
        }
    }
}
