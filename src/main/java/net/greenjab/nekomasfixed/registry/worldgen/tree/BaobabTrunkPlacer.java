package net.greenjab.nekomasfixed.registry.worldgen.tree;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.BiConsumer;

public class BaobabTrunkPlacer extends TrunkPlacer {
    public BaobabTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final MapCodec<BaobabTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance).apply(instance, BaobabTrunkPlacer::new));

    @Override
    protected @NonNull TrunkPlacerType<?> type() {
        return ModTrunkPlacers.BAOBAB_TRUNK_PLACER;
    }

    @Override
    public @NonNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NonNull LevelSimulatedReader world, @NonNull BiConsumer<BlockPos, BlockState> replacer, @NonNull RandomSource random, int height, @NonNull BlockPos startPos, @NonNull TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        boolean water = false;
        if (world instanceof WorldGenRegion chunkRegion)
            if (!chunkRegion.getLevel().environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, startPos))
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
                        BlockPos pos = startPos.offset(x, y, z);
                        this.placeLog(world, replacer, random, pos, config);
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
                            BlockPos pos = startPos.offset(x, y, z);
                            this.placeLog(world, replacer, random, pos, config);
                        } else if (water && y < 3) {
                            BlockPos pos = startPos.offset(x, y, z);
                            if (world.isStateAtPosition(pos,  state -> state.is(BlockTags.REPLACEABLE))) {
                                replacer.accept(pos, Blocks.WATER.defaultBlockState());
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
                if (world.isStateAtPosition(pos,  state -> state.is(BlockTags.REPLACEABLE))) {
                    if (length < 3) replacer.accept(pos, BlockRegistry.BAOBAB_LOG.defaultBlockState());
                    else replacer.accept(pos, BlockRegistry.BAOBAB_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.fromYRot(rot).getAxis()));
                }
                if (length == 0) {
                    BlockPos leafPos = pos.above(1);
                    list.add(new FoliagePlacer.FoliageAttachment(leafPos, 0, true));
                }
            }
        }

        return list;
    }
}
