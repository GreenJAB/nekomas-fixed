package net.greenjab.nekomasfixed.registries;

import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.ClockBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.EndermanHeadBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.HollowLogBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.StackedCakeBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanHeadBlockModel;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModBlockEntityRendererRegistry {

    public static final ModelLayerLocation CLAM =
            new ModelLayerLocation(NekomasFixed.id("clam"), "main");
    public static final ModelLayerLocation ENDERMAN_HEAD =
            new ModelLayerLocation(NekomasFixed.id("enderman_head"), "main");

    @SuppressWarnings("deprecation")
    public static void registerBlockEntityRenderers() {
        EntityModelLayerRegistry.registerModelLayer(CLAM, ClamBlockModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ENDERMAN_HEAD, EndermanHeadBlockModel::createBodyLayer);
        BlockEntityRendererRegistry.INSTANCE.register(
                BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY, ClamBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(
                BlockEntityTypeRegistry.HOLLOW_LOG_BLOCK_ENTITY, HollowLogBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(
                BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, EndermanHeadBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(
                BlockEntityTypeRegistry.STACKED_CAKE_BLOCK_ENTITY, StackedCakeBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(
                BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, ClockBlockEntityRenderer::new);
    }
}
