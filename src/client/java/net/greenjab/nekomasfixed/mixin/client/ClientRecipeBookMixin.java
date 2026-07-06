package net.greenjab.nekomasfixed.mixin.client;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "rebuildCollections", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/recipebook/SearchRecipeBookCategory;values()[Lnet/minecraft/client/gui/screens/recipebook/SearchRecipeBookCategory;"))
    private void kilnRecipeBookRefresh(CallbackInfo ci, @Local(ordinal = 1) Map<ExtendedRecipeBookCategory, List<RecipeCollection>> map2) {
        map2.put(RecipeRegistry.KILNING, RecipeRegistry.KILNING.getCategories()
                        .stream().flatMap( group -> (map2.getOrDefault(group, List.of())).stream())
                        .collect(ImmutableList.toImmutableList()));
    }
}