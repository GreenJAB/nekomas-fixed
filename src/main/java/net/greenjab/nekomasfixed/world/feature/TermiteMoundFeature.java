package net.greenjab.nekomasfixed.world.feature;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.entity.Termite.TermiteEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
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
                        boolean isSupported = world.getBlockState(pos.down()).isSolidBlock(world, pos.down()) && !world.getBlockState(pos.down()).isIn(BlockTags.REPLACEABLE);

                        if (isSurface && random.nextInt(4) == 0 && isSupported) {
                            world.setBlockState(pos, BlockRegistry.TERMITE_HIVE.getDefaultState(), 3);
                        } else if(isSupported){
                            world.setBlockState(pos, context.getConfig().toPlace().get(random, pos), 3);
                        }
                    }
                }
            }
        }

        if (world instanceof ServerWorld serverWorld) {

            int count = 3 + random.nextInt(3);

            for (int i = 0; i < count; i++) {

                double angle = random.nextDouble() * Math.PI * 2;
                double distance = random.nextDouble() * 4;

                int dx = (int)(Math.cos(angle) * distance);
                int dz = (int)(Math.sin(angle) * distance);

                BlockPos spawnPos = start.add(dx, 0, dz);

                spawnPos = serverWorld.getTopPosition(
                        net.minecraft.world.Heightmap.Type.WORLD_SURFACE,
                        spawnPos
                );

                TermiteEntity termite = EntityTypeRegistry.TERMITE.create(serverWorld, SpawnReason.STRUCTURE);

                if (termite != null) {
                    termite.refreshPositionAndAngles(
                            spawnPos.getX() + 0.5,
                            spawnPos.getY(),
                            spawnPos.getZ() + 0.5,
                            random.nextFloat() * 360F,
                            0
                    );

                    termite.initialize(
                            serverWorld,
                            serverWorld.getLocalDifficulty(spawnPos),
                            SpawnReason.STRUCTURE,
                            null
                    );


                    serverWorld.spawnEntity(termite);
                }
            }
        }
        return true;
    }


}
