package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(RaiderEntity.class)
public class RaiderEntityMixin {

    @Inject(method = "tickMovement", at = @At("RETURN"))
    private void moveBoat(CallbackInfo ci) {
        RaiderEntity RE = (RaiderEntity)(Object)this;
        if (RE.hasVehicle() && RE.getVehicle() instanceof AbstractBoatEntity boatEntity && RE == boatEntity.getFirstPassenger()) {
            boatEntity.setInputs(false, false, false, false);
            LivingEntity LE = RE.getTarget();
            if (LE!=null) {
                Optional<Float> toYawOptional = getTargetYaw(LE.getX(), LE.getZ(), RE.getX(), RE.getZ());
                if (toYawOptional.isPresent()) {
                    float targetYaw = toYawOptional.get();
                    float boatYaw = boatEntity.getYaw();
                    double toYaw = (targetYaw-boatYaw)*(Math.PI/180f);
                    toYaw = Math.atan(Math.tan(toYaw/2.0));
                    if (Vector3f.distanceSquared((float)LE.getX(), (float)LE.getY(), (float)LE.getZ(), (float)RE.getX(), (float)RE.getY(), (float)RE.getZ())>150) {
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
    protected Optional<Float> getTargetYaw(double x1, double z1, double x2, double z2) {
        double d = x1 - x2;
        double e = z1 - z2;
        return !(Math.abs(e) > 1.0E-5F) && !(Math.abs(d) > 1.0E-5F)
                ? Optional.empty()
                : Optional.of((float)(MathHelper.atan2(e, d) * 180.0F / (float)Math.PI) - 90.0F);
    }
}