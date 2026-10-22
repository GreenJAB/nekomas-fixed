package net.greenjab.nekomasfixed.registry.block.enums;

import net.greenjab.nekomasfixed.registry.block.HollowLogBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import java.util.HashMap;
import java.util.Map;


public enum HollowLogType {

    OAK(Blocks.OAK_LOG, BlockRegistry.HOLLOW_OAK_LOG),
    SPRUCE(Blocks.SPRUCE_LOG, BlockRegistry.HOLLOW_SPRUCE_LOG),
    BIRCH(Blocks.BIRCH_LOG, BlockRegistry.HOLLOW_BIRCH_LOG),
    JUNGLE(Blocks.JUNGLE_LOG, BlockRegistry.HOLLOW_JUNGLE_LOG),
    ACACIA(Blocks.ACACIA_LOG, BlockRegistry.HOLLOW_ACACIA_LOG),
    DARK_OAK(Blocks.DARK_OAK_LOG, BlockRegistry.HOLLOW_DARK_OAK_LOG),
    MANGROVE(Blocks.CHERRY_LOG, BlockRegistry.HOLLOW_MANGROVE_LOG),
    CHERRY(Blocks.CHERRY_LOG, BlockRegistry.HOLLOW_CHERRY_LOG),
    PALE_OAK(Blocks.PALE_OAK_LOG, BlockRegistry.HOLLOW_PALE_OAK_LOG),
    POPLAR(Blocks.POPLAR_LOG, BlockRegistry.HOLLOW_POPLAR_LOG),
    BAMBOO(Blocks.PALE_OAK_LOG, BlockRegistry.HOLLOW_BAMBOO_BLOCK),
    CRIMSON(Blocks.CRIMSON_HYPHAE, BlockRegistry.HOLLOW_CRIMSON_STEM),
    WARPED(Blocks.WARPED_HYPHAE, BlockRegistry.HOLLOW_WARPED_STEM),
    BAOBAB(BlockRegistry.BAOBAB_LOG, BlockRegistry.HOLLOW_BAOBAB_LOG),

    STRIPPED_OAK(Blocks.STRIPPED_OAK_LOG, BlockRegistry.STRIPPED_HOLLOW_OAK_LOG),
    STRIPPED_SPRUCE(Blocks.STRIPPED_SPRUCE_LOG, BlockRegistry.STRIPPED_HOLLOW_SPRUCE_LOG),
    STRIPPED_BIRCH(Blocks.STRIPPED_BIRCH_LOG, BlockRegistry.STRIPPED_HOLLOW_BIRCH_LOG),
    STRIPPED_JUNGLE(Blocks.STRIPPED_JUNGLE_LOG, BlockRegistry.STRIPPED_HOLLOW_JUNGLE_LOG),
    STRIPPED_ACACIA(Blocks.STRIPPED_ACACIA_LOG, BlockRegistry.STRIPPED_HOLLOW_ACACIA_LOG),
    STRIPPED_DARK_OAK(Blocks.STRIPPED_DARK_OAK_LOG, BlockRegistry.STRIPPED_HOLLOW_DARK_OAK_LOG),
    STRIPPED_MANGROVE(Blocks.STRIPPED_CHERRY_LOG, BlockRegistry.STRIPPED_HOLLOW_MANGROVE_LOG),
    STRIPPED_CHERRY(Blocks.STRIPPED_CHERRY_LOG, BlockRegistry.STRIPPED_HOLLOW_CHERRY_LOG),
    STRIPPED_PALE_OAK(Blocks.STRIPPED_PALE_OAK_LOG, BlockRegistry.STRIPPED_HOLLOW_PALE_OAK_LOG),
    STRIPPED_POPLAR(Blocks.STRIPPED_POPLAR_LOG, BlockRegistry.STRIPPED_HOLLOW_POPLAR_LOG),
    STRIPPED_BAMBOO(Blocks.STRIPPED_PALE_OAK_LOG, BlockRegistry.STRIPPED_HOLLOW_BAMBOO_BLOCK),
    STRIPPED_CRIMSON(Blocks.STRIPPED_CRIMSON_HYPHAE, BlockRegistry.STRIPPED_HOLLOW_CRIMSON_STEM),
    STRIPPED_WARPED(Blocks.STRIPPED_WARPED_HYPHAE, BlockRegistry.STRIPPED_HOLLOW_WARPED_STEM),
    STRIPPED_BAOBAB(BlockRegistry.STRIPPED_BAOBAB_LOG, BlockRegistry.STRIPPED_HOLLOW_BAOBAB_LOG);

    private final Block baseLog;
    private final Block hollowLog;

    private static final Map<Block, Block> BASE_TO_HOLLOW = new HashMap<>();

    static {
        for (HollowLogType type : values()) {
            BASE_TO_HOLLOW.put(type.baseLog, type.hollowLog);
        }
    }

    HollowLogType(Block baseLog, Block hollowLog) {
        this.baseLog = baseLog;
        this.hollowLog = hollowLog;
    }

    public static Block getHollowBlock(Block baseLog) {
        return BASE_TO_HOLLOW.getOrDefault(baseLog, Blocks.AIR);
    }

    public static BlockState getHollowState(BlockState baseLog) {
        BlockState hollowState = getHollowBlock(baseLog.getBlock()).defaultBlockState();
        if (!hollowState.is(Blocks.AIR) && hollowState.hasProperty(RotatedPillarBlock.AXIS)) {
            return hollowState.setValue(HollowLogBlock.AXIS, baseLog.getValue(RotatedPillarBlock.AXIS));
        }
        return hollowState;
    }
}