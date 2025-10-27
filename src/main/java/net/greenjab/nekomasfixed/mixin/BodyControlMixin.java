package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BodyControl.class)
public class BodyControlMixin {
    @Shadow @Final
    private MobEntity entity;

    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    private void notInBoat(CallbackInfo ci) {
        if (entity.hasVehicle() && entity.getVehicle() instanceof AbstractBoatEntity) {
            ci.cancel();
        }
    }
}