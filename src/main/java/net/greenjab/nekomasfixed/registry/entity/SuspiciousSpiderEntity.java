package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SuspiciousSpiderEntity extends Spider {

    public SuspiciousSpiderEntity(EntityType<? extends Spider> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createSuspiciousSpiderAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 16.0F).add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor world, @NonNull DifficultyInstance difficulty, @NonNull EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        entityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        if (entityData instanceof Spider.SpiderEffectsGroupData spiderData) {
            spiderData.setRandomEffect(random);
            Holder<MobEffect> registryEntry = spiderData.effect;
            if (registryEntry != null) this.addEffect(new MobEffectInstance(registryEntry, -1));
        }
        return entityData;
    }

    @Override
    public boolean doHurtTarget(@NonNull ServerLevel world, @NonNull Entity target) {
        boolean bl = super.doHurtTarget(world, target);
        if (bl && target instanceof LivingEntity)
            ((LivingEntity)target).addEffect(getRandomStatusEffectOnHit());
        return bl;
    }

    private MobEffectInstance getRandomStatusEffectOnHit(){
        int i = random.nextInt(4);
        return switch (i) {
            case 0 -> new MobEffectInstance(MobEffects.WEAKNESS, 200, 1, false, true);
            case 1 -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 1, false, true);
            case 2 -> new MobEffectInstance(MobEffects.POISON, 200, 1, false, true);
            default -> new MobEffectInstance(MobEffects.WITHER, 200, 1, false, true);
        };
    }

    public static boolean canSpawn(
            EntityType<? extends Mob> type, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random
    ) {
        return checkMonsterSpawnRules(type, world, spawnReason, pos, random) && (EntitySpawnReason.isSpawner(spawnReason) || !world.canSeeSky(pos));
    }
}
