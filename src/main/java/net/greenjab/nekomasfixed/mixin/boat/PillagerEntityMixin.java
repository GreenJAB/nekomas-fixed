package net.greenjab.nekomasfixed.mixin.boat;

import net.greenjab.nekomasfixed.mixin.accessor.MobEntityAccessor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.ChargeKineticWeaponGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    @Inject(method = "initEquipment", at = @At("HEAD"), cancellable = true)
    protected void initSpearEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        PillagerEntity pillager = (PillagerEntity)(Object)this;
        if (random.nextInt(20)==0) pillager.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SPEAR));
        else pillager.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
        ci.cancel();
    }
    @Inject(method = "initGoals", at = @At("TAIL"))
    private void addMeleeGoalForSpear(CallbackInfo ci) {
        PillagerEntity pillager = (PillagerEntity)(Object)this;
        ((MobEntityAccessor) pillager).getGoalSelector()
                .add(3, new MeleeAttackGoal(pillager, 1.2, false));
    }
    @Inject(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",ordinal = 0))
    protected void spearGoal(CallbackInfo ci) {
        PillagerEntity pillager = (PillagerEntity) (Object) this;
        pillager.goalSelector.add(1, new ChargeKineticWeaponGoal<>(pillager, 1.0, 1.0, 10.0F, 2.0F));
    }
}