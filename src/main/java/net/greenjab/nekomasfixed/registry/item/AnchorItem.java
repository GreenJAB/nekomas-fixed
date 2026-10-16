package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.NonNull;

public class AnchorItem extends Item {
    public AnchorItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public boolean isEnchantable(@NonNull ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean hurtEnemy(@NonNull ItemStack stack, @NonNull LivingEntity target, @NonNull LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public boolean isValidRepairItem(@NonNull ItemStack stack, @NonNull ItemStack repairCandidate) {
        return repairCandidate.is(Items.PRISMARINE_SHARD);
    }
}
