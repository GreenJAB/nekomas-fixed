package net.greenjab.nekomasfixed.mixin.boat;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.SpearUseGoal;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Pillager.class)
public class PillagerMixin {

    @ModifyArg(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/raid/Raider$HoldGroundAttackGoal;<init>(Lnet/minecraft/world/entity/monster/illager/AbstractIllager;F)V"), index = 1)
    private float shootFurther(float distance) {
        return 15;
    }
    @ModifyConstant(method = "registerGoals", constant = @Constant(floatValue = 8.0f, ordinal = 1))
    private float shootFurther2(float distance) {
        return 12;
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
    protected void initSpearEquipment(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        Pillager pillager = (Pillager)(Object)this;
        if (random.nextInt(20)==0) pillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SPEAR));
        else pillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
        ci.cancel();
    }

    @Inject(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",ordinal = 0))
    protected void spearGoal(CallbackInfo ci) {
        Pillager pillager = (Pillager) (Object) this;
        pillager.goalSelector.addGoal(1, new SpearUseGoal<>(pillager, 1.0, 1.0, 10.0F, 2.0F));
    }
}