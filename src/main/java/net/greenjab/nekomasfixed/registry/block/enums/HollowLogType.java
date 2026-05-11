package net.greenjab.nekomasfixed.registry.block.enums;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;


public enum HollowLogType implements StringIdentifiable {

    OAK("oak", Blocks.OAK_LOG, BlockRegistry.HOLLOW_OAK_LOG),
    BIRCH("birch", Blocks.BIRCH_LOG, BlockRegistry.HOLLOW_BIRCH_LOG),
    JUNGLE("jungle", Blocks.JUNGLE_LOG, BlockRegistry.HOLLOW_JUNGLE_LOG),
    SPRUCE("spruce", Blocks.SPRUCE_LOG, BlockRegistry.HOLLOW_SPRUCE_LOG),
    ACACIA("acacia", Blocks.ACACIA_LOG, BlockRegistry.HOLLOW_ACACIA_LOG),
    CHERRY("cherry", Blocks.CHERRY_LOG, BlockRegistry.HOLLOW_CHERRY_LOG),
    PALE_OAK("pale_oak", Blocks.PALE_OAK_LOG, BlockRegistry.HOLLOW_PALE_OAK_LOG),
    DARK_OAK("dark_oak", Blocks.DARK_OAK_LOG, BlockRegistry.HOLLOW_DARK_OAK_LOG),
    CRIMSON("crimson", Blocks.CRIMSON_HYPHAE, BlockRegistry.HOLLOW_CRIMSON_LOG),
    WARPED("warped", Blocks.WARPED_HYPHAE, BlockRegistry.HOLLOW_WARPED_LOG);

    private final String name;
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

    public static final Codec<HollowLogType> CODEC = StringIdentifiable.createCodec(HollowLogType::values);

    HollowLogType(final String name, Block baseLog, Block hollowLog) {
        this.name = name;
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

    @Override
    public String asString() {
        return "";
    }
}