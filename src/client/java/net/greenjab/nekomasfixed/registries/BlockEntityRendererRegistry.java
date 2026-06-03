package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.render.block.entity.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BlockEntityRendererRegistry {

    public static void registerBlockEntityRenderer() {
        System.out.println("register BlockEntityRenderer");
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY, ClamBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, ClockBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, EndermanHeadBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.HOLLOW_LOG_BLOCK_ENTITY, HollowLogBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY,SoupCauldronBlockEntityRenderer::new);
    }
}
