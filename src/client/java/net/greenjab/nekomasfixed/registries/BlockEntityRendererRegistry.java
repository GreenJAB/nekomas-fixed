package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BlockEntityRendererRegistry {

    public static void registerBlockEntityRenderer() {
        System.out.println("register BlockEntityRenderer");
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.Clam_Block_Entity, ClamBlockEntityRenderer::new);
    }
}
