package net.greenjab.nekomasfixed.mixin.client;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.recipe.book.RecipeBookGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "refresh", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/recipebook/RecipeBookType;values()[Lnet/minecraft/client/recipebook/RecipeBookType;"))
    private void allowOffhandAttack(CallbackInfo ci, @Local(ordinal = 1) Map<RecipeBookGroup, List<RecipeResultCollection>> map2) {
        map2.put(
                RecipeRegistry.KILNING,
                (List)RecipeRegistry.KILNING.getCategories()
                        .stream()
                        .flatMap(/* method_64852 */ group -> ((List)map2.getOrDefault(group, List.of())).stream())
                        .collect(ImmutableList.toImmutableList())
        );
    }

}