package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
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
        int randInt = random.nextInt(3) + 1;
        if (randInt == 1) {
            pillager.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SPEAR));
        } else {
            pillager.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
        }
        pillager.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SPEAR));
        ci.cancel();
    }
}