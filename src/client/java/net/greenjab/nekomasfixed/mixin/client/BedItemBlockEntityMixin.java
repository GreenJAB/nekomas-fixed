package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// renderByItem builds the item entity from Blocks.RED_BED, so the BedRendererMixin's
// block check misses it. Give the renderer an entity carrying our bed instead.
@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BedItemBlockEntityMixin {

    @Unique
    private static final BedBlockEntity AMBER = bed(BlockRegistry.AMBER_BED);
    @Unique
    private static final BedBlockEntity AQUA = bed(BlockRegistry.AQUA_BED);
    @Unique
    private static final BedBlockEntity INDIGO = bed(BlockRegistry.INDIGO_BED);
    @Unique
    private static final BedBlockEntity MAROON = bed(BlockRegistry.MAROON_BED);

    @Unique
    private static BedBlockEntity bed(Block block) {
        return new BedBlockEntity(BlockPos.ZERO, block.defaultBlockState());
    }

    @ModifyVariable(
            method = "renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At("STORE"),
            ordinal = 0
    )
    private BlockEntity nekomasfixed$customAncientBedItemEntity(
            BlockEntity blockEntity, ItemStack stack, ItemDisplayContext displayContext,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (blockEntity instanceof BedBlockEntity && stack.getItem() instanceof BlockItem blockItem) {
            Block b = blockItem.getBlock();
            if (b == BlockRegistry.AMBER_BED) return AMBER;
            if (b == BlockRegistry.AQUA_BED) return AQUA;
            if (b == BlockRegistry.INDIGO_BED) return INDIGO;
            if (b == BlockRegistry.MAROON_BED) return MAROON;
        }
        return blockEntity;
    }
}