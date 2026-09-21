package net.greenjab.nekomasfixed.registries;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.entity.BaobabBoatRenderer;
import net.greenjab.nekomasfixed.render.entity.MoobloomRenderer;
import net.greenjab.nekomasfixed.render.entity.TargetDummyRenderer;
import net.greenjab.nekomasfixed.render.entity.model.*;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ModEntityRendererRegistry {

    public static final ModelLayerLocation BAOBAB_BOAT_LAYER =
            new ModelLayerLocation(NekomasFixed.id("boat/baobab"), "main");
    public static final ModelLayerLocation BAOBAB_CHEST_BOAT_LAYER =
            new ModelLayerLocation(NekomasFixed.id("chest_boat/baobab"), "main");
    public static final ModelLayerLocation TARGET_DUMMY =
            new ModelLayerLocation(NekomasFixed.id("target_dummy"), "main");
    public static final ModelLayerLocation TARGET_DUMMY_BASE =
            new ModelLayerLocation(NekomasFixed.id("target_dummy"), "base");
    public static final ModelLayerLocation TARGET_DUMMY_INNER_ARMOR =
            new ModelLayerLocation(NekomasFixed.id("target_dummy"), "inner_armor");
    public static final ModelLayerLocation TARGET_DUMMY_OUTER_ARMOR =
            new ModelLayerLocation(NekomasFixed.id("target_dummy"), "outer_armor");
    public static final ModelLayerLocation MOOBLOOM =
            new ModelLayerLocation(NekomasFixed.id("moobloom"), "main");
    public static final ModelLayerLocation MOOBLOOM_BABY =
            new ModelLayerLocation(NekomasFixed.id("moobloom"), "baby");

    public static void registerEntityRenderer() {
        EntityModelLayerRegistry.registerModelLayer(BAOBAB_BOAT_LAYER, BoatModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(BAOBAB_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(TARGET_DUMMY, TargetDummyModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(TARGET_DUMMY_BASE, BasePlateModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(TARGET_DUMMY_INNER_ARMOR,
                () -> TargetDummyArmorModel.createBodyModel(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(TARGET_DUMMY_OUTER_ARMOR,
                () -> TargetDummyArmorModel.createBodyModel(new CubeDeformation(1.0F)));
        EntityModelLayerRegistry.registerModelLayer(MOOBLOOM, MoobloomModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MOOBLOOM_BABY, BabyMoobloomModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityTypeRegistry.BAOBAB_BOAT, context ->
                new BaobabBoatRenderer(context, BAOBAB_BOAT_LAYER, false,
                        NekomasFixed.id("textures/entity/boat/baobab.png")));
        EntityRendererRegistry.register(EntityTypeRegistry.BAOBAB_CHEST_BOAT, context ->
                new BaobabBoatRenderer(context, BAOBAB_CHEST_BOAT_LAYER, true,
                        NekomasFixed.id("textures/entity/chest_boat/baobab.png")));
        EntityRendererRegistry.register(EntityTypeRegistry.TARGET_DUMMY, TargetDummyRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.CLEAR_ITEM_FRAME, ItemFrameRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.MOOBLOOM, MoobloomRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.SLINGSHOT_PROJECTILE, ThrownItemRenderer::new);
    }
}
