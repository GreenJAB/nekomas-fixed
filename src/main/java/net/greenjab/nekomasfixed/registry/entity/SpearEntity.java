package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.PiercingWeapon;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

public class SpearEntity extends Entity {
	private int warmup = 0;
	private boolean startedAttack;
	private int ticksLeft = 20;

	protected static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(SpearEntity.class, EntityDataSerializers.DIRECTION);
	protected static final EntityDataAccessor<ItemStack> SPEAR = SynchedEntityData.defineId(SpearEntity.class, EntityDataSerializers.ITEM_STACK);


	public SpearEntity(EntityType<? extends SpearEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(DIRECTION, Direction.UP);
		builder.define(SPEAR, Items.WOODEN_SPEAR.getDefaultInstance());
	}

	public void setDirection(Direction dir) {
		entityData.set(DIRECTION, dir);
	}
	public @NonNull Direction getDirection() {
		return entityData.get(DIRECTION);
	}
	public void setStack(ItemStack item) {
		entityData.set(SPEAR, item);
	}
	public ItemStack getStack() {
		return entityData.get(SPEAR);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput view) {
		this.warmup = view.getIntOr("Warmup", 0);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput view) {
		view.putInt("Warmup", this.warmup);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			this.ticksLeft--;
			if (this.ticksLeft == 20-5) {
				Vec3 b = getDirection().getUnitVec3().multiply(getDirection().getUnitVec3()).scale(0.4);
				AABB box = this.getBoundingBox().inflate(b.x, b.y, b.z);
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, box);
				if (!list.isEmpty()) {
					this.level()
							.playLocalSound(
									this.getX(),
									this.getY(),
									this.getZ(),
									SoundEvents.SPEAR_HIT.value(),
									this.getSoundSource(),
									1.0F,
									1f,
									false
							);
					for (int i = 0; i < 12; i++) {
						double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
						double e = this.getY() + 0.05 + this.random.nextDouble();
						double f = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
						double g = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
						double h = 0.3 + this.random.nextDouble() * 0.3;
						double j = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
						this.level().addParticle(ParticleTypes.CRIT, d, e, f, g, h, j);
					}

				}
			}
		} else if (--this.warmup < 0) {
			if (this.warmup == -5) {
				Vec3 b = getDirection().getUnitVec3().multiply(getDirection().getUnitVec3()).scale(0.4);
				AABB box = this.getBoundingBox().inflate(b.x, b.y, b.z);
				for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, box)) {
					this.damage(livingEntity);
				}
			}

			if (!this.startedAttack) {
				this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
				this.startedAttack = true;
			}

			if (--this.ticksLeft <= 0) {
				this.discard();
			}
		}
	}

	private void damage(LivingEntity target) {
		if (target.isAlive() && !target.isInvulnerable()) {
			if (this.level() instanceof ServerLevel serverWorld) {
				Player p = new Player(serverWorld, new GameProfile(UUID.randomUUID(), "Dispenser")) {
					@Override
					public @NotNull GameType gameMode() {
						return GameType.SURVIVAL;
					}
				};
				ItemStack stack = entityData.get(SPEAR);
				Direction direction = entityData.get(DIRECTION);
				if (direction.getAxis().isHorizontal()) {
					p.absSnapTo(this.getX(), this.getY(), this.getZ(), direction.toYRot(), 0);
				} else {
					p.absSnapTo(this.getX(), this.getY(), this.getZ(), 0, direction==Direction.UP?-90:90);
				}
				p.attackStrengthTicker =1000;
				p.getInventory().setItem(0, stack);
				PiercingWeapon piercingWeaponComponent = stack.get(DataComponents.PIERCING_WEAPON);
				if (piercingWeaponComponent != null) {
					piercingWeaponComponent.attack(p, EquipmentSlot.MAINHAND);

					float f = EnchantmentHelper.modifyDamage(serverWorld, stack, target, stack.getDamageSource(p, () -> p.damageSources().playerAttack(p)), getDamageValue(stack));
					p.attackStrengthTicker =1000;
					p.stabAttack(EquipmentSlot.MAINHAND, target, f, true, direction.getAxis().isHorizontal(), false);

					p.onAttack();
					p.lungeForwardMaybe();
				}
			}
		}
	}

	private float getDamageValue(ItemStack stack) {
		ItemAttributeModifiers attributeModifiersComponent = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
		return (float) (attributeModifiersComponent.compute(Attributes.ATTACK_DAMAGE, 1, EquipmentSlot.MAINHAND));
	}

	@Override
	public void handleEntityEvent(byte status) {
		super.handleEntityEvent(status);
		if (status == EntityEvent.START_ATTACKING) {
			if (!this.isSilent()) {
				this.level()
						.playLocalSound(
								this.getX(),
								this.getY(),
								this.getZ(),
								SoundEvents.PISTON_EXTEND,
								this.getSoundSource(),
								0.7F,
								0.7f,
								false
						);
			}
		}
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel world, @NonNull DamageSource source, float amount) {
		return false;
	}
}