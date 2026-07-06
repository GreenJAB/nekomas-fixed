package net.greenjab.nekomasfixed.registries;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.ClockBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanEyesBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanHeadBlockModel;
import net.greenjab.nekomasfixed.render.entity.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.resources.Identifier;

public class ModEntityLayerRegistry {

    public static final ModelLayerLocation CLAM = register("clam", "main");
    public static final ModelLayerLocation CLOCK = register("clock", "main");
    public static final ModelLayerLocation ENDERMAN_HEAD = register("enderman_head", "main");
    public static final ModelLayerLocation ENDERMAN_EYES = register("enderman_head", "eyes");

    public static final ModelLayerLocation BIG_ACACIA_BOAT = register("big_boat/acacia", "main");
    public static final ModelLayerLocation BIG_BAMBOO_BOAT = register("big_boat/bamboo", "main");
    public static final ModelLayerLocation BIG_BIRCH_BOAT = register("big_boat/birch", "main");
    public static final ModelLayerLocation BIG_CHERRY_BOAT = register("big_boat/cherry", "main");
    public static final ModelLayerLocation BIG_DARK_OAK_BOAT = register("big_boat/dark_oak", "main");
    public static final ModelLayerLocation BIG_JUNGLE_BOAT = register("big_boat/jungle", "main");
    public static final ModelLayerLocation BIG_MANGROVE_BOAT = register("big_boat/mangrove", "main");
    public static final ModelLayerLocation BIG_OAK_BOAT = register("big_boat/oak", "main");
    public static final ModelLayerLocation BIG_PALE_OAK_BOAT = register("big_boat/pale_oak", "main");
    public static final ModelLayerLocation BIG_SPRUCE_BOAT = register("big_boat/spruce", "main");
    public static final ModelLayerLocation BIG_BAOBAB_BOAT = register("big_boat/baobab", "main");

    public static final ModelLayerLocation HUGE_ACACIA_BOAT = register("huge_boat/acacia", "main");
    public static final ModelLayerLocation HUGE_BAMBOO_BOAT = register("huge_boat/bamboo", "main");
    public static final ModelLayerLocation HUGE_BIRCH_BOAT = register("huge_boat/birch", "main");
    public static final ModelLayerLocation HUGE_CHERRY_BOAT = register("huge_boat/cherry", "main");
    public static final ModelLayerLocation HUGE_DARK_OAK_BOAT = register("huge_boat/dark_oak", "main");
    public static final ModelLayerLocation HUGE_JUNGLE_BOAT = register("huge_boat/jungle", "main");
    public static final ModelLayerLocation HUGE_MANGROVE_BOAT = register("huge_boat/mangrove", "main");
    public static final ModelLayerLocation HUGE_OAK_BOAT = register("huge_boat/oak", "main");
    public static final ModelLayerLocation HUGE_PALE_OAK_BOAT = register("huge_boat/pale_oak", "main");
    public static final ModelLayerLocation HUGE_SPRUCE_BOAT = register("huge_boat/spruce", "main");
    public static final ModelLayerLocation HUGE_BAOBAB_BOAT = register("huge_boat/baobab", "main");

    public static final ModelLayerLocation TARGET_DUMMY = register("target_dummy", "main");
    public static final ModelLayerLocation TARGET_DUMMY_BASE = register("target_dummy_base", "main");
    public static final ArmorModelSet<ModelLayerLocation> TARGET_DUMMY_EQUIPMENT = registerEquipment(NekomasFixed.id("target_dummy"));

    public static final ModelLayerLocation WILD_FIRE = register("wild_fire", "main");
    public static final ModelLayerLocation TERMITE = register("termite", "main");
    public static final ModelLayerLocation MOOBLOOM = register("moobloom", "main");
    public static final ModelLayerLocation MOOBLOOM_BABY = register("moobloom", "baby");
    public static final ModelLayerLocation WILDFIRE_TRIDENT = register("wildfirefire_trident", "main");

    public static final ModelLayerLocation DRENCHED = new ModelLayerLocation(NekomasFixed.id("drenched"), "main");
    public static final ModelLayerLocation SUSPICIOUS_SPIDER = new ModelLayerLocation(NekomasFixed.id("suspicious_spider"), "main");
    public static final ModelLayerLocation RIME = new ModelLayerLocation(NekomasFixed.id("zombie/rime"), "main");
    public static final ModelLayerLocation DERELICT = new ModelLayerLocation(NekomasFixed.id("zombie/derelict"), "main");

    public static ModelLayerLocation BAOBAB_BOAT = register("boat/baobab", "main");
    public static ModelLayerLocation BAOBAB_CHEST_BOAT = register("chest_boat/baobab", "main");

    private static ModelLayerLocation register(String path, String layer) {
        return new ModelLayerLocation(NekomasFixed.id(path), layer);
    }

    private static ArmorModelSet<ModelLayerLocation> registerEquipment(Identifier id) {
        return new ArmorModelSet<>(new ModelLayerLocation(id, "helmet"), new ModelLayerLocation(id, "chestplate"), new ModelLayerLocation(id, "leggings"), new ModelLayerLocation(id, "boots"));
    }

    public static void registerEntityModelLayer() {
        System.out.println("register EntityModelLayer");

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BAOBAB_BOAT, BoatModel::createBoatModel);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BAOBAB_CHEST_BOAT, BoatModel::createChestBoatModel);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TERMITE, TermiteModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.MOOBLOOM, MoobloomEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.MOOBLOOM_BABY, MoobloomEntityModel::getBabyTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.SUSPICIOUS_SPIDER, SuspiciousSpiderEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.DRENCHED, DrenchedEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.RIME, RimeEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.DERELICT, DerelictEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.CLAM, ClamBlockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.CLOCK, ClockBlockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.ENDERMAN_HEAD, EndermanHeadBlockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.ENDERMAN_EYES, EndermanEyesBlockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_ACACIA_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_BAMBOO_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_BIRCH_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_CHERRY_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_DARK_OAK_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_JUNGLE_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_MANGROVE_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_OAK_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_PALE_OAK_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_SPRUCE_BOAT, BigBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BIG_BAOBAB_BOAT, BigBoatEntityModel::getChestTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_ACACIA_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_BAMBOO_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_BIRCH_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_CHERRY_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_DARK_OAK_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_JUNGLE_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_MANGROVE_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_OAK_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_PALE_OAK_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_SPRUCE_BOAT, HugeBoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.HUGE_BAOBAB_BOAT, HugeBoatEntityModel::getChestTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY, TargetDummyEntityModel::getTexturedModelData);
        ArmorModelSet<LayerDefinition> equipmentModelData6 = TargetDummyEntityModel.getEquipmentModelData(new CubeDeformation(0.5F), new CubeDeformation(1.0F));
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.head(), equipmentModelData6::head);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.chest(), equipmentModelData6::chest);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.legs(), equipmentModelData6::legs);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.feet(), equipmentModelData6::feet);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_BASE, BasePlateEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.WILD_FIRE, WildfireEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.WILDFIRE_TRIDENT, TridentModel::createLayer);
    }
}
