package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.mob.PillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PillagerEntity.class)
public class PillagerEntityMixin {

    @ModifyArg(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/raid/RaiderEntity$PatrolApproachGoal;<init>(Lnet/minecraft/entity/mob/IllagerEntity;F)V"), index = 1)
    private float shootFurther(float distance) {
        return 15;
    }
    @ModifyConstant(method = "initGoals", constant = @Constant(floatValue = 8.0f, ordinal = 1))
    private float shootFurther2(float distance) {
        return 12;
    }
}