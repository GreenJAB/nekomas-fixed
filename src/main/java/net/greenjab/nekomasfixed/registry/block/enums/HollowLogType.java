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
    MANGROVE(Blocks.MANGROVE_LOG, BlockRegistry.HOLLOW_MANGROVE_LOG),
    CHERRY(Blocks.CHERRY_LOG, BlockRegistry.HOLLOW_CHERRY_LOG),
    BAMBOO(Blocks.BAMBOO_BLOCK, BlockRegistry.HOLLOW_BAMBOO_BLOCK),
    CRIMSON(Blocks.CRIMSON_HYPHAE, BlockRegistry.HOLLOW_CRIMSON_STEM),
    WARPED(Blocks.WARPED_HYPHAE, BlockRegistry.HOLLOW_WARPED_STEM),
    BAOBAB(BlockRegistry.BAOBAB_LOG, BlockRegistry.HOLLOW_BAOBAB_LOG);

    private static final Map<Block, Block> BASE_TO_HOLLOW = new HashMap<>();
    // Stripping a hollow log with an axe yields its stripped variant. Both
    // share the same HollowLogBlockEntity type, so vanilla keeps the block
    // entity (and its stored contents) across the swap.
    private static final Map<Block, Block> HOLLOW_TO_STRIPPED = new HashMap<>();

    static {
        for (HollowLogType type : values()) {
            BASE_TO_HOLLOW.put(type.baseLog, type.hollowLog);
        }
    }

    static {
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_OAK_LOG, BlockRegistry.HOLLOW_STRIPPED_OAK_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_SPRUCE_LOG, BlockRegistry.HOLLOW_STRIPPED_SPRUCE_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_BIRCH_LOG, BlockRegistry.HOLLOW_STRIPPED_BIRCH_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_JUNGLE_LOG, BlockRegistry.HOLLOW_STRIPPED_JUNGLE_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_ACACIA_LOG, BlockRegistry.HOLLOW_STRIPPED_ACACIA_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_DARK_OAK_LOG, BlockRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_MANGROVE_LOG, BlockRegistry.HOLLOW_STRIPPED_MANGROVE_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_CHERRY_LOG, BlockRegistry.HOLLOW_STRIPPED_CHERRY_LOG);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_BAMBOO_BLOCK, BlockRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_CRIMSON_STEM, BlockRegistry.HOLLOW_STRIPPED_CRIMSON_STEM);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_WARPED_STEM, BlockRegistry.HOLLOW_STRIPPED_WARPED_STEM);
        HOLLOW_TO_STRIPPED.put(BlockRegistry.HOLLOW_BAOBAB_LOG, BlockRegistry.HOLLOW_STRIPPED_BAOBAB_LOG);
    }

    private final Block baseLog;
    private final Block hollowLog;

    HollowLogType(Block baseLog, Block hollowLog) {
        this.baseLog = baseLog;
        this.hollowLog = hollowLog;
    }

    public static Block getStrippedBlock(Block hollowLog) {
        return HOLLOW_TO_STRIPPED.get(hollowLog);
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