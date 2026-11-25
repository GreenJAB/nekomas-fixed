package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.mixin.BlockEntityTypeAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class BlockEntityRegistry {

    public static void attachBlockEntities() {
        attachSignBlockEntities();
        attachBedBlockEntities();
        attachBannerBlockEntities();
        attachShulkerBoxBlockEntities();
    }

    private static void attachSignBlockEntities() {
        ((BlockEntityTypeAccessor) BlockEntityType.SIGN)
                .nekomasfixed$getValidBlocks()
                .addAll(Set.of(
                        BlockRegistry.BAOBAB_SIGN,
                        BlockRegistry.BAOBAB_WALL_SIGN
                ));
        ((BlockEntityTypeAccessor) BlockEntityType.HANGING_SIGN)
                .nekomasfixed$getValidBlocks()
                .addAll(Set.of(
                        BlockRegistry.BAOBAB_HANGING_SIGN,
                        BlockRegistry.BAOBAB_WALL_HANGING_SIGN
                ));
    }

    // Ancient beds use vanilla BedBlock (getRenderShape ENTITYBLOCK_ANIMATED) so the vanilla
    // BedRenderer handles them; attach each to BlockEntityType.BED for the block-entity lookup.
    private static void attachBedBlockEntities() {
        ((BlockEntityTypeAccessor) BlockEntityType.BED)
                .nekomasfixed$getValidBlocks()
                .addAll(Set.of(
                        BlockRegistry.AMBER_BED,
                        BlockRegistry.AQUA_BED,
                        BlockRegistry.INDIGO_BED,
                        BlockRegistry.MAROON_BED
                ));
    }

    private static void attachBannerBlockEntities() {
        ((BlockEntityTypeAccessor) BlockEntityType.BANNER)
                .nekomasfixed$getValidBlocks()
                .addAll(Set.of(
                        BlockRegistry.AMBER_BANNER,
                        BlockRegistry.AQUA_BANNER,
                        BlockRegistry.INDIGO_BANNER,
                        BlockRegistry.MAROON_BANNER,
                        BlockRegistry.AMBER_WALL_BANNER,
                        BlockRegistry.AQUA_WALL_BANNER,
                        BlockRegistry.INDIGO_WALL_BANNER,
                        BlockRegistry.MAROON_WALL_BANNER
                ));
    }

    // Ancient shulker boxes use vanilla ShulkerBoxBlock (ENTITYBLOCK_ANIMATED) so the vanilla
    // ShulkerBoxRenderer handles them; attach each to BlockEntityType.SHULKER_BOX for storage.
    private static void attachShulkerBoxBlockEntities() {
        ((BlockEntityTypeAccessor) BlockEntityType.SHULKER_BOX)
                .nekomasfixed$getValidBlocks()
                .addAll(Set.of(
                        BlockRegistry.AMBER_SHULKER_BOX,
                        BlockRegistry.AQUA_SHULKER_BOX,
                        BlockRegistry.INDIGO_SHULKER_BOX,
                        BlockRegistry.MAROON_SHULKER_BOX
                ));
    }
}
