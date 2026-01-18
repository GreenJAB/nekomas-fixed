package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.PiercingWeaponComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class SpearEntity extends Entity {
	private int warmup = 0;
	private boolean startedAttack;
	private int ticksLeft = 20;

	protected static final TrackedData<Direction> DIRECTION = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.FACING);
	protected static final TrackedData<ItemStack> SPEAR = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);


	public SpearEntity(EntityType<? extends SpearEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(DIRECTION, Direction.UP);
		builder.add(SPEAR, Items.WOODEN_SPEAR.getDefaultStack());
	}

	public void setDirection(Direction dir) {
		dataTracker.set(DIRECTION, dir);
	}
	public Direction getDirection() {
		return dataTracker.get(DIRECTION);
	}
	public void setStack(ItemStack item) {
		dataTracker.set(SPEAR, item);
	}
	public ItemStack getStack() {
		return dataTracker.get(SPEAR);
	}

	@Override
	protected void readCustomData(ReadView view) {
		this.warmup = view.getInt("Warmup", 0);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		view.putInt("Warmup", this.warmup);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getEntityWorld().isClient()) {
			this.ticksLeft--;
			if (this.ticksLeft == 20-5) {
				Vec3d b = getDirection().getDoubleVector().multiply(getDirection().getDoubleVector()).multiply(0.4);
				Box box = this.getBoundingBox().expand(b.x, b.y, b.z);
				List<LivingEntity> list = this.getEntityWorld().getNonSpectatingEntities(LivingEntity.class, box);
				if (!list.isEmpty()) {
					this.getEntityWorld()
							.playSoundClient(
									this.getX(),
									this.getY(),
									this.getZ(),
									SoundEvents.ITEM_SPEAR_HIT.value(),
									this.getSoundCategory(),
									1.0F,
									1f,
									false
							);
					for (int i = 0; i < 12; i++) {
						double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * this.getWidth() * 0.5;
						double e = this.getY() + 0.05 + this.random.nextDouble();
						double f = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * this.getWidth() * 0.5;
						double g = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
						double h = 0.3 + this.random.nextDouble() * 0.3;
						double j = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
						this.getEntityWorld().addParticleClient(ParticleTypes.CRIT, d, e, f, g, h, j);
					}

				}
			}
		} else if (--this.warmup < 0) {
			if (this.warmup == -5) {
				Vec3d b = getDirection().getDoubleVector().multiply(getDirection().getDoubleVector()).multiply(0.4);
				Box box = this.getBoundingBox().expand(b.x, b.y, b.z);
				for (LivingEntity livingEntity : this.getEntityWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
					this.damage(livingEntity);
				}
			}

			if (!this.startedAttack) {
				this.getEntityWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
				this.startedAttack = true;
			}

			if (--this.ticksLeft <= 0) {
				this.discard();
			}
		}
	}

	private void damage(LivingEntity target) {
		if (target.isAlive() && !target.isInvulnerable()) {
			if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
				PlayerEntity p = new PlayerEntity(serverWorld, new GameProfile(UUID.randomUUID(), "Dispenser")) {
					@Override
					public @NotNull GameMode getGameMode() {
						return GameMode.SURVIVAL;
					}
				};
				ItemStack stack = dataTracker.get(SPEAR);
				Direction direction = dataTracker.get(DIRECTION);
				if (direction.getAxis().isHorizontal()) {
					p.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), direction.getPositiveHorizontalDegrees(), 0);
				} else {
					p.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, direction==Direction.UP?-90:90);
				}
				p.ticksSinceLastAttack =1000;
				p.getInventory().setStack(0, stack);
				PiercingWeaponComponent piercingWeaponComponent = stack.get(DataComponentTypes.PIERCING_WEAPON);
				if (piercingWeaponComponent != null) {
					piercingWeaponComponent.stab(p, EquipmentSlot.MAINHAND);

					float f = EnchantmentHelper.getDamage(serverWorld, stack, target, stack.getDamageSource(p, () -> p.getDamageSources().playerAttack(p)), getDamageValue(stack));
					p.ticksSinceLastAttack =1000;
					p.pierce(EquipmentSlot.MAINHAND, target, f, true, direction.getAxis().isHorizontal(), false);

					p.beforePlayerAttack();
					p.useAttackEnchantmentEffects();
				}
			}
		}
	}

	private float getDamageValue(ItemStack stack) {
		AttributeModifiersComponent attributeModifiersComponent = stack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
		return (float) (attributeModifiersComponent.applyOperations(EntityAttributes.ATTACK_DAMAGE, 1, EquipmentSlot.MAINHAND));
	}

	@Override
	public void handleStatus(byte status) {
		super.handleStatus(status);
		if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
			if (!this.isSilent()) {
				this.getEntityWorld()
						.playSoundClient(
								this.getX(),
								this.getY(),
								this.getZ(),
								SoundEvents.BLOCK_PISTON_EXTEND,
								this.getSoundCategory(),
								0.7F,
								0.7f,
								false
						);
			}
		}
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		return false;
	}
}