package net.greenjab.nekomasfixed.mixin.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBoat.class)
public abstract class AbstractBoatMixin {

    @Shadow private float deltaRotation;

    @Shadow protected abstract void controlBoat();

    @Shadow protected abstract int getMaxPassengers();

    @WrapOperation(method = "controlBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;setYRot(F)V"))
    private void adjustTurningForBigBoat(AbstractBoat boat, float v, Operation<Void> original) {
        float f = 1.0F;
        if (boat instanceof BigBoat bigBoat) f = bigBoat.getRotationSpeed();
        original.call(boat, boat.getYRot() + deltaRotation * f);
    }

    @WrapOperation(method = "positionRider", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setYRot(F)V"))
    private void adjustTurningForBigBoat2(Entity passenger, float yRot, Operation<Void> original) {
        float f = 1.0F;
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) f = bigBoat.getRotationSpeed();
        original.call(passenger, passenger.getYRot() + deltaRotation * f);
    }

    @WrapOperation(method = "positionRider", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setYHeadRot(F)V"))
    private void adjustTurningForBigBoat3(Entity passenger, float yHeadRot, Operation<Void> original) {
        float f = 1.0F;
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) f = bigBoat.getRotationSpeed();
        original.call(passenger, passenger.getYHeadRot() + deltaRotation * f);
    }

    // Moved from positionRider to calculatePassengerBodyYRot where size() is now called
    @ModifyExpressionValue(method = "calculatePassengerBodyYRot", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private int animalsFaceSideways(int original) {
        if (original < 2) return original;
        return getMaxPassengers();
    }

    // clampRotation returns float now, so use CallbackInfoReturnable<Float>
    @Inject(method = "clampRotation", at = @At("HEAD"), cancellable = true)
    private void adjustTurningForBigBoat4(Entity passenger, CallbackInfoReturnable<Float> cir) {
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (!(passenger instanceof Player)) {
            passenger.setYBodyRot(ABE.getYRot());
            float f = Mth.wrapDegrees(passenger.getYHeadRot() - ABE.getYRot());
            float g = Mth.clamp(f, -105.0F, 105.0F) + ABE.getYRot();
            passenger.yRotO += g;
            passenger.setYRot(g);
            passenger.setYHeadRot(g);
            cir.setReturnValue(Mth.wrapDegrees(ABE.getYRot() - g));
        }
    }

    @WrapOperation(method = "controlBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private void adjustAccelerationForBigBoat(AbstractBoat instance, Vec3 vec3, Operation<Void> original, @Local(ordinal = 0) float acceleration) {
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) acceleration *= bigBoat.getSpeed();
        original.call(instance,
                ABE.getDeltaMovement().add(Mth.sin(-ABE.getYRot() * (float)(Math.PI / 180.0)) * acceleration, 0.0, Mth.cos(ABE.getYRot() * (float)(Math.PI / 180.0)) * acceleration)
        );
    }

    @WrapOperation(method = "floatBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;setDeltaMovement(DDD)V"))
    private void adjustSpeedForBigBoat2(AbstractBoat instance, double x, double y, double z, Operation<Void> original, @Local(ordinal = 0) float invFriction) {
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) {
            invFriction = 1.0F - (1.0F - invFriction) / (bigBoat.getSpeed() * 3.0F);
        }
        Vec3 vec3d = ABE.getDeltaMovement();
        original.call(instance, vec3d.x * (double)invFriction, y, vec3d.z * (double)invFriction);
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z", ordinal = 1))
    private boolean letIllagerControl(boolean original) {
        if (!original) {
            AbstractBoat ABE = (AbstractBoat)(Object)this;
            if (ABE.getFirstPassenger() instanceof Raider) {
                this.controlBoat();
            }
        }
        return original;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void stopTurnWhenEmpty(CallbackInfo ci) {
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (!(ABE.getFirstPassenger() instanceof Player || ABE.getFirstPassenger() instanceof Raider)) {
            deltaRotation = 0.0F;
        }
    }
}