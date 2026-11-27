package net.greenjab.nekomasfixed.mixin.boat;

import net.minecraft.world.entity.monster.Pillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Pillager.class)
public class PillagerMixin {

    @ModifyArg(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/raid/Raider$HoldGroundAttackGoal;<init>(Lnet/minecraft/world/entity/raid/Raider;Lnet/minecraft/world/entity/monster/AbstractIllager;F)V"), index = 2)
    private float shootFurther(float distance) {
        return 15;
    }

    @ModifyArg(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/RangedCrossbowAttackGoal;<init>(Lnet/minecraft/world/entity/monster/Monster;DF)V"), index = 2)
    private float shootFurther2(float distance) {
        return 12;
    }
}
