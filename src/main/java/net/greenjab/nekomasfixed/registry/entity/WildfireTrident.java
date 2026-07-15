package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class WildfireTrident extends AbstractArrow {
	private static final EntityDataAccessor<Byte> LOYALTY = SynchedEntityData.defineId(WildfireTrident.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> ENCHANTED = SynchedEntityData.defineId(WildfireTrident.class, EntityDataSerializers.BOOLEAN);
	private boolean dealtDamage = false;
	public int returnTimer;

	public WildfireTrident(EntityType<? extends WildfireTrident> entityType, Level level) {
		super(entityType, level);
	}

	public WildfireTrident(Level level, LivingEntity owner, ItemStack stack) {
		super(EntityTypeRegistry.WILDFIRE_TRIDENT, owner, level, stack, null);
		this.entityData.set(LOYALTY, this.getLoyalty(stack));
		this.entityData.set(ENCHANTED, stack.hasFoil());
	}

	public WildfireTrident(Level level, double x, double y, double z, ItemStack stack) {
		super(EntityTypeRegistry.WILDFIRE_TRIDENT, x, y, z, level, stack, stack);
		this.entityData.set(LOYALTY, this.getLoyalty(stack));
		this.entityData.set(ENCHANTED, stack.hasFoil());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(LOYALTY, (byte) 0);
		builder.define(ENCHANTED, false);
	}

	@Override
	public void tick() {
		if (this.level() instanceof ServerLevel level) level.sendParticles(ParticleTypes.FLAME, this.getX(),
				this.getY()+0.2, this.getZ(), 0, 0, 0.0, 0.0, 0);

		if (this.inGroundTime > 4) this.dealtDamage = true;

		Entity entity = this.getOwner();
		int i = this.entityData.get(LOYALTY);
		if (i > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
			if (!this.isOwnerAlive()) {
				if (this.level() instanceof ServerLevel level && this.pickup == AbstractArrow.Pickup.ALLOWED)
					this.spawnAtLocation(level, this.getPickupItem(), 0.1F);
				this.discard();
			} else {
				if (!(entity instanceof Player) && this.position().distanceTo(entity.getEyePosition()) < entity.getBbWidth() + 1.0) {
					this.discard();
					return;
				}
				this.setNoPhysics(true);
				Vec3 vec3d = entity.getEyePosition().subtract(this.position());
				this.setPosRaw(this.getX(), this.getY() + vec3d.y * 0.015 * i, this.getZ());
				double d = 0.05 * i;
				this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3d.normalize().scale(d)));
				if (this.returnTimer == 0) this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
				this.returnTimer++;
			}
		}
		super.tick();
	}

	private boolean isOwnerAlive() {
		Entity entity = this.getOwner();
		return entity != null && entity.isAlive() && (!(entity instanceof ServerPlayer) || !entity.isSpectator());
	}

	public boolean isEnchanted() {
		return this.entityData.get(ENCHANTED);
	}

	@Nullable
	@Override
	protected EntityHitResult findHitEntity(@NonNull Vec3 currentPosition, @NonNull Vec3 nextPosition) {
		return this.dealtDamage ? null : super.findHitEntity(currentPosition, nextPosition);
	}

	@Override
	protected @NonNull Collection<EntityHitResult> findHitEntities(@NonNull Vec3 from, @NonNull Vec3 to) {
		EntityHitResult entityHitResult = this.findHitEntity(from, to);
		return entityHitResult != null ? List.of(entityHitResult) : List.of();
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		float damage = 8.0F;
		Entity entity2 = this.getOwner();
		DamageSource damageSource = this.damageSources().trident(this, (entity2 == null ? this : entity2));
		if (this.level() instanceof ServerLevel level)
			damage = EnchantmentHelper.modifyDamage(level, this.getWeaponItem(), entity, damageSource, damage);

		this.dealtDamage = true;
		if (entity.hurtOrSimulate(damageSource, damage)) {
			if (entity.getType() == EntityTypes.ENDERMAN) return;

			if (this.level() instanceof ServerLevel level) {
				EnchantmentHelper.doPostAttackEffectsWithItemSourceOnBreak(level, entity, damageSource,
						this.getWeaponItem(), _ -> this.kill(level));
			}

			if (entity instanceof LivingEntity livingEntity) {
				this.doKnockback(livingEntity, damageSource);
				entity.igniteForTicks(20*3);
				this.doPostHurtEffects(livingEntity);
			}
		}

		this.deflect(ProjectileDeflection.REVERSE, entity, this.owner, false);
		this.setDeltaMovement(this.getDeltaMovement().multiply(0.02, 0.2, 0.02));
		this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
	}

	@Override
	protected void hitBlockEnchantmentEffects(@NonNull ServerLevel level, BlockHitResult blockHitResult, @NonNull ItemStack weaponStack) {
		Vec3 vec3d = blockHitResult.getBlockPos().clampLocationWithin(blockHitResult.getLocation());
		EnchantmentHelper.onHitBlock(level, weaponStack,
				this.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null, this,
				null, vec3d, level.getBlockState(blockHitResult.getBlockPos()), _ -> this.kill(level));
	}

	@Override
	public ItemStack getWeaponItem() {
		return this.getPickupItemStackOrigin();
	}

	@Override
	protected boolean tryPickup(@NonNull Player player) {
		return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return new ItemStack(ItemRegistry.WILDFIRE_TRIDENT);
	}

	@Override
	protected @NonNull SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.TRIDENT_HIT_GROUND;
	}

	@Override
	public void playerTouch(@NonNull Player player) {
		if (this.ownedBy(player) || this.getOwner() == null) super.playerTouch(player);
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput view) {
		super.readAdditionalSaveData(view);
		this.dealtDamage = view.getBooleanOr("DealtDamage", false);
		this.entityData.set(LOYALTY, this.getLoyalty(this.getPickupItemStackOrigin()));
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput view) {
		super.addAdditionalSaveData(view);
		view.putBoolean("DealtDamage", this.dealtDamage);
	}

	private byte getLoyalty(ItemStack stack) {
		return this.level() instanceof ServerLevel level ? (byte)
				Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(level, stack, this), 0, 127) : 0;
	}

	@Override
	public void tickDespawn() {
		int i = this.entityData.get(LOYALTY);
		if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
			super.tickDespawn();
		}
	}

	@Override
	protected float getWaterInertia() {
		return 0.99F;
	}

	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}
}