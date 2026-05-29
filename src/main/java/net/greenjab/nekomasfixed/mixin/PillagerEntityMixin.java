package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PillagerEntity.class)
public class PillagerEntityMixin {
    @Inject(method = "initEquipment", at = @At("HEAD"), cancellable = true)
    protected void initEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        PillagerEntity pillager = (PillagerEntity) (Object) this;
        pillager.equipStack(EquipmentSlot.MAINHAND, random.nextBoolean() ? new ItemStack(Items.CROSSBOW) : new ItemStack(Items.IRON_SPEAR));
        ci.cancel();
    }

}
