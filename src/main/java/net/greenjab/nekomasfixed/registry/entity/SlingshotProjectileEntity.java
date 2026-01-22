package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SlingshotProjectileEntity extends ThrownItemEntity {

    public SlingshotProjectileEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntityType.SNOWBALL, owner, world, stack);
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
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.serverDamage(this.getDamageSources().thrown(this, this.getOwner()), 2f);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult instanceof BlockHitResult blockHitResult && this.getStack().isOf(Items.AMETHYST_SHARD) && (blockHitResult.getSide() != Direction.UP || this.getVelocity().y < -0.035)) {
            Direction.Axis axis = blockHitResult.getSide().getAxis();
            Vec3d vec = blockHitResult.getSide().getDoubleVector();
            if (Math.signum(this.getVelocity().getComponentAlongAxis(axis))!=Math.signum(vec.getComponentAlongAxis(axis))){
                Vec3d vec2 = new Vec3d(vec.x==0?1:-0.9,vec.y==0?1:-0.9,vec.z==0?1:-0.9);
                this.setVelocity(this.getVelocity().multiply(vec2));
                this.velocityDirty = true;
                this.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);
            }
        } else {
            super.onCollision(hitResult);
            if (!this.getEntityWorld().isClient()) {
                this.getEntityWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
                this.discard();
            }
        }
    }
}
