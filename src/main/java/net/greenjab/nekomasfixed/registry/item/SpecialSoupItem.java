package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
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
            ContainerComponent c = stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(List.of()));
            List<ItemStack> ingredients = c.stream().toList();
            for (ItemStack ingredient : ingredients) {
                PotionContentsComponent potions = ingredient.get(DataComponentTypes.POTION_CONTENTS);
                if (potions != null) potions.getEffects().forEach(effect -> player.addStatusEffect(new StatusEffectInstance(effect.withScaledDuration(0.5f))));

                FoodComponent food = ingredient.get(DataComponentTypes.FOOD);
                if (food != null) player.getHungerManager().add(MathHelper.ceil(food.nutrition()/2f), food.saturation()/2f);

                ConsumableComponent consume = ingredient.get(DataComponentTypes.CONSUMABLE);
                if (consume != null) consume.finishConsumption(world, user, stack.copy());
            }
        }
        ConsumableComponent consumableComponent = stack.get(DataComponentTypes.CONSUMABLE);
        return consumableComponent != null ? consumableComponent.finishConsumption(world, user, stack) : stack;
    }
}
