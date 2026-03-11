package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class SoulfireShieldItem extends ShieldItem {
    public SoulfireShieldItem(Item.Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(user.getHealth()<=6.0F){
            Vec3d velocity = user.getVelocity();
            entity.setOnFireForTicks(20*3);
            entity.knockbackTarget(entity, 3f, velocity);
        }else{
            entity.setOnFireForTicks(20*1);
        }

        return ActionResult.PASS;
    }
}
