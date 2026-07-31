package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.SingleStackRecipe;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

@Mixin(ServerRecipeManager.class)
public abstract class ServerRecipeManagerMixin {

    @Shadow
    private static ServerRecipeManager.SoleIngredientGetter cookingIngredientGetter(RecipeType<? extends SingleStackRecipe> expectedType) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @ModifyExpressionValue(method = "initialize", at = @At(value = "FIELD", target = "Lnet/minecraft/recipe/ServerRecipeManager;SOLE_INGREDIENT_GETTERS:Ljava/util/Map;", opcode = Opcodes.GETSTATIC))
    private static <K, V> Map<K, V> depowerRedstoneStruckBlocks(Map<K, V> original) {
        Map<K, V> newMap = new HashMap<>(Map.of());
        newMap.putAll(original);
        newMap.put((K) RecipeRegistry.KILN_INPUT, (V) cookingIngredientGetter(RecipeRegistry.KILN));
        return newMap;
    }
}
