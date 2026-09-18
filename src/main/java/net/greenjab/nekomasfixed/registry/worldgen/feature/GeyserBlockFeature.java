package net.greenjab.nekomasfixed.registry.worldgen.feature;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public class GeyserBlockFeature implements Feature {

    public static final MapCodec<GeyserBlockFeature> MAP_CODEC = MapCodec.unit(GeyserBlockFeature::new);

    public GeyserBlockFeature() {
    }

    @Override
    public MapCodec<? extends Feature> codec() {
        return MAP_CODEC;
    }

    @Override
    public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos start) {
        if (!world.isEmptyBlock(start) || world.isEmptyBlock(start.below())) return false;
        boolean adjacentToTerrain = false;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = start.relative(dir);
            if (world.getBlockState(pos).isRedstoneConductor(world, pos)) {
                adjacentToTerrain = true;
                break;
            }
        }
        if (!adjacentToTerrain) return false;
        world.setBlock(start.below(), BlockRegistry.GEYSER.defaultBlockState(), 3);
        return true;
    }
}