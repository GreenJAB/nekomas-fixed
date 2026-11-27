package net.greenjab.nekomasfixed.mixin.wildfire;

import net.greenjab.nekomasfixed.registry.entity.wildfire.WildfireEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallFireball.class)
public class SmallFireballMixin {

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/SmallFireball;damageSources()Lnet/minecraft/world/damagesource/DamageSources;"))
    private void wildFireProjectileDamage(EntityHitResult hitResult, CallbackInfo ci) {
        SmallFireball fireball = (SmallFireball) (Object) this;
        if (fireball.getOwner() instanceof WildfireEntity wildFireEntity && fireball.level() instanceof ServerLevel serverLevel) {
            Entity hitEntity = hitResult.getEntity();
            DamageSource damageSource = fireball.damageSources().mobProjectile(fireball, wildFireEntity);
            if (hitEntity.hurt(damageSource, wildFireEntity.isSoulActive() ? 3.0F : 2.0F)) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, hitEntity, damageSource);
            }
        }
    }
}
