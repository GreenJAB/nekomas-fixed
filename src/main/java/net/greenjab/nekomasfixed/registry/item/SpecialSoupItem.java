package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class SpecialSoupItem extends Item {

    public SpecialSoupItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient() && user instanceof PlayerEntity player) {

            List<ItemStack> ingredients = stack.getOrDefault(ComponentRegistry.SOUP_INGREDIENTS, List.of());

            int totalNutrition = 0;
            float totalSaturation = 0;

            for (ItemStack ingredient : ingredients) {

                if (ingredient.isOf(Items.POTION)) {
                    PotionContentsComponent potions = ingredient.get(DataComponentTypes.POTION_CONTENTS);

                    if (potions != null) {
                        potions.getEffects().forEach(effect ->
                                player.addStatusEffect(new StatusEffectInstance(effect))
                        );
                    }
                    continue;
                }

                FoodComponent food = ingredient.get(DataComponentTypes.FOOD);

                if (food != null) {
                    totalNutrition += food.nutrition();
                    totalSaturation += food.saturation();
                }
            }

            player.getHungerManager().add(totalNutrition, totalSaturation);
        }

        return new ItemStack(Items.BOWL); // clean replacement
    }
}
