package net.greenjab.nekomasfixed.registry.recipe;

import net.greenjab.nekomasfixed.registry.block.CoralNautilusBlock;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;

public class CoralNautilusRecipe extends ShapedRecipe {
    private final RawShapedRecipe raw;
    private final ItemStack result;
    private final String group;
    private final CraftingRecipeCategory category;
    private final boolean showNotification;

    public CoralNautilusRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe pattern, ItemStack result, boolean showNotification) {
        super(group, category, pattern, result, showNotification);
        this.group = group;
        this.category = category;
        this.raw = pattern;
        this.result = result;
        this.showNotification = showNotification;
    }

    public RawShapedRecipe getRaw() { return this.raw; }

    public ItemStack getResultStack() { return result; }

    public String getGroup() { return group; }

    public CraftingRecipeCategory getCategory() { return category; }

    public boolean showNotification() { return showNotification; }



    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack resultStack = this.result.copy();

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);

            if (stack.isOf(BlockRegistry.NAUTILUS_BLOCK.asItem())) {
                AnimalComponent data = stack.get(OtherRegistry.ANIMAL);
                if (data != null && !data.animal().isEmpty()) {
                    resultStack.set(OtherRegistry.ANIMAL, data);
                    resultStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT
                            .with(CoralNautilusBlock.OCCUPIED, true));
                } else {
                    resultStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT
                            .with(CoralNautilusBlock.OCCUPIED, false));
                }
                break;
            }
        }

        return resultStack;
    }

    @Override
    public RecipeSerializer<? extends ShapedRecipe> getSerializer() {
        return RecipeRegistry.CORAL_NAUTILUS_SERIALIZER;
    }
}
