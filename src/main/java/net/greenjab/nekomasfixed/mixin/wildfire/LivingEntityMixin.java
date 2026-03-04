package net.greenjab.nekomasfixed.mixin.wildfire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildFireEntity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyExpressionValue(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tryUseDeathProtector(Lnet/minecraft/entity/damage/DamageSource;)Z"))
    private boolean wildFireSecondPhase(boolean original, @Local(argsOnly = true) DamageSource source) {
        LivingEntity LE = (LivingEntity)(Object)this;
        if (LE instanceof WildFireEntity wildFireEntity) {
            if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return false;
            } else {
                if (!wildFireEntity.isSoulActive()) {
                    wildFireEntity.setSoulActive(true);
                    wildFireEntity.setShieldsActive(4);
                    wildFireEntity.setHealth(wildFireEntity.getMaxHealth());
                    wildFireEntity.getEntityWorld().sendEntityStatus(wildFireEntity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                    return true;
                }
            }
        }
        return original;
    }
}
