package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.SickleItem;
import net.greenjab.nekomasfixed.registry.item.SlingshotItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = {"isPrimaryItem", "canEnchant", "isSupportedItem"}, at = @At(value = "HEAD"), cancellable = true)
    private void otherChecks(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment)(Object)this;
        Item item = stack.getItem();
        if (item instanceof SickleItem) {
            cir.setReturnValue(enchantment.canEnchant(Items.DIAMOND_SWORD.getDefaultInstance()) && enchantment.getMaxLevel()!=5 && !enchantment.description().plainCopy().toString().contains("sweeping"));
            cir.cancel();
        }
        if (item instanceof SlingshotItem) {
            cir.setReturnValue(enchantment.canEnchant(Items.FLINT_AND_STEEL.getDefaultInstance())
                            || enchantment.description().plainCopy().toString().contains("multishot")
                            || enchantment.description().plainCopy().toString().contains("power")
                            || enchantment.description().plainCopy().toString().contains("punch")
                            || enchantment.description().plainCopy().toString().contains("shatter")
            );
            cir.cancel();
        }
    }
}
