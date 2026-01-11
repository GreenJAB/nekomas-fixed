package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.explosion.ExplosionImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExplosionImpl.class)
public class ExplosionImplMixin {

    @ModifyExpressionValue(method="damageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/explosion/ExplosionBehavior;getKnockbackModifier(Lnet/minecraft/entity/Entity;)F"))
    private float targetDummyNoExplosionKnockback(float original, @Local Entity entity) {
        if (entity instanceof TargetDummyEntity) {
            return 0;
        }
        return original;
    }
}
