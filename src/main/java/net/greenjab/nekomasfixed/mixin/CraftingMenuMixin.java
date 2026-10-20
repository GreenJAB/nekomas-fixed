package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {
    @ModifyVariable(method = "slotChangedCraftingGrid", at = @At("STORE"), ordinal = 0)
    private static Optional<RecipeHolder<CraftingRecipe>> nekomasfixed$lockRecipes(
            Optional<RecipeHolder<CraftingRecipe>> maybeRecipe) {
        return maybeRecipe.filter(h -> ModConfigValues.isRecipeEnabled(h.id().identifier()));
    }
}