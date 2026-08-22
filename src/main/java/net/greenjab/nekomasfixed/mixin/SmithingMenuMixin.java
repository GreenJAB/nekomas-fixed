package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.screen.SmithingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SmithingScreenHandler.class)
public class SmithingMenuMixin {

    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresentOrElse(Ljava/util/function/Consumer;Ljava/lang/Runnable;)V"))
    private <T> void initCustomDataTracker(Optional<RecipeEntry<SmithingRecipe>> instance, Consumer<? super T> action, Runnable emptyAction, Operation<Void> original, @Local SmithingRecipeInput input) {
        ItemStack gear = input.getStackInSlot(1);
        if (gear.isOf(ItemRegistry.TURTLE_CHESTPLATE) || gear.isOf(ItemRegistry.TURTLE_LEGGINGS)
                || gear.isOf(ItemRegistry.TURTLE_BOOTS))  instance = Optional.empty();
        original.call(instance, action, emptyAction);
    }
}
