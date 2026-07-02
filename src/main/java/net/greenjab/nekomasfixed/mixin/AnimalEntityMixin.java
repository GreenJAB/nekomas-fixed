package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {

    @Inject(method = "interactMob", at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;lovePlayer(Lnet/minecraft/entity/player/PlayerEntity;)V", ordinal = 0))
    private void saveLastEatenFlower(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir, @Local ItemStack itemStack) {
        if (((AnimalEntity)(Object)this) instanceof MoobloomEntity moobloomEntity) {
            moobloomEntity.setLastFlowerEaten(itemStack.copy());
        }
    }

}
