package net.greenjab.nekomasfixed.render.entity.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.player.PlayerModel;

public class FlowerCrownModel extends PlayerModel {
    public FlowerCrownModel(ModelPart root) {
        super(root, false);
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition root = mesh.getRoot().clearRecursively();
        PartDefinition head = root.getChild("head");
        PartDefinition group = head.addOrReplaceChild("group",
                CubeListBuilder.create()
                        .texOffs(22, 0).addBox(4.1F, -6.0F, -4.3F, 0.0F, 10.0F, 10.0F, CubeDeformation.NONE)
                        .texOffs(22, 20).addBox(-5.9F, -6.0F, -4.3F, 0.0F, 10.0F, 10.0F, CubeDeformation.NONE)
                        .texOffs(42, 0).addBox(-5.9F, -6.0F, 5.7F, 10.0F, 10.0F, 0.0F, CubeDeformation.NONE)
                        .texOffs(42, 10).addBox(-5.9F, -6.0F, -4.3F, 10.0F, 10.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.9F, -4.5F, -0.7F));

        group.addOrReplaceChild("cube_r1",
                CubeListBuilder.create()
                        .texOffs(44, 41).addBox(0.0F, -0.25F, -1.5F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.575F, -2.75F, 5.175F, 1.5708F, 0.9599F, 1.5708F));

        group.addOrReplaceChild("cube_r2",
                CubeListBuilder.create()
                        .texOffs(44, 34).addBox(0.0F, -0.25F, -1.5F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.425F, -2.75F, -3.775F, -1.5708F, -0.9599F, 1.5708F));

        group.addOrReplaceChild("cube_r3",
                CubeListBuilder.create()
                        .texOffs(42, 27).addBox(0.0F, -0.25F, -1.5F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-5.375F, -2.75F, 2.5F, 0.0F, 0.0F, 0.6109F));

        group.addOrReplaceChild("cube_r4",
                CubeListBuilder.create()
                        .texOffs(42, 20).addBox(0.0F, -0.25F, -1.5F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(3.575F, -2.75F, -1.5F, 0.0F, 0.0F, -0.6109F));

        group.addOrReplaceChild("outer_layer",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(1.0F, -10.0F, -2.0F, 0.0F, 10.0F, 11.0F, CubeDeformation.NONE)
                        .texOffs(22, 40).addBox(-10.0F, -10.0F, 9.0F, 11.0F, 10.0F, 0.0F, CubeDeformation.NONE)
                        .texOffs(0, 21).addBox(-10.0F, -10.0F, -2.0F, 0.0F, 10.0F, 11.0F, CubeDeformation.NONE)
                        .texOffs(0, 42).addBox(-10.0F, -10.0F, -2.0F, 11.0F, 10.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(3.6F, 4.5F, -2.8F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}