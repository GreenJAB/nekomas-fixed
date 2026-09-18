package net.greenjab.nekomasfixed.registry.other;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

// Instant effect: strikes the target with a lightning bolt when in view of the sky.
public class LightningEffect extends InstantenousMobEffect {
    public LightningEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NonNull LivingEntity entity, int amplifier) {
        strike(entity.level(), entity);
        return true;
    }

    @Override
    public void applyInstantenousEffect(Entity source, Entity attacker, @NonNull LivingEntity target, int amplifier, double proximity) {
        strike(target.level(), target);
    }

    private void strike(Level level, LivingEntity entity) {
        if (level.canSeeSky(entity.blockPosition())) {
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
            if (lightning != null) {
                lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                level.addFreshEntity(lightning);
            }
        }
    }
}