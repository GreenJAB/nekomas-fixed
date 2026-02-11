package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.mojang.serialization.Dynamic;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.World;
import net.minecraft.world.debug.DebugTrackable;
import org.jspecify.annotations.Nullable;
import java.util.Optional;

public class WildFireEntity extends HostileEntity {
	public float eyeOffset = 0.5F;
	//private int eyeOffsetCooldown;
	public float clientFireTime = 0;
	public float clientExtraSpin = 0;
	private static final TrackedData<Byte> WILD_FIRE_FLAGS = DataTracker.registerData(WildFireEntity.class, TrackedDataHandlerRegistry.BYTE);

	public WildFireEntity(EntityType<? extends WildFireEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experiencePoints = 50;
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return WildFireBrain.create(this, this.createBrainProfile().deserialize(dynamic));
	}

	@Override
	public Brain<WildFireEntity> getBrain() {
		return (Brain<WildFireEntity>)super.getBrain();
	}

	@Override
	protected Brain.Profile<WildFireEntity> createBrainProfile() {
		return Brain.createProfile(WildFireBrain.MEMORY_MODULES, WildFireBrain.SENSORS);
	}

	/*@Override
	protected void initGoals() {
		this.goalSelector.add(4, new WildFireEntity.ShootFireballGoal(this));
		//this.goalSelector.add(4, new WildFireEntity.MeleeAttackGoal(this));
		this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
	}*/

	public static DefaultAttributeContainer.Builder createWildFireAttributes() {
		return HostileEntity.createHostileAttributes()
				.add(EntityAttributes.ATTACK_DAMAGE, 6.0)
				.add(EntityAttributes.MOVEMENT_SPEED, 0.23F)
				.add(EntityAttributes.FOLLOW_RANGE, 48.0);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(WILD_FIRE_FLAGS, (byte)16);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_BLAZE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BLAZE_DEATH;
	}

	public Optional<LivingEntity> getHurtBy() {
		return this.getBrain()
				.getOptionalRegisteredMemory(MemoryModuleType.HURT_BY)
				.map(DamageSource::getAttacker)
				.filter(attacker -> attacker instanceof LivingEntity)
				.map(livingAttacker -> (LivingEntity)livingAttacker);
	}

	@Override
	public float getBrightnessAtEyes() {
		return 1.0F;
	}

	@Override
	public void tickMovement() {
		if (!this.isOnGround() && this.getVelocity().y < 0.0) {
			this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
		}

		if (this.getEntityWorld().isClient()) {
			if (this.random.nextInt(24) == 0 && !this.isSilent()) {
				this.getEntityWorld()
						.playSoundClient(
								this.getX() + 0.5,
								this.getY() + 0.5,
								this.getZ() + 0.5,
								SoundEvents.ENTITY_BLAZE_BURN,
								this.getSoundCategory(),
								1.0F + this.random.nextFloat(),
								this.random.nextFloat() * 0.7F + 0.3F,
								false
						);
			}

			for (int i = 0; i < 2; i++) {
				this.getEntityWorld().addParticleClient(ParticleTypes.LARGE_SMOKE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
			}

			this.clientFireTime= MathHelper.clamp(this.clientFireTime +0.5f/20f*(this.isOnFire()?1:-1), 0, 1);
			this.clientExtraSpin+=this.clientFireTime*4;
		}

		super.tickMovement();
	}

	@Override
	public boolean hurtByWater() {
		return true;
	}

	public boolean isWithinShortRange(Vec3d pos) {
		Vec3d vec3d = this.getBlockPos().toCenterPos();
		return pos.isWithinRangeOf(vec3d, 4.0, 10.0);
	}

	@Override
	protected void mobTick(ServerWorld world) {
		/*this.eyeOffsetCooldown--;
		if (this.eyeOffsetCooldown <= 0) {
			this.eyeOffsetCooldown = 100;
			this.eyeOffset = (float)this.random.nextTriangular(0.5, 6.891);
		}*/

		LivingEntity livingEntity = this.getTarget();
		if (livingEntity != null && livingEntity.getEyeY() > this.getEyeY() + this.eyeOffset && this.canTarget(livingEntity)) {
			Vec3d vec3d = this.getVelocity();
			this.setVelocity(this.getVelocity().add(0.0, (0.3F - vec3d.y) * 0.3F, 0.0));
			this.velocityDirty = true;
		}

		if (world.getTime()%20==0) {
			setSoulActive(this.getTargetInBrain()!=null);
			setShieldsActive((int)(5*this.getHealth()/this.getMaxHealth()));
		}

		Profiler profiler = Profilers.get();
		profiler.push("wildFireBrain");
		this.getBrain().tick(world, this);
		profiler.swap("wildFireActivityUpdate");
		WildFireBrain.updateActivities(this);
		profiler.pop();
		super.mobTick(world);
	}

	@Nullable
	@Override
	public LivingEntity getTarget() {
		return this.getTargetInBrain();
	}

	@Override
	public void registerTracking(ServerWorld world, DebugTrackable.Tracker tracker) {
		super.registerTracking(world, tracker);
		tracker.track(
				OtherRegistry.WILDFIRES,
				 () -> new WildFireDebugData(
						this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).map(Entity::getId),
						this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
				)
		);
	}
	
	
	@Override
	public boolean isOnFire() {
		return this.isFireActive();
	}

