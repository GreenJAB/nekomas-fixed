package net.greenjab.nekomasfixed.registries;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.entity.MoobloomEntityRenderer;
import net.greenjab.nekomasfixed.render.entity.SuspiciousSpiderEntityRenderer;
import net.greenjab.nekomasfixed.render.entity.TermiteRenderer;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.TermiteModel;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.util.Identifier;

public class ModEntityLayerRegistry {

    public static final EntityModelLayer CLAM = register("clam", "main");
    public static final EntityModelLayer CLOCK = register("clock", "main");
    public static final EntityModelLayer ENDERMAN_HEAD = register("enderman_head", "main");
    public static final EntityModelLayer ENDERMAN_EYES = register("enderman_head", "eyes");

    public static final EntityModelLayer BIG_ACACIA_BOAT = register("big_boat/acacia", "main");
    public static final EntityModelLayer BIG_BAMBOO_BOAT = register("big_boat/bamboo", "main");
    public static final EntityModelLayer BIG_BIRCH_BOAT = register("big_boat/birch", "main");
    public static final EntityModelLayer BIG_CHERRY_BOAT = register("big_boat/cherry", "main");
    public static final EntityModelLayer BIG_DARK_OAK_BOAT = register("big_boat/dark_oak", "main");
    public static final EntityModelLayer BIG_JUNGLE_BOAT = register("big_boat/jungle", "main");
    public static final EntityModelLayer BIG_MANGROVE_BOAT = register("big_boat/mangrove", "main");
    public static final EntityModelLayer BIG_OAK_BOAT = register("big_boat/oak", "main");
    public static final EntityModelLayer BIG_PALE_OAK_BOAT = register("big_boat/pale_oak", "main");
    public static final EntityModelLayer BIG_SPRUCE_BOAT = register("big_boat/spruce", "main");
    public static final EntityModelLayer BIG_BAOBAB_BOAT = register("big_boat/baobab", "main");

    public static final EntityModelLayer HUGE_ACACIA_BOAT = register("huge_boat/acacia", "main");
    public static final EntityModelLayer HUGE_BAMBOO_BOAT = register("huge_boat/bamboo", "main");
    public static final EntityModelLayer HUGE_BIRCH_BOAT = register("huge_boat/birch", "main");
    public static final EntityModelLayer HUGE_CHERRY_BOAT = register("huge_boat/cherry", "main");
    public static final EntityModelLayer HUGE_DARK_OAK_BOAT = register("huge_boat/dark_oak", "main");
    public static final EntityModelLayer HUGE_JUNGLE_BOAT = register("huge_boat/jungle", "main");
    public static final EntityModelLayer HUGE_MANGROVE_BOAT = register("huge_boat/mangrove", "main");
    public static final EntityModelLayer HUGE_OAK_BOAT = register("huge_boat/oak", "main");
    public static final EntityModelLayer HUGE_PALE_OAK_BOAT = register("huge_boat/pale_oak", "main");
    public static final EntityModelLayer HUGE_SPRUCE_BOAT = register("huge_boat/spruce", "main");
    public static final EntityModelLayer HUGE_BAOBAB_BOAT = register("huge_boat/baobab", "main");

    public static final EntityModelLayer TARGET_DUMMY = register("target_dummy", "main");
    public static final EntityModelLayer TARGET_DUMMY_BASE = register("target_dummy_base", "main");
    public static final EquipmentModelData<EntityModelLayer> TARGET_DUMMY_EQUIPMENT = registerEquipment(NekomasFixed.id("target_dummy"));

    public static final EntityModelLayer WILD_FIRE = register("wild_fire", "main");
    public static final EntityModelLayer TERMITE = register("termite", "main");
    public static final EntityModelLayer MOOBLOOM = register("moobloom", "main");
    public static final EntityModelLayer MOOBLOOM_BABY = register("moobloom", "baby");
    public static final EntityModelLayer SOULFIRE_TRIDENT = register("soulfire_trident", "main");

    public static EntityModelLayer BAOBAB_BOAT = register("boat/baobab", "main");
    public static EntityModelLayer BAOBAB_CHEST_BOAT = register("chest_boat/baobab", "main");

    private static EntityModelLayer register(String path, String layer) {
        return new EntityModelLayer(NekomasFixed.id(path), layer);
    }

    private static EquipmentModelData<EntityModelLayer> registerEquipment(Identifier id) {
        return new EquipmentModelData<>(new EntityModelLayer(id, "helmet"), new EntityModelLayer(id, "chestplate"), new EntityModelLayer(id, "leggings"), new EntityModelLayer(id, "boots"));
    }

    public static void registerEntityModelLayer() {
        System.out.println("register EntityModelLayer");

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TERMITE, TermiteModel::getTexturedModelData);
        EntityRendererFactories.register(EntityTypeRegistry.TERMITE, TermiteRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.MOOBLOOM, MoobloomEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.MOOBLOOM_BABY, MoobloomEntityModel::getBabyTexturedModelData);
        EntityRendererFactories.register(EntityTypeRegistry.MOOBLOOM, MoobloomEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SuspiciousSpiderEntityModel.SUS_SPIDER, SuspiciousSpiderEntityModel::getTexturedModelData);
        EntityRendererFactories.register(EntityTypeRegistry.SUS_SPIDER, SuspiciousSpiderEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(BAOBAB_BOAT, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BAOBAB_CHEST_BOAT, BoatEntityModel::getChestTexturedModelData);

        EntityRendererFactories.register(EntityTypeRegistry.BAOBAB_BOAT_ENTITY, context -> new BoatEntityRenderer(context, ModEntityLayerRegistry.BAOBAB_BOAT));
        EntityRendererFactories.register(EntityTypeRegistry.BAOBAB_CHEST_BOAT_ENTITY,context -> new BoatEntityRenderer(context, ModEntityLayerRegistry.BAOBAB_CHEST_BOAT));

    }
}
