package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.rule.GameRules;
import org.jspecify.annotations.Nullable;
import java.util.function.Predicate;

public class TargetDummyEntity extends PlayerLikeEntity {
	protected static final TrackedData<ProfileComponent> PROFILE = DataTracker.registerData(TargetDummyEntity.class, TrackedDataHandlerRegistry.PROFILE);
	public static final ProfileComponent DEFAULT_INFO = ProfileComponent.Static.EMPTY;
	public static final EulerAngle DEFAULT_HEAD_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
	public static final EulerAngle DEFAULT_BODY_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
	public static final EulerAngle DEFAULT_LEFT_ARM_ROTATION = new EulerAngle(-5.0F, 0.0F, -5.0F);
	public static final EulerAngle DEFAULT_RIGHT_ARM_ROTATION = new EulerAngle(-5.0F, 0.0F, 5.0F);
	public static final EulerAngle DEFAULT_LEFT_LEG_ROTATION = new EulerAngle(-1.0F, 0.0F, -1.0F);
	public static final EulerAngle DEFAULT_RIGHT_LEG_ROTATION = new EulerAngle(1.0F, 0.0F, 1.0F);
	public static final TrackedData<EulerAngle> TRACKER_HEAD_ROTATION = DataTracker.registerData(TargetDummyEntity.class, TrackedDataHandlerRegistry.ROTATION);
	public static final TrackedData<EulerAngle> TRACKER_BODY_ROTATION = DataTracker.registerData(TargetDummyEntity.class, TrackedDataHandlerRegistry.ROTATION);
	public static final TrackedData<EulerAngle> TRACKER_LEFT_ARM_ROTATION = DataTracker.registerData(TargetDummyEntity.class, TrackedDataHandlerRegistry.ROTATION);
	public static final TrackedData<EulerAngle> TRACKER_RIGHT_ARM_ROTATION = DataTracker.registerData(TargetDummyEntity.class, TrackedDataHandlerRegistry.ROTATION);
	public static final TrackedData<EulerAngle> TRACKER_LEFT_LEG_ROTATION = DataTracker.registerData(TargetDummyEntity.class, TrackedDataHandlerRegistry.ROTATION);
	public static final TrackedData<EulerAngle> TRACKER_RIGHT_LEG_ROTATION = DataTracker.registerData(TargetDummyEntity.class, TrackedDataHandlerRegistry.ROTATION);
	private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE =  entity -> entity instanceof AbstractMinecartEntity abstractMinecartEntity
			&& abstractMinecartEntity.isRideable();

	public long lastHitTime;

