package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.IsTropicalFishFedDataTracker;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DolphinEntity.class)
public class DolphinEntityMixin {


    @Unique
    DolphinEntity dolphinEntity = (DolphinEntity) (Object) this;

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initCustomDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(IsTropicalFishFedDataTracker.IS_TROPICAL_FISH_FED, false);
    }

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        if(stack.isOf(Items.TROPICAL_FISH)){
            dolphinEntity.getDataTracker().set(IsTropicalFishFedDataTracker.IS_TROPICAL_FISH_FED, true);
            stack.decrementUnlessCreative(1, player);
        }
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void initCustomGoals(CallbackInfo ci){
        dolphinEntity.goalSelector.add(10, new MoveIntoWaterGoal(dolphinEntity));
    }
}
