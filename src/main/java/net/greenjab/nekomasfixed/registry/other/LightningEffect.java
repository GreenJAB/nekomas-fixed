package net.greenjab.nekomasfixed.registry.other;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import org.jspecify.annotations.Nullable;


public class LightningEffect extends InstantStatusEffect {
    public LightningEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);
        if (lightning != null) {
            lightning.refreshPositionAfterTeleport(entity.getX(), entity.getY(), entity.getZ());
            world.spawnEntity(lightning);
        }
        return true;
    }

    @Override
    public void applyInstantEffect(
            ServerWorld world, @org.jspecify.annotations.Nullable Entity effectEntity, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity
    ) {
        LightningEntity lightning =(LightningEntity) EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);
        if (lightning != null) {
            lightning.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
            world.spawnEntity(lightning);
        }
    }
}
