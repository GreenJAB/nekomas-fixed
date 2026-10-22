package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.greenjab.nekomasfixed.registry.other.TippedArrowCustomComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomTippedArrowRecipe extends NormalCraftingRecipe {

    public static final MapCodec<CustomTippedArrowRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(recipe -> recipe.bookInfo),
                    Ingredient.CODEC.fieldOf("arrow").forGetter(recipe -> recipe.arrow),
                    Ingredient.CODEC.fieldOf("potion").forGetter(recipe -> recipe.potion),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
            ).apply(instance, CustomTippedArrowRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CustomTippedArrowRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Recipe.CommonInfo.STREAM_CODEC,
                    recipe -> recipe.commonInfo,
                    CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
                    recipe -> recipe.bookInfo,
                    Ingredient.CONTENTS_STREAM_CODEC,
                    recipe -> recipe.arrow,
                    Ingredient.CONTENTS_STREAM_CODEC,
                    recipe -> recipe.potion,
                    ItemStackTemplate.STREAM_CODEC,
                    recipe -> recipe.result,
                    CustomTippedArrowRecipe::new
            );

    public static final RecipeSerializer<CustomTippedArrowRecipe> SERIALIZER =
            new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Ingredient arrow;
    private final Ingredient potion;
    private final ItemStackTemplate result;

    public CustomTippedArrowRecipe(CommonInfo info, CraftingBookInfo bookInfo, Ingredient arrow, Ingredient potion, ItemStackTemplate result) {
        super(info, bookInfo);
        this.arrow = arrow;
        this.potion = potion;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, @NonNull Level level) {
        boolean foundArrow = false;
        List<Holder<Potion>> potions = new ArrayList<>();

        for (ItemStack stack : input.items()) {
            if (stack.isEmpty()) continue;

            if(arrow.test(stack) && foundArrow){
                return false;
            }

            if (arrow.test(stack)) {
                foundArrow = true;
                continue;
            }



            if (potion.test(stack)) {
                PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
                if (contents == null) continue;

                Holder<Potion> type = contents.potion().orElse(null);
                if (type == null) continue;

                potions.add(type);
            }
        }

        if (!foundArrow || potions.isEmpty()) return false;

        for (int i = 0; i < potions.size(); i++) {
            for (int j = i + 1; j < potions.size(); j++) {
                if (!checkPotions(potions.get(i).value(), potions.get(j).value())) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkPotions(Potion pot1, Potion pot2) {
        List<MobEffectInstance> pot1Effects = pot1.getEffects();
        List<MobEffectInstance> pot2Effects = pot2.getEffects();

        if (pot1Effects.size() != pot2Effects.size()) {
            return true;
        }

        for (int i = 0; i < pot1Effects.size(); i++) {
            MobEffectInstance pot1Ins = pot1Effects.get(i);
            MobEffectInstance pot2Ins = pot2Effects.get(i);

            if(pot1Ins.getEffect().value().equals(pot2Ins.getEffect().value()))return false;
        }
        return true;
    }


    @Override
    public @NonNull ItemStack assemble(CraftingInput input) {
        List<PotionContents> combinedEffects = new ArrayList<>();
        int count = 0;


        for (ItemStack stack : input.items()) {
            if (stack.isEmpty()) continue;
            if(arrow.test(stack) && count==0){
                count = stack.getCount();
            }
            if (potion.test(stack)) {
                PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
                if (contents != null) {
                    combinedEffects.add(contents);
                }
            }
        }

        ItemStack output = result.create();
        output.setCount(count);
        if (!combinedEffects.isEmpty()) {

            TippedArrowCustomComponent component = new TippedArrowCustomComponent(combinedEffects);
            output.set(ComponentRegistry.TIPPED_POTION_CONTENTS, component);
        }

        return output;
    }

    @Override
    public @NonNull RecipeSerializer<? extends NormalCraftingRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected @NonNull PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(List.of(arrow, potion));
    }


}