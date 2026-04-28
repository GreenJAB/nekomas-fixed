package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.List;

public class SpecialSoupItem extends Item {

    public SpecialSoupItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        List<ItemStack> ingredients = stack.getOrDefault(OtherRegistry.SOUP_INGREDIENTS, List.of());
        if(user instanceof PlayerEntity player && !ingredients.isEmpty()){

            int totalNutrition = 0;
            float totalSaturation = 0;

            for(ItemStack ingredient : ingredients){
                if(ingredient.isOf(Items.POTION)){
                    PotionContentsComponent potions = ingredient.get(DataComponentTypes.POTION_CONTENTS);
                    if(potions != null){
                        potions.getEffects().forEach(effect -> player.addStatusEffect(new StatusEffectInstance(effect)));
                    }

                    continue;
                }

                FoodComponent food = ingredient.get(DataComponentTypes.FOOD);

                if(food != null){
                    totalNutrition += food.nutrition();
                    totalSaturation += food.saturation();
                }
            }

            player.getHungerManager().add(totalNutrition, totalSaturation);
        }

        return Items.BOWL.getDefaultStack();
    }
}
