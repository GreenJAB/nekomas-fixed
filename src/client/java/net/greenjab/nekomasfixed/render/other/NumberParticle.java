package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class NumberParticle extends Particle {
     private final double damage;
    public static ParticleTextureSheet particleTextureSheet = new ParticleTextureSheet("number");
    NumberParticle(ClientWorld world, double x, double y, double z, double damage) {
        super(world, x, y, z);
        this.damage = damage;
        this.velocityMultiplier = 0.66F;
        this.ascending = true;
        this.velocityX *= 0.01F;
        this.velocityY *= 0.01F;
        this.velocityZ *= 0.01F;
        this.velocityY += 0.2;
        this.maxAge = 30;
    }

    @Override
    public ParticleTextureSheet textureSheet() {
        return particleTextureSheet;
    }

    public double getDamage() {
        return damage;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {

        public Factory() {
        }

        public Particle createParticle(
                SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, Random random
        ) {
            return new NumberParticle(clientWorld, d, e, f, g);
        }
    }
}
