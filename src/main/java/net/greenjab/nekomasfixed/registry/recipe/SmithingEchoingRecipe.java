package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.NekomasFixed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class SmithingEchoingRecipe implements SmithingRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;

    public List<EchoingLayer> copyEchoingLayers(ItemStack item) {
        List<EchoingLayer> echoingLayers = item.get(ComponentRegistry.ECHOING_LAYERS);
        if (echoingLayers == null) return EchoingLayer.emptyList();
            // If the echoing layer limit reached, refuse to craft
        else if (echoingLayers.size() >= NekomasFixed.ECHOING_LAYER_LIMIT) {
            return null;
        }
        // If not, then copy layers from original item stack
        else {
            return new ArrayList<>(echoingLayers);
        }
    }

    public ItemStack getStackWithLayers(ItemStack item, List<EchoingLayer> layers) {
        ItemStack newStack = item.copyWithCount(1);
        newStack.set(ComponentRegistry.ECHOING_LAYERS, layers);
        return newStack;
    }

    public SmithingEchoingRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    public Ingredient getTemplate() {
        return template;
    }

    public Ingredient getBase() {
        return base;
    }

    public Ingredient getAddition() {
        return addition;
    }

    @Override
    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(@NotNull ItemStack stack) {
        return base.test(stack) && stack.has(DataComponents.TRIM);
    }

    @Override
    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        return addition.test(stack);
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, @NotNull Level world) {
        return template.test(smithingRecipeInput.template()) && base.test(smithingRecipeInput.base()) && addition.test(smithingRecipeInput.addition());
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registriesLookup) {
        ItemStack itemStack = new ItemStack(Items.IRON_CHESTPLATE);
        Optional<Holder.Reference<TrimPattern>> pattern = registriesLookup.lookupOrThrow(Registries.TRIM_PATTERN).listElements().findFirst();
        Optional<Holder.Reference<TrimMaterial>> material = registriesLookup.lookupOrThrow(Registries.TRIM_MATERIAL).get(TrimMaterials.REDSTONE);
        if (pattern.isPresent() && material.isPresent()) {
            itemStack.set(DataComponents.TRIM, new ArmorTrim(material.get(), pattern.get()));
        }

        return itemStack;
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(template, base, addition).anyMatch(Ingredient::isEmpty);
    }
}
