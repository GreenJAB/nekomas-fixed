package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.LongJumpUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LongJumpUtil.class)
public abstract class LongJumpUtilMixin {

    @ModifyExpressionValue(method = "getJumpingVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getFinalGravity()D"))
    private static double projectileGravity(double original, @Local(ordinal = 0, argsOnly = true) float max) {
        if (max == 1.11f) {
            return 0.03;
        }
        return original;
    }
}