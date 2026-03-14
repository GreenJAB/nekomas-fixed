package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
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

}