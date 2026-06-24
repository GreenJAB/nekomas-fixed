package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class DrenchedEntity extends AbstractSkeletonEntity {
    public DrenchedEntity(EntityType<? extends DrenchedEntity> entityType, World world) {
        super(entityType, world);
    }

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(DrenchedEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    protected void initGoals() {
        super.initGoals();
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData) {
        this.setVariant(this.random.nextInt(3));
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData);
        if (spawnReason != SpawnReason.CONVERSION && world.getRandom().nextFloat() < 0.0625F) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.ANCHOR));
            this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.085F);
        }
        return data;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20*10, 1, true, false));
        if (this.isAlive() && this.isAffectedByDaylight()) {
            this.setOnFireFor(8);
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
    }

    public int getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
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