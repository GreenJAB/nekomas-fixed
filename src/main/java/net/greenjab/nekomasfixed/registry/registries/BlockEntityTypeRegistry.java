package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.entity.*;
import net.greenjab.nekomasfixed.registry.block.entity.TermiteHiveBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class BlockEntityTypeRegistry {

    public static final BlockEntityType<ClamBlockEntity> CLAM_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("clam"),
            FabricBlockEntityTypeBuilder.create(ClamBlockEntity::new, BlockRegistry.CLAM, BlockRegistry.CLAM_BLUE, BlockRegistry.CLAM_PINK, BlockRegistry.CLAM_PURPLE).build());

    public static final BlockEntityType<NautilusBlockEntity> NAUTILUS_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("nautilus"),
            FabricBlockEntityTypeBuilder.create(NautilusBlockEntity::new, BlockRegistry.NAUTILUS_BLOCK, BlockRegistry.ZOMBIE_NAUTILUS_BLOCK, BlockRegistry.CORAL_NAUTILUS_BLOCK).build());

    public static final BlockEntityType<ClockBlockEntity> CLOCK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("clock"),
            FabricBlockEntityTypeBuilder.create(ClockBlockEntity::new, BlockRegistry.CLOCK, BlockRegistry.WALL_CLOCK).build());

    public static final BlockEntityType<EndermanHeadBlockEntity> ENDERMAN_HEAD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("enderman_head"),
            FabricBlockEntityTypeBuilder.create(EndermanHeadBlockEntity::new, BlockRegistry.ENDERMAN_HEAD, BlockRegistry.WALL_ENDERMAN_HEAD).build());

    public static final BlockEntityType<KilnBlockEntity> KILN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("kiln"),
            FabricBlockEntityTypeBuilder.create(KilnBlockEntity::new, BlockRegistry.KILN).build());

    public static final BlockEntityType<TermiteHiveBlockEntity> TERMITE_HIVE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("termite_hive"),
            FabricBlockEntityTypeBuilder.create(TermiteHiveBlockEntity::new, BlockRegistry.TERMITE_HIVE).build());

    public static final BlockEntityType<SoupCauldronBlockEntity> SOUP_CAULDRON_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("soup_cauldron"),
            FabricBlockEntityTypeBuilder.create(SoupCauldronBlockEntity::new, BlockRegistry.SOUP_CAULDRON).build());

    public static final BlockEntityType<HollowLogBlockEntity> HOLLOW_LOG_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("hollow_log"),
            FabricBlockEntityTypeBuilder.create(HollowLogBlockEntity::new, BlockRegistry.HOLLOW_OAK_LOG,
                    BlockRegistry.HOLLOW_ACACIA_LOG, BlockRegistry.HOLLOW_BIRCH_LOG, BlockRegistry.HOLLOW_CHERRY_LOG,
                    BlockRegistry.HOLLOW_CRIMSON_STEM, BlockRegistry.HOLLOW_DARK_OAK_LOG, BlockRegistry.HOLLOW_JUNGLE_LOG,
                    BlockRegistry.HOLLOW_PALE_OAK_LOG, BlockRegistry.HOLLOW_SPRUCE_LOG, BlockRegistry.HOLLOW_WARPED_STEM
            ).build());

    public static final BlockEntityType<StackedCakeBlockEntity> STACKED_CAKE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("stacked_strawberry_cake"),
            FabricBlockEntityTypeBuilder.create(StackedCakeBlockEntity::new, BlockRegistry.STACKED_CAKES).build());


    public static void registerBlockEntityType() {
        System.out.println("register BlockEntityType");
    }


}
