package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.registries.ParticleRegistry;
import net.greenjab.nekomasfixed.render.other.NumberParticle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleResources.class)
public abstract class ParticleResourcesMixin {

    @Shadow protected abstract <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider);

    @Inject(method = "registerProviders", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleResources;register(Lnet/minecraft/core/particles/ParticleType;Lnet/minecraft/client/particle/ParticleResources$SpriteParticleRegistration;)V", ordinal = 48))
    private void addNumberParticle(CallbackInfo ci) {
        register(ParticleRegistry.NUMBER, new NumberParticle.Factory());
    }
}