package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.FireworkExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkExplosion.class)
public class FireworkExplosionMixin {

    @Inject(method = "getColorName", at = @At(value = "HEAD"), cancellable = true)
    private static void newDyes(int colorIndex, CallbackInfoReturnable<Component> cir) {
        if (colorIndex == ModColors.AMBER.getColor()) cir.setReturnValue(Component.translatable("item.minecraft.firework_star.amber"));
        else if (colorIndex == ModColors.AQUA.getColor()) cir.setReturnValue(Component.translatable("item.minecraft.firework_star.aqua"));
        else if (colorIndex == ModColors.INDIGO.getColor()) cir.setReturnValue(Component.translatable("item.minecraft.firework_star.indigo"));
        else if (colorIndex == ModColors.MAROON.getColor()) cir.setReturnValue(Component.translatable("item.minecraft.firework_star.maroon"));
    }
}