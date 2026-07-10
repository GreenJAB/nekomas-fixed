package net.greenjab.nekomasfixed.mixin.target_dummy;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerExplosion.class)
public class ServerExplosionMixin {

    @ModifyExpressionValue(method="hurtEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ExplosionDamageCalculator;getKnockbackMultiplier(Lnet/minecraft/world/entity/Entity;)F"))
    private float targetDummyNoExplosionKnockback(float original, @Local Entity entity) {
        if (entity instanceof TargetDummy) {
            return 0;
        }
        return original;
    }
}
