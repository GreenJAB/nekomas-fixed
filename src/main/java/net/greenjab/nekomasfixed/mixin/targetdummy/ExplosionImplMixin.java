package net.greenjab.nekomasfixed.mixin.targetdummy;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerExplosion.class)
public class ExplosionImplMixin {

    @ModifyExpressionValue(method="hurtEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ExplosionDamageCalculator;getKnockbackMultiplier(Lnet/minecraft/world/entity/Entity;)F"))
    private float targetDummyNoExplosionKnockback(float original, @Local Entity entity) {
        if (entity instanceof TargetDummyEntity) {
            return 0;
        }
        return original;
    }
}
