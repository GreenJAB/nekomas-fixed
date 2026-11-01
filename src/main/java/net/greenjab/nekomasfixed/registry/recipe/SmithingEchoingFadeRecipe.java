package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.registry.other.EchoingLayer;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;

import java.util.Optional;

public class SmithingEchoingFadeRecipe extends SmithingToggleEchoingFlagRecipe {

    public SmithingEchoingFadeRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        super(template, base, addition);
    }

    @Override
    public EchoingLayer computeEchoingLayer(EchoingKeyframe keyFrame) {
        return new EchoingLayer(
                Optional.empty(),
                Optional.of(!keyFrame.hidden()),
                Optional.empty()
        );
    }
    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return RecipeRegistry.SMITHING_ECHOING_FADE_RECIPE;
    }
}

