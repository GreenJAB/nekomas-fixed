package net.greenjab.nekomasfixed.registry.worldgen.feature;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class GeyserBlockFeature extends Feature<SimpleBlockConfiguration> {
    public GeyserBlockFeature(Codec<SimpleBlockConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {

        WorldGenLevel world = context.level();
        BlockPos start = context.origin();
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
