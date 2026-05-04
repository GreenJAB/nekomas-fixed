package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class JungleZombieEntity extends ZombieEntity {

    private int cloudCooldown = 0;

    public JungleZombieEntity(EntityType<? extends JungleZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.cloudCooldown > 0) {
            this.cloudCooldown--;
        }
        if (this.isAlive() && this.isAffectedByDaylight()) {
            this.setOnFireFor(8);
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        boolean isDamaged = super.damage(world, source, amount);

        if (isDamaged && this.cloudCooldown == 0 && source.getAttacker() instanceof LivingEntity) {
            this.spawnPoisonCloud(world);
            this.cloudCooldown = 40;
        }

        return isDamaged;
    }

    private void spawnPoisonCloud(ServerWorld world) {
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, this.getX(), this.getY(), this.getZ());
        cloud.setOwner(this);

        cloud.setRadius(2.5f);
        cloud.setRadiusOnUse(-0.5f);
        cloud.setWaitTime(10);
        cloud.setDuration(60);

        cloud.addEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0));

        world.spawnEntity(cloud);
    }
}