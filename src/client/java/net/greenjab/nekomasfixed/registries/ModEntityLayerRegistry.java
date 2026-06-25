package net.greenjab.nekomasfixed.registries;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.ClockBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanEyesBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanHeadBlockModel;
import net.greenjab.nekomasfixed.render.entity.*;
import net.greenjab.nekomasfixed.render.entity.model.*;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.*;
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

    public static final EntityModelLayer DRENCHED = new EntityModelLayer(NekomasFixed.id("drenched"), "main");
    public static final EntityModelLayer SUS_SPIDER = new EntityModelLayer(NekomasFixed.id("sus_spider"), "main");
    public static final EntityModelLayer SNOW_ZOMBIE = new EntityModelLayer(NekomasFixed.id("zombie/snow_zombie"), "main");
    public static final EntityModelLayer JUNGLE_ZOMBIE = new EntityModelLayer(NekomasFixed.id("zombie/jungle_zombie"), "main");

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

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BAOBAB_BOAT, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.BAOBAB_CHEST_BOAT, BoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TERMITE, TermiteModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.MOOBLOOM, MoobloomEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.MOOBLOOM_BABY, MoobloomEntityModel::getBabyTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.SUS_SPIDER, SuspiciousSpiderEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.DRENCHED, DrenchedEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.SNOW_ZOMBIE, SnowZombieEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.JUNGLE_ZOMBIE, JungleZombieEntityModel::getTexturedModelData);

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
        EquipmentModelData<TexturedModelData> equipmentModelData6 = TargetDummyEntityModel.getEquipmentModelData(new Dilation(0.5F), new Dilation(1.0F));
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.head(), equipmentModelData6::head);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.chest(), equipmentModelData6::chest);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.legs(), equipmentModelData6::legs);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT.feet(), equipmentModelData6::feet);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.TARGET_DUMMY_BASE, BasePlateEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.WILD_FIRE, WildFireEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityLayerRegistry.SOULFIRE_TRIDENT, TridentEntityModel::getTexturedModelData);
    }
}
