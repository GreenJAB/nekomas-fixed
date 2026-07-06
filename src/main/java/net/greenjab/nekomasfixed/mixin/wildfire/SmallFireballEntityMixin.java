package net.greenjab.nekomasfixed.mixin.wildfire;

import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallFireball.class)
public class SmallFireballEntityMixin {

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/hurtingprojectile/SmallFireball;damageSources()Lnet/minecraft/world/damagesource/DamageSources;"))
    private void wildFireProjectileDamage(EntityHitResult entityHitResult, CallbackInfo ci) {
        SmallFireball SFE = (SmallFireball)(Object)this;
        LivingEntity ownerEntity = (SFE.getOwner() instanceof LivingEntity livingEntity) ? livingEntity:null;
        if (ownerEntity instanceof WildfireEntity wildFireEntity) {
            Entity hitEntity = entityHitResult.getEntity();
            DamageSource damageSource = SFE.damageSources().mobProjectile(SFE, ownerEntity);
            if (SFE.level() instanceof ServerLevel serverWorld && hitEntity.hurtServer(serverWorld, damageSource, wildFireEntity.isSoulActive()?3.0F:2.0F)) {
                EnchantmentHelper.doPostAttackEffects(serverWorld, hitEntity, damageSource);
            }
        }
    }
}