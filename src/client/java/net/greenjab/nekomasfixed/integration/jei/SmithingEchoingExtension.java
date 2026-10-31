package net.greenjab.nekomasfixed.integration.jei;

import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SmithingEchoingExtension<R extends SmithingEchoingRecipe> implements ISmithingCategoryExtension<@NotNull R> {
    private static final Random RANDOM = new Random();
    private static RegistryWrapper.WrapperLookup WRAPPER_LOOKUP;

    private static List<RegistryEntry<ArmorTrimMaterial>> MATERIALS;
    private static List<RegistryEntry<ArmorTrimPattern>> PATTERNS;

    public static void reload() {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.world != null;

        WRAPPER_LOOKUP = client.world.getRegistryManager();
        MATERIALS = WRAPPER_LOOKUP.getOrThrow(RegistryKeys.TRIM_MATERIAL).streamEntries().collect(Collectors.toUnmodifiableList());
        PATTERNS = WRAPPER_LOOKUP.getOrThrow(RegistryKeys.TRIM_PATTERN).streamEntries().collect(Collectors.toUnmodifiableList());
    }

    private ArmorTrim getRandomTrim() {
        RegistryEntry<ArmorTrimMaterial> material = MATERIALS.get(RANDOM.nextInt(MATERIALS.size()));
        RegistryEntry<ArmorTrimPattern> pattern = PATTERNS.get(RANDOM.nextInt(MATERIALS.size()));
        return new ArmorTrim(material, pattern);
    }

    @Override
    public <T extends IIngredientAcceptor<@NotNull T>> void setTemplate(R recipe, T ingredientAcceptor) {
        recipe.template().ifPresent(ingredientAcceptor::add);
    }

    @Override
    public <T extends IIngredientAcceptor<@NotNull T>> void setBase(R recipe, T ingredientAcceptor) {
        ingredientAcceptor.addItemStacks(recipe.base().getMatchingItems()
                .map(ItemStack::new)
                .peek(stack -> stack.set(DataComponentTypes.TRIM, getRandomTrim()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public <T extends IIngredientAcceptor<@NotNull T>> void setAddition(R recipe, T ingredientAcceptor) {
        recipe.addition().ifPresent(ingredientAcceptor::add);
    }

    @Override
    public void onDisplayedIngredientsUpdate(@NonNull R recipe, IRecipeSlotDrawable templateSlot, @NotNull IRecipeSlotDrawable baseSlot, @NotNull IRecipeSlotDrawable additionSlot, @NotNull IRecipeSlotDrawable outputSlot, @NotNull IFocusGroup focuses) {
        templateSlot.getDisplayedIngredient().ifPresent(typedTemplate ->
                typedTemplate.getItemStack().flatMap(template -> baseSlot.getDisplayedItemStack()).ifPresent(base ->
                        additionSlot.getDisplayedItemStack().ifPresent(addition -> {

                            SmithingRecipeInput input = new SmithingRecipeInput(typedTemplate.getItemStack().get(), base, addition);
                            ItemStack outputStack = recipe.craft(input, WRAPPER_LOOKUP);

                            outputSlot.createDisplayOverrides().add(outputStack);
                        })
                )
        );
    }
}
