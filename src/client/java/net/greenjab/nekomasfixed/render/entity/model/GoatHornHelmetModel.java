package net.greenjab.nekomasfixed.render.entity.model;

import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.object.armorstand.ArmorStandModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.world.inventory.SmithingMenu;

public class GoatHornHelmetModel extends PlayerModel {

    public GoatHornHelmetModel(ModelPart root) {
        super(root, false);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.getChild("head");

        // Helmet cube(the helmet)
        PartDefinition helmet = head.addOrReplaceChild("Helmet",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Right horn group
        PartDefinition rightHorn = helmet.addOrReplaceChild("RightHorn",
                CubeListBuilder.create(),
                PartPose.offset(3.0F, -13.5F, -3.0F));

        PartDefinition fat = rightHorn.addOrReplaceChild("Fat",
                CubeListBuilder.create(),
                PartPose.offset(-7.5F, 9.15F, 3.0F));

        fat.addOrReplaceChild("cube_r1",
                CubeListBuilder.create()
                        .texOffs(16, 16).mirror().addBox(-5.75F, -5.45F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false)
                        .texOffs(0, 16).mirror().addBox(-4.5F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        // Left horn group
        PartDefinition leftHorn = helmet.addOrReplaceChild("LeftHorn",
                CubeListBuilder.create(),
                PartPose.offset(-3.0F, -13.5F, -3.0F));

        PartDefinition fat2 = leftHorn.addOrReplaceChild("Fat2",
                CubeListBuilder.create(),
                PartPose.offset(7.5F, 9.15F, 3.0F));

        fat2.addOrReplaceChild("cube_r3",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(3.75F, -5.45F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F))
                        .texOffs(0, 16).addBox(0.5F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    public static LayerDefinition getTexturedModelDataForTrim() {
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition root = mesh.getRoot().clearRecursively();
        PartDefinition head = root.getChild("head");

        PartDefinition helmet = head.addOrReplaceChild("Helmet",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightHorn = helmet.addOrReplaceChild("RightHorn",
                CubeListBuilder.create(),
                PartPose.offset(3.0F, -13.5F, -3.0F));

        PartDefinition fat = rightHorn.addOrReplaceChild("Fat",
                CubeListBuilder.create(),
                PartPose.offset(-7.5F, 9.15F, 3.0F));

        fat.addOrReplaceChild("cube_r1",
                CubeListBuilder.create()
                        .texOffs(16, 16).mirror().addBox(-5.75F, -5.45F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false)
                        .texOffs(0, 16).mirror().addBox(-4.5F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition leftHorn = helmet.addOrReplaceChild("LeftHorn",
                CubeListBuilder.create(),
                PartPose.offset(-3.0F, -13.5F, -3.0F));

        PartDefinition fat2 = leftHorn.addOrReplaceChild("Fat2",
                CubeListBuilder.create(),
                PartPose.offset(7.5F, 9.15F, 3.0F));

        fat2.addOrReplaceChild("cube_r3",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(3.75F, -5.45F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F))
                        .texOffs(0, 16).addBox(0.5F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }
}