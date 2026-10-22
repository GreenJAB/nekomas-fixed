package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class CustomArrow extends Arrow {
    public CustomArrow(final EntityType<? extends CustomArrow> type, Level level) {
        super(type, level);
    }
    public CustomArrow(final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, final @Nullable ItemStack firedFromWeapon) {
        super(EntityTypeRegistry.CUSTOM_ARROW, level);
    }

    @Override
    protected void doPostHurtEffects(final LivingEntity mob) {
        super.doPostHurtEffects(mob);
        Entity effectSource = this.getEffectSource();
        PotionContents potionContents = this.getPotionContents();
        float durationScale = this.getPotionDurationScale();
        potionContents.forEachEffect(/* lambda$doPostHurtEffects$0 */ effect -> mob.addEffect(effect, effectSource), durationScale);
    }

}
