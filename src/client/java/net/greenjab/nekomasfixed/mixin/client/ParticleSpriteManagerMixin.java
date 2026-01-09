package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.render.other.NumberParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleSpriteManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleSpriteManager.class)
public abstract class ParticleSpriteManagerMixin {

    @Shadow protected abstract <T extends ParticleEffect> void register(ParticleType<T> type, ParticleFactory<T> factory);

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleSpriteManager;register(Lnet/minecraft/particle/ParticleType;Lnet/minecraft/client/particle/ParticleSpriteManager$SpriteAwareFactory;)V", ordinal = 48))
    private void addNumberParticle(CallbackInfo ci) {
        register(OtherRegistry.NUMBER, new NumberParticle.Factory());
    }
}