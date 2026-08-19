package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.greenjab.nekomasfixed.registry.item.CustomArrowItem;
import net.greenjab.nekomasfixed.registry.other.TippedArrowCustomComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;

import java.util.ArrayList;
import java.util.List;

public class CustomTippedArrowRecipe extends CustomRecipe {

    public static final MapCodec<CustomTippedArrowRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Ingredient.CODEC.listOf().fieldOf("potions")
                            .forGetter(recipe -> recipe.potions),
                    ItemStackTemplate.CODEC.fieldOf("result")
                            .forGetter(recipe -> recipe.result)
            ).apply(instance, CustomTippedArrowRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CustomTippedArrowRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                    recipe -> recipe.potions,
                    ItemStackTemplate.STREAM_CODEC,
                    recipe -> recipe.result,
                    CustomTippedArrowRecipe::new
            );

    public static final RecipeSerializer<CustomTippedArrowRecipe> SERIALIZER =
            new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final List<Ingredient> potions;
    private final ItemStackTemplate result;

    public CustomTippedArrowRecipe(Ingredient potions, ItemStackTemplate result){
        this(List.of(potions), result);
    }

    public CustomTippedArrowRecipe(final List<Ingredient> potions, final ItemStackTemplate result) {
        this.potions = potions;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        List<ItemStack> items = input.items();
        boolean foundPotion = false;
        boolean foundArrow = false;

        for (ItemStack stack : items) {
            if (stack.isEmpty()) continue;

            boolean isPotion = potions.stream().anyMatch(ing -> ing.test(stack));
            if (isPotion) {
                if (foundPotion) return false;
                foundPotion = true;
                continue;
            }

            if (stack.getItem() == Items.ARROW || stack.getItem() instanceof CustomArrowItem) {
                foundArrow = true;
                continue;
            }

            return false;
        }

        return foundPotion && foundArrow;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<PotionContents> combinedEffects = new ArrayList<>();

        for (ItemStack stack : input.items()) {
            if (stack.isEmpty()) continue;
            boolean isPotion = potions.stream().anyMatch(ing -> ing.test(stack));
            if (isPotion) {
                PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
                if (contents != null) {
                    combinedEffects.add(contents);
                }
            }
        }

        if (result == null) {
            return new ItemStack(Items.ARROW, 1);
        }

        ItemStack output = result.create();
        if (!combinedEffects.isEmpty()) {
            TippedArrowCustomComponent component = new TippedArrowCustomComponent(combinedEffects);
            output.set(ComponentRegistry.TIPPED_POTION_CONTENTS, component);
        }

        return output;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    // Optionally override getIngredients() and getResultItem() if needed

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(potions);
        return list;
    }


    public ItemStack getResultItem() {
        return result != null ? result.create() : ItemStack.EMPTY;
    }
}