package net.greenjab.nekomasfixed.registry.worldgen.feature;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.TermitehiveBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public class TermiteMoundFeature implements Feature {

    public static final MapCodec<TermiteMoundFeature> MAP_CODEC = MapCodec.unit(TermiteMoundFeature::new);

    public TermiteMoundFeature() {
    }

    @Override
    public MapCodec<? extends Feature> codec() {
        return MAP_CODEC;
    }

    @Override
    public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos start) {
        int height = random.nextInt(2) + 6;

        int x, y, z;
        if (!world.getBlockState(start.below()).isRedstoneConductor(world, start.below())) return false;
        if (!world.getBlockState(start).isAir()) return false;

        float maxRadius = 3.5f - random.nextFloat() * 1.5f;

        for (y = 0; y < height - 2; y++) {
            float r = maxRadius * (1 - (y / (float) height)) - (y / (float) height);
            for (x = -(int) maxRadius; x <= maxRadius; x++) {
                for (z = -(int) maxRadius; z <= maxRadius; z++) {
                    float distSq = x * x + z * z;
                    if (distSq <= r * r) {
                        BlockPos pos = start.offset(x, y, z);

                        boolean isSurface = distSq >= (r - 1) * (r - 1);
                        boolean isSupported = world.getBlockState(pos.below()).isRedstoneConductor(world, pos.below()) && !world.getBlockState(pos.below()).is(BlockTags.REPLACEABLE);

                        if (isSurface && random.nextInt(4) == 0 && isSupported) {
                            world.setBlock(pos, BlockRegistry.TERMITE_HIVE.defaultBlockState(), 3);
                            world.getBlockEntity(pos, BlockEntityTypeRegistry.TERMITE_HIVE_BLOCK_ENTITY).ifPresent(blockEntity -> {
                                if (random.nextBoolean()) blockEntity.addTermite(TermitehiveBlockEntity.TermiteData.create(random.nextInt(599)));
                            });
                        } else if (isSupported) {
                            world.setBlock(pos, BlockRegistry.TERMITE_HIVE.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
        return true;
    }
}