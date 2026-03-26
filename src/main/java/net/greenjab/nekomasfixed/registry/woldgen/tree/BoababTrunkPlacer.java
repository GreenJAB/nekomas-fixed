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
        setToDirt(world, replacer, random, blockPos.east(), config);
        setToDirt(world, replacer, random, blockPos.south(), config);
        setToDirt(world, replacer, random, blockPos.south().east(), config);
        Direction direction = Direction.Type.HORIZONTAL.random(random);

        //now getting into complicated stuff
        int girthRadius = 2 ; //min 3, max 4 (6-8 blocks in diameter)
//
//        int X = startPos.getX();
//        int startY = startPos.getY();
//        int Z = startPos.getZ();

        int lowerPart = height / 3;
        int midPart = height / 3;
        int upperPart = height / 3 -1 ;

        int r = girthRadius+1;
        int x,y,z = 0;

        int offsetX = 3;
        int offsetZ = 2;

        for (y = 0; y < height; y++) {

            // move toward center (0,0)
            if (offsetX > 0) offsetX--;
            if (offsetZ > 0) offsetZ--;

            for (x = -r; x <= r; x++) {
                for (z = -r; z <= r; z++) {
                    if (x*x + z*z <= r*r) {
                        BlockPos pos = startPos.add(x + offsetX, y, z + offsetZ);
                        this.getAndSetState(world, replacer, random, pos, config);
                    }
                }
            }

            // your radius logic
            if (y < lowerPart) {
                r = girthRadius + 1;
            } else if (y < midPart) {
                r = girthRadius;
            } else if (y < upperPart) {
                r = girthRadius - 1;
            }
        }


        list.add(new FoliagePlacer.TreeNode(startPos.up(height), 0, false));
        return list;
    }
}
