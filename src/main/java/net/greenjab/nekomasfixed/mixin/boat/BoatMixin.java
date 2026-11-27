package net.greenjab.nekomasfixed.mixin.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public abstract class BoatMixin {

    @Shadow private float deltaRotation;

    @Shadow private float invFriction;

    @Shadow protected abstract int getMaxPassengers();

    @Unique
    private float rotationSpeed() {
        return (Object) this instanceof BigBoat bigBoat ? bigBoat.getRotationSpeed() : 1.0F;
    }

    @WrapOperation(method = "controlBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;setYRot(F)V"))
    private void slowBigBoatTurning(Boat boat, float yRot, Operation<Void> original) {
        original.call(boat, boat.getYRot() + deltaRotation * rotationSpeed());
    }

    @WrapOperation(method = "positionRider", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setYRot(F)V"))
    private void slowPassengerTurning(Entity passenger, float yRot, Operation<Void> original) {
        original.call(passenger, passenger.getYRot() + deltaRotation * rotationSpeed());
    }

    @WrapOperation(method = "positionRider", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setYHeadRot(F)V", ordinal = 0))
    private void slowPassengerHeadTurning(Entity passenger, float yHeadRot, Operation<Void> original) {
        original.call(passenger, passenger.getYHeadRot() + deltaRotation * rotationSpeed());
    }

    @ModifyExpressionValue(method = "positionRider", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private int animalsFaceSideways(int original) {
        return original < 2 ? original : getMaxPassengers();
    }

    @Inject(method = "clampRotation", at = @At("HEAD"), cancellable = true)
    private void clampMobRotation(Entity passenger, CallbackInfo ci) {
        if (passenger instanceof Player) return;
        Boat boat = (Boat) (Object) this;
        passenger.setYBodyRot(boat.getYRot());
        float g = Mth.clamp(Mth.wrapDegrees(passenger.getYHeadRot() - boat.getYRot()), -105.0F, 105.0F) + boat.getYRot();
        passenger.yRotO += g - passenger.getYRot();
        passenger.setYRot(g);
        passenger.setYHeadRot(g);
        ci.cancel();
    }

    @WrapOperation(method = "controlBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private void bigBoatAcceleration(Boat boat, Vec3 motion, Operation<Void> original, @Local(ordinal = 0) float acceleration) {
        if (boat instanceof BigBoat bigBoat) {
            acceleration *= bigBoat.getSpeed();
            motion = boat.getDeltaMovement().add(
                    Mth.sin(-boat.getYRot() * (float) (Math.PI / 180.0)) * acceleration, 0.0,
                    Mth.cos(boat.getYRot() * (float) (Math.PI / 180.0)) * acceleration);
        }
        original.call(boat, motion);
    }

    @WrapOperation(method = "floatBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;setDeltaMovement(DDD)V", ordinal = 0))
    private void bigBoatFriction(Boat boat, double x, double y, double z, Operation<Void> original) {
        if (boat instanceof BigBoat bigBoat) {
            float friction = 1.0F - (1.0F - invFriction) / (bigBoat.getSpeed() * 3.0F);
            Vec3 motion = boat.getDeltaMovement();
            x = motion.x * friction;
            z = motion.z * friction;
        }
        original.call(boat, x, y, z);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void stopTurnWhenEmpty(CallbackInfo ci) {
        if (!(((Boat) (Object) this).getFirstPassenger() instanceof Player)) {
            deltaRotation = 0.0F;
        }
    }
}
