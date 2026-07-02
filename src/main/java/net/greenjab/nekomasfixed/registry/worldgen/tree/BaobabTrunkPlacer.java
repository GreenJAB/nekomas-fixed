package net.greenjab.nekomasfixed.registry.worldgen.tree;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import java.util.List;
import java.util.function.BiConsumer;

public class BaobabTrunkPlacer extends TrunkPlacer {
    public BaobabTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final MapCodec<BaobabTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    fillTrunkPlacerFields(instance).apply(instance, BaobabTrunkPlacer::new));

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacers.BAOBAB_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        boolean water = false;
        if (world instanceof ChunkRegion chunkRegion)
            if (!chunkRegion.toServerWorld().getEnvironmentAttributes().getAttributeValue(EnvironmentAttributes.WATER_EVAPORATES_GAMEPLAY, startPos))
                water = random.nextBoolean();
        int x,y,z;
        float X = random.nextFloat()-0.5f;
        float Z = random.nextFloat()-0.5f;

        //"roots"
        for (y = -4; y < 0; y++) {
            float r = 3.5f -1.66f * (y / (height + 0f));
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
            float r = 3.5f -1.66f * (y / (height + 0f));
            for (x = -4; x <= 4; x++) {
                for (z = -4; z <= 4; z++) {
                    float distSq = (x - X) * (x - X) + (z - Z) * (z - Z);
                    if (distSq <= r*r) {
                        if (distSq >= (r - 1) * (r - 1)) {
                            BlockPos pos = startPos.add(x, y, z);
                            this.getAndSetState(world, replacer, random, pos, config);
                        } else if (water && y < 3) {
                            BlockPos pos = startPos.add(x, y, z);
                            if (world.testBlockState(pos,  state -> state.isIn(BlockTags.REPLACEABLE))) {
                                replacer.accept(pos, Blocks.WATER.getDefaultState());
                            }
                        }
                    }
                }
            }
        }

        //branches
        int b = random.nextInt(2)+5;
        for (int i  = 0;i<b;i++) {
            float rot =random.nextFloat()*40+i*360/(b+0f);
            float dx = startPos.getX()+(float) Math.sin(rot*Math.PI/180f);
            float dz = startPos.getZ()+(float) Math.cos(rot*Math.PI/180f);

            int by = height - random.nextInt(5) - 2;
            for (int length = 4 + random.nextInt(4); length >= 0; length--) {
                int dy = startPos.getY()+by+(length <3?3- length :0);
                rot+=random.nextFloat()*30-15;
                dx += (float) Math.sin(rot*Math.PI/180f);
                dz += (float) Math.cos(rot*Math.PI/180f);
                BlockPos pos = new BlockPos((int) dx, dy, (int) dz);
                if (world.testBlockState(pos,  state -> state.isIn(BlockTags.REPLACEABLE))) {
                    if (length < 3) replacer.accept(pos, BlockRegistry.BAOBAB_LOG.getDefaultState());
                    else replacer.accept(pos, BlockRegistry.BAOBAB_LOG.getDefaultState().with(PillarBlock.AXIS, Direction.fromHorizontalDegrees(rot).getAxis()));
                }
                if (length == 0) {
                    BlockPos leafPos = pos.up(1);
                    list.add(new FoliagePlacer.TreeNode(leafPos, 0, true));
                }
            }
        }

        return list;
    }
}
