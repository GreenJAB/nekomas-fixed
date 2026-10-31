package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.registry.other.EchoingLayer;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;
import java.util.Optional;

public class SmithingEchoingPigmentRecipe extends SmithingEchoingRecipe {

    public SmithingEchoingPigmentRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        super(template, base, addition);
    }

    public ItemStack craft(SmithingRecipeInput smithingRecipeInput, RegistryWrapper.WrapperLookup registries) {
        ItemStack itemStack = smithingRecipeInput.base();
            Optional<RegistryEntry<ArmorTrimMaterial>> optionalMaterial = ArmorTrimMaterials.get(registries, smithingRecipeInput.addition());
            if (optionalMaterial.isPresent()) {
                RegistryEntry<ArmorTrimMaterial> material = optionalMaterial.get();

                EchoingKeyframe last = EchoingKeyframe.buildLast(itemStack);
                if (last.material().equals(material)) {
                    return ItemStack.EMPTY;
                }

                List<EchoingLayer> echoingLayers = copyEchoingLayers(itemStack);
                if (echoingLayers == null) {
                    return ItemStack.EMPTY;
                }

                echoingLayers.add(new EchoingLayer(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(material)
                ));

                ItemStack newStack = itemStack.copyWithCount(1);
                newStack.set(ComponentRegistry.ECHOING_LAYERS, echoingLayers);
                return newStack;
            }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return RecipeRegistry.SMITHING_ECHOING_PIGMENT_RECIPE;
    }
}