	private boolean isFireActive() {
		return (this.dataTracker.get(WILD_FIRE_FLAGS) & 1) != 0;
	}

	public void setFireActive(boolean fireActive) {
		byte b = this.dataTracker.get(WILD_FIRE_FLAGS);
		if (fireActive) {
			b = (byte)(b | 1);
		} else {
			b = (byte)(b & -(1+1));
		}

		this.dataTracker.set(WILD_FIRE_FLAGS, b);
	}

	public boolean isSoulActive() {
		return (this.dataTracker.get(WILD_FIRE_FLAGS) & 2) != 0;
	}

	void setSoulActive(boolean soulActive) {
		byte b = this.dataTracker.get(WILD_FIRE_FLAGS);
		if (soulActive) {
			b = (byte)(b | 2);
		} else {
			b = (byte)(b & -(2+1));
		}

		this.dataTracker.set(WILD_FIRE_FLAGS, b);
	}

	public int getShieldsActive() {
		return (this.dataTracker.get(WILD_FIRE_FLAGS) & 28)/4;
	}

	void setShieldsActive(int shieldsActive) {
		byte b = this.dataTracker.get(WILD_FIRE_FLAGS);
		b = (byte)(b & -(28+1));
		b = (byte)(b | 4*shieldsActive);

		this.dataTracker.set(WILD_FIRE_FLAGS, b);
	}

