package net.greenjab.nekomasfixed.mixin.wildfire;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.ai.behavior.LongJumpUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LongJumpUtil.class)
public class LongJumpUtilMixin {

    @ModifyExpressionValue(method = "calculateJumpVectorForAngle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getGravity()D"))
    private static double projectileGravity(double original, @Local(ordinal = 0, argsOnly = true) float max) {
        if (max == 1.11f) return 0.03;
        return original;
    }
}