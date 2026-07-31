package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Shadow
    private static RecipeManager.IngredientExtractor forSingleInput(RecipeType<? extends SingleItemRecipe> type) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @ModifyExpressionValue(method = "finalizeRecipeLoading", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/crafting/RecipeManager;RECIPE_PROPERTY_SETS:Ljava/util/Map;", opcode = Opcodes.GETSTATIC))
    private static <K, V> Map<K, V> depowerRedstoneStruckBlocks(Map<K, V> original) {
        Map<K, V> newMap = new HashMap<>(Map.of());
        newMap.putAll(original);
        newMap.put((K) RecipeRegistry.KILN_INPUT, (V) forSingleInput(RecipeRegistry.KILN));
        return newMap;
    }
}
