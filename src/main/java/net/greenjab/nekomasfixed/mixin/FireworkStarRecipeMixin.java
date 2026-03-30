package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.ints.IntList;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.FireworkStarRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireworkStarRecipe.class)
public class FireworkStarRecipeMixin {

    @Redirect(method = "craft(Lnet/minecraft/recipe/input/CraftingRecipeInput;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/IntList;add(I)Z"))
    private boolean craftCustom(IntList intList, int original, @Local ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item.equals(ItemRegistry.AMBER_DYE)) return intList.add(ModColors.AMBER.color());
        if (item.equals(ItemRegistry.AQUA_DYE)) return intList.add(ModColors.AQUA.color());
        if (item.equals(ItemRegistry.MAROON_DYE)) return intList.add(ModColors.MAROON.color());
        if (item.equals(ItemRegistry.INDIGO_DYE)) return intList.add(ModColors.INDIGO.color());
        return intList.add(original);
    }
}