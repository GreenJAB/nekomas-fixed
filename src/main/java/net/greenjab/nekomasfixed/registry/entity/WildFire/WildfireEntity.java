package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.mojang.serialization.Dynamic;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.util.debug.DebugValueSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.monster.breeze.BreezeAi;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class WildfireEntity extends Monster {
	public float eyeOffset = 0.5F;
	public float clientFireTime = 0;
	public float clientExtraSpin = 0;
	private final ServerBossEvent bossBar;
	private BlockPos spawnPos;
	private static final EntityDataAccessor<Byte> WILDFIRE_FLAGS = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BYTE);

	private static final Brain.Provider<WildfireEntity> BRAIN_PROVIDER = Brain.provider(
			List.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, OtherRegistry.WILDFIRE_ATTACK_ENTITY_SENSOR), WildfireAi::getActivities
	);

	public WildfireEntity(EntityType<? extends WildfireEntity> entityType, Level world) {
		super(entityType, world);
		this.setPathfindingMalus(PathType.WATER, -1.0F);
		this.setPathfindingMalus(PathType.LAVA, 8.0F);
		this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 0.0F);
		this.setPathfindingMalus(PathType.FIRE, 0.0F);
		this.bossBar = (new ServerBossEvent(Mth.createInsecureUUID(this.level().getRandom()), this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS));
		this.xpReward = 50;
		setShieldsActive(4);
	}

	public static boolean canSpawn(EntityType<WildfireEntity> type, LevelAccessor world, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		return true;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader world) {
		return world.isUnobstructed(this);
	}

	public BlockPos getSpawnPos(){
		return spawnPos;
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput view) {
		super.addAdditionalSaveData(view);
		view.putInt("State", this.entityData.get(WILDFIRE_FLAGS));
		if (spawnPos==null) spawnPos = new BlockPos(0, 0, 0);
		view.putInt("spawnX", spawnPos.getX());
		view.putInt("spawnY", spawnPos.getY());
		view.putInt("spawnZ", spawnPos.getZ());
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput view) {
		super.readAdditionalSaveData(view);
		this.entityData.set(WILDFIRE_FLAGS, (byte)view.getIntOr("State", 0));
		spawnPos = new BlockPos(view.getIntOr("spawnX", 0), view.getIntOr("spawnY", 0), view.getIntOr("spawnZ", 0));
		if (this.hasCustomName()) {
			this.bossBar.setName(this.getDisplayName());
		}
	}
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.bossBar.setName(this.getDisplayName());
	}

	@Override
	public @NonNull Brain<WildfireEntity> getBrain() {
		return (Brain<WildfireEntity>)super.getBrain();
	}

	@Override
	protected Brain<WildfireEntity> makeBrain(final Brain.Packed input) {
		Brain<WildfireEntity> brain = BRAIN_PROVIDER.makeBrain(this, input);
		brain.setDefaultActivity(Activity.FIGHT);
		brain.useDefaultActivity();
		return brain;
	}

	public static AttributeSupplier.Builder createWildfireAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 150.0)
				.add(Attributes.ATTACK_DAMAGE, 6.0)
				.add(Attributes.MOVEMENT_SPEED, 0.5F)
				.add(Attributes.FOLLOW_RANGE, 48.0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(WILDFIRE_FLAGS, (byte)16);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.BLAZE_AMBIENT;
	}

	@Override
	protected @NonNull SoundEvent getHurtSound(@NonNull DamageSource source) {
		return SoundEvents.BLAZE_HURT;
	}

	@Override
	protected @NonNull SoundEvent getDeathSound() {
		return SoundEvents.BLAZE_DEATH;
	}

	@Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Override
	public void aiStep() {
		if (spawnPos == null || spawnPos.closerThan(new BlockPos(0, 0 ,0), 1))
			spawnPos = new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ());
		if (!this.onGround() && this.getDeltaMovement().y < 0.0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, (this.eyeOffset > -1?0.85:0.6), 1.0));
		}

		if (this.level().isClientSide()) {
			if (this.random.nextInt(24) == 0 && !this.isSilent()) {
				this.level()
						.playLocalSound(
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

			if (this.level().getGameTime()%2==0) {
				this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
				this.level().addParticle(isSoulActive()?ParticleTypes.SOUL:ParticleTypes.LAVA, this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 0.0, 0.0, 0.0);
			}

			this.clientFireTime= Mth.clamp(this.clientFireTime +0.5f/20f*(this.isOnFire()?1:-1), 0, 1);
			this.clientExtraSpin+=this.clientFireTime*4;
		}

		super.aiStep();
	}

	@Override
	public boolean isSensitiveToWater() {
		return true;
	}

	@Override
	protected void customServerAiStep(@NonNull ServerLevel world) {
		LivingEntity livingEntity = this.getTarget();
		this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
		if (livingEntity != null && this.canAttack(livingEntity)) {

			if (this.hasLineOfSight(livingEntity)) {
				brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
			}

			Vec3 vec3d = this.getDeltaMovement();

			double d = livingEntity.getEyeY() - (this.getEyeY() + this.eyeOffset);
			if (this.eyeOffset > -1 && d>-3) {
				BlockHitResult blockHitResult = this.level()
						.clip(
								new ClipContext(
										this.getEyePosition(), this.position().add(0, -3, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this
								)
						);
				if (blockHitResult.getType() == HitResult.Type.MISS ) {
					if ( livingEntity.getEyeY() > this.getEyeY() + this.eyeOffset) {
						this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.3F - vec3d.y) * 0.6F, 0.0));
					}
					this.setDeltaMovement(this.getDeltaMovement().add(livingEntity.getEyePosition().subtract(this.position()).horizontal().normalize().scale(0.03f)));
				}
			} else {
				if ( livingEntity.getEyeY() > this.getEyeY() + this.eyeOffset) {
					this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.3F - vec3d.y) * 0.6F, 0.0));
					this.needsSync = true;
				}
			}
		}

		if (world.getGameTime()%20==0) {
			if (world.getBlockState(this.blockPosition()).is(BlockTags.FIRE))this.heal(1);
			int lastShields = getShieldsActive();
			int newShields = (int)Mth.clamp(5*this.getHealth()/this.getMaxHealth(), 0, 4);
			setShieldsActive(newShields);
			if (newShields < lastShields) {
				world.playSound(null, this, SoundEvents.WOLF_ARMOR_BREAK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
			} else if (newShields > lastShields) {
				world.playSound(null, this, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.7F, 2.0F);
			}
		}

		ProfilerFiller profiler = Profiler.get();
		profiler.push("wildfireBrain");
		this.getBrain().tick(world, this);
		profiler.popPush("wildfireActivityUpdate");
		WildfireAi.updateActivities(this);
		profiler.pop();
		super.customServerAiStep(world);
	}

	@Nullable
	@Override
	public LivingEntity getTarget() {
		return this.getTargetFromBrain();
	}

	@Override
	public void registerDebugValues(@NonNull ServerLevel world, DebugValueSource.@NonNull Registration tracker) {
		super.registerDebugValues(world, tracker);
		tracker.register(
				OtherRegistry.WILDFIRES,
				 () -> new WildfireDebugData(
						this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).map(Entity::getId),
						this.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
				)
		);
	}

	public void startSeenByPlayer(@NonNull ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossBar.addPlayer(player);
	}
	public void stopSeenByPlayer(@NonNull ServerPlayer player) {
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
			b = (byte)(b | 1);
		} else {
			b = (byte)(b & -(1+1));
		}

		this.entityData.set(WILDFIRE_FLAGS, b);
	}

	public boolean isSoulActive() {
		return (this.entityData.get(WILDFIRE_FLAGS) & 2) != 0;
	}

	public void setSoulActive(boolean soulActive) {
		byte b = this.entityData.get(WILDFIRE_FLAGS);
		if (soulActive) {
			b = (byte)(b | 2);
		} else {
			b = (byte)(b & -(2+1));
		}
		this.bossBar.setColor(BossEvent.BossBarColor.BLUE);
		this.entityData.set(WILDFIRE_FLAGS, b);
	}

	public int getShieldsActive() {
		return (this.entityData.get(WILDFIRE_FLAGS) & 28)/4;
	}

	public void setShieldsActive(int shieldsActive) {
		byte b = this.entityData.get(WILDFIRE_FLAGS);
		b = (byte)(b & -(28+1));
		b = (byte)(b | 4*shieldsActive);

		this.entityData.set(WILDFIRE_FLAGS, b);
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel world, DamageSource source, float amount) {
		if(this == source.getEntity())return false;
		if (!isOnFire()) {
			Entity entity = source.getDirectEntity();
			if (entity instanceof AbstractArrow || entity instanceof WindCharge) {
				if (random.nextInt(4)<getShieldsActive()) {
					world.playSound(null, this, SoundEvents.SHIELD_BLOCK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
					return false;
				}
			}
		}
		return super.hurtServer(world,source,amount);
	}
}