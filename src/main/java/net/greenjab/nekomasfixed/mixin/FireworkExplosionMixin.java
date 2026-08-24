package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkExplosionComponent.class)
public class FireworkExplosionMixin {

    @Inject(method = "getColorText", at = @At(value = "HEAD"), cancellable = true)
    private static void newDyes(int color, CallbackInfoReturnable<Text> cir) {
        if (color == ModColors.AMBER.getColor()) cir.setReturnValue(Text.translatable("item.minecraft.firework_star.amber"));
        else if (color == ModColors.AQUA.getColor()) cir.setReturnValue(Text.translatable("item.minecraft.firework_star.aqua"));
        else if (color == ModColors.INDIGO.getColor()) cir.setReturnValue(Text.translatable("item.minecraft.firework_star.indigo"));
        else if (color == ModColors.MAROON.getColor()) cir.setReturnValue(Text.translatable("item.minecraft.firework_star.maroon"));
    }
}