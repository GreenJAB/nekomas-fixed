package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.render.entity.state.MoobloomEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;

import java.util.Set;

public class MoobloomEntityModel extends QuadrupedModel<MoobloomEntityRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(false, 8.0F, 6.0F, Set.of("head"));

    public MoobloomEntityModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return LayerDefinition.create(getModelData(), 64, 64);
    }

    public static LayerDefinition getBabyTexturedModelData() {
        MeshDefinition modelData = getModelData();
        modelData.apply(BABY_TRANSFORMER);
        return LayerDefinition.create(modelData, 64, 64);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 2.0F));

        PartDefinition head = modelPartData.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F)
                        .texOffs(1, 33)
                        .addBox(-3.0F, 1.0F, -7.0F, 6.0F, 3.0F, 1.0F)
                        .texOffs(22, 0)
                        .addBox("right_horn", -5.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F)
                        .texOffs(22, 0)
                        .addBox("left_horn", 4.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 4.0F, -8.0F)
        );
        PartDefinition body = modelPartData.addOrReplaceChild("body",CubeListBuilder.create(),PartPose.offset(0.0F, 5.0F, 2.0F));
        modelPartData.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),PartPose.offset(-4.0F, 12.0F, 7.0F));
        modelPartData.addOrReplaceChild("left_hind_leg",CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),PartPose.offset(4.0F, 12.0F, 7.0F));
        modelPartData.addOrReplaceChild("right_front_leg",CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),PartPose.offset(-4.0F, 12.0F, -5.0F));
        modelPartData.addOrReplaceChild("left_front_leg",CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),PartPose.offset(4.0F, 12.0F, -5.0F));

        PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition flower1 = rotation.addOrReplaceChild("flower1", CubeListBuilder.create().texOffs(0, 42).addBox(-7.975F, -16.0F, 1.2F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.025F, -2.0F, 2.8F, -1.5708F, 0.0F, 0.0F));
        flower1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, -8.0F, 1.2F, 0.0F, 3.1416F, 0.0F));

        PartDefinition flower2 = rotation.addOrReplaceChild("flower2", CubeListBuilder.create().texOffs(0, 42).addBox(-5.95F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.95F, 5.0F, 3.0F, -1.5708F, 0.0F, 0.7854F));
        flower2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.05F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        
        PartDefinition flower3 = head.addOrReplaceChild("flower3", CubeListBuilder.create().texOffs(0, 42).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -3.2F, 0.0F, -0.576F, 0.0F));
        flower3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return modelData;
    }
}
