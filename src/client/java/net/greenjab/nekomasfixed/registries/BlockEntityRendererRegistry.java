package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.render.block.entity.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class BlockEntityRendererRegistry {

    public static void registerBlockEntityRenderer() {
        System.out.println("register BlockEntityRenderer");
        BlockEntityRenderers.register(BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY, ClamBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, ClockBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, EndermanHeadBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityTypeRegistry.HOLLOW_LOG_BLOCK_ENTITY, HollowLogBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY,SoupCauldronBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityTypeRegistry.STACKED_CAKE_BLOCK_ENTITY,StackedCakeBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityTypeRegistry.GOAT_HORN_BLOCK_ENTITY,GoatHornBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityTypeRegistry.TERRACOTTA_DECORATED_POT_BLOCK_ENTITY, TerracottaDecoratedPotBlockEntityRenderer::new);
    }
}
