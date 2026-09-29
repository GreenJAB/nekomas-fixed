package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.mixin.accessor.LivingEntityAccessor;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

// Scythe-like harvest weapon. A plain TieredItem (enchantability/repair come from the
// tier) — deliberately NOT a SwordItem, since main's sickle has no sweep attack.
// The dual-wield trick: right-clicking with the off-hand sickle (main hand also a
// sickle) swaps hands so Player.attack reads the sickle as the main-hand weapon,
// swings, then swaps back.
public class SickleItem extends TieredItem {

    public static final float SPEED = -2.4F;

    public SickleItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    private static void swapHands(Player user) {
        ItemStack off = user.getItemInHand(InteractionHand.OFF_HAND);
        user.setItemInHand(InteractionHand.OFF_HAND, user.getItemInHand(InteractionHand.MAIN_HAND));
        user.setItemInHand(InteractionHand.MAIN_HAND, off);
    }

    @Override
    public @NonNull InteractionResultHolder<ItemStack> use(@NonNull Level level, @NonNull Player user, @NonNull InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) return InteractionResultHolder.pass(user.getItemInHand(hand));
        if (!user.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES))
            return InteractionResultHolder.pass(user.getItemInHand(hand));
        if (user.getAttackStrengthScale(0) < 0.5) return InteractionResultHolder.pass(user.getItemInHand(hand));
        user.getCooldowns().addCooldown(user.getItemInHand(hand).getItem(), 12);
        LivingEntityAccessor player = (LivingEntityAccessor) user;
        if (player.getAttackStrengthTicker() > 5) player.setAttackStrengthTicker(5);
        return InteractionResultHolder.success(user.getItemInHand(hand));
    }

    @Override
    public @NonNull InteractionResult interactLivingEntity(@NonNull ItemStack stack, @NonNull Player user, @NonNull LivingEntity entity, @NonNull InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (!user.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES)) return InteractionResult.PASS;
        if (user.getAttackStrengthScale(0) < 0.5) return InteractionResult.PASS;
        if (user.getCooldowns().getCooldownPercent(stack.getItem(), 0) > 0) return InteractionResult.PASS;
        user.getCooldowns().addCooldown(stack.getItem(), 12);
        LivingEntityAccessor player = (LivingEntityAccessor) user;
        if (player.getAttackStrengthTicker() > 5) player.setAttackStrengthTicker(5);
        if (user.level().isClientSide()) return InteractionResult.SUCCESS;

        int ticks = player.getAttackStrengthTicker();
        swapHands(user);
        player.nekomasfixed$detectEquipmentUpdates();
        player.setAttackStrengthTicker(1000);
        user.attack(entity);
        swapHands(user);
        player.setAttackStrengthTicker(ticks);
        return InteractionResult.SUCCESS;
    }
}
