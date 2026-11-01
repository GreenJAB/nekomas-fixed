package net.greenjab.nekomasfixed.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingFadeRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingPigmentRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingTwinkleRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingGlowingRecipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class Plugin implements IModPlugin {
    private static final Identifier ID = NekomasFixed.id("jei_plugin");

    @Override
    public @NotNull Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        SmithingEchoingExtension.reload();

//        RecipeSynchronization.synchronizeRecipeSerializer(RecipeRegistry.SMITHING_ECHOING_FADE_RECIPE);
//        RecipeSynchronization.synchronizeRecipeSerializer(RecipeRegistry.SMITHING_ECHOING_PIGMENT_RECIPE);
//        RecipeSynchronization.synchronizeRecipeSerializer(RecipeRegistry.SMITHING_ECHOING_TWINKLE_RECIPE);
//        RecipeSynchronization.synchronizeRecipeSerializer(RecipeRegistry.SMITHING_GLOWING_RECIPE);

        registration.getSmithingCategory().addExtension(SmithingEchoingFadeRecipe.class, new SmithingEchoingExtension<>());
        registration.getSmithingCategory().addExtension(SmithingEchoingPigmentRecipe.class, new SmithingEchoingExtension<>());
        registration.getSmithingCategory().addExtension(SmithingEchoingTwinkleRecipe.class, new SmithingEchoingExtension<>());
        registration.getSmithingCategory().addExtension(SmithingGlowingRecipe.class, new SmithingEchoingExtension<>());
    }
}
