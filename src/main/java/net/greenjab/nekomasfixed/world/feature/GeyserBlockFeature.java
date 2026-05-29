package net.greenjab.nekomasfixed.world.feature;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class GeyserBlockFeature extends Feature<SimpleBlockFeatureConfig> {
    public GeyserBlockFeature(Codec<SimpleBlockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {

        StructureWorldAccess world = context.getWorld();
        BlockPos start = context.getOrigin();
        if (!world.isAir(start) || world.isAir(start.down())) {
            return false;
        }

        boolean adjacentToTerrain = false;

        for (Direction dir : Direction.Type.HORIZONTAL) {
            BlockPos pos = start.offset(dir);

            if (world.getBlockState(pos).isSolidBlock(world, pos)) {
                adjacentToTerrain = true;
                break;
            }
        }

        if (!adjacentToTerrain) {
            return false;
        }



        world.setBlockState(start.down(), BlockRegistry.GEYSER.getDefaultState(), 3);
        return true;
    }
}
