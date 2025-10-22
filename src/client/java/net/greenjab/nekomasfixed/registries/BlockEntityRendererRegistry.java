package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BlockEntityRendererRegistry {

    public static void registerBlockEntityRenderer() {
        System.out.println("register BlockEntityRenderer");
        BlockEntityRendererFactories.register(OtherRegistry.Clam_BlockEntity, ClamBlockEntityRenderer::new);
    }
}
