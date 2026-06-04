package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.entity.*;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ModEntityRendererRegistry {

    public static void registerEntityRenderer() {
        System.out.println("register EntityRenderer");
        EntityRendererFactories.register(EntityTypeRegistry.FAKE_BOAT, FakeBoatEntityRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.BIG_ACACIA_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_ACACIA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_BAMBOO_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_BAMBOO_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_BIRCH_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_BIRCH_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_CHERRY_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_CHERRY_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_DARK_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_DARK_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_JUNGLE_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_JUNGLE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_MANGROVE_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_MANGROVE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_PALE_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_PALE_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_SPRUCE_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_SPRUCE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BIG_BAOBAB_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_BAOBAB_BOAT));

        EntityRendererFactories.register(EntityTypeRegistry.HUGE_ACACIA_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_ACACIA_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_BAMBOO_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_BAMBOO_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_BIRCH_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_BIRCH_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_CHERRY_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_CHERRY_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_DARK_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_DARK_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_JUNGLE_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_JUNGLE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_MANGROVE_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_MANGROVE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_PALE_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_PALE_OAK_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_SPRUCE_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_SPRUCE_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.HUGE_BAOBAB_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_BAOBAB_BOAT));


        EntityRendererFactories.register(EntityTypeRegistry.TARGET_DUMMY, TargetDummyEntityRenderer::new);

        EntityRendererFactories.register(EntityTypeRegistry.SPEAR, SpearEntityRenderer::new);

        EntityRendererFactories.register(EntityTypeRegistry.WILD_FIRE, WildFireEntityRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.TERMITE, TermiteRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.MOOBLOOM, MoobloomEntityRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.FIRE_BOMB, FireBombEntityRenderer::new);
        EntityRendererFactories.register(EntityTypeRegistry.SOULFIRE_TRIDENT, SoulfireTridentEntityRenderer::new);

        EntityRendererFactories.register(EntityTypeRegistry.SLINGSHOT_PROJECTILE, FlyingItemEntityRenderer::new);

    }
}
