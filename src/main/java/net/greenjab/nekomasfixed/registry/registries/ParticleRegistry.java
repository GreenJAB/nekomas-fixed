package net.greenjab.nekomasfixed.registry.registries;

import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ParticleRegistry {
    public static void registerParticles() {
        System.out.println("register Particles");
    }

    public static final SimpleParticleType NUMBER = registerParticle("number", true);

    private static SimpleParticleType registerParticle(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, name, new SimpleParticleType(alwaysShow));
    }
}
