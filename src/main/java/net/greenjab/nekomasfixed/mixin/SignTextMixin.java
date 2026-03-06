package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.block.entity.SignText;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignText.class)
public class SignTextMixin {


    @Inject(method = "withGlowing", at = @At("HEAD"), cancellable = true)
    private void withGlowing(boolean glowing, CallbackInfoReturnable<SignText> cir) {
        SignText text = (SignText) (Object)this;
        DyeColor color = text.getColor();
        if(
                text.getColor().equals(ModColors.AMBER) ||
                        text.getColor().equals(ModColors.AQUA) ||
                        text.getColor().equals(ModColors.CRIMSON) ||
                        text.getColor().equals(ModColors.INDIGO)
        ){
             color = text.getColor();
        }
        cir.setReturnValue(glowing == text.isGlowing() ? text : new SignText(text.getMessages(false), text.getMessages(true), color, glowing));
    }
}
