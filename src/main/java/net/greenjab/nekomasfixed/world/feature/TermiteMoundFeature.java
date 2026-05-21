package net.greenjab.nekomasfixed.world.feature;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
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
        int height = random.nextInt(2)+6;
        BlockPos start = context.getOrigin();

        int x,y,z;
        if (!world.getBlockState(start.down()).isSolidBlock(world, start.down())) {
            return false;
        }
        if (!world.getBlockState(start).isAir()) {
            return false;
        }

        float maxRadius = 3.5f - random.nextFloat() * 1.5f;

        for (y = 0; y < height-2; y++) {
            float r = maxRadius * (1 - (y / (float) height) ) - (y/(float)height);
            for (x = -(int)maxRadius; x <= maxRadius; x++) {
                for (z = -(int)maxRadius; z <= maxRadius; z++) {
                    float distSq = x * x + z * z;
                    if (distSq <= r * r) {
                        BlockPos pos = start.add(x, y, z);

                        boolean isSurface = distSq >= (r - 1) * (r - 1);
                        boolean isSupported = !world.getBlockState(pos.down()).isSolidBlock(world, pos.down());

                        if (isSurface && random.nextInt(4) == 0 && isSupported) {
                            world.setBlockState(pos, BlockRegistry.TERMITE_HIVE.getDefaultState(), 3);
                        } else if(isSupported){
                            world.setBlockState(pos, context.getConfig().toPlace().get(random, pos), 3);
                        }
                    }
                }
            }
            r -= (float) (0.9 * Math.abs(MathHelper.sin(y)));
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
