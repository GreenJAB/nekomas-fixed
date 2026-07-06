package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.entity.*;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ModEntityRendererRegistry {

    public static void registerEntityRenderer() {
        System.out.println("register EntityRenderer");
        EntityRenderers.register(EntityTypeRegistry.FAKE_BOAT, FakeBoatEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.BIG_ACACIA_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_ACACIA_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_BAMBOO_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_BAMBOO_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_BIRCH_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_BIRCH_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_CHERRY_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_CHERRY_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_DARK_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_DARK_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_JUNGLE_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_JUNGLE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_MANGROVE_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_MANGROVE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_PALE_OAK_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_PALE_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_SPRUCE_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_SPRUCE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_BAOBAB_BOAT, context -> new BigBoatEntityRenderer<>(context, ModEntityLayerRegistry.BIG_BAOBAB_BOAT));

        EntityRenderers.register(EntityTypeRegistry.HUGE_ACACIA_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_ACACIA_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_BAMBOO_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_BAMBOO_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_BIRCH_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_BIRCH_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_CHERRY_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_CHERRY_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_DARK_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_DARK_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_JUNGLE_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_JUNGLE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_MANGROVE_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_MANGROVE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_PALE_OAK_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_PALE_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_SPRUCE_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_SPRUCE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_BAOBAB_BOAT, context -> new HugeBoatEntityRenderer(context, ModEntityLayerRegistry.HUGE_BAOBAB_BOAT));

        EntityRenderers.register(EntityTypeRegistry.BAOBAB_BOAT, context -> new BoatRenderer(context, ModEntityLayerRegistry.BAOBAB_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BAOBAB_CHEST_BOAT, context -> new BoatRenderer(context, ModEntityLayerRegistry.BAOBAB_CHEST_BOAT));

        EntityRenderers.register(EntityTypeRegistry.TARGET_DUMMY, TargetDummyEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SPEAR, SpearEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.WILDFIRE_TRIDENT, WildfireTridentEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.FIRE_BOMB, FireBombEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SLINGSHOT_PROJECTILE, ThrownItemRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SLOWNESS_SNOWBALL, ThrownItemRenderer::new);


        EntityRenderers.register(EntityTypeRegistry.WILDFIRE, WildfireEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.TERMITE, TermiteRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.MOOBLOOM, MoobloomEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SUSPICIOUS_SPIDER, SuspiciousSpiderEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.DRENCHED, DrenchedEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.DERELICT, DerelictRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.RIME, RimeRenderer::new);

    }
}
