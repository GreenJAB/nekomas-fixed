package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.ClockBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.EndermanHeadBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BlockEntityRendererRegistry {

    public static void registerBlockEntityRenderer() {
        System.out.println("register BlockEntityRenderer");
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY, ClamBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, ClockBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, EndermanHeadBlockEntityRenderer::new);
    }
}