	public TargetDummyEntity(EntityType<? extends TargetDummyEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createTargetDummyAttributes() {
		return createLivingAttributes().add(EntityAttributes.STEP_HEIGHT, 0.0);
	}


	@Override
	public void calculateDimensions() {
		double d = this.getX();
		double e = this.getY();
		double f = this.getZ();
		super.calculateDimensions();
		this.setPosition(d, e, f);
	}

	private boolean canClip() {
		return !this.hasNoGravity();
	}

	@Override
	public boolean canActVoluntarily() {
		return super.canActVoluntarily() && this.canClip();
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(PROFILE, DEFAULT_INFO);
		builder.add(TRACKER_HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
		builder.add(TRACKER_BODY_ROTATION, DEFAULT_BODY_ROTATION);
		builder.add(TRACKER_LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
		builder.add(TRACKER_RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
		builder.add(TRACKER_LEFT_LEG_ROTATION, DEFAULT_LEFT_LEG_ROTATION);
		builder.add(TRACKER_RIGHT_LEG_ROTATION, DEFAULT_RIGHT_LEG_ROTATION);
	}

	public ProfileComponent getTargetDummyProfile() {
		return this.dataTracker.get(PROFILE);
	}

	private void setTargetDummyProfile(ProfileComponent profile) {
		this.dataTracker.set(PROFILE, profile);
	}

	@Override
	public boolean canUseSlot(EquipmentSlot slot) {
		return slot != EquipmentSlot.BODY && slot != EquipmentSlot.SADDLE && !this.isSlotDisabled(slot);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		super.writeCustomData(view);
		view.put("profile", ProfileComponent.CODEC, this.getTargetDummyProfile());
		view.put("Pose", TargetDummyEntity.PackedRotation.CODEC, this.packRotation());
	}

	@Override
	protected void readCustomData(ReadView view) {
		super.readCustomData(view);
		this.noClip = !this.canClip();
		view.read("profile", ProfileComponent.CODEC).ifPresent(this::setTargetDummyProfile);
		view.read("Pose", TargetDummyEntity.PackedRotation.CODEC).ifPresent(this::unpackRotation);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void pushAway(Entity entity) {
	}

	@Override
	protected void tickCramming() {
		for (Entity entity : this.getEntityWorld().getOtherEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE)) {
			if (this.squaredDistanceTo(entity) <= 0.2) {
				entity.pushAwayFrom(this);
			}
		}
	}

	@Override
	public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.NAME_TAG)) {
			if (itemStack.hasChangedComponent(DataComponentTypes.CUSTOM_NAME)) {
				Text name = itemStack.get(DataComponentTypes.CUSTOM_NAME);
				if (name!=null) {
					setTargetDummyProfile(ProfileComponent.ofDynamic(name.getLiteralString()));
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		} else if (itemStack.isOf(Items.PLAYER_HEAD)) {
			if (itemStack.hasChangedComponent(DataComponentTypes.PROFILE)) {
				ProfileComponent PC = itemStack.get(DataComponentTypes.PROFILE);
				if (PC!=null) {
					setTargetDummyProfile(PC);
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		} else if (player.isSpectator()) {
			return ActionResult.SUCCESS;
		} else if (player.getEntityWorld().isClient()) {
			return ActionResult.SUCCESS_SERVER;
		} else {
			EquipmentSlot equipmentSlot = this.getPreferredEquipmentSlot(itemStack);
			if (itemStack.isEmpty()) {
				EquipmentSlot equipmentSlot2 = this.getSlotFromPosition(hitPos);
				EquipmentSlot equipmentSlot3 = this.isSlotDisabled(equipmentSlot2) ? equipmentSlot : equipmentSlot2;
				if (this.hasStackEquipped(equipmentSlot3) && this.equip(player, equipmentSlot3, itemStack, hand)) {
					return ActionResult.SUCCESS_SERVER;
				}
			} else {
				if (this.isSlotDisabled(equipmentSlot)) {
					return ActionResult.FAIL;
				}

				//if (equipmentSlot.getType() == EquipmentSlot.Type.HAND) {
				//	return ActionResult.FAIL;
				//}

				if (this.equip(player, equipmentSlot, itemStack, hand)) {
					return ActionResult.SUCCESS_SERVER;
				}
			}

			return ActionResult.PASS;
		}
	}

	private EquipmentSlot getSlotFromPosition(Vec3d hitPos) {
		EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
		double d = hitPos.y / (this.getScale() * this.getScaleFactor());
		EquipmentSlot equipmentSlot2 = EquipmentSlot.FEET;
		if (d >= 0.1 && d < 0.1 + 0.45 && this.hasStackEquipped(equipmentSlot2)) {
			equipmentSlot = EquipmentSlot.FEET;
		} else if (d >= 0.9 + 0.0 && d < 0.9 + 0.7 && this.hasStackEquipped(EquipmentSlot.CHEST)) {
			equipmentSlot = EquipmentSlot.CHEST;
		} else if (d >= 0.4 && d < 0.4 + 0.8 && this.hasStackEquipped(EquipmentSlot.LEGS)) {
			equipmentSlot = EquipmentSlot.LEGS;
		} else if (d >= 1.6 && this.hasStackEquipped(EquipmentSlot.HEAD)) {
			equipmentSlot = EquipmentSlot.HEAD;
		} else if (!this.hasStackEquipped(EquipmentSlot.MAINHAND) && this.hasStackEquipped(EquipmentSlot.OFFHAND)) {
			equipmentSlot = EquipmentSlot.OFFHAND;
		}

		return equipmentSlot;
	}

	private boolean isSlotDisabled(EquipmentSlot slot) {
		return  false;//slot.getType() == EquipmentSlot.Type.HAND;
	}

	private boolean equip(PlayerEntity player, EquipmentSlot slot, ItemStack stack, Hand hand) {
		ItemStack itemStack = this.getEquippedStack(slot);
        if (player.isInCreativeMode() && itemStack.isEmpty() && !stack.isEmpty()) {
            this.equipStack(slot, stack.copyWithCount(1));
            return true;
        } else if (stack.isEmpty() || stack.getCount() <= 1) {
            this.equipStack(slot, stack);
            player.setStackInHand(hand, itemStack);
            return true;
        } else if (!itemStack.isEmpty()) {
            return false;
        } else {
            this.equipStack(slot, stack.split(1));
            return true;
        }
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		if (this.isRemoved()) {
			return false;
		} else if (!world.getGameRules().getValue(GameRules.DO_MOB_GRIEFING) && source.getAttacker() instanceof MobEntity) {
			return false;
		} else if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			this.kill(world);
			return false;
		} else if (this.isInvulnerableTo(world, source) ) {
			return false;
		} else if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
			this.onBreak(world, source);
			this.kill(world);
			return false;
		} else if (source.isIn(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
			if (this.isOnFire()) {
				this.updateHealth(world, source, 0.15F);
			} else {
				this.setOnFireFor(5.0F);
			}

			return false;
		} else if (source.isIn(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
			this.updateHealth(world, source, 4.0F);
			return false;
		} else {
			if (source.getWeaponStack().isOf(Items.SHEARS)) {
				this.breakAndDropItem(world, source);
				this.spawnBreakParticles();
				this.kill(world);
				return true;
			} else if (source.getAttacker() instanceof PlayerEntity playerEntity && !playerEntity.getAbilities().allowModifyWorld) {
				return false;
			} else if (source.isSourceCreativePlayer()) {
				this.playBreakSound();
				this.spawnBreakParticles();
				this.kill(world);
				return true;
			} else {
				long l = world.getTime();
				world.sendEntityStatus(this, EntityStatuses.HIT_ARMOR_STAND);
				this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
				this.lastHitTime = l;
				//world.addParticleClient(ParticleTypes.NOTE, this.getEntityPos().getX() + 0.5, this.getEntityPos().getY() + 1.2, this.getEntityPos().getZ() + 0.5, amount / 24.0, 0.0, 0.0);
				if (this.getEntityWorld() instanceof ServerWorld) {
					((ServerWorld)this.getEntityWorld())
							.spawnParticles(
									OtherRegistry.NUMBER,
									this.getX(),
									this.getY()+2,
									this.getZ(),
									0,
									1,
									0,
									0,
									Math.round(amount*10)/20.0
							);
				}
				return true;
			}
		}
	}

	@Override
	public void handleStatus(byte status) {
		if (status == EntityStatuses.HIT_ARMOR_STAND) {
			if (this.getEntityWorld().isClient()) {
				this.getEntityWorld()
						.playSoundClient(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
				this.lastHitTime = this.getEntityWorld().getTime();
			}
		} else {
			super.handleStatus(status);
		}
	}

	@Override
	public boolean shouldRender(double distance) {
		double d = this.getBoundingBox().getAverageSideLength() * 4.0;
		if (Double.isNaN(d) || d == 0.0) {
			d = 4.0;
		}

		d *= 64.0;
		return distance < d * d;
	}

	private void spawnBreakParticles() {
		if (this.getEntityWorld() instanceof ServerWorld) {
			((ServerWorld)this.getEntityWorld())
					.spawnParticles(
							new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()),
							this.getX(),
							this.getBodyY(0.6666666666666666),
							this.getZ(),
							10,
							this.getWidth() / 4.0F,
							this.getHeight() / 4.0F,
							this.getWidth() / 4.0F,
							0.05
					);
		}
	}

	private void updateHealth(ServerWorld world, DamageSource damageSource, float amount) {
		float f = this.getHealth();
		f -= amount;
		if (f <= 0.5F) {
			this.onBreak(world, damageSource);
			this.kill(world);
		} else {
			this.setHealth(f);
			this.emitGameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getAttacker());
		}
	}

	private void breakAndDropItem(ServerWorld world, DamageSource damageSource) {
		ItemStack itemStack = new ItemStack(ItemRegistry.TARGET_DUMMY);
		itemStack.set(DataComponentTypes.CUSTOM_NAME, this.getCustomName());
		Block.dropStack(this.getEntityWorld(), this.getBlockPos(), itemStack);
		this.onBreak(world, damageSource);
	}

	private void onBreak(ServerWorld world, DamageSource damageSource) {
		this.playBreakSound();
		this.drop(world, damageSource);

		for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
			ItemStack itemStack = this.equipment.put(equipmentSlot, ItemStack.EMPTY);
			if (!itemStack.isEmpty()) {
				Block.dropStack(this.getEntityWorld(), this.getBlockPos().up(), itemStack);
			}
		}
	}

	private void playBreakSound() {
		this.getEntityWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
	}

	@Override
	protected void turnHead(float bodyRotation) {
		this.lastBodyYaw = this.lastYaw;
		this.bodyYaw = this.getYaw();
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (this.canClip()) {
			super.travel(movementInput);
		}
	}

	@Override
	public void setBodyYaw(float bodyYaw) {
		this.lastBodyYaw = this.lastYaw = bodyYaw;
		this.lastHeadYaw = this.headYaw = bodyYaw;
	}

	@Override
	public void setHeadYaw(float headYaw) {
		this.lastBodyYaw = this.lastYaw = headYaw;
		this.lastHeadYaw = this.headYaw = headYaw;
	}

	@Override
	public void kill(ServerWorld world) {
		this.remove(Entity.RemovalReason.KILLED);
		this.emitGameEvent(GameEvent.ENTITY_DIE);
	}

	@Override
	public boolean isImmuneToExplosion(Explosion explosion) {
		return !explosion.preservesDecorativeEntities() || this.isInvisible();
	}


	public void setHeadRotation(EulerAngle angle) {
		this.dataTracker.set(TRACKER_HEAD_ROTATION, angle);
	}

	public void setBodyRotation(EulerAngle angle) {
		this.dataTracker.set(TRACKER_BODY_ROTATION, angle);
	}

	public void setLeftArmRotation(EulerAngle angle) {
		this.dataTracker.set(TRACKER_LEFT_ARM_ROTATION, angle);
	}

	public void setRightArmRotation(EulerAngle angle) {
		this.dataTracker.set(TRACKER_RIGHT_ARM_ROTATION, angle);
	}

	public void setLeftLegRotation(EulerAngle angle) {
		this.dataTracker.set(TRACKER_LEFT_LEG_ROTATION, angle);
	}

	public void setRightLegRotation(EulerAngle angle) {
		this.dataTracker.set(TRACKER_RIGHT_LEG_ROTATION, angle);
	}

	public EulerAngle getHeadRotation() {
		return this.dataTracker.get(TRACKER_HEAD_ROTATION);
	}

	public EulerAngle getBodyRotation() {
		return this.dataTracker.get(TRACKER_BODY_ROTATION);
	}

	public EulerAngle getLeftArmRotation() {
		return this.dataTracker.get(TRACKER_LEFT_ARM_ROTATION);
	}

	public EulerAngle getRightArmRotation() {
		return this.dataTracker.get(TRACKER_RIGHT_ARM_ROTATION);
	}

	public EulerAngle getLeftLegRotation() {
		return this.dataTracker.get(TRACKER_LEFT_LEG_ROTATION);
	}

	public EulerAngle getRightLegRotation() {
		return this.dataTracker.get(TRACKER_RIGHT_LEG_ROTATION);
	}

	@Override
	public boolean canHit() {
		return super.canHit();
	}

	@Override
	public boolean handleAttack(Entity attacker) {
		return attacker instanceof PlayerEntity playerEntity && !this.getEntityWorld().canEntityModifyAt(playerEntity, this.getBlockPos());
	}

	@Override
	public Arm getMainArm() {
		return Arm.RIGHT;
	}

	@Override
	public LivingEntity.FallSounds getFallSounds() {
		return new LivingEntity.FallSounds(SoundEvents.ENTITY_ARMOR_STAND_FALL, SoundEvents.ENTITY_ARMOR_STAND_FALL);
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ARMOR_STAND_HIT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
	}

	@Override
	public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
	}

	@Override
	public boolean isAffectedBySplashPotions() {
		return false;
	}

	@Override
	public boolean isMobOrPlayer() {
		return false;
	}

	@Override
	public EntityDimensions getBaseDimensions(EntityPose pose) {
		return this.getType().getDimensions();
	}


	@Override
	public ItemStack getPickBlockStack() {
		return new ItemStack(Items.ARMOR_STAND);
	}

	@Override
	public boolean isPartOfGame() {
		return !this.isInvisible();
	}

	public void unpackRotation(TargetDummyEntity.PackedRotation packedRotation) {
		this.setHeadRotation(packedRotation.head());
		this.setBodyRotation(packedRotation.body());
		this.setLeftArmRotation(packedRotation.leftArm());
		this.setRightArmRotation(packedRotation.rightArm());
		this.setLeftLegRotation(packedRotation.leftLeg());
		this.setRightLegRotation(packedRotation.rightLeg());
	}

	public TargetDummyEntity.PackedRotation packRotation() {
		return new TargetDummyEntity.PackedRotation(
				this.getHeadRotation(), this.getBodyRotation(), this.getLeftArmRotation(), this.getRightArmRotation(), this.getLeftLegRotation(), this.getRightLegRotation()
		);
	}

	public record PackedRotation(EulerAngle head, EulerAngle body, EulerAngle leftArm, EulerAngle rightArm, EulerAngle leftLeg, EulerAngle rightLeg) {
		public static final Codec<TargetDummyEntity.PackedRotation> CODEC = RecordCodecBuilder.create(
				  instance -> instance.group(
								EulerAngle.CODEC.optionalFieldOf("Head", TargetDummyEntity.DEFAULT_HEAD_ROTATION).forGetter(TargetDummyEntity.PackedRotation::head),
								EulerAngle.CODEC.optionalFieldOf("Body", TargetDummyEntity.DEFAULT_BODY_ROTATION).forGetter(TargetDummyEntity.PackedRotation::body),
								EulerAngle.CODEC.optionalFieldOf("LeftArm", TargetDummyEntity.DEFAULT_LEFT_ARM_ROTATION).forGetter(TargetDummyEntity.PackedRotation::leftArm),
								EulerAngle.CODEC.optionalFieldOf("RightArm", TargetDummyEntity.DEFAULT_RIGHT_ARM_ROTATION).forGetter(TargetDummyEntity.PackedRotation::rightArm),
								EulerAngle.CODEC.optionalFieldOf("LeftLeg", TargetDummyEntity.DEFAULT_LEFT_LEG_ROTATION).forGetter(TargetDummyEntity.PackedRotation::leftLeg),
								EulerAngle.CODEC.optionalFieldOf("RightLeg", TargetDummyEntity.DEFAULT_RIGHT_LEG_ROTATION).forGetter(TargetDummyEntity.PackedRotation::rightLeg)
						)
						.apply(instance, TargetDummyEntity.PackedRotation::new)
		);
	}

}

