package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.BigBoatEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(RaiderEntity.class)
public class RaiderEntityMixin {

    @Inject(method = "tickMovement", at = @At("RETURN"))
    private void moveBoat(CallbackInfo ci) {
        RaiderEntity RE = (RaiderEntity)(Object)this;
        if (RE.hasVehicle() && RE.getVehicle() instanceof AbstractBoatEntity boatEntity && RE == boatEntity.getFirstPassenger()) {
            boatEntity.setInputs(false, false, false, false);
            //Vec3d
            Vec3d target = null;
            if (RE.getTarget()!=null) target = RE.getTarget().getEntityPos();
            else {
                updatePatrol(RE, boatEntity);
                if (RE.hasPatrolTarget()) target = RE.getPatrolTarget().toCenterPos();
            }

            if (target!=null) {
                Optional<Float> toYawOptional = getTargetYaw(target.getX(), target.getZ(), RE.getX(), RE.getZ());
                if (toYawOptional.isPresent()) {
                    float targetYaw = toYawOptional.get();
                    float boatYaw = boatEntity.getYaw();
                    double toYaw = (targetYaw-boatYaw)*(Math.PI/180f);
                    toYaw = Math.atan(Math.tan(toYaw/2.0));
                    if (Vector3f.distanceSquared((float)target.getX(), (float)target.getY(), (float)target.getZ(), (float)RE.getX(), (float)RE.getY(), (float)RE.getZ())>150) {
                        if (toYaw > 0.25) boatEntity.setInputs(false, true, Math.abs(toYaw)<Math.PI/4, false);
                        else if (toYaw < -0.25) boatEntity.setInputs(true, false, Math.abs(toYaw)<Math.PI/4, false);
                        else boatEntity.setInputs(false, false, true, false);
                    } else {
                        toYaw-=(Math.PI/4)*(toYaw>0?1:-1);
                        if (toYaw > 0.25) boatEntity.setInputs(false, true, false, false);
                        else if (toYaw < -0.25) boatEntity.setInputs(true, false, false, false);
                        else boatEntity.setInputs(false, false, false, false);
                    }
                }
            }
        }
    }

    @Unique
    private void updatePatrol(RaiderEntity RE, AbstractBoatEntity boatEntity) {
        if (boatEntity instanceof BigBoatEntity bigBoatEntity && RE == bigBoatEntity.getFirstPassenger()) {
            if (RE.getEntityWorld().getTime() % 20 == 0 && RE.getEntityWorld().random.nextInt(10) == 0) {
                if (!RE.hasPatrolTarget()) {
                    if (RE.getEntityWorld().random.nextInt(10) == 0) RE.setPatrolTarget(null);
                    Random random = RE.getEntityWorld().random;
                    BlockPos pos = RE.getBlockPos();
                    pos = pos.add(-50 + random.nextInt(100), 0, -50 + random.nextInt(100));
                    RE.setPatrolTarget(pos);
                    List<PatrolEntity> list = RE.getEntityWorld().getEntitiesByClass(
                            PatrolEntity.class, RE.getBoundingBox().expand(32.0), patrolEntity -> patrolEntity.hasNoRaid() && !patrolEntity.isPartOf(RE));

                    for (PatrolEntity patrolEntity : list) {
                        patrolEntity.setPatrolTarget(pos);
                    }
                } else {
                    if (RE.getPatrolTarget().isWithinDistance(RE.getEntityPos(), 20.0)) {
                        RE.setPatrolTarget(null);
                    }
                }
            }
        }
    }

    @Unique
    protected Optional<Float> getTargetYaw(double x1, double z1, double x2, double z2) {
        double d = x1 - x2;
        double e = z1 - z2;
        return !(Math.abs(e) > 1.0E-5F) && !(Math.abs(d) > 1.0E-5F)
                ? Optional.empty()
                : Optional.of((float)(MathHelper.atan2(e, d) * 180.0F / (float)Math.PI) - 90.0F);
    }
}