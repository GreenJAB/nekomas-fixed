package net.greenjab.nekomasfixed.world.feature;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class TermiteMoundFeature extends Feature<SimpleBlockFeatureConfig> {
    public TermiteMoundFeature(Codec<SimpleBlockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        int height = random.nextInt(2)+4;
        BlockPos start = context.getOrigin();
        BlockPos.Mutable currentPos = start.mutableCopy();
        int x,y,z;

        for (y = 0; y < height - 1; y++) {
            float r = 1.5f - 0.33f * (y / (height + 0f));
            for (x = -4; x <= 4; x++) {
                for (z = -4; z <= 4; z++) {
                    float distSq = x * x + z * z;
                    if (distSq <= r * r) {
                        BlockPos pos = start.add(x, y, z);
                        world.setBlockState(pos, context.getConfig().toPlace().get(random, pos), 3);

                    }
                }
            }
        }
        return true;
    }


//    @Unique
//    private static boolean isExposedToAir(World world, BlockPos pos){
//        int airCount=0;
//        if(world.getBlockState(pos).+=1;}
//        return airCount>0;
//    }
}
