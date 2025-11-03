package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AbstractBoatEntity.class)
public abstract class AbstractBoatEntityMixin {

    @Shadow private float yawVelocity;

    @Shadow protected abstract void updatePaddles();

    @Shadow protected abstract int getMaxPassengers();

    @Redirect(method = "updatePaddles", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;setYaw(F)V"))
    private void adjustTurningForBigBoat(AbstractBoatEntity boat, float v){
        float f = 1.0f;
        if (boat instanceof MegaBoatEntity) f=0.4f;
        boat.setYaw(boat.getYaw() + yawVelocity*f);
    }

    @Redirect(method = "updatePassengerPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setYaw(F)V"))
    private void adjustTurningForBigBoat2(Entity instance, float yaw){
        float f = 1.0f;
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (ABE instanceof MegaBoatEntity) f=0.4f;
        instance.setYaw(instance.getYaw() + yawVelocity*f);
    }
    @Redirect(method = "updatePassengerPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setHeadYaw(F)V"))
    private void adjustTurningForBigBoat3(Entity instance, float yaw){
        float f = 1.0f;
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (ABE instanceof MegaBoatEntity) f=0.4f;
        instance.setHeadYaw(instance.getHeadYaw() + yawVelocity*f);
    }
    @ModifyExpressionValue(method = "updatePassengerPosition", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private int animalsFaceSideways(int original){
        if (original <2 ) return original;
        return getMaxPassengers();
    }

    @Inject(method = "clampPassengerYaw", at = @At(value = "HEAD"), cancellable = true)
    private void adjustTurningForBigBoat4(Entity passenger, CallbackInfo ci){
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (!(passenger instanceof PlayerEntity)) {
            passenger.setBodyYaw(ABE.getYaw());
            float f = MathHelper.wrapDegrees(passenger.getHeadYaw() - ABE.getYaw());
            float g = MathHelper.clamp(f, -105.0F, 105.0F)+ABE.getYaw();
            passenger.lastYaw += g;
            passenger.setYaw(g);
            passenger.setHeadYaw(g);
            ci.cancel();
        }
    }

    @Redirect(method = "updatePaddles", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void adjustAccelerationForBigBoat(AbstractBoatEntity instance, Vec3d vec3d, @Local float f){
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (ABE instanceof MegaBoatEntity megaBoatEntity) f*=megaBoatEntity.getSpeed();
        ABE.setVelocity(
                ABE.getVelocity().add(MathHelper.sin(-ABE.getYaw() * (float) (Math.PI / 180.0)) * f, 0.0, MathHelper.cos(ABE.getYaw() * (float) (Math.PI / 180.0)) * f)
        );
    }

    @Redirect(method = "updateVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;setVelocity(DDD)V", ordinal = 0))
    private void adjustSpeedForBigBoat2(AbstractBoatEntity instance, double x, double y, double z, @Local float f){
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (ABE instanceof MegaBoatEntity megaBoatEntity) f=1-(1-f)/(megaBoatEntity.getSpeed()*3.0f);
        Vec3d vec3d = ABE.getVelocity();
        ABE.setVelocity(vec3d.x * f, y, vec3d.z * f);
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isClient()Z", ordinal = 1))
    private boolean letIllagerControl(boolean original) {
        if (!original) {
            AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
            if (ABE.getFirstPassenger() instanceof RaiderEntity) {
                this.updatePaddles();
            }
        }
        return original;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void stopTurnWhenEmpty(CallbackInfo ci) {
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (!(ABE.getFirstPassenger() instanceof PlayerEntity ||ABE.getFirstPassenger() instanceof RaiderEntity)) {
            yawVelocity=0;
        }
    }
}