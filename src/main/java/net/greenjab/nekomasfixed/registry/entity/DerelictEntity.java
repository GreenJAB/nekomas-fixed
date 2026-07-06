package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class DerelictEntity extends Zombie {

    private int cloudCooldown = 0;

    public DerelictEntity(EntityType<? extends DerelictEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.cloudCooldown > 0) {
            this.cloudCooldown--;
        }
    }

    @Override
    public boolean hurtServer(@NonNull ServerLevel world, @NonNull DamageSource source, float amount) {
        boolean isDamaged = super.hurtServer(world, source, amount);

        if (isDamaged && this.cloudCooldown == 0 && source.getEntity() instanceof LivingEntity) {
            this.spawnPoisonCloud(world);
            this.cloudCooldown = 40;
        }

        return isDamaged;
    }

    private void spawnPoisonCloud(ServerLevel world) {
        AreaEffectCloud cloud = new AreaEffectCloud(world, this.getX(), this.getY(), this.getZ());
        cloud.setOwner(this);

        cloud.setRadius(2.5f);
        cloud.setRadiusOnUse(-0.5f);
        cloud.setWaitTime(10);
        cloud.setDuration(60);

        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));

        world.addFreshEntity(cloud);
    }
}