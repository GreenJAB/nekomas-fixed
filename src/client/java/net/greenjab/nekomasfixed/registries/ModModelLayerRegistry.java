package net.greenjab.nekomasfixed.registries;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.ClockBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanEyesBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanHeadBlockModel;
import net.greenjab.nekomasfixed.render.entity.model.*;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;

public class ModModelLayerRegistry {

    public static final ModelLayerLocation CLAM = register("clam");
    public static final ModelLayerLocation CLOCK = register("clock");
    public static final ModelLayerLocation ENDERMAN_HEAD = register("enderman_head");
    public static final ModelLayerLocation ENDERMAN_EYES = register("enderman_head", "eyes");

    public static final ModelLayerLocation BIG_ACACIA_BOAT = register("big_boat/acacia");
    public static final ModelLayerLocation BIG_BAMBOO_BOAT = register("big_boat/bamboo");
    public static final ModelLayerLocation BIG_BIRCH_BOAT = register("big_boat/birch");
    public static final ModelLayerLocation BIG_CHERRY_BOAT = register("big_boat/cherry");
    public static final ModelLayerLocation BIG_DARK_OAK_BOAT = register("big_boat/dark_oak");
    public static final ModelLayerLocation BIG_JUNGLE_BOAT = register("big_boat/jungle");
    public static final ModelLayerLocation BIG_MANGROVE_BOAT = register("big_boat/mangrove");
    public static final ModelLayerLocation BIG_OAK_BOAT = register("big_boat/oak");
    public static final ModelLayerLocation BIG_PALE_OAK_BOAT = register("big_boat/pale_oak");
    public static final ModelLayerLocation BIG_SPRUCE_BOAT = register("big_boat/spruce");
    public static final ModelLayerLocation BIG_BAOBAB_BOAT = register("big_boat/baobab");

    public static final ModelLayerLocation HUGE_ACACIA_BOAT = register("huge_boat/acacia");
    public static final ModelLayerLocation HUGE_BAMBOO_BOAT = register("huge_boat/bamboo");
    public static final ModelLayerLocation HUGE_BIRCH_BOAT = register("huge_boat/birch");
    public static final ModelLayerLocation HUGE_CHERRY_BOAT = register("huge_boat/cherry");
    public static final ModelLayerLocation HUGE_DARK_OAK_BOAT = register("huge_boat/dark_oak");
    public static final ModelLayerLocation HUGE_JUNGLE_BOAT = register("huge_boat/jungle");
    public static final ModelLayerLocation HUGE_MANGROVE_BOAT = register("huge_boat/mangrove");
    public static final ModelLayerLocation HUGE_OAK_BOAT = register("huge_boat/oak");
    public static final ModelLayerLocation HUGE_PALE_OAK_BOAT = register("huge_boat/pale_oak");
    public static final ModelLayerLocation HUGE_SPRUCE_BOAT = register("huge_boat/spruce");
    public static final ModelLayerLocation HUGE_BAOBAB_BOAT = register("huge_boat/baobab");

    public static final ModelLayerLocation TARGET_DUMMY = register("target_dummy");
    public static final ModelLayerLocation TARGET_DUMMY_BASE = register("target_dummy_base");
    public static final ArmorModelSet<ModelLayerLocation> TARGET_DUMMY_EQUIPMENT = registerArmorSet("target_dummy");

    public static final ModelLayerLocation WILD_FIRE = register("wild_fire");
    public static final ModelLayerLocation TERMITE = register("termite");
    public static final ModelLayerLocation MOOBLOOM = register("moobloom");
    public static final ModelLayerLocation MOOBLOOM_BABY = register("moobloom", "baby");
    public static final ModelLayerLocation WILDFIRE_TRIDENT = register("wildfirefire_trident");

    public static final ModelLayerLocation DRENCHED = register("drenched");
    public static final ModelLayerLocation SUSPICIOUS_SPIDER = register("suspicious_spider");
    
    public static final ModelLayerLocation RIME = register("rime");
    public static final ModelLayerLocation RIME_BABY = register("rime_baby");
    public static final ArmorModelSet<ModelLayerLocation> RIME_BABY_ARMOR = registerArmorSet("rime_baby");
    public static final ModelLayerLocation RIME_BABY_OUTER_LAYER = register("rime_baby", "outer");
    public static final ArmorModelSet<ModelLayerLocation> RIME_ARMOR = registerArmorSet("rime");
    public static final ModelLayerLocation RIME_OUTER_LAYER = register("rime", "outer");

    public static final ModelLayerLocation DERELICT = register("derelict");
    public static final ModelLayerLocation DERELICT_BABY = register("derelict_baby");
    public static final ArmorModelSet<ModelLayerLocation> DERELICT_BABY_ARMOR = registerArmorSet("derelict_baby");
    public static final ModelLayerLocation DERELICT_BABY_OUTER_LAYER = register("derelict_baby", "outer");
    public static final ArmorModelSet<ModelLayerLocation> DERELICT_ARMOR = registerArmorSet("derelict");
    public static final ModelLayerLocation DERELICT_OUTER_LAYER = register("derelict", "outer");

