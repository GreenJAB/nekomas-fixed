package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.WildFire.WildFireEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallFireballEntity.class)
public class SmallFireballEntityMixin {

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/SmallFireballEntity;getDamageSources()Lnet/minecraft/entity/damage/DamageSources;"))
    private void wildFireProjectileDamage(EntityHitResult entityHitResult, CallbackInfo ci) {
        SmallFireballEntity SFE = (SmallFireballEntity)(Object)this;
        LivingEntity ownerEntity = (SFE.getOwner() instanceof LivingEntity livingEntity) ? livingEntity:null;
        if (ownerEntity instanceof WildFireEntity) {
            Entity hitEntity = entityHitResult.getEntity();
            DamageSource damageSource = SFE.getDamageSources().mobProjectile(SFE, ownerEntity);
            if (SFE.getEntityWorld() instanceof ServerWorld serverWorld && hitEntity.damage(serverWorld, damageSource, 2.0F)) {
                EnchantmentHelper.onTargetDamaged(serverWorld, hitEntity, damageSource);
            }
        }
    }
}
