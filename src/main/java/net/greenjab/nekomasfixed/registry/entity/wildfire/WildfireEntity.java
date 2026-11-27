package net.greenjab.nekomasfixed.registry.entity.wildfire;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class WildfireEntity extends Monster {
    public float eyeOffset = 0.5F;
    public float clientFireTime = 0;
    public float clientExtraSpin = 0;
    private final ServerBossEvent bossBar;
    private BlockPos spawnPos;
    private static final EntityDataAccessor<Byte> WILDFIRE_FLAGS = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BYTE);

    private static final List<SensorType<? extends Sensor<? super WildfireEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, EntityTypeRegistry.WILDFIRE_ATTACK_ENTITY_SENSOR
    );
    private static final List<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.BREEZE_JUMP_COOLDOWN,
            MemoryModuleType.BREEZE_JUMP_INHALING,
            MemoryModuleType.BREEZE_SHOOT,
            MemoryModuleType.BREEZE_SHOOT_CHARGING,
            MemoryModuleType.BREEZE_SHOOT_RECOVERING,
            MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
            MemoryModuleType.BREEZE_JUMP_TARGET,
            MemoryModuleType.BREEZE_LEAVING_WATER,
            MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS,
            MemoryModuleType.TOUCH_COOLDOWN,
            MemoryModuleType.SNIFF_COOLDOWN,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.PATH
    );

    public WildfireEntity(EntityType<? extends WildfireEntity> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        this.bossBar = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS);
        this.xpReward = 50;
        setShieldsActive(4);
    }

    public static boolean canSpawn(EntityType<WildfireEntity> type, ServerLevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    public BlockPos getSpawnPos() {
        return spawnPos;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("State", this.entityData.get(WILDFIRE_FLAGS));
        if (spawnPos == null) spawnPos = new BlockPos(0, 0, 0);
        tag.putInt("spawnX", spawnPos.getX());
        tag.putInt("spawnY", spawnPos.getY());
        tag.putInt("spawnZ", spawnPos.getZ());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(WILDFIRE_FLAGS, (byte) tag.getInt("State"));
        spawnPos = new BlockPos(tag.getInt("spawnX"), tag.getInt("spawnY"), tag.getInt("spawnZ"));
        if (this.hasCustomName()) this.bossBar.setName(this.getDisplayName());
        if (isSoulActive()) this.bossBar.setColor(BossEvent.BossBarColor.BLUE);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    protected Brain.Provider<WildfireEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return WildfireAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<WildfireEntity> getBrain() {
        return (Brain<WildfireEntity>) super.getBrain();
    }

    public static AttributeSupplier.Builder createWildfireAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 150.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(WILDFIRE_FLAGS, (byte) 16);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    public void aiStep() {
        if (spawnPos == null || spawnPos.closerThan(new BlockPos(0, 0, 0), 1))
            spawnPos = new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ());
        if (!this.onGround() && this.getDeltaMovement().y < 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, (this.eyeOffset > -1 ? 0.85 : 0.6), 1.0));
        }

        if (this.level().isClientSide()) {
            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                this.level().playLocalSound(
                        this.getX() + 0.5,
                        this.getY() + 0.5,
                        this.getZ() + 0.5,
                        SoundEvents.BLAZE_BURN,
                        this.getSoundSource(),
                        1.0F + this.random.nextFloat(),
                        this.random.nextFloat() * 0.7F + 0.3F,
                        false
                );
            }

            if (this.level().getGameTime() % 2 == 0) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
                this.level().addParticle(isSoulActive() ? ParticleTypes.SOUL : ParticleTypes.LAVA, this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 0.0, 0.0, 0.0);
            }

            this.clientFireTime = Mth.clamp(this.clientFireTime + 0.5f / 20f * (this.isOnFire() ? 1 : -1), 0, 1);
            this.clientExtraSpin += this.clientFireTime * 4;
        }

        super.aiStep();
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel level = (ServerLevel) this.level();
        LivingEntity livingEntity = this.getTarget();
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        if (livingEntity != null && this.canAttack(livingEntity)) {

            if (this.hasLineOfSight(livingEntity)) {
                this.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            }

            Vec3 vec3d = this.getDeltaMovement();

            double d = livingEntity.getEyeY() - (this.getEyeY() + this.eyeOffset);
            if (this.eyeOffset > -1 && d > -3) {
                BlockHitResult blockHitResult = level.clip(
                        new ClipContext(
                                this.getEyePosition(), this.position().add(0, -3, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this
                        )
                );
                if (blockHitResult.getType() == HitResult.Type.MISS) {
                    if (livingEntity.getEyeY() > this.getEyeY() + this.eyeOffset) {
                        this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.3F - vec3d.y) * 0.6F, 0.0));
                    }
                    this.setDeltaMovement(this.getDeltaMovement().add(livingEntity.getEyePosition().subtract(this.position()).multiply(1, 0, 1).normalize().scale(0.03f)));
                }
            } else {
                if (livingEntity.getEyeY() > this.getEyeY() + this.eyeOffset) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.3F - vec3d.y) * 0.6F, 0.0));
                    this.hurtMarked = true;
                }
            }
        }

        if (level.getGameTime() % 20 == 0) {
            if (level.getBlockState(this.blockPosition()).is(BlockTags.FIRE)) this.heal(1);
            int lastShields = getShieldsActive();
            int newShields = (int) Mth.clamp(5 * this.getHealth() / this.getMaxHealth(), 0, 4);
            setShieldsActive(newShields);
            if (newShields < lastShields) {
                level.playSound(null, this, SoundEvents.WOLF_ARMOR_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else if (newShields > lastShields) {
                level.playSound(null, this, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.7F, 2.0F);
            }
        }

        ProfilerFiller profiler = level.getProfiler();
        profiler.push("wildfireBrain");
        this.getBrain().tick(level, this);
        profiler.popPush("wildfireActivityUpdate");
        WildfireAi.updateActivities(this);
        profiler.pop();
        super.customServerAiStep();
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetFromBrain();
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public boolean isOnFire() {
        return this.isFireActive();
    }

    private boolean isFireActive() {
        return (this.entityData.get(WILDFIRE_FLAGS) & 1) != 0;
    }

    public void setFireActive(boolean fireActive) {
        byte b = this.entityData.get(WILDFIRE_FLAGS);
        if (fireActive) {
            b = (byte) (b | 1);
        } else {
            b = (byte) (b & -(1 + 1));
        }

        this.entityData.set(WILDFIRE_FLAGS, b);
    }

    public boolean isSoulActive() {
        return (this.entityData.get(WILDFIRE_FLAGS) & 2) != 0;
    }

    public void setSoulActive(boolean soulActive) {
        byte b = this.entityData.get(WILDFIRE_FLAGS);
        if (soulActive) {
            b = (byte) (b | 2);
        } else {
            b = (byte) (b & -(2 + 1));
        }
        this.bossBar.setColor(BossEvent.BossBarColor.BLUE);
        this.entityData.set(WILDFIRE_FLAGS, b);
    }

    public int getShieldsActive() {
        return (this.entityData.get(WILDFIRE_FLAGS) & 28) / 4;
    }

    public void setShieldsActive(int shieldsActive) {
        byte b = this.entityData.get(WILDFIRE_FLAGS);
        b = (byte) (b & -(28 + 1));
        b = (byte) (b | 4 * shieldsActive);

        this.entityData.set(WILDFIRE_FLAGS, b);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this == source.getEntity()) return false;
        if (!isOnFire()) {
            Entity entity = source.getDirectEntity();
            if (entity instanceof AbstractArrow || entity instanceof WindCharge) {
                if (random.nextInt(4) < getShieldsActive()) {
                    this.level().playSound(null, this, SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
                    return false;
                }
            }
        }
        return super.hurt(source, amount);
    }
}
