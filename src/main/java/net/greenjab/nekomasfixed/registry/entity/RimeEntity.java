package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;

public class RimeEntity extends Zombie implements RangedAttackMob {

    public RimeEntity(EntityType<? extends RimeEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 40, 15.0F));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        SlownessSnowballEntity snowball = new SlownessSnowballEntity(this.level(), this);

        double dX = target.getX() - this.getX();
        double dY = target.getY(0.3333333333333333D) - snowball.getY();
        double dZ = target.getZ() - this.getZ();
        double distance = Math.sqrt(dX * dX + dZ * dZ);

        snowball.shoot(dX, dY + distance * 0.2D, dZ, 1.6F, 14.0F);

        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

        this.level().addFreshEntity(snowball);
    }
}