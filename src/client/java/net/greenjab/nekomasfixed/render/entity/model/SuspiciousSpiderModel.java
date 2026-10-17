package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SuspiciousSpiderModel extends HierarchicalModel<SuspiciousSpider> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleLeg;
    private final ModelPart leftMiddleLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public SuspiciousSpiderModel(ModelPart modelPart) {
        this.root = modelPart;
        this.head = modelPart.getChild(PartNames.HEAD);
        this.rightHindLeg = modelPart.getChild(PartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = modelPart.getChild(PartNames.LEFT_HIND_LEG);
        this.rightMiddleLeg = modelPart.getChild("right_middle_hind_leg");
        this.leftMiddleLeg = modelPart.getChild("left_middle_hind_leg");
        this.rightMiddleFrontLeg = modelPart.getChild("right_middle_front_leg");
        this.leftMiddleFrontLeg = modelPart.getChild("left_middle_front_leg");
        this.rightFrontLeg = modelPart.getChild(PartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = modelPart.getChild(PartNames.LEFT_FRONT_LEG);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(32, 4).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 15.0F, -3.0F));
        PartDefinition body0 = modelPartData.addOrReplaceChild("body0", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
        modelPartData.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F), PartPose.offset(0.0F, 15.0F, 9.0F));
        CubeListBuilder modelPartBuilder = CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        CubeListBuilder modelPartBuilder2 = CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        modelPartData.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, 2.0F, 0.0F, (float) (Math.PI / 4), (float) (-Math.PI / 4)));
        modelPartData.addOrReplaceChild(PartNames.LEFT_HIND_LEG, modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, 2.0F, 0.0F, (float) (-Math.PI / 4), (float) (Math.PI / 4)));
        modelPartData.addOrReplaceChild("right_middle_hind_leg", modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, 1.0F, 0.0F, (float) (Math.PI / 8), -0.58119464F));
        modelPartData.addOrReplaceChild("left_middle_hind_leg", modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, 1.0F, 0.0F, (float) (-Math.PI / 8), 0.58119464F));
        modelPartData.addOrReplaceChild("right_middle_front_leg", modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, 0.0F, 0.0F, (float) (-Math.PI / 8), -0.58119464F));
        modelPartData.addOrReplaceChild("left_middle_front_leg", modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, 0.0F, 0.0F, (float) (Math.PI / 8), 0.58119464F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, 0.0F, (float) (-Math.PI / 4), (float) (-Math.PI / 4)));
        modelPartData.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, -1.0F, 0.0F, (float) (Math.PI / 4), (float) (Math.PI / 4)));

        PartDefinition mushroom1 = body0.addOrReplaceChild("mushroom1", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, 4.0F, 0.0F, 8.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));
        mushroom1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 42).addBox(-5.0F, -2.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition mushroom2 = mushroom1.addOrReplaceChild("mushroom2", CubeListBuilder.create(), PartPose.offset(-2.0F, -1.0F, 11.0F));
        mushroom2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(SuspiciousSpider entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        this.rightHindLeg.zRot = (float) (-Math.PI / 4);
        this.leftHindLeg.zRot = (float) (Math.PI / 4);
        this.rightMiddleLeg.zRot = -0.58119464F;
        this.leftMiddleLeg.zRot = 0.58119464F;
        this.rightMiddleFrontLeg.zRot = -0.58119464F;
        this.leftMiddleFrontLeg.zRot = 0.58119464F;
        this.rightFrontLeg.zRot = (float) (-Math.PI / 4);
        this.leftFrontLeg.zRot = (float) (Math.PI / 4);
        this.rightHindLeg.yRot = (float) (Math.PI / 4);
        this.leftHindLeg.yRot = (float) (-Math.PI / 4);
        this.rightMiddleLeg.yRot = (float) (Math.PI / 8);
        this.leftMiddleLeg.yRot = (float) (-Math.PI / 8);
        this.rightMiddleFrontLeg.yRot = (float) (-Math.PI / 8);
        this.leftMiddleFrontLeg.yRot = (float) (Math.PI / 8);
        this.rightFrontLeg.yRot = (float) (-Math.PI / 4);
        this.leftFrontLeg.yRot = (float) (Math.PI / 4);
        float f = -(Mth.cos(limbSwing * 0.6662F * 2.0F) * 0.4F) * limbSwingAmount;
        float g = -(Mth.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float h = -(Mth.cos(limbSwing * 0.6662F * 2.0F + (float) (Math.PI / 2)) * 0.4F) * limbSwingAmount;
        float i = -(Mth.cos(limbSwing * 0.6662F * 2.0F + (float) (Math.PI * 1.5)) * 0.4F) * limbSwingAmount;
        float j = Math.abs(Mth.sin(limbSwing * 0.6662F) * 0.4F) * limbSwingAmount;
        float k = Math.abs(Mth.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float l = Math.abs(Mth.sin(limbSwing * 0.6662F + (float) (Math.PI / 2)) * 0.4F) * limbSwingAmount;
        float m = Math.abs(Mth.sin(limbSwing * 0.6662F + (float) (Math.PI * 1.5)) * 0.4F) * limbSwingAmount;
        this.rightHindLeg.yRot += f;
        this.leftHindLeg.yRot += -f;
        this.rightMiddleLeg.yRot += g;
        this.leftMiddleLeg.yRot += -g;
        this.rightMiddleFrontLeg.yRot += h;
        this.leftMiddleFrontLeg.yRot += -h;
        this.rightFrontLeg.yRot += i;
        this.leftFrontLeg.yRot += -i;
        this.rightHindLeg.zRot += j;
        this.leftHindLeg.zRot += -j;
        this.rightMiddleLeg.zRot += k;
        this.leftMiddleLeg.zRot += -k;
        this.rightMiddleFrontLeg.zRot += l;
        this.leftMiddleFrontLeg.zRot += -l;
        this.rightFrontLeg.zRot += m;
        this.leftFrontLeg.zRot += -m;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
