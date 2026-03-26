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

import java.util.ArrayList;
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

        class Pillar {
            int dx, dz;
            int height;
            int radius;
        }

        List<Pillar> pillars = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Pillar p = new Pillar();

            double angle = random.nextDouble() * Math.PI * 2;

            int dist = girthRadius + random.nextInt(2); // outside trunk

            p.dx = (int)(Math.cos(angle) * dist);
            p.dz = (int)(Math.sin(angle) * dist);

            p.height = height - random.nextInt(6); // different heights
            p.radius = 1 + random.nextInt(2); // thickness

            pillars.add(p);
        }

        for (y = 0; y < height-1; y++) {

            int r = girthRadius;

            // BASE TRUNK
            for (x = -r; x <= r; x++) {
                for (z = -r; z <= r; z++) {
                    if (x*x + z*z <= r*r) {
                        BlockPos pos = startPos.add(x, y, z);
                        this.getAndSetState(world, replacer, random, pos, config);
                    }
                }
            }

            // ADD PILLARS
            for (Pillar p : pillars) {
                if (y < p.height) {
                    for (x = -p.radius; x <= p.radius; x++) {
                        for (z = -p.radius; z <= p.radius; z++) {
                            BlockPos pos = startPos.add(p.dx + x, y, p.dz + z);
                            this.getAndSetState(world, replacer, random, pos, config);
                        }
                    }
                }
            }
        }

        list.add(new FoliagePlacer.TreeNode(startPos.up(height), 0, false));
        return list;
    }
}
