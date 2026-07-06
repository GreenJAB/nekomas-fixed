package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class SickleItem extends Item {

    public static final float SPEED = -2.4F;

    public SickleItem(Item.Properties settings) {
        super(settings);
    }


    public @NonNull InteractionResult use(@NonNull Level world, @NonNull Player user, @NonNull InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (!user.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES))  return InteractionResult.PASS;
        if (user.getAttackStrengthScale(0)<0.5) return InteractionResult.PASS;
        user.getCooldowns().addCooldown(user.getItemInHand(hand), 12);
        if (user.attackStrengthTicker>5) user.attackStrengthTicker = 5;
        return InteractionResult.SUCCESS;
    }

    public @NonNull InteractionResult interactLivingEntity(@NonNull ItemStack stack, @NonNull Player user, @NonNull LivingEntity entity, @NonNull InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (!user.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES))  return InteractionResult.PASS;
        if (user.getAttackStrengthScale(0)<0.5) return InteractionResult.PASS;
        if (user.getCooldowns().getCooldownPercent(user.getItemInHand(hand), 0)>0) return InteractionResult.PASS;
        user.getCooldowns().addCooldown(stack, 12);
        if (user.attackStrengthTicker>5) user.attackStrengthTicker = 5;
        if (user.level().isClientSide()) return InteractionResult.SUCCESS;

        int tt = user.attackStrengthTicker;

        swapHands(user);
        user.detectEquipmentUpdates();

        user.attackStrengthTicker =1000;
        user.attack(entity);

        swapHands(user);

        user.attackStrengthTicker =tt;
        return InteractionResult.SUCCESS;
    }

    private static void swapHands(Player user) {
        ItemStack itemStack = user.getItemInHand(InteractionHand.OFF_HAND);
        user.setItemInHand(InteractionHand.OFF_HAND, user.getItemInHand(InteractionHand.MAIN_HAND));
        user.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
    }

}
