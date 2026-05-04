package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class SnowZombieEntity extends ZombieEntity implements RangedAttackMob {

    public SnowZombieEntity(EntityType<? extends SnowZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isAlive() && this.isAffectedByDaylight()) {
            this.setOnFireFor(8);
        }
    }

    @Override
    protected void initCustomGoals() {
        super.initCustomGoals();
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0D, 40, 40, 15.0F));
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        SlownessSnowballEntity snowball = new SlownessSnowballEntity(this.getEntityWorld(), this);

        double dX = target.getX() - this.getX();
        double dY = target.getBodyY(0.3333333333333333D) - snowball.getY();
        double dZ = target.getZ() - this.getZ();
        double distance = Math.sqrt(dX * dX + dZ * dZ);

        snowball.setVelocity(dX, dY + distance * 0.2D, dZ, 1.6F, 14.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getEntityWorld().spawnEntity(snowball);
    }
}