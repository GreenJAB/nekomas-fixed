package net.greenjab.nekomasfixed.registry.entity.goal;

import com.mojang.datafixers.util.Pair;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class MoveToCoralReefGoal extends Goal {
    private final DolphinEntity dolphin;
    private BlockPos target;
    private int timer = 0;

    public MoveToCoralReefGoal(DolphinEntity dolphin) {
        this.dolphin = dolphin;
    }

    private BlockPos searchCoralReef() {
        if (dolphin.getEntityWorld() instanceof ServerWorld serverWorld) {
            Pair<BlockPos, RegistryEntry<Biome>> pair = serverWorld.locateBiome(
                    entry -> entry.matchesKey(BiomeKeys.WARM_OCEAN),
                    dolphin.getBlockPos(),5000,32,64);
            if (pair != null) return pair.getFirst();
        }
        return null;
    }

    @Override
    public boolean canStart() {
        return dolphin.getDataTracker().get(OtherRegistry.IS_TROPICAL_FISH_FED)
                && !dolphin.isOnGround() && !dolphin.getEntityWorld().getBiome(dolphin.getBlockPos()).matchesKey(BiomeKeys.WARM_OCEAN);
    }

    @Override
    public boolean canStop(){
        return !dolphin.isOnGround() || dolphin.getEntityWorld().getBiome(dolphin.getBlockPos()).matchesKey(BiomeKeys.WARM_OCEAN) || timer >= 30;
    }

    @Override
    public void start() {
        this.target = searchCoralReef();
        this.timer = 0;
    }

    @Override
    public void tick() {
        if (dolphin.getEntityWorld().isClient()) return;
        ((ServerWorld)dolphin.getEntityWorld()).spawnParticles(ParticleTypes.GLOW, dolphin.getX(), dolphin.getY(), dolphin.getZ(), 1, 0, 0, 0, 0);
        if (target != null && dolphin.getEntityWorld().getTime()%20==0&&target.isWithinDistance(dolphin.getBlockPos(), 5000) && dolphin.getVelocity().horizontalLength()>0.1) {
            dolphin.getNavigation().startMovingTo(target.getX(), target.getY(), target.getZ(),1.2);
            timer++;
        } else timer = 30;
    }
}