	/*static class ShootFireballGoal extends Goal {
		private final WildFireEntity wildFire;
		private int fireballsFired;
		private int fireballCooldown;
		private int targetNotVisibleTicks;

		public ShootFireballGoal(WildFireEntity wildFire) {
			this.wildFire = wildFire;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		@Override
		public boolean canStart() {
			LivingEntity livingEntity = this.wildFire.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.wildFire.canTarget(livingEntity);
		}

		@Override
		public void start() {
			this.fireballsFired = 0;
		}

		@Override
		public void stop() {
			this.wildFire.setFireActive(false);
			this.targetNotVisibleTicks = 0;
		}

		@Override
		public boolean shouldRunEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			this.fireballCooldown--;
			LivingEntity livingEntity = this.wildFire.getTarget();
			if (livingEntity != null) {
				boolean bl = this.wildFire.getVisibilityCache().canSee(livingEntity);
				if (bl) {
					this.targetNotVisibleTicks = 0;
				} else {
					this.targetNotVisibleTicks++;
				}

				double d = this.wildFire.squaredDistanceTo(livingEntity);
				if (d < 4.0) {
					if (!bl) {
						return;
					}

					if (this.fireballCooldown <= 0) {
						this.fireballCooldown = 20;
						this.wildFire.tryAttack(getServerWorld(this.wildFire), livingEntity);
					}

					this.wildFire.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
				} else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
					double e = livingEntity.getX() - this.wildFire.getX();
					double f = livingEntity.getBodyY(0.5) - this.wildFire.getBodyY(0.5);
					double g = livingEntity.getZ() - this.wildFire.getZ();
					if (this.fireballCooldown <= 0) {
						this.fireballsFired++;
						if (this.fireballsFired == 1) {
							this.fireballCooldown = 60;
							this.wildFire.setFireActive(true);
						} else if (this.fireballsFired <= 9) {
							this.fireballCooldown = 4;
						} else {
							this.fireballCooldown = 100;
							this.fireballsFired = 0;
							this.wildFire.setFireActive(false);
						}

						if (this.fireballsFired > 1) {
							double h = Math.sqrt(Math.sqrt(d)) * 0.5;
							if (!this.wildFire.isSilent()) {
								this.wildFire.getEntityWorld().syncWorldEvent(null, WorldEvents.BLAZE_SHOOTS, this.wildFire.getBlockPos(), 0);
							}

							for (int i = 0; i < 1; i++) {
								Vec3d vec3d = new Vec3d(this.wildFire.getRandom().nextTriangular(e, 1 * h), f, this.wildFire.getRandom().nextTriangular(g, 1 * h));
								SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.wildFire.getEntityWorld(), this.wildFire, vec3d.normalize());
								smallFireballEntity.setPosition(smallFireballEntity.getX(), this.wildFire.getBodyY(0.5) + 0.5, smallFireballEntity.getZ());
								this.wildFire.getEntityWorld().spawnEntity(smallFireballEntity);
							}
						}
					}

					this.wildFire.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
				} else if (this.targetNotVisibleTicks < 5) {
					this.wildFire.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
				}

				super.tick();
			}
		}

		private double getFollowRange() {
			return this.wildFire.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
		}
	}

	static class MeleeAttackGoal extends Goal {
		private final WildFireEntity wildFire;
		private int attackTime;
		private int targetNotVisibleTicks;

		public MeleeAttackGoal(WildFireEntity wildFire) {
			this.wildFire = wildFire;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		@Override
		public boolean canStart() {
			LivingEntity livingEntity = this.wildFire.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.wildFire.canTarget(livingEntity);
		}

		@Override
		public void start() {
			this.attackTime = 0;
			this.wildFire.setFireActive(true);
		}

		@Override
		public void stop() {
			this.wildFire.setFireActive(false);
			this.targetNotVisibleTicks = 0;
		}

		@Override
		public boolean shouldRunEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			this.attackTime++;
			this.wildFire.eyeOffset = 0;
			if (attackTime>200){
				this.stop();
				return;
			}
			LivingEntity livingEntity = this.wildFire.getTarget();
			if (livingEntity != null) {
				boolean bl = this.wildFire.getVisibilityCache().canSee(livingEntity);
				if (bl) {
					this.targetNotVisibleTicks = 0;
				} else {
					this.targetNotVisibleTicks++;
				}

				double d = this.wildFire.squaredDistanceTo(livingEntity);
				if (d < 4.0) {
					if (!bl) {
						return;
					}

					/*if (this.fireballCooldown <= 0) {
						this.fireballCooldown = 20;
						this.wildFire.tryAttack(getServerWorld(this.wildFire), livingEntity);
					}* /

					this.wildFire.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
				} else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
					/*double e = livingEntity.getX() - this.wildFire.getX();
					double f = livingEntity.getBodyY(0.5) - this.wildFire.getBodyY(0.5);
					double g = livingEntity.getZ() - this.wildFire.getZ();
					if (this.fireballCooldown <= 0) {
						this.fireballsFired++;
						if (this.fireballsFired == 1) {
							this.fireballCooldown = 60;
							this.wildFire.setFireActive(true);
						} else if (this.fireballsFired <= 9) {
							this.fireballCooldown = 4;
						} else {
							this.fireballCooldown = 100;
							this.fireballsFired = 0;
							this.wildFire.setFireActive(false);
						}

						if (this.fireballsFired > 1) {
							double h = Math.sqrt(Math.sqrt(d)) * 0.5;
							if (!this.wildFire.isSilent()) {
								this.wildFire.getEntityWorld().syncWorldEvent(null, WorldEvents.BLAZE_SHOOTS, this.wildFire.getBlockPos(), 0);
							}

							for (int i = 0; i < 1; i++) {
								Vec3d vec3d = new Vec3d(this.wildFire.getRandom().nextTriangular(e, 1 * h), f, this.wildFire.getRandom().nextTriangular(g, 1 * h));
								SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.wildFire.getEntityWorld(), this.wildFire, vec3d.normalize());
								smallFireballEntity.setPosition(smallFireballEntity.getX(), this.wildFire.getBodyY(0.5) + 0.5, smallFireballEntity.getZ());
								this.wildFire.getEntityWorld().spawnEntity(smallFireballEntity);
							}
						}
					}
					* /
					this.wildFire.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
					this.wildFire.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.5);
				} else if (this.targetNotVisibleTicks < 5) {
					this.wildFire.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
				}

				super.tick();
			}
		}

		private double getFollowRange() {
			return this.wildFire.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
		}
	}*/
}