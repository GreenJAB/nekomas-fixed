package net.greenjab.nekomasfixed.registry.woldgen.tree;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BoababTrunkPlacer extends TrunkPlacer {
    public BoababTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final MapCodec<BoababTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    fillTrunkPlacerFields(instance).apply(instance, BoababTrunkPlacer::new)
            );


    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacers.BOABAB_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        System.out.println("Generating Baobab Tree");
        BlockPos blockPos = startPos.down();
        setToDirt(world, replacer, random, blockPos, config);
        Direction direction = Direction.Type.HORIZONTAL.random(random);

        int girthRadius = 3 ;
        int thickLowerPart = height/3 - 2;
        int lowerPart = height / 3;
        int midPart = height / 3 + lowerPart;
        int upperPart = height / 3 +midPart ;
        int x,y,z = 0;// current<DIMENSION> values

        for (y = 0; y < height-1; y++) {

            float t = (float) y / height;
            int r = Math.max(2, (int)(girthRadius * (1.0f - t)));
            for (x = -r-3; x <= r+3; x++) {
                for (z = -r-3; z <= r+3; z++) {

                    double angle = Math.atan2(z, x);
                    double ridges = Math.sin(angle * 5) * 1.5;
                    double verticalNoise = Math.sin(y * 0.3) * 0.5;
                    int localR = (int)(r + ridges + verticalNoise);
                    double dist = Math.sqrt(x*x + z*z);

                    if (dist <= localR) {
                        BlockPos pos = startPos.add(x, y, z);
                        this.getAndSetState(world, replacer, random, pos, config);
                    }
                }
            }
        }

        list.add(new FoliagePlacer.TreeNode(startPos.up(height), 0, false));
        return list;
    }
}
