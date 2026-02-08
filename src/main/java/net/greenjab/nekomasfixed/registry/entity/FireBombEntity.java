package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class FireBombEntity extends ThrownEntity {

    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new ExplosionBehavior()  {
        @Override
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            return state.isOf(Blocks.AIR);
        }
    };

    public FireBombEntity(EntityType<FireBombEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public void handleStatus(byte status) {
        //if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
        //}
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!this.getEntityWorld().isClient()) {
            this.getEntityWorld().createExplosion(this, Explosion.createDamageSource(this.getEntityWorld(), this), EXPLOSION_BEHAVIOR, entity.getX(), entity.getY()+1, entity.getZ(), 1, true, World.ExplosionSourceType.MOB);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getEntityWorld().isClient()) {
            this.getEntityWorld().createExplosion(this, Explosion.createDamageSource(this.getEntityWorld(), this), EXPLOSION_BEHAVIOR, this.getX(), this.getY(), this.getZ(), 1, true, World.ExplosionSourceType.MOB);
            this.discard();
        }
    }
}