    public static ModelLayerLocation BAOBAB_BOAT = register("boat/baobab");
    public static ModelLayerLocation BAOBAB_CHEST_BOAT = register("chest_boat/baobab");

    private static ModelLayerLocation register(final String model) {
        return register(model, "main");
    }
    private static ModelLayerLocation register(String path, String layer) {
        return new ModelLayerLocation(NekomasFixed.id(path), layer);
    }

    private static ArmorModelSet<ModelLayerLocation> registerArmorSet(final String modelId) {
        return new ArmorModelSet<>(register(modelId, "helmet"), register(modelId, "chestplate"), register(modelId, "leggings"), register(modelId, "boots"));
    }

    public static void registerEntityModelLayer() {
        System.out.println("register EntityModelLayer");

        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BAOBAB_BOAT, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BAOBAB_CHEST_BOAT, BoatModel::createChestBoatModel);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.TERMITE, TermiteModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.MOOBLOOM, MoobloomModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.MOOBLOOM_BABY, BabyMoobloomModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.SUSPICIOUS_SPIDER, SuspiciousSpiderModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DRENCHED, DrenchedModel::getTexturedModelData);
        
        ArmorModelSet<LayerDefinition> humanoidArmor = HumanoidModel.createArmorMeshSet(new CubeDeformation(0.5F), new CubeDeformation(1.0F))
                .map( mesh -> LayerDefinition.create(mesh, 64, 32));
        ArmorModelSet<LayerDefinition> humanoidBabyArmor = HumanoidModel.createBabyArmorMeshSet(
                new CubeDeformation(-0.1F, 0.3F, 0.3F), new CubeDeformation(-0.1F, 0.5F, 0.3F), PartPose.ZERO
        ).map(mesh -> LayerDefinition.create(mesh, 64, 64));
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME, RimeModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_OUTER_LAYER, RimeModel::createOuterLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_ARMOR.head(), humanoidArmor::head);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_ARMOR.chest(), humanoidArmor::chest);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_ARMOR.legs(), humanoidArmor::legs);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_ARMOR.feet(), humanoidArmor::feet);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_BABY, BabyRimeModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_BABY_OUTER_LAYER, BabyRimeModel::createOuterLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_BABY_ARMOR.head(), humanoidBabyArmor::head);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_BABY_ARMOR.chest(), humanoidBabyArmor::chest);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_BABY_ARMOR.legs(), humanoidBabyArmor::legs);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.RIME_BABY_ARMOR.feet(), humanoidBabyArmor::feet);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT, DerelictModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_OUTER_LAYER, DerelictModel::createOuterLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_ARMOR.head(), humanoidArmor::head);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_ARMOR.chest(), humanoidArmor::chest);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_ARMOR.legs(), humanoidArmor::legs);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_ARMOR.feet(), humanoidArmor::feet);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_BABY, BabyDerelictModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_BABY_OUTER_LAYER, BabyDerelictModel::createOuterLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_BABY_ARMOR.head(), humanoidBabyArmor::head);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_BABY_ARMOR.chest(), humanoidBabyArmor::chest);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_BABY_ARMOR.legs(), humanoidBabyArmor::legs);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.DERELICT_BABY_ARMOR.feet(), humanoidBabyArmor::feet);

        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.CLAM, ClamBlockModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.CLOCK, ClockBlockModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.ENDERMAN_HEAD, EndermanHeadBlockModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.ENDERMAN_EYES, EndermanEyesBlockModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_ACACIA_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_BAMBOO_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_BIRCH_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_CHERRY_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_DARK_OAK_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_JUNGLE_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_MANGROVE_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_OAK_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_PALE_OAK_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_SPRUCE_BOAT, BigBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.BIG_BAOBAB_BOAT, BigBoatModel::getChestTexturedModelData);

        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_ACACIA_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_BAMBOO_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_BIRCH_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_CHERRY_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_DARK_OAK_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_JUNGLE_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_MANGROVE_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_OAK_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_PALE_OAK_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_SPRUCE_BOAT, HugeBoatModel::getChestTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.HUGE_BAOBAB_BOAT, HugeBoatModel::getChestTexturedModelData);

        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.TARGET_DUMMY, TargetDummyModel::getTexturedModelData);
        ArmorModelSet<LayerDefinition> equipmentModelData6 = TargetDummyModel.getEquipmentModelData(new CubeDeformation(0.5F), new CubeDeformation(1.0F));
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.TARGET_DUMMY_EQUIPMENT.head(), equipmentModelData6::head);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.TARGET_DUMMY_EQUIPMENT.chest(), equipmentModelData6::chest);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.TARGET_DUMMY_EQUIPMENT.legs(), equipmentModelData6::legs);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.TARGET_DUMMY_EQUIPMENT.feet(), equipmentModelData6::feet);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.TARGET_DUMMY_BASE, BasePlateModel::getTexturedModelData);

        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.WILD_FIRE, WildfireModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModModelLayerRegistry.WILDFIRE_TRIDENT, TridentModel::createLayer);
    }
}
