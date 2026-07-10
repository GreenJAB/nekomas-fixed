package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.Moobloom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public class AnimalMixin {

    @Inject(method = "mobInteract", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;setInLove(Lnet/minecraft/world/entity/player/Player;)V", ordinal = 0))
    private void saveLastEatenFlower(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir, @Local ItemStack itemStack) {
        if (((Animal)(Object)this) instanceof Moobloom moobloomEntity) {
            moobloomEntity.setLastFlowerEaten(itemStack.copy());
        }
    }

}
