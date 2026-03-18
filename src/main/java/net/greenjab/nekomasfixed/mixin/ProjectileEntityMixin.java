package net.greenjab.nekomasfixed.mixin;

import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {
    @Shadow
    public abstract @Nullable Entity getOwner();

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    protected void onCollision(HitResult hitResult, CallbackInfo ci) {
ProjectileEntity projectileEntity = (ProjectileEntity)(Object)this;
        World world = Objects.requireNonNull(projectileEntity.getOwner()).getEntityWorld();
Vec3d pos = hitResult.getPos();
        LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);
        if(lightningEntity!=null){
            lightningEntity.refreshPositionAfterTeleport(pos);
            world.spawnEntity(lightningEntity);
        }
    }
}
