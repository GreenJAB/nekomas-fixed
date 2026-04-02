package net.greenjab.nekomasfixed.world.feature;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Unique;

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
        float size = 1.5f;

        for (y = 0; y < height - 1; y++) {
            float r = size - 0.33f * (y / (height + 0f));
            if(y==0){
                r = size + 1;
            }
            if(y>height-2){
                r = 1;
            }
            for (x = -2; x <= 2; x++) {
                for (z = -2; z <= 2; z++) {
                    float distSq = x * x + z * z;
                    if (distSq <= r * r) {
                        BlockPos pos = start.add(x, y, z);
                        boolean randomForHive = random.nextInt(3) == 0;
                        if(randomForHive && isExposedToAir(world.toServerWorld(), pos)){
                            world.setBlockState(pos, BlockRegistry.TERMITE_HIVE.getDefaultState(), 3);
                        }else {
                            world.setBlockState(pos, context.getConfig().toPlace().get(random, pos), 3);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Unique
    private static boolean isExposedToAir(World world, BlockPos pos) {
        for (var dir : Direction.values()) {
            if (world.getBlockState(pos.offset(dir)).isAir()) {
                return true;
            }
        }
        return false;
    }
}
