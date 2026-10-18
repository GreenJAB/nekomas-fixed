package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(RecipeMap.class)
public class RecipeMapMixin {
    @Inject(method = "getRecipesFor", at = @At("RETURN"), cancellable = true)
    private <I extends RecipeInput, T extends Recipe<I>> void nekomasfixed$filterRecipes(
            RecipeType<T> type, I container, Level level, CallbackInfoReturnable<Stream<RecipeHolder<T>>> cir) {
        cir.setReturnValue(cir.getReturnValue()
                .filter(holder -> ModConfigValues.isRecipeEnabled(holder.id().identifier())));
    }
}
