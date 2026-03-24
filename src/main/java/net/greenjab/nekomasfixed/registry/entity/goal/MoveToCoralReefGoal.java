package net.greenjab.nekomasfixed.registry.entity.goal;

import net.greenjab.nekomasfixed.util.IsTropicalFishFedDataTracker;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class MoveToCoralReefGoal extends MoveToTargetPosGoal {
    private final PathAwareEntity mob;
    private boolean reached = false;

    public MoveToCoralReefGoal(PathAwareEntity mob,  double speed, int range, int maxYDifference) {
        super(mob, speed, range, maxYDifference);
        this.mob = mob;
    }


    private BlockPos searchCoralReef() {
        RegistryKey<Biome> biomeSearch = BiomeKeys.FOREST;
        World world = mob.getEntityWorld();
        if (world instanceof ServerWorld serverWorld) {
            int map = serverWorld.random.nextInt(2);
            biomeSearch = switch (map) {
                case 0 -> BiomeKeys.LUKEWARM_OCEAN;
                case 1 -> BiomeKeys.DEEP_LUKEWARM_OCEAN;
                default -> biomeSearch;
            };

            List<RegistryKey<Biome>> biomes = List.of(biomeSearch);
            Set<RegistryKey<Biome>> var10001 = Set.copyOf(biomes);
            Objects.requireNonNull(var10001);
            Predicate<RegistryEntry<Biome>> predicate = var10001::contains;

            com.mojang.datafixers.util.Pair<BlockPos, RegistryEntry<Biome>> pair = serverWorld.locateBiome(predicate, mob.getBlockPos(), 6400, 32, 64);
            if (pair != null) {
                System.out.println(pair.getFirst() + " got the location \n");
                return pair.getFirst();
            }
        }
        return mob.getBlockPos();
    }

    @Override
    public boolean canStart() {
        return !this.mob.isOnGround() && this.mob.getEntityWorld().getFluidState(this.mob.getBlockPos()).isIn(FluidTags.WATER) && this.mob instanceof DolphinEntity dolphinEntity && dolphinEntity.getDataTracker().get(IsTropicalFishFedDataTracker.IS_TROPICAL_FISH_FED);
    }

    @Override
    public boolean canStop(){
        RegistryEntry<Biome> biome = this.mob.getEntityWorld().getBiome(this.mob.getBlockPos());
        return this.mob.isOnGround() && (biome.matchesKey(BiomeKeys.LUKEWARM_OCEAN) ||  biome.matchesKey(BiomeKeys.DEEP_LUKEWARM_OCEAN));
    }

    public BlockPos getTargetPos(){
        return searchCoralReef();
    }


    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return false;
    }

    public void tick(){
        BlockPos blockPos = this.getTargetPos();
        if (!blockPos.isWithinDistance(this.mob.getEntityPos(), this.getDesiredDistanceToTarget())) {
            this.reached = false;
            ++this.tryingTime;
            if (this.shouldResetPath()) {
                this.mob.getNavigation().startMovingTo((double)blockPos.getX() + (double)0.5F, (double)blockPos.getY(), (double)blockPos.getZ() + (double)0.5F, this.speed);
            }
        } else {
            this.reached = true;
            --this.tryingTime;
        }
    }



}
