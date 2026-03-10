package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SickleItem extends Item {

    public static final float SPEED = -2.4F;

    public SickleItem(Item.Settings settings) {
        super(settings);
    }


    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND) return ActionResult.PASS;
        if (!user.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES))  return ActionResult.PASS;
        if (user.getAttackCooldownProgress(0)<0.5) return ActionResult.PASS;
        user.getItemCooldownManager().set(user.getStackInHand(hand), 12);
        if (user.ticksSinceLastAttack>5) user.ticksSinceLastAttack = 5;
        return ActionResult.SUCCESS;
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (hand == Hand.MAIN_HAND) return ActionResult.PASS;
        if (!user.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES))  return ActionResult.PASS;
        if (user.getAttackCooldownProgress(0)<0.5) return ActionResult.PASS;
        if (user.getItemCooldownManager().getCooldownProgress(user.getStackInHand(hand), 0)>0) return ActionResult.PASS;
        user.getItemCooldownManager().set(stack, 12);
        if (user.ticksSinceLastAttack>5) user.ticksSinceLastAttack = 5;
        if (user.getEntityWorld().isClient()) return ActionResult.SUCCESS;

        int tt = user.ticksSinceLastAttack;

        swapHands(user);
        user.sendEquipmentChanges();

        user.ticksSinceLastAttack =1000;
        user.attack(entity);

        swapHands(user);

        user.ticksSinceLastAttack =tt;
        return ActionResult.SUCCESS;
    }

    private static void swapHands(PlayerEntity user) {
        ItemStack itemStack = user.getStackInHand(Hand.OFF_HAND);
        user.setStackInHand(Hand.OFF_HAND, user.getStackInHand(Hand.MAIN_HAND));
        user.setStackInHand(Hand.MAIN_HAND, itemStack);
    }

}
