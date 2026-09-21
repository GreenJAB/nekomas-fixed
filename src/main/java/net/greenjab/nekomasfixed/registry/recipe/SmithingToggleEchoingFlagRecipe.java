package net.greenjab.nekomasfixed.registry.recipe;

import java.util.List;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import org.jetbrains.annotations.NotNull;

public abstract class SmithingToggleEchoingFlagRecipe extends SmithingEchoingRecipe {

    public SmithingToggleEchoingFlagRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        super(template, base, addition);
    }

    public abstract EchoingLayer computeEchoingLayer(EchoingKeyframe keyFrame);

    public @NotNull ItemStack assemble(SmithingRecipeInput smithingRecipeInput, HolderLookup.@NotNull Provider wrapperLookup) {
        ItemStack itemStack = smithingRecipeInput.base();
        if (isBaseIngredient(itemStack)) {
            EchoingKeyframe last = EchoingKeyframe.buildLast(itemStack);

            List<EchoingLayer> echoingLayers = copyEchoingLayers(itemStack);
            if (echoingLayers == null) {
                // Echoing layer limit reached
                return ItemStack.EMPTY;
            }

            echoingLayers.add(computeEchoingLayer(last));

            ItemStack newStack = itemStack.copyWithCount(1);
            newStack.set(ComponentRegistry.ECHOING_LAYERS, echoingLayers);
            return newStack;
        }

        return ItemStack.EMPTY;
    }
}

