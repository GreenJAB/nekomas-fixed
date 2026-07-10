package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class FireBomb extends Projectile {

    private static final ExplosionDamageCalculator EXPLOSION_BEHAVIOR = new ExplosionDamageCalculator()  {
        @Override
        public boolean shouldBlockExplode(@NonNull Explosion explosion, @NonNull BlockGetter world, @NonNull BlockPos pos, BlockState state, float power) {
            return state.is(Blocks.AIR);
        }
    };

    public FireBomb(Level level, LivingEntity owner) {
        this(EntityTypeRegistry.FIRE_BOMB, level);
        this.setOwner(owner);
        this.snapTo(owner.getX(), owner.getY(), owner.getZ(), this.getYRot(), this.getXRot());
        this.reapplyPosition();
    }

    public FireBomb(EntityType<FireBomb> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
    }

    @Override
    public void handleEntityEvent(byte status) {
    }

    @Override
    public void tick() {
        this.applyGravity();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        Vec3 vec3d;
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3d = hitResult.getLocation();
        } else {
            vec3d = this.position().add(this.getDeltaMovement());
        }

        this.setPos(vec3d);
        this.updateRotation();
        this.applyEffectsFromBlocks();
        super.tick();
        if (hitResult.getType() != HitResult.Type.MISS && this.isAlive()) {
            this.hitTargetOrDeflectSelf(hitResult);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!(entity instanceof WildfireEntity)) {
            super.onHitEntity(entityHitResult);
            if (!this.level().isClientSide()) {
                this.level().explode(this, Explosion.getDefaultDamageSource(this.level(), this), EXPLOSION_BEHAVIOR, entity.getX(), entity.getY() + 1, entity.getZ(), 1, true, Level.ExplosionInteraction.MOB);
                this.discard();
            }
        }
    }

    @Override
    protected void onHit(@NonNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            this.level().explode(this, Explosion.getDefaultDamageSource(this.level(), this), EXPLOSION_BEHAVIOR, this.getX(), this.getY(), this.getZ(), 1, true, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    @Override
    protected double getDefaultGravity() {
        return 0.03;
    }
}
