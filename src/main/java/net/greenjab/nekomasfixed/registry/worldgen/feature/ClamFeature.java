package net.greenjab.nekomasfixed.registry.worldgen.feature;

import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

public class ClamFeature implements Feature {

	public static final MapCodec<ClamFeature> MAP_CODEC = MapCodec.unit(ClamFeature::new);

	public ClamFeature() {
	}

	@Override
	public MapCodec<? extends Feature> codec() {
		return MAP_CODEC;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos blockPos) {
		int i = 0;
		int j = 4;

		for (int k = 0; k < j; k++) {
			int l = random.nextInt(8) - random.nextInt(8);
			int m = random.nextInt(8) - random.nextInt(8);
			int n = level.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + l, blockPos.getZ() + m);
			BlockPos blockPos2 = new BlockPos(blockPos.getX() + l, n, blockPos.getZ() + m);
			Block clamType = getClam(level.getRandom().nextFloat());
			BlockState blockState = clamType.defaultBlockState()
					.setValue(ClamBlock.WATERLOGGED, true)
					.setValue(ClamBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));

			if (level.getBlockState(blockPos2).is(Blocks.WATER) &&
					level.getBlockState(blockPos2.above()).is(Blocks.WATER) &&
					level.getBlockState(blockPos2.below()).is(Blocks.SAND) &&
					blockState.canSurvive(level, blockPos2)) {
				level.setBlock(blockPos2, blockState, Block.UPDATE_CLIENTS);
				level.getBlockEntity(blockPos2, BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY)
						.ifPresent(blockEntity -> {
							LootTable lootTable = level.getServer()
									.reloadableRegistries()
									.getLootTable(LootTableRegistry.CLAM_LOOT_TABLE);

							LootParams lootContextParameterSet = (new LootParams.Builder(level.getLevel()))
									.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos2))
									.withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
									.withParameter(LootContextParams.THIS_ENTITY, null)
									.withLuck(getLuck(clamType))
									.create(LootContextParamSets.FISHING);

							ObjectArrayList<ItemStack> loots = lootTable.getRandomItems(lootContextParameterSet);
							if (!loots.isEmpty()) blockEntity.setHeldStack(loots.getFirst());
						});
				i++;
			}
		}

		return i > 0;
	}

	@Unique
	private Block getClam(float rarity) {
		if (rarity > 0.5f) return BlockRegistry.CLAM;
		if (rarity > 0.25f) return BlockRegistry.CLAM_BLUE;
		if (rarity > 0.125f) return BlockRegistry.CLAM_PINK;
		if (rarity > 0.0625f) return BlockRegistry.CLAM_PURPLE;
		return BlockRegistry.CLAM;
	}

	public static int getLuck(Block clamType) {
		if (clamType == BlockRegistry.CLAM) return 0;
		if (clamType == BlockRegistry.CLAM_BLUE) return 1;
		if (clamType == BlockRegistry.CLAM_PINK) return 2;
		if (clamType == BlockRegistry.CLAM_PURPLE) return 3;
		return 0;
	}
}