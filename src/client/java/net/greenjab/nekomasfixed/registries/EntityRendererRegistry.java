package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.entity.MegaBoatEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class EntityRendererRegistry {

    public static void registerEntityRenderer() {
        System.out.println("register EntityRenderer");
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.Clam_Block_Entity, ClamBlockEntityRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.OAK_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.OAK_MEGA_BOAT));
    }
}
