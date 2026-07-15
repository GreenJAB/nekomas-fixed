package net.greenjab.nekomasfixed.registry.other;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.InstantaneousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.*;
import org.jspecify.annotations.Nullable;


public class LightningEffect extends InstantaneousMobEffect {
    public LightningEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (level.canSeeSky(entity.blockPosition())) {
            LightningBolt lightning = EntityTypes.LIGHTNING_BOLT.create(level, EntitySpawnReason.EVENT);
            if (lightning != null) {
                lightning.snapTo(entity.getX(), entity.getY(), entity.getZ());
                level.addFreshEntity(lightning);
            }
        }
        return true;
    }

    @Override
    public void applyInstantaneousEffect(
            ServerLevel level, @org.jspecify.annotations.Nullable Entity effectEntity, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity
    ) {
        if (level.canSeeSky(target.blockPosition())) {
            LightningBolt lightning =EntityTypes.LIGHTNING_BOLT.create(level, EntitySpawnReason.EVENT);
            if (lightning != null) {
                lightning.snapTo(target.getX(), target.getY(), target.getZ());
                level.addFreshEntity(lightning);
            }
        }
    }
}
