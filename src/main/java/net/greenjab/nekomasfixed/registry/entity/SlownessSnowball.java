package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class SlownessSnowball extends Snowball {

    public SlownessSnowball(EntityType<? extends Snowball> entityType, Level level) {
        super(entityType, level);
    }

    public SlownessSnowball(Level level, LivingEntity owner) {
        super(level, owner);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1), this.getOwner());
            livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 1.0f);
            livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + 100);
        }
    }
}
