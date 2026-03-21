package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class SlingshotProjectileEntity extends ThrownItemEntity {

    private boolean shatter = false;
    private int ticksStuck = 0;
    private ItemStack weapon = ItemStack.EMPTY;

    public SlingshotProjectileEntity(World world, LivingEntity owner, ItemStack stack, ItemStack weapon, boolean shatter) {
        super(EntityTypeRegistry.SLINGSHOT_PROJECTILE, owner, world, stack);
        this.weapon = weapon.copy();
        this.shatter = shatter;
    }

    public SlingshotProjectileEntity(EntityType<SlingshotProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getStack();
        return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; i++) {
                this.getEntityWorld().addParticleClient(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void tick(){
        super.tick();
        if (!this.getEntityWorld().isClient()) {
            if (!this.getEntityWorld().isSpaceEmpty(this, this.getBoundingBox().contract(1.0E-7))) {
                ticksStuck++;
            } else ticksStuck =0;
        }
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = this.getDamageSources().thrown(this, this.getOwner());
        if (entity.sidedDamage(damageSource, getDamage(this.getStack().getItem()))) {
            if (entity instanceof LivingEntity livingEntity2) {
                this.knockback(livingEntity2, damageSource);
            }
        }
    }

    protected void knockback(LivingEntity target, DamageSource source) {
        double d = this.weapon != null && this.getEntityWorld() instanceof ServerWorld serverWorld
                ? EnchantmentHelper.modifyKnockback(serverWorld, this.weapon, target, source, 0.0F)
                : 0.0F;
        if (d > 0.0) {
            double e = Math.max(0.0, 1.0 - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
            Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply(d * 0.6 * e);
            if (vec3d.lengthSquared() > 0.0) {
                target.addVelocity(vec3d.x, 0.1, vec3d.z);
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
    protected void onCollision(HitResult hitResult) {
        if (shatter && getOwner() instanceof LivingEntity entity) {
            shatter = false;
            for (int i = 0;i<3;i++) {
                SlingshotProjectileEntity newSlingshotProjectileEntity = new SlingshotProjectileEntity(this.getEntityWorld(), entity, getStack(), weapon, false);
                if (hitResult instanceof BlockHitResult blockHitResult) {
                    newSlingshotProjectileEntity.setPosition(this.getX(), this.getY(), this.getZ());
                    Direction.Axis axis = blockHitResult.getSide().getAxis();
                    Vec3d vec = blockHitResult.getSide().getDoubleVector();
                    if (Math.signum(this.getVelocity().getComponentAlongAxis(axis))!=Math.signum(vec.getComponentAlongAxis(axis))){
                        Vec3d vec2 = new Vec3d(vec.x==0?1:-0.9,vec.y==0?1:-0.9,vec.z==0?1:-0.9);
                        Vec3d vec3d = this.getVelocity().multiply(vec2).multiply(0.8).add(new Vec3d(this.getRandom().nextTriangular(0, 1), 0, this.getRandom().nextTriangular(0, 1)));
                        newSlingshotProjectileEntity.setVelocity(vec3d);
                        newSlingshotProjectileEntity.velocityDirty = true;
                    }
                } else if (hitResult instanceof EntityHitResult entityHitResult) {
                    Entity e = entityHitResult.getEntity();
                    newSlingshotProjectileEntity.setPosition(e.getX(), this.getY(), e.getZ());
                    Vec3d vec3d = this.getVelocity().multiply(0.8).add(new Vec3d(this.getRandom().nextTriangular(0, 1), 0, this.getRandom().nextTriangular(0, 1)));
                    newSlingshotProjectileEntity.setVelocity(vec3d);
                    newSlingshotProjectileEntity.velocityDirty = true;
                }

                this.getEntityWorld().spawnEntity(newSlingshotProjectileEntity);
            }
        }
        if (hitResult instanceof BlockHitResult blockHitResult && this.getStack().isOf(Items.AMETHYST_SHARD) && (blockHitResult.getSide() != Direction.UP || this.getVelocity().y < -0.035) && ticksStuck<5) {
            Direction.Axis axis = blockHitResult.getSide().getAxis();
            Vec3d vec = blockHitResult.getSide().getDoubleVector();
            if (Math.signum(this.getVelocity().getComponentAlongAxis(axis))!=Math.signum(vec.getComponentAlongAxis(axis))){
                Vec3d vec2 = new Vec3d(vec.x==0?1:-1,vec.y==0?1:-1,vec.z==0?1:-1).multiply(0.9);
                this.setVelocity(this.getVelocity().multiply(vec2));
                this.velocityDirty = true;
                this.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_FALL, 1, 1);
            } else {
                super.onCollision(hitResult);
                if (!this.getEntityWorld().isClient()) {
                    this.getEntityWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
                    this.discard();
                }
            }
        } else {
            super.onCollision(hitResult);
            if (this.getStack().isOf(Items.RESIN_CLUMP)) {
                AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getEntityWorld(), this.getX(), this.getY(), this.getZ());
                areaEffectCloudEntity.setRadius(3.0F);
                areaEffectCloudEntity.setRadiusOnUse(-0.5F);
                areaEffectCloudEntity.setDuration(60);
                areaEffectCloudEntity.setWaitTime(0);
                areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / areaEffectCloudEntity.getDuration());
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 4));
                this.getEntityWorld().spawnEntity(areaEffectCloudEntity);
            }
            if (!this.getEntityWorld().isClient()) {
                this.getEntityWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
                this.discard();
            }
        }
    }
}
