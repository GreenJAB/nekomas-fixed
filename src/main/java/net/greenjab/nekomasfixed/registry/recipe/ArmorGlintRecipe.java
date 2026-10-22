package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.other.ArmorGlint;
import net.greenjab.nekomasfixed.registry.other.GlintColor;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SmithingRecipeDisplay;

import java.util.List;
import java.util.Optional;

public class ArmorGlintRecipe extends SimpleSmithingRecipe {
    public static final MapCodec<ArmorGlintRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
            Ingredient.CODEC.fieldOf("template").forGetter(r -> r.template),
            Ingredient.CODEC.fieldOf("base").forGetter(r -> r.base),
            Ingredient.CODEC.fieldOf("addition").forGetter(r -> r.addition)
    ).apply(i, ArmorGlintRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorGlintRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC, r -> r.commonInfo,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.template,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.base,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.addition,
            ArmorGlintRecipe::new);

    public static final RecipeSerializer<ArmorGlintRecipe> SERIALIZER =
            new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;

    public ArmorGlintRecipe(Recipe.CommonInfo commonInfo, Ingredient template, Ingredient base, Ingredient addition) {
        super(commonInfo);
        this.base = base;
        this.addition = addition;
        this.template = template;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input) {
        Holder<GlintColor> color = input.template().get(ComponentRegistry.PROVIDES_GLINT);
        if (color == null) return ItemStack.EMPTY;

        ArmorGlint glint = new ArmorGlint(color);
        if (glint.equals(input.base().get(ComponentRegistry.ARMOR_GLINT))) return ItemStack.EMPTY; // already this color

        ItemStack result = input.base().copyWithCount(1);
        result.set(ComponentRegistry.ARMOR_GLINT, glint);
        return result;
    }

    @Override public Optional<Ingredient> templateIngredient() { return Optional.of(template); }
    @Override public Ingredient baseIngredient() { return base; }
    @Override public Optional<Ingredient> additionIngredient() { return Optional.of(addition); }

    @Override
    public RecipeSerializer<ArmorGlintRecipe> getSerializer() { return SERIALIZER; }

    @Override
    protected PlacementInfo createPlacementInfo() { return PlacementInfo.create(List.of(template, base, addition)); }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new SmithingRecipeDisplay(
                template.display(),
                base.display(),
                addition.display(),
                base.display(),
                new SlotDisplay.ItemSlotDisplay(Items.SMITHING_TABLE)));
    }
}