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
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.debug.DebugTrackable;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class WildFireEntity extends HostileEntity {
	public float eyeOffset = 0.5F;
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
		setShieldsActive(4);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		view.putInt("State", this.dataTracker.get(WILD_FIRE_FLAGS));
	}

	@Override
	protected void readCustomData(ReadView view) {
		this.dataTracker.set(WILD_FIRE_FLAGS, (byte)view.getInt("State", 0));
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

	public static DefaultAttributeContainer.Builder createWildFireAttributes() {
		return HostileEntity.createHostileAttributes()
				.add(EntityAttributes.ATTACK_DAMAGE, 6.0)
				.add(EntityAttributes.MOVEMENT_SPEED, 0.5F)
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
			this.setVelocity(this.getVelocity().multiply(1.0, (this.eyeOffset > -1?0.85:0.6), 1.0));
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

	@Override
	protected void mobTick(ServerWorld world) {
		LivingEntity livingEntity = this.getTarget();
		if (livingEntity != null && this.canTarget(livingEntity)) {

			if (this.canSee(livingEntity)) {
				brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
			}

			Vec3d vec3d = this.getVelocity();

			double d = livingEntity.getEyeY() - (this.getEyeY() + this.eyeOffset);
			if (this.eyeOffset > -1 && d>-3) {
				BlockHitResult blockHitResult = this.getEntityWorld()
						.raycast(
								new RaycastContext(
										this.getEyePos(), this.getEntityPos().add(0, -3, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this
								)
						);
				if (blockHitResult.getType() == HitResult.Type.MISS ) {
					if ( livingEntity.getEyeY() > this.getEyeY() + this.eyeOffset) {
						this.setVelocity(this.getVelocity().add(0.0, (0.3F - vec3d.y) * 0.6F, 0.0));
					}
					this.setVelocity(this.getVelocity().add(livingEntity.getEyePos().subtract(this.getEntityPos()).getHorizontal().normalize().multiply(0.03f)));
				}
			} else {
				if ( livingEntity.getEyeY() > this.getEyeY() + this.eyeOffset) {
					this.setVelocity(this.getVelocity().add(0.0, (0.3F - vec3d.y) * 0.6F, 0.0));
					this.velocityDirty = true;
				}
			}
		}

		if (world.getTime()%20==0) {
			if (world.getBlockState(this.getBlockPos()).isIn(BlockTags.FIRE))this.heal(1);
			setShieldsActive((int)MathHelper.clamp(5*this.getHealth()/this.getMaxHealth(), 0, 4));
		}

		Profiler profiler = Profilers.get();
		profiler.push("wildFireBrain");
		this.getBrain().tick(world, this);
		profiler.swap("wildFireActivityUpdate");WildFireBrain.updateActivities(this);
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

	public void setSoulActive(boolean soulActive) {
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

	public void setShieldsActive(int shieldsActive) {
		byte b = this.dataTracker.get(WILD_FIRE_FLAGS);
		b = (byte)(b & -(28+1));
		b = (byte)(b | 4*shieldsActive);

		this.dataTracker.set(WILD_FIRE_FLAGS, b);
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		if(this == source.getAttacker())return false;
		return super.damage(world,source,amount);
	}
}