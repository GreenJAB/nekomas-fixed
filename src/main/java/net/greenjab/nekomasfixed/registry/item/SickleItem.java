package net.greenjab.nekomasfixed.registry.item;

import com.mojang.authlib.GameProfile;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SickleItem extends Item {

    public static final float SPEED = -1F;

    public SickleItem(Item.Settings settings) {
        super(settings);
    }


    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND)  return ActionResult.PASS;
        if (!user.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES))  return ActionResult.PASS;
        user.getItemCooldownManager().set(user.getStackInHand(hand), 10);
        return ActionResult.SUCCESS;
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (hand == Hand.MAIN_HAND)  return ActionResult.PASS;
        if (!user.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES))  return ActionResult.PASS;
        if (user.getEntityWorld().isClient()) return ActionResult.SUCCESS;
        user.getItemCooldownManager().set(stack, 10);

        PlayerEntity p = new PlayerEntity(entity.getEntityWorld(), new GameProfile(UUID.randomUUID(), user.getStringifiedName())) {
            @Override
            public @NotNull GameMode getGameMode() {
                return GameMode.SURVIVAL;
            }
        };

        p.updatePositionAndAngles(user.getX(), user.getY(), user.getZ(), user.getYaw(), user.getPitch());
        p.getInventory().setStack(0, stack);
        p.sendEquipmentChanges();
        p.ticksSinceLastAttack =1000;
        p.attack(entity);
        return ActionResult.SUCCESS;
    }

}
