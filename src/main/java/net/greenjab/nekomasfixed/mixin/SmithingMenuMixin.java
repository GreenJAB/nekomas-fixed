package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SmithingMenu.class)
public class SmithingMenuMixin {

    @WrapOperation(method = "createResult", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresentOrElse(Ljava/util/function/Consumer;Ljava/lang/Runnable;)V"))
    private <T> void initCustomDataTracker(Optional<RecipeHolder<SmithingRecipe>> instance, Consumer<? super T> action, Runnable emptyAction, Operation<Void> original, @Local SmithingRecipeInput input) {
        ItemStack gear = input.getItem(1);
        if (gear.is(ItemRegistry.TURTLE_CHESTPLATE) || gear.is(ItemRegistry.TURTLE_LEGGINGS)
                || gear.is(ItemRegistry.TURTLE_BOOTS))  instance = Optional.empty();
        original.call(instance, action, emptyAction);
    }
}
