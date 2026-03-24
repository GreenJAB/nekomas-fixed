package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.IsTropicalFishFedDataTracker;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.passive.DolphinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DolphinEntity.class)
public class DolphinEntityMixin {


    @Unique
    DolphinEntity dolphinEntity = (DolphinEntity) (Object) this;

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initCustomDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(IsTropicalFishFedDataTracker.IS_TROPICAL_FISH_FED, false);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    private void initCustomGoals(CallbackInfo ci){
        dolphinEntity.goalSelector.add(10, new MoveIntoWaterGoal(dolphinEntity));
    }
}
