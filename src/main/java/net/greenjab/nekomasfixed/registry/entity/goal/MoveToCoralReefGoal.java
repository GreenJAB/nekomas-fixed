package net.greenjab.nekomasfixed.registry.entity.goal;

import net.greenjab.nekomasfixed.util.IsTropicalFishFedDataTracker;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import java.util.function.Predicate;

public class MoveToCoralReefGoal extends Goal {
    private final DolphinEntity dolphin;
    private BlockPos target;
    private int timer = 0;

    public MoveToCoralReefGoal(DolphinEntity dolphin) {
        this.dolphin = dolphin;
    }

    private BlockPos searchCoralReef() {
        if (dolphin.getEntityWorld() instanceof ServerWorld serverWorld) {

            RegistryKey<Biome> biomeSearch = BiomeKeys.WARM_OCEAN;

            Predicate<RegistryEntry<Biome>> predicate =
                    entry -> entry.matchesKey(biomeSearch);

            var pair = serverWorld.locateBiome(
                    predicate,
                    dolphin.getBlockPos(),
                    6400,
                    32,
                    64
            );

            if (pair != null) {
                System.out.println(pair.getFirst());
                return pair.getFirst();
            }
        }
        return null;
    }

    @Override
    public boolean canStart() {
        return dolphin.getDataTracker().get(
                IsTropicalFishFedDataTracker.IS_TROPICAL_FISH_FED
        )&&!dolphin.isOnGround() && !dolphin.getEntityWorld().getBiome(dolphin.getBlockPos()).matchesKey(BiomeKeys.WARM_OCEAN);
    }

    @Override
    public boolean canStop(){
        return !dolphin.isOnGround() && dolphin.getEntityWorld().getBiome(dolphin.getBlockPos()).matchesKey(BiomeKeys.WARM_OCEAN) && timer < 20*10;
    }

    @Override
    public void start() {
        this.target = searchCoralReef();
        this.timer = 0;
    }

    @Override
    public void tick() {
        if (target != null && target.isWithinDistance(dolphin.getBlockPos(), 1000)) {
            dolphin.getNavigation().startMovingTo(
                    target.getX(),
                    target.getY(),
                    target.getZ(),
                    1.2
            );
            timer++;
        } else {
            timer = 100000;
        }
    }
}
