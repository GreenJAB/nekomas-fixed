package net.greenjab.nekomasfixed.registry.woldgen.tree;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
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
        setToDirt(world, replacer, random, startPos.down(), config);
        boolean isWaterInTrunk = random.nextInt(100) >= 50;

        int x,y,z;
        float X = random.nextFloat()-0.5f;
        float Z = random.nextFloat()-0.5f;

        //"roots"
        for (y = -4; y < 0; y++) {
            float r = -1.5f * (y / (height + 0f)) + 3.5f;
            for (x = -4; x <= 4; x++) {
                for (z = -4; z <= 4; z++) {
                    float distSq = (x - X) * (x - X) + (z - Z) * (z - Z)+ y*y;
                    if (distSq <= r * r && distSq >= (r - 1) * (r - 1)) {
                        BlockPos pos = startPos.add(x, y, z);
                        this.getAndSetState(world, replacer, random, pos, config);
                    }
                }
            }
        }

        //trunk
        for (y = 0; y < height-1; y++) {
            float r = 3.5f -2f * (y / (height + 0f));
            for (x = -4; x <= 4; x++) {
                for (z = -4; z <= 4; z++) {
                    float distSq = (x - X) * (x - X) + (z - Z) * (z - Z);
                    if(isWaterInTrunk && distSq <= r*r){
                        if (distSq >= (r - 1) * (r - 1)) {
                            BlockPos pos = startPos.add(x, y, z);
                            this.getAndSetState(world, replacer, random, pos, config);
                        }else if(y<3){
                            BlockPos pos = startPos.add(x, y, z);
                            replacer.accept(pos, Blocks.WATER.getDefaultState());
                        }

                    }
                    if (distSq <= r * r && distSq >= (r - 1) * (r - 1)) {
                        BlockPos pos = startPos.add(x, y, z);
                        this.getAndSetState(world, replacer, random, pos, config);
                    }
                }
            }
        }

        //branches
        int b = random.nextInt(2)+5;
        for (int i  = 0;i<b;i++) {
            float rot =random.nextFloat()*40+i*360/(b+0f);
            float k = startPos.getX()+(float) Math.sin(rot*Math.PI/180f)*0.5f;
            float l = startPos.getZ()+(float) Math.cos(rot*Math.PI/180f)*0.5f;

            int by = height - random.nextInt(5) - 2;
            int o = 4 + random.nextInt(4);
            for (; o > 0; o--) {
                int q = startPos.getY()+by+(o<3?3-o:0);
                k += (float) Math.sin(rot*Math.PI/180f);
                l += (float) Math.cos(rot*Math.PI/180f);
                rot+=random.nextFloat()*30-15;
                BlockPos pos = new BlockPos((int)k, q, (int)l);
                this.getAndSetState(world, replacer, random, pos, config);
                if (o==1) list.add(new FoliagePlacer.TreeNode(pos.up(1), 0, true));
            }
        }

        return list;
    }
}
