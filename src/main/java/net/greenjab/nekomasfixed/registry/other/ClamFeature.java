package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.greenjab.nekomasfixed.registry.block.ClamBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Unique;

public class ClamFeature extends Feature<CountConfig> {
	public ClamFeature(Codec<CountConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<CountConfig> context) {
		int i = 0;
		Random random = context.getRandom();
		StructureWorldAccess world = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		int j = context.getConfig().getCount().get(random);

		for (int k = 0; k < j; k++) {
			int l = random.nextInt(8) - random.nextInt(8);
			int m = random.nextInt(8) - random.nextInt(8);
			int n = world.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + l, blockPos.getZ() + m);
			BlockPos blockPos2 = new BlockPos(blockPos.getX() + l, n, blockPos.getZ() + m);
			Block clamType =  getClam(world.getRandom().nextFloat());
			BlockState blockState =clamType.getDefaultState().with(ClamBlock.WATERLOGGED, true).with(ClamBlock.FACING, Direction.Type.HORIZONTAL.random(random));
			if (world.getBlockState(blockPos2).isOf(Blocks.WATER) &&
					world.getBlockState(blockPos2.up()).isOf(Blocks.WATER) &&
					world.getBlockState(blockPos2.down()).isOf(Blocks.SAND) &&
					blockState.canPlaceAt(world, blockPos2)) {
				world.setBlockState(blockPos2, blockState, Block.NOTIFY_LISTENERS);
				world.getBlockEntity(blockPos2, BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY)
						.ifPresent(blockEntity -> {
							LootTable lootTable = world.getServer()
									.getReloadableRegistries()
									.getLootTable(OtherRegistry.CLAM_LOOT_TABLE);

							LootWorldContext lootContextParameterSet = (new LootWorldContext.Builder(world.toServerWorld())).add(LootContextParameters.ORIGIN, blockPos2.toCenterPos()).add(LootContextParameters.TOOL, null).add(LootContextParameters.THIS_ENTITY, null).luck(getLuck(clamType)).build(LootContextTypes.FISHING);

							ObjectArrayList<ItemStack> loots = lootTable.generateLoot(lootContextParameterSet);
							if (!loots.isEmpty()) blockEntity.setHeldStack(loots.get(0));
						});
				i++;
			}
		}

		return i > 0;
	}

	@Unique
	private Block getClam(float rarity) {
		if (rarity>0.5) return BlockRegistry.CLAM;
		if (rarity>0.25) return BlockRegistry.CLAM_BLUE;
		if (rarity>0.125) return BlockRegistry.CLAM_PINK;
		if (rarity>0.0625) return BlockRegistry.CLAM_PURPLE;
		return BlockRegistry.CLAM;
	}

	public static int getLuck(Block clamType) {
		if (clamType==BlockRegistry.CLAM) return 0;
		if (clamType==BlockRegistry.CLAM_BLUE) return 1;
		if (clamType==BlockRegistry.CLAM_PINK) return 2;
		if (clamType==BlockRegistry.CLAM_PURPLE) return 3;
		return 0;
	}
}
