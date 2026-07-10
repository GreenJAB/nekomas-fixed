package net.greenjab.nekomasfixed.mixin.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
public abstract class AbstractBoatMixin {

    @Shadow private float deltaRotation;

    @Shadow protected abstract void controlBoat();

    @Shadow protected abstract int getMaxPassengers();

    @Redirect(method = "controlBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;setYRot(F)V"))
    private void adjustTurningForBigBoat(AbstractBoat boat, float v){
        float f = 1.0f;
        if (boat instanceof BigBoat bigBoat) f= bigBoat.getRotationSpeed();
        boat.setYRot(boat.getYRot() + deltaRotation*f);
    }

    @Redirect(method = "positionRider", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setYRot(F)V"))
    private void adjustTurningForBigBoat2(Entity instance, float yRot){
        float f = 1.0f;
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) f= bigBoat.getRotationSpeed();
        instance.setYRot(instance.getYRot() + deltaRotation*f);
    }
    @Redirect(method = "positionRider", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setYHeadRot(F)V"))
    private void adjustTurningForBigBoat3(Entity instance, float yHeadRot){
        float f = 1.0f;
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) f= bigBoat.getRotationSpeed();
        instance.setYHeadRot(instance.getYHeadRot() + deltaRotation*f);
    }
    @ModifyExpressionValue(method = "positionRider", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private int animalsFaceSideways(int original){
        if (original <2 ) return original;
        return getMaxPassengers();
    }

    @Inject(method = "clampRotation", at = @At(value = "HEAD"), cancellable = true)
    private void adjustTurningForBigBoat4(Entity passenger, CallbackInfo ci){
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (!(passenger instanceof Player)) {
            passenger.setYBodyRot(ABE.getYRot());
            float f = Mth.wrapDegrees(passenger.getYHeadRot() - ABE.getYRot());
            float g = Mth.clamp(f, -105.0F, 105.0F)+ABE.getYRot();
            passenger.yRotO += g;
            passenger.setYRot(g);
            passenger.setYHeadRot(g);
            ci.cancel();
        }
    }

    @Redirect(method = "controlBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private void adjustAccelerationForBigBoat(AbstractBoat instance, Vec3 vec3d, @Local float acceleration){
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) acceleration *= bigBoat.getSpeed();
        ABE.setDeltaMovement(
                ABE.getDeltaMovement().add(Mth.sin(-ABE.getYRot() * (float) (Math.PI / 180.0)) * acceleration, 0.0, Mth.cos(ABE.getYRot() * (float) (Math.PI / 180.0)) * acceleration)
        );
    }

    @Redirect(method = "floatBoat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractBoat;setDeltaMovement(DDD)V", ordinal = 0))
    private void adjustSpeedForBigBoat2(AbstractBoat instance, double x, double y, double z, @Local float invFriction){
        AbstractBoat ABE = (AbstractBoat)(Object)this;
        if (ABE instanceof BigBoat bigBoat) invFriction =1-(1- invFriction)/(bigBoat.getSpeed()*3.0f);
        Vec3 vec3d = ABE.getDeltaMovement();
        ABE.setDeltaMovement(vec3d.x * invFriction, y, vec3d.z * invFriction);
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
        if (!(ABE.getFirstPassenger() instanceof Player ||ABE.getFirstPassenger() instanceof Raider)) {
            deltaRotation=0;
        }
    }
}