package net.greenjab.nekomasfixed.integration.jei;

import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SmithingEchoingExtension<R extends SmithingEchoingRecipe> implements ISmithingCategoryExtension<R> {
    private static final HolderLookup.Provider WRAPPER_LOOKUP;
    private static final Random RANDOM = new Random();


    public static final List<Holder<TrimMaterial>> MATERIALS;
    public static final List<Holder<TrimPattern>> PATTERNS;

    static {
        Minecraft client = Minecraft.getInstance();
        assert client.level != null;

        WRAPPER_LOOKUP = client.level.registryAccess();
        MATERIALS = WRAPPER_LOOKUP.lookupOrThrow(Registries.TRIM_MATERIAL).listElements().collect(Collectors.toUnmodifiableList());
        PATTERNS = WRAPPER_LOOKUP.lookupOrThrow(Registries.TRIM_PATTERN).listElements().collect(Collectors.toUnmodifiableList());
    }

    private ArmorTrim getRandomTrim() {
        Holder<TrimMaterial> material = MATERIALS.get(RANDOM.nextInt(MATERIALS.size()));
        Holder<TrimPattern> pattern = PATTERNS.get(RANDOM.nextInt(MATERIALS.size()));
        return new ArmorTrim(material, pattern);
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setTemplate(R recipe, T ingredientAcceptor) {
        ingredientAcceptor.addIngredients(recipe.getTemplate());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(R recipe, T ingredientAcceptor) {
        ingredientAcceptor.addItemStacks(Arrays.stream(recipe.getBase().getItems())
                .peek((stack) -> {
                    stack.set(DataComponents.TRIM, getRandomTrim());

                }).collect(Collectors.toList())
        );
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(R recipe, T ingredientAcceptor) {
        ingredientAcceptor.addIngredients(recipe.getAddition());
    }

    @Override
    public void onDisplayedIngredientsUpdate(@NotNull R recipe, IRecipeSlotDrawable templateSlot, IRecipeSlotDrawable baseSlot, IRecipeSlotDrawable additionSlot, IRecipeSlotDrawable outputSlot, IFocusGroup focuses) {
        templateSlot.getDisplayedIngredient().ifPresent(typedTemplate ->
                typedTemplate.getItemStack().flatMap(template -> baseSlot.getDisplayedItemStack()).ifPresent(base ->
                        additionSlot.getDisplayedItemStack().ifPresent(addition -> {

                            SmithingRecipeInput input = new SmithingRecipeInput(typedTemplate.getItemStack().get(), base, addition);
                            ItemStack outputStack = recipe.assemble(input, WRAPPER_LOOKUP);

                            outputSlot.createDisplayOverrides().addItemStack(outputStack);
                        })));
    }
}
