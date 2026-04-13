package net.greenjab.nekomasfixed.registry.block.enums;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;

import java.util.HashMap;
import java.util.Map;


public enum HollowLogType {

    OAK(Blocks.OAK_LOG, BlockRegistry.HOLLOW_OAK_LOG),
    BIRCH(Blocks.BIRCH_LOG, BlockRegistry.HOLLOW_BIRCH_LOG),
    JUNGLE(Blocks.JUNGLE_LOG, BlockRegistry.HOLLOW_JUNGLE_LOG),
    SPRUCE(Blocks.SPRUCE_LOG, BlockRegistry.HOLLOW_SPRUCE_LOG),
    ACACIA(Blocks.ACACIA_LOG, BlockRegistry.HOLLOW_ACACIA_LOG),
    CHERRY(Blocks.CHERRY_LOG, BlockRegistry.HOLLOW_CHERRY_LOG),
    PALE_OAK(Blocks.PALE_OAK_LOG, BlockRegistry.HOLLOW_PALE_OAK_LOG),
    DARK_OAK(Blocks.DARK_OAK_LOG, BlockRegistry.HOLLOW_DARK_OAK_LOG),
    CRIMSON(Blocks.CRIMSON_HYPHAE, BlockRegistry.HOLLOW_CRIMSON_LOG),
    WARPED(Blocks.WARPED_HYPHAE, BlockRegistry.HOLLOW_WARPED_LOG);

    private final Block baseLog;
    private final Block hollowLog;

    private static final Map<Block, Block> BASE_TO_HOLLOW = new HashMap<>();
    private static final Map<Block, Block> HOLLOW_TO_BASE = new HashMap<>();

    static {
        for (HollowLogType type : values()) {
            BASE_TO_HOLLOW.put(type.baseLog, type.hollowLog);
            HOLLOW_TO_BASE.put(type.hollowLog, type.baseLog);
        }
    }

    HollowLogType(Block baseLog, Block hollowLog) {
        this.baseLog = baseLog;
        this.hollowLog = hollowLog;
    }

    public static Block getHollowBlock(Block baseLog) {
        return BASE_TO_HOLLOW.getOrDefault(baseLog, Blocks.ACACIA_LOG);
    }

    public static BlockState getHollowState(Block baseLog) {
        BlockState baseState = baseLog.getDefaultState();
        BlockState hollowState = getHollowBlock(baseLog).getDefaultState();

        if (baseState.contains(PillarBlock.AXIS)) {
            return hollowState.with(PillarBlock.AXIS, baseState.get(PillarBlock.AXIS));
        }
        return hollowState;
    }
}