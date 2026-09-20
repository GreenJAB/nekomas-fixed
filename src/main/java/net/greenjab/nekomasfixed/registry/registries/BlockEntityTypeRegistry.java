package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.entity.ClamBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.EndermanHeadBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class BlockEntityTypeRegistry {

    @SuppressWarnings("DataFlowIssue")
    public static final BlockEntityType<ClamBlockEntity> CLAM_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, NekomasFixed.id("clam"),
                    new BlockEntityType<>(
                            ClamBlockEntity::new,
                            Set.of(BlockRegistry.CLAM, BlockRegistry.CLAM_BLUE, BlockRegistry.CLAM_PINK, BlockRegistry.CLAM_PURPLE),
                            null));

    @SuppressWarnings("DataFlowIssue")
    public static final BlockEntityType<HollowLogBlockEntity> HOLLOW_LOG_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, NekomasFixed.id("hollow_log"),
                    new BlockEntityType<>(
                            HollowLogBlockEntity::new,
                            Set.of(BlockRegistry.HOLLOW_OAK_LOG, BlockRegistry.HOLLOW_SPRUCE_LOG,
                                    BlockRegistry.HOLLOW_BIRCH_LOG, BlockRegistry.HOLLOW_JUNGLE_LOG,
                                    BlockRegistry.HOLLOW_ACACIA_LOG, BlockRegistry.HOLLOW_DARK_OAK_LOG,
                                    BlockRegistry.HOLLOW_MANGROVE_LOG, BlockRegistry.HOLLOW_CHERRY_LOG,
                                    BlockRegistry.HOLLOW_BAMBOO_BLOCK,
                                    BlockRegistry.HOLLOW_CRIMSON_STEM, BlockRegistry.HOLLOW_WARPED_STEM,
                                    BlockRegistry.HOLLOW_BAOBAB_LOG,
                                    BlockRegistry.HOLLOW_STRIPPED_OAK_LOG, BlockRegistry.HOLLOW_STRIPPED_SPRUCE_LOG,
                                    BlockRegistry.HOLLOW_STRIPPED_BIRCH_LOG, BlockRegistry.HOLLOW_STRIPPED_JUNGLE_LOG,
                                    BlockRegistry.HOLLOW_STRIPPED_ACACIA_LOG, BlockRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG,
                                    BlockRegistry.HOLLOW_STRIPPED_MANGROVE_LOG, BlockRegistry.HOLLOW_STRIPPED_CHERRY_LOG,
                                    BlockRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK,
                                    BlockRegistry.HOLLOW_STRIPPED_CRIMSON_STEM, BlockRegistry.HOLLOW_STRIPPED_WARPED_STEM,
                                    BlockRegistry.HOLLOW_STRIPPED_BAOBAB_LOG),
                            null));

    @SuppressWarnings("DataFlowIssue")
    public static final BlockEntityType<EndermanHeadBlockEntity> ENDERMAN_HEAD_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, NekomasFixed.id("enderman_head"),
                    new BlockEntityType<>(
                            EndermanHeadBlockEntity::new,
                            Set.of(BlockRegistry.ENDERMAN_HEAD, BlockRegistry.WALL_ENDERMAN_HEAD),
                            null));

    @SuppressWarnings("DataFlowIssue")
    public static final BlockEntityType<StackedCakeBlockEntity> STACKED_CAKE_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, NekomasFixed.id("stacked_cake"),
                    new BlockEntityType<>(
                            StackedCakeBlockEntity::new,
                            Set.of(BlockRegistry.SWEETBERRY_CAKE, BlockRegistry.PAN_CAKE, BlockRegistry.GLOWBERRY_CAKE,
                                    BlockRegistry.APPLE_CAKE, BlockRegistry.VANILLA_CAKE, BlockRegistry.COOKIE_CAKE,
                                    BlockRegistry.CHOCOLATE_CAKE, BlockRegistry.BEETROOT_CAKE),
                            null));

    @SuppressWarnings("DataFlowIssue")
    public static final BlockEntityType<ClockBlockEntity> CLOCK_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, NekomasFixed.id("clock"),
                    new BlockEntityType<>(
                            ClockBlockEntity::new,
                            Set.of(BlockRegistry.CLOCK, BlockRegistry.WALL_CLOCK),
                            null));

    public static void registerBlockEntityTypes() {
        NekomasFixed.LOGGER.info("Registering block entity types");
    }
}
