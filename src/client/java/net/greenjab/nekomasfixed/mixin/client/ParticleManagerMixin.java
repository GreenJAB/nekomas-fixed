package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.render.other.NumberParticle;
import net.greenjab.nekomasfixed.render.other.NumberParticleRenderer;
import net.minecraft.client.particle.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ParticleEngine.class)
public class ParticleManagerMixin {

    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/util/List;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;"))
    private static List<ParticleRenderType> addParticleToList(List<ParticleRenderType> original){
        ArrayList<ParticleRenderType> newList = new ArrayList<>(original);
        newList.add(NumberParticle.particleTextureSheet);
        return newList.stream().toList();
    }

    @Inject(method = "createParticleGroup", at = @At(value = "HEAD"), cancellable = true)
    private void addNumberParticle(ParticleRenderType textureSheet, CallbackInfoReturnable<ParticleGroup<?>> cir) {
        if (textureSheet == NumberParticle.particleTextureSheet) {
            cir.setReturnValue(new NumberParticleRenderer((ParticleEngine)(Object)this));
        }
    }
}