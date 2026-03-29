package net.greenjab.nekomasfixed.registry.entity.Termite;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class TermiteEntity extends AnimalEntity {
    public static final AnimationState idleAnimationState = new AnimationState();
    int idleAnimationTimeout = 0;

    public TermiteEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new WanderNearTargetGoal(this, 1.3f, 32f));
        this.goalSelector.add(1, new LookAroundGoal(this));
        this.goalSelector.add(2, new AttackGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.ATTACK_DAMAGE, 2d)
                .add(EntityAttributes.ATTACK_SPEED, 1.6d)
                .add(EntityAttributes.ATTACK_KNOCKBACK, 0.6d)
                .add(EntityAttributes.MOVEMENT_SPEED, 1.3d)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 2d)
                .add(EntityAttributes.STEP_HEIGHT, 1d);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getEntityWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return EntityTypeRegistry.TERMITE.create(world, SpawnReason.BREEDING);
    }
}
