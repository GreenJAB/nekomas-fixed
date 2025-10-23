package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(AbstractBoatEntity.class)
public class AbstractBoatEntityMixin {

    @Shadow private float yawVelocity;

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

    /*@ModifyVariable(method = "updatePaddles", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private float adjustVelocityForBigBoat(float f) {
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (ABE instanceof MegaBoatEntity) return f*0.1f;
        return f;
    }*/
    @Redirect(method = "updatePaddles", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void adjustAccelerationForBigBoat(AbstractBoatEntity instance, Vec3d vec3d, @Local float f){
        //float f = 1.0f;
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (ABE instanceof MegaBoatEntity megaBoatEntity) f*=megaBoatEntity.getSpeed();
        ABE.setVelocity(
                ABE.getVelocity().add(MathHelper.sin(-ABE.getYaw() * (float) (Math.PI / 180.0)) * f, 0.0, MathHelper.cos(ABE.getYaw() * (float) (Math.PI / 180.0)) * f)
        );
    }

    @Redirect(method = "updateVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;setVelocity(DDD)V", ordinal = 0))
    private void adjustSpeedForBigBoat2(AbstractBoatEntity instance, double x, double y, double z, @Local float f){
        //float f = 1.0f;
        AbstractBoatEntity ABE = (AbstractBoatEntity)(Object)this;
        if (ABE instanceof MegaBoatEntity megaBoatEntity) f=1-(1-f)/(megaBoatEntity.getSpeed()*3.0f);
        Vec3d vec3d = ABE.getVelocity();
        ABE.setVelocity(vec3d.x * f, y, vec3d.z * f);
    }
}