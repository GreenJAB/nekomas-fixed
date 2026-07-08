package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class SlingshotProjectileEntity extends ThrowableItemProjectile {

    private boolean shatter = false;
    private int ticksStuck = 0;
    private ItemStack weapon = ItemStack.EMPTY;

    public SlingshotProjectileEntity(Level world, LivingEntity owner, ItemStack stack, ItemStack weapon, boolean shatter) {
        super(EntityTypeRegistry.SLINGSHOT_PROJECTILE, owner, world, stack);
        this.weapon = weapon.copy();
        this.shatter = shatter;
    }

    public SlingshotProjectileEntity(EntityType<SlingshotProjectileEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected @NonNull Item getDefaultItem() {
        return Items.AIR;
    }

    private ParticleOptions getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemStack.getItem());
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.DEATH) {
            ParticleOptions particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void tick(){
        super.tick();
        if (!this.level().isClientSide()) {
            if (!this.level().noCollision(this, this.getBoundingBox().deflate(1.0E-7))) {
                ticksStuck++;
            } else ticksStuck =0;
        }
    }


    @Override
    protected void onHitEntity(@NonNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = this.damageSources().thrown(this, this.getOwner());
        if (entity.hurtOrSimulate(damageSource, getDamage(this.getItem().getItem()))) {
            if (entity instanceof LivingEntity livingEntity2) {
                this.knockback(livingEntity2, damageSource);
            }
        }
    }

    protected void knockback(LivingEntity target, DamageSource source) {
        double d = this.weapon != null && this.level() instanceof ServerLevel serverWorld
                ? EnchantmentHelper.modifyKnockback(serverWorld, this.weapon, target, source, 0.0F)
                : 0.0F;
        if (d > 0.0) {
            double e = Math.max(0.0, 1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3d = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(d * 0.6 * e);
            if (vec3d.lengthSqr() > 0.0) {
                target.push(vec3d.x, 0.1, vec3d.z);
            }
        }
    }

    private float getDamage(Item item) {
        int i;
        if (item==Items.COPPER_NUGGET) i= 2;
        else if (item==Items.GOLD_NUGGET) i= 3;
        else if (item==Items.IRON_NUGGET) i= 4;
        else if (item==Items.AMETHYST_SHARD) i= 2;
        else if (item==Items.RESIN_CLUMP) i= 1;
        else i= 2;
        i+=NekomasFixed.enchantLevel(weapon, "power");
        return i;
    }

    @Override
    protected void onHit(@NonNull HitResult hitResult) {
        if (shatter && getOwner() instanceof LivingEntity entity) {
            shatter = false;
            for (int i = 0;i<3;i++) {
                SlingshotProjectileEntity newSlingshotProjectileEntity = new SlingshotProjectileEntity(this.level(), entity, getItem(), weapon, false);
                if (hitResult instanceof BlockHitResult blockHitResult) {
                    newSlingshotProjectileEntity.setPos(this.getX(), this.getY(), this.getZ());
                    Direction.Axis axis = blockHitResult.getDirection().getAxis();
                    Vec3 vec = blockHitResult.getDirection().getUnitVec3();
                    if (Math.signum(this.getDeltaMovement().get(axis))!=Math.signum(vec.get(axis))){
                        Vec3 vec2 = new Vec3(vec.x==0?1:-0.9,vec.y==0?1:-0.9,vec.z==0?1:-0.9);
                        Vec3 vec3d = this.getDeltaMovement().multiply(vec2).scale(0.8).add(new Vec3(this.getRandom().triangle(0, 1), 0, this.getRandom().triangle(0, 1)));
                        newSlingshotProjectileEntity.setDeltaMovement(vec3d);
                        newSlingshotProjectileEntity.needsSync = true;
                    }
                } else if (hitResult instanceof EntityHitResult entityHitResult) {
                    Entity e = entityHitResult.getEntity();
                    newSlingshotProjectileEntity.setPos(e.getX(), this.getY(), e.getZ());
                    Vec3 vec3d = this.getDeltaMovement().scale(0.8).add(new Vec3(this.getRandom().triangle(0, 1), 0, this.getRandom().triangle(0, 1)));
                    newSlingshotProjectileEntity.setDeltaMovement(vec3d);
                    newSlingshotProjectileEntity.needsSync = true;
                }

                this.level().addFreshEntity(newSlingshotProjectileEntity);
            }
        }
        if (hitResult instanceof BlockHitResult blockHitResult && this.getItem().is(Items.AMETHYST_SHARD) && (blockHitResult.getDirection() != Direction.UP || this.getDeltaMovement().y < -0.035) && ticksStuck<5) {
            Direction.Axis axis = blockHitResult.getDirection().getAxis();
            Vec3 vec = blockHitResult.getDirection().getUnitVec3();
            if (Math.signum(this.getDeltaMovement().get(axis))!=Math.signum(vec.get(axis))){
                Vec3 vec2 = new Vec3(vec.x==0?1:-1,vec.y==0?1:-1,vec.z==0?1:-1).scale(0.9);
                this.setDeltaMovement(this.getDeltaMovement().multiply(vec2));
                this.needsSync = true;
                this.playSound(SoundEvents.AMETHYST_BLOCK_FALL, 1, 1);
            } else {
                super.onHit(hitResult);
                if (!this.level().isClientSide()) {
                    this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
                    this.discard();
                }
            }
        } else {
            super.onHit(hitResult);
            if (this.getItem().is(Items.RESIN_CLUMP)) {
                AreaEffectCloud areaEffectCloudEntity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
                areaEffectCloudEntity.setRadius(3.0F);
                areaEffectCloudEntity.setRadiusOnUse(-0.5F);
                areaEffectCloudEntity.setDuration(60);
                areaEffectCloudEntity.setWaitTime(0);
                areaEffectCloudEntity.setRadiusPerTick(-areaEffectCloudEntity.getRadius() / areaEffectCloudEntity.getDuration());
                areaEffectCloudEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 4));
                this.level().addFreshEntity(areaEffectCloudEntity);
            }
            if (!this.level().isClientSide()) {
                this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
                this.discard();
            }
        }
    }
}
