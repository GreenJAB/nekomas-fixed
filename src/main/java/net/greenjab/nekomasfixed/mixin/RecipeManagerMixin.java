package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Shadow
    private static RecipeManager.IngredientExtractor forSingleInput(RecipeType<? extends SingleItemRecipe> type) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

//    @Shadow
//    public static <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> getRecipeFor(RecipeType<T> type, I input, Level level) {
//        throw new UnsupportedOperationException("Implemented via mixin");
//    }

    @ModifyExpressionValue(method = "finalizeRecipeLoading", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/crafting/RecipeManager;RECIPE_PROPERTY_SETS:Ljava/util/Map;", opcode = Opcodes.GETSTATIC))
    private static <K, V> Map<K, V> depowerRedstoneStruckBlocks(Map<K, V> original) {
        Map<K, V> newMap = new HashMap<>(Map.of());
        newMap.putAll(original);
        newMap.put((K) RecipeRegistry.KILN_INPUT, (V) forSingleInput(RecipeRegistry.KILN));
//        newMap.put((K) RecipeRegistry.FLETCHING_INPUT, (V) getRecipeFor(RecipeRegistry.QUIVER_FLETCHING, RecipeRegistry.FLETCHING_INPUT));
        return newMap;
    }
}
