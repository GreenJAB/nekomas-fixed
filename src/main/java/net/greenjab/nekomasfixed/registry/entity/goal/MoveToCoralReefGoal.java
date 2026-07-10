package net.greenjab.nekomasfixed.registry.entity.goal;

import com.mojang.datafixers.util.Pair;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class MoveToCoralReefGoal extends Goal {
    private final Dolphin dolphin;
    private BlockPos target;
    private int timer = 0;

    public MoveToCoralReefGoal(Dolphin dolphin) {
        this.dolphin = dolphin;
    }

    private BlockPos searchCoralReef() {
        if (dolphin.level() instanceof ServerLevel level) {
            Pair<BlockPos, Holder<Biome>> pair = level.findClosestBiome3d(
                    entry -> entry.is(Biomes.WARM_OCEAN),
                    dolphin.blockPosition(),5000,32,64);
            if (pair != null) return pair.getFirst();
        }
        return null;
    }

    @Override
    public boolean canUse() {
        return dolphin.getEntityData().get(OtherRegistry.IS_TROPICAL_FISH_FED)
                && !dolphin.onGround() && !dolphin.level().getBiome(dolphin.blockPosition()).is(Biomes.WARM_OCEAN);
    }

    @Override
    public boolean isInterruptable(){
        return !dolphin.onGround() || dolphin.level().getBiome(dolphin.blockPosition()).is(Biomes.WARM_OCEAN) || timer >= 30;
    }

    @Override
    public void start() {
        this.target = searchCoralReef();
        this.timer = 0;
    }

    @Override
    public void tick() {
        if (dolphin.level().isClientSide()) return;
        ((ServerLevel)dolphin.level()).sendParticles(ParticleTypes.GLOW, dolphin.getX(), dolphin.getY(), dolphin.getZ(), 1, 0, 0, 0, 0);
        if (target != null && dolphin.level().getGameTime()%20==0&&target.closerThan(dolphin.blockPosition(), 5000) && dolphin.getDeltaMovement().horizontalDistance()>0.1) {
            dolphin.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(),1.2);
            timer++;
        } else timer = 30;
    }
}
