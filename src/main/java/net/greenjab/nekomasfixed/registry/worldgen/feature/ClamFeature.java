package net.greenjab.nekomasfixed.registry.worldgen.feature;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.greenjab.nekomasfixed.registry.block.ClamBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.LootTableRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class ClamFeature extends Feature<CountConfiguration> {

    public ClamFeature(Codec<CountConfiguration> codec) {
        super(codec);
    }

    public static int getLuck(Block clamType) {
        if (clamType == BlockRegistry.CLAM) return 0;
        if (clamType == BlockRegistry.CLAM_BLUE) return 1;
        if (clamType == BlockRegistry.CLAM_PINK) return 2;
        if (clamType == BlockRegistry.CLAM_PURPLE) return 3;
        return 0;
    }

    @Override
    public boolean place(FeaturePlaceContext<CountConfiguration> context) {
        int placed = 0;
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        int count = context.config().count().sample(random);

        for (int i = 0; i < count; i++) {
            int dx = random.nextInt(8) - random.nextInt(8);
            int dz = random.nextInt(8) - random.nextInt(8);
            int y = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX() + dx, origin.getZ() + dz);
            BlockPos pos = new BlockPos(origin.getX() + dx, y, origin.getZ() + dz);
            Block clamType = getClam(level.getRandom().nextFloat());
            BlockState blockState = clamType.defaultBlockState()
                    .setValue(ClamBlock.WATERLOGGED, true)
                    .setValue(ClamBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));

            if (level.getBlockState(pos).is(Blocks.WATER)
                    && level.getBlockState(pos.above()).is(Blocks.WATER)
                    && level.getBlockState(pos.below()).is(Blocks.SAND)
                    && blockState.canSurvive(level, pos)) {
                level.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
                level.getBlockEntity(pos, BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY)
                        .ifPresent(blockEntity -> {
                            LootTable lootTable = Objects.requireNonNull(level.getServer())
                                    .reloadableRegistries()
                                    .getLootTable(LootTableRegistry.CLAM_LOOT_TABLE);

                            LootParams lootParams = (new LootParams.Builder(level.getLevel()))
                                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                                    .withLuck(getLuck(clamType))
                                    .create(LootContextParamSets.FISHING);

                            ObjectArrayList<ItemStack> loots = lootTable.getRandomItems(lootParams);
                            if (!loots.isEmpty()) {
                                blockEntity.setHeldStack(loots.getFirst());
                            }
                        });
                placed++;
            }
        }
        return placed > 0;
    }

    private Block getClam(float rarity) {
        if (rarity > 0.5F) return BlockRegistry.CLAM;
        if (rarity > 0.25F) return BlockRegistry.CLAM_BLUE;
        if (rarity > 0.125F) return BlockRegistry.CLAM_PINK;
        if (rarity > 0.0625F) return BlockRegistry.CLAM_PURPLE;
        return BlockRegistry.CLAM;
    }
}
