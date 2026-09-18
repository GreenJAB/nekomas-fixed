package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.mixin.accessor.MobAccessor;
import net.greenjab.nekomasfixed.registry.entity.goal.PollinatingMoobloomGoal;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bee.class)
public class BeeMixin {
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void initGoals(CallbackInfo ci) {
        Bee beeEntity = (Bee) (Object) this;
        ((MobAccessor) this).getGoalSelector().addGoal(4, new PollinatingMoobloomGoal(beeEntity));
    }
}