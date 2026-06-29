package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class SuspiciousSpiderEntity extends SpiderEntity {

    public SuspiciousSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSuspiciousSpiderAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.MAX_HEALTH, 16.0F).add(EntityAttributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);
        if (entityData instanceof SpiderEntity.SpiderData spiderData) {
            spiderData.setEffect(random);
            RegistryEntry<StatusEffect> registryEntry = spiderData.effect;
            if (registryEntry != null) this.addStatusEffect(new StatusEffectInstance(registryEntry, -1));
        }
        return entityData;
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        boolean bl = super.tryAttack(world, target);
        if (bl && target instanceof LivingEntity)
            ((LivingEntity)target).addStatusEffect(getRandomStatusEffectOnHit());
        return bl;
    }

    private StatusEffectInstance getRandomStatusEffectOnHit(){
        int i = random.nextInt(4);
        return switch (i) {
            case 0 -> new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 1, false, true);
            case 1 -> new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 1, false, true);
            case 2 -> new StatusEffectInstance(StatusEffects.POISON, 200, 1, false, true);
            default -> new StatusEffectInstance(StatusEffects.WITHER, 200, 1, false, true);
        };
    }

    public static boolean canSpawn(
            EntityType<? extends MobEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random
    ) {
        return canSpawnInDark(type, world, spawnReason, pos, random) && (SpawnReason.isAnySpawner(spawnReason) || !world.isSkyVisible(pos));
    }
}
