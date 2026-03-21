package net.greenjab.nekomasfixed.mixin;

import net.minecraft.recipe.ServerRecipeManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerRecipeManager.class)
public class ServerRecipeManagerMixin {

    /*@ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/util/Map;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;"))
    private static Map<RegistryKey<RecipePropertySet>, ServerRecipeManager.SoleIngredientGetter> withGlowing(Map<RegistryKey<RecipePropertySet>, ServerRecipeManager.SoleIngredientGetter> original) {
        original.put(RecipeRegistry.KILN_INPUT,
                cookingIngredientGetter(ModRecipeType.KILNING));
        return original;
    }

    @Unique
    private static ServerRecipeManager.SoleIngredientGetter cookingIngredientGetter(RecipeType<? extends SingleStackRecipe> expectedType) {
        return recipe -> recipe.getType() == expectedType && recipe instanceof SingleStackRecipe singleStackRecipe
                ? Optional.of(singleStackRecipe.ingredient())
                : Optional.empty();
    }*/
}
