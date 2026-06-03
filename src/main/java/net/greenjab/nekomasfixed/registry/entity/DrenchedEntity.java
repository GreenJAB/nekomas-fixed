package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class DrenchedEntity extends SkeletonEntity {

    public DrenchedEntity(EntityType<? extends DrenchedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
       super.initGoals();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20*10, 1, true, false));
        if (this.isAlive() && this.isAffectedByDaylight()) {
            this.setOnFireFor(8);
        }
    } //this is the class

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }
}