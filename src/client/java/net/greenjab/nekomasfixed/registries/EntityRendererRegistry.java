package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.ClockBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.EndermanHeadBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.entity.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class EntityRendererRegistry {

    public static void registerEntityRenderer() {
        System.out.println("register EntityRenderer");
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY, ClamBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, ClockBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, EndermanHeadBlockEntityRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.FAKE_BOAT, FakeBoatEntityRenderer::new);
        
        EntityRendererFactories.register(EntityTypeRegistry.BIG_ACACIA_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_ACACIA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_BAMBOO_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_BAMBOO_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_BIRCH_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_BIRCH_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_CHERRY_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_CHERRY_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_DARK_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_DARK_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_JUNGLE_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_JUNGLE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_MANGROVE_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_MANGROVE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_PALE_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_PALE_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_SPRUCE_BOAT, context -> new BigBoatEntityRenderer<>(context, EntityModelLayerRegistry.BIG_SPRUCE_BOAT));

        EntityRendererFactories.register(EntityTypeRegistry.HUGE_ACACIA_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_ACACIA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_BAMBOO_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_BAMBOO_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_BIRCH_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_BIRCH_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_CHERRY_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_CHERRY_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_DARK_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_DARK_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_JUNGLE_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_JUNGLE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_MANGROVE_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_MANGROVE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_PALE_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_PALE_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_SPRUCE_BOAT, context -> new HugeBoatEntityRenderer(context, EntityModelLayerRegistry.HUGE_SPRUCE_BOAT));

        EntityRendererFactories.register(EntityTypeRegistry.TARGET_DUMMY, TargetDummyEntityRenderer::new);

        EntityRendererFactories.register(EntityTypeRegistry.SPEAR, SpearEntityRenderer::new);

        EntityRendererFactories.register(EntityTypeRegistry.WILD_FIRE, WildFireEntityRenderer::new);

        EntityRendererFactories.register(EntityTypeRegistry.SLINGSHOT_PROJECTILE, FlyingItemEntityRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.FIRE_BOMB, FireBombEntityRenderer::new);
    }
}
