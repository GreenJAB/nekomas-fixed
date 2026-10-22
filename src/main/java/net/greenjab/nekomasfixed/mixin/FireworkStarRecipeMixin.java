package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.ints.IntList;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireworkStarRecipe.class)
public class FireworkStarRecipeMixin {

    @WrapOperation(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/IntList;add(I)Z"))
    private boolean craftCustom(IntList instance, int i, Operation<Boolean> original, @Local ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item.equals(ItemRegistry.AMBER_DYE)) i= ModColors.AMBER.getColor();
        if (item.equals(ItemRegistry.AQUA_DYE)) i= ModColors.AQUA.getColor();
        if (item.equals(ItemRegistry.MAROON_DYE)) i= ModColors.MAROON.getColor();
        if (item.equals(ItemRegistry.INDIGO_DYE)) i= ModColors.INDIGO.getColor();
        return original.call(instance, i);
    }
}