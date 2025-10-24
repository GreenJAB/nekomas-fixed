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
        EntityRendererFactories.register(EntityTypeRegistry.ACACIA_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.ACACIA_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BAMBOO_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.BAMBOO_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIRCH_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.BIRCH_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.CHERRY_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.CHERRY_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.DARK_OAK_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.DARK_OAK_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.JUNGLE_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.JUNGLE_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.MANGROVE_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.MANGROVE_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.OAK_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.OAK_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.PALE_OAK_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.PALE_OAK_MEGA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.SPRUCE_MEGA_BOAT, context -> new MegaBoatEntityRenderer(context, EntityModelLayerRegistry.SPRUCE_MEGA_BOAT));
    }
}
