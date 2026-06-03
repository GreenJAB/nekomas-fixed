package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.goal.PollinatingMoobloomGoal;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    @Inject(method = "initGoals", at = @At("HEAD"))
    private void initGoals(CallbackInfo ci){
        BeeEntity beeEntity = (BeeEntity) (Object) this;
        beeEntity.goalSelector.add(4, new PollinatingMoobloomGoal(beeEntity));
    }
}
