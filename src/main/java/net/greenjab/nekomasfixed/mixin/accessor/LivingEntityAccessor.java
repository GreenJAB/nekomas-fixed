package net.greenjab.nekomasfixed.mixin.accessor;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

// Exposes LivingEntity.attackStrengthTicker (protected) so the sickle can briefly
// force a full-charge attack while dual-wielding, then restore the player's rhythm,
// plus the private detectEquipmentUpdates needed to refresh attributes after a hand swap.
@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("attackStrengthTicker")
    int getAttackStrengthTicker();

    @Accessor("attackStrengthTicker")
    void setAttackStrengthTicker(int value);

    @Invoker("detectEquipmentUpdates")
    void nekomasfixed$detectEquipmentUpdates();
}
