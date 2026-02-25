package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.mixin.PlayerEntityInterfaceMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.greenjab.nekomasfixed.mixin.PlayerEntityInterfaceMixin;

public class DamageHelper {

    public static class DamageCalculation {
        private final float baseDamage;
        private final float finalDamage;
        private final Entity target;
        private final DamageSource damageSource;

        public DamageCalculation(float baseDamage, float finalDamage, Entity target, DamageSource damageSource) {
            this.baseDamage = baseDamage;
            this.finalDamage = finalDamage;
            this.target = target;
            this.damageSource = damageSource;
        }

        public float getBaseDamage() { return baseDamage; }
        public float getFinalDamage() { return finalDamage; }
        public float getDamageReduction() {
            return baseDamage - finalDamage;
        }
        public float getReductionPercentage() {
            return baseDamage > 0 ? ((baseDamage - finalDamage) / baseDamage) * 100 : 0;
        }

        @Override
        public String toString() {
            return String.format("Damage: %.2f → %.2f (Reduced by %.2f, %.1f%%)",
                    baseDamage, finalDamage, getDamageReduction(), getReductionPercentage());
        }
    }

    public static DamageCalculation calculateDamage(PlayerEntity player, Entity target, float baseDamage, DamageSource damageSource) {
        float finalDamage = ((PlayerEntityInterfaceMixin) player).invokeGetDamageAgainst(target, baseDamage, damageSource);
        return new DamageCalculation(baseDamage, finalDamage, target, damageSource);
    }

    public static float onPlayerAttack(PlayerEntity player, Entity target, float baseDamage) {
        DamageSource damageSource = player.getDamageSources().playerAttack(player);
        DamageCalculation calc = calculateDamage(player, target, baseDamage, damageSource);
        float finalDamage = calc.getFinalDamage();
        float reduction = calc.getDamageReduction();
        return finalDamage;
    }

}