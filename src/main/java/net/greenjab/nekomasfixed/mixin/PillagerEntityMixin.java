package net.greenjab.nekomasfixed.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Pillager.class)
public class PillagerEntityMixin {
    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
    protected void initEquipment(RandomSource random, DifficultyInstance localDifficulty, CallbackInfo ci) {
        Pillager pillager = (Pillager) (Object) this;
        pillager.setItemSlot(EquipmentSlot.MAINHAND, random.nextBoolean() ? new ItemStack(Items.CROSSBOW) : new ItemStack(Items.IRON_SPEAR));
        ci.cancel();
    }

}
