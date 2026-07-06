package net.greenjab.nekomasfixed.registry.recipe;
import net.greenjab.nekomasfixed.registry.block.NautilusBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jspecify.annotations.NonNull;

public class ZombieNautilusRecipe extends ShapedRecipe {
    private final ShapedRecipePattern raw;
    private final ItemStack result;
    private final String group;
    private final CraftingBookCategory category;
    private final boolean showNotification;

    public ZombieNautilusRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
        super(group, category, pattern, result, showNotification);
        this.group = group;
        this.category = category;
        this.raw = pattern;
        this.result = result;
        this.showNotification = showNotification;
    }

    public ShapedRecipePattern getRaw() { return this.raw; }

    public ItemStack getResultStack() { return result; }

    public @NonNull String group() { return group; }

    public @NonNull CraftingBookCategory category() { return category; }

    public boolean showNotification() { return showNotification; }



    @Override
    public @NonNull ItemStack assemble(CraftingInput input, HolderLookup.@NonNull Provider lookup) {
        ItemStack resultStack = this.result.copy();

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);

            if (stack.is(BlockRegistry.NAUTILUS_BLOCK.asItem())) {
                AnimalComponent data = stack.get(ComponentRegistry.ANIMAL);
                if (data != null && !data.animal().isEmpty()) {
                    resultStack.set(ComponentRegistry.ANIMAL, data);
                    resultStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY
                            .with(NautilusBlock.OCCUPIED, true));
                } else {
                    resultStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY
                            .with(NautilusBlock.OCCUPIED, false));
                }
                break;
            }
        }

        return resultStack;
    }

    @Override
    public @NonNull RecipeSerializer<? extends ShapedRecipe> getSerializer() {
        return RecipeRegistry.ZOMBIE_NAUTILUS_SERIALIZER;
    }
}