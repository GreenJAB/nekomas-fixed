package net.greenjab.nekomasfixed.registry.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ParticleRegistry {
    public static void registerParticles() {
        System.out.println("register Particles");
    }

    public static final SimpleParticleType NUMBER = registerParticle("number", true);

    private static SimpleParticleType registerParticle(String name, boolean alwaysShow) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, name, new SimpleParticleType(alwaysShow));
    }
}
