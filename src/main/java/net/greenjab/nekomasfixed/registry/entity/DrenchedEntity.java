package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class DrenchedEntity extends AbstractSkeletonEntity {

    public DrenchedEntity(EntityType<? extends DrenchedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {

    }

    @Override
    public SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }

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