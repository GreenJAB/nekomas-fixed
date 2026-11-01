package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.other.BannerEffects;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShieldDecorationRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldDecorationRecipe.class)
public class ShieldDecorationRecipeMixin {
    @Inject(
            method = "craft(Lnet/minecraft/recipe/input/CraftingRecipeInput;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/item/ItemStack;",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/component/DataComponentTypes;BANNER_PATTERNS:Lnet/minecraft/component/ComponentType;"
            )
    )
    private void copyBannerEffectsComponent(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 0) ItemStack originalStack, @Local(ordinal = 1) ItemStack copyStack) {
        BannerEffects effects = originalStack.get(ComponentRegistry.BANNER_EFFECTS);
        if (effects != null) {
            copyStack.set(ComponentRegistry.BANNER_EFFECTS, effects);
        }
    }
}
