package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.entity.*;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ModEntityRendererRegistry {

    public static void registerEntityRenderer() {
        System.out.println("register EntityRenderer");
        EntityRenderers.register(EntityTypeRegistry.FAKE_BOAT, FakeBoatRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.BIG_ACACIA_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_ACACIA_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_BAMBOO_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_BAMBOO_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_BIRCH_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_BIRCH_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_CHERRY_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_CHERRY_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_DARK_OAK_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_DARK_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_JUNGLE_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_JUNGLE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_MANGROVE_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_MANGROVE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_OAK_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_PALE_OAK_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_PALE_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_SPRUCE_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_SPRUCE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BIG_BAOBAB_BOAT, context -> new BigBoatRenderer<>(context, ModModelLayerRegistry.BIG_BAOBAB_BOAT));

        EntityRenderers.register(EntityTypeRegistry.HUGE_ACACIA_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_ACACIA_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_BAMBOO_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_BAMBOO_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_BIRCH_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_BIRCH_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_CHERRY_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_CHERRY_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_DARK_OAK_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_DARK_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_JUNGLE_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_JUNGLE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_MANGROVE_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_MANGROVE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_OAK_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_PALE_OAK_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_PALE_OAK_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_SPRUCE_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_SPRUCE_BOAT));
        EntityRenderers.register(EntityTypeRegistry.HUGE_BAOBAB_BOAT, context -> new HugeBoatRenderer(context, ModModelLayerRegistry.HUGE_BAOBAB_BOAT));

        EntityRenderers.register(EntityTypeRegistry.BAOBAB_BOAT, context -> new BoatRenderer(context, ModModelLayerRegistry.BAOBAB_BOAT));
        EntityRenderers.register(EntityTypeRegistry.BAOBAB_CHEST_BOAT, context -> new BoatRenderer(context, ModModelLayerRegistry.BAOBAB_CHEST_BOAT));

        EntityRenderers.register(EntityTypeRegistry.TARGET_DUMMY, TargetDummyEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SPEAR, SpearRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.WILDFIRE_TRIDENT, ThrownWildfireTridentRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.FIRE_BOMB, FireBombRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SLINGSHOT_PROJECTILE, ThrownItemRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SLOWNESS_SNOWBALL, ThrownItemRenderer::new);


        EntityRenderers.register(EntityTypeRegistry.WILDFIRE, WildfireRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.TERMITE, TermiteRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.MOOBLOOM, MoobloomRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SUSPICIOUS_SPIDER, SuspiciousSpiderEntityRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.DRENCHED, DrenchedRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.DERELICT, DerelictRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.RIME, RimeRenderer::new);

    }
}
