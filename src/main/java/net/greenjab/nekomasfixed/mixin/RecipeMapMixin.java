package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(RecipeMap.class)
public class RecipeMapMixin {

    @Inject(method = "byKey", at = @At("RETURN"), cancellable = true)
    private void nekomasfixed$filterByKey(ResourceKey<Recipe<?>> recipeId, CallbackInfoReturnable<RecipeHolder<?>> cir) {
        RecipeHolder<?> holder = cir.getReturnValue();
        if (holder != null && !ModConfigValues.isRecipeEnabled(holder.id().identifier())) {
            cir.setReturnValue(null);
        }
    }
}
