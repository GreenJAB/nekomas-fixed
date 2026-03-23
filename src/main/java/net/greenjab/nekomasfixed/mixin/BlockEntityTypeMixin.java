package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin{

    @Shadow
    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.BlockEntityFactory<? extends T> factory,
                                                                     Block... blocks) {
        return null;
    }

    @Redirect(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;", ordinal = 0), slice = @Slice( from = @At(value = "FIELD",
            target = "Lnet/minecraft/block/entity/BlockEntityType;SHULKER_BOX:Lnet/minecraft/block/entity/BlockEntityType;")))
    private static BlockEntityType<BedBlockEntity> sign(String id, BlockEntityType.BlockEntityFactory<? extends BedBlockEntity> factory, Block[] blocks) {
        return create("bed", BedBlockEntity::new,
                Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED,
                BlockRegistry.AMBER_BED, BlockRegistry.AQUA_BED, BlockRegistry.INDIGO_BED, BlockRegistry.CRIMSON_BED

        );
    }

    @Redirect(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;", ordinal = 0), slice = @Slice( from = @At(value = "FIELD",
            target = "Lnet/minecraft/block/entity/BlockEntityType;COMMAND_BLOCK:Lnet/minecraft/block/entity/BlockEntityType;")))
    private static BlockEntityType<ShulkerBoxBlockEntity> shulker(String id, BlockEntityType.BlockEntityFactory<? extends ShulkerBoxBlockEntity> factory, Block[] blocks) {
        return create("shulker_box",
                ShulkerBoxBlockEntity::new,
                Blocks.SHULKER_BOX,
                Blocks.BLACK_SHULKER_BOX,
                Blocks.BLUE_SHULKER_BOX,
                Blocks.BROWN_SHULKER_BOX,
                Blocks.CYAN_SHULKER_BOX,
                Blocks.GRAY_SHULKER_BOX,
                Blocks.GREEN_SHULKER_BOX,
                Blocks.LIGHT_BLUE_SHULKER_BOX,
                Blocks.LIGHT_GRAY_SHULKER_BOX,
                Blocks.LIME_SHULKER_BOX,
                Blocks.MAGENTA_SHULKER_BOX,
                Blocks.ORANGE_SHULKER_BOX,
                Blocks.PINK_SHULKER_BOX,
                Blocks.PURPLE_SHULKER_BOX,
                Blocks.RED_SHULKER_BOX,
                Blocks.WHITE_SHULKER_BOX,
                Blocks.YELLOW_SHULKER_BOX,
                BlockRegistry.AMBER_SHULKER_BOX, BlockRegistry.AQUA_SHULKER_BOX, BlockRegistry.INDIGO_SHULKER_BOX, BlockRegistry.CRIMSON_SHULKER_BOX

        );
    }

}