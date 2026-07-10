package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class NumberParticle extends Particle {
     private final double damage;
    public static ParticleRenderType particleTextureSheet = new ParticleRenderType("number");
    NumberParticle(ClientLevel level, double x, double y, double z, double damage) {
        super(level, x+ level.getRandom().nextGaussian()/5f, y+ level.getRandom().nextGaussian()/10f,
                z+ level.getRandom().nextGaussian()/5f);
        this.damage = damage;
        this.friction = 0.66F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.lifetime = (int) Math.min(20+damage*2, 50);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) this.remove();
        else this.move(0, 0.015, 0);
    }

    @Override
    public @NonNull ParticleRenderType getGroup() {
        return particleTextureSheet;
    }

    public double getDamage() {
        return damage;
    }

    public int getAge() {
        return age;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {

        public Factory() {
        }

        public Particle createParticle(@NonNull SimpleParticleType simpleParticleType, @NonNull ClientLevel level,
                                       double d, double e, double f, double g, double h, double i, @NonNull RandomSource random) {
            return new NumberParticle(level, d, e, f, g);
        }
    }
}
