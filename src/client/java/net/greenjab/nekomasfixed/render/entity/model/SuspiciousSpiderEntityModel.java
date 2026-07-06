package net.greenjab.nekomasfixed.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class SuspiciousSpiderEntityModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleLeg;
    private final ModelPart leftMiddleLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public SuspiciousSpiderEntityModel(ModelPart modelPart) {
        super(modelPart);
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
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(
                PartNames.HEAD, CubeListBuilder.create().texOffs(32, 4).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 15.0F, -3.0F)
        );
        PartDefinition root = modelPartData.addOrReplaceChild("body0", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
        modelPartData.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F), PartPose.offset(0.0F, 15.0F, 9.0F));
        CubeListBuilder modelPartBuilder = CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        CubeListBuilder modelPartBuilder2 = CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        modelPartData.addOrReplaceChild(
                PartNames.RIGHT_HIND_LEG, modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, 2.0F, 0.0F, (float) (Math.PI / 4), (float) (-Math.PI / 4))
        );
        modelPartData.addOrReplaceChild(
                PartNames.LEFT_HIND_LEG, modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, 2.0F, 0.0F, (float) (-Math.PI / 4), (float) (Math.PI / 4))
        );
        modelPartData.addOrReplaceChild("right_middle_hind_leg", modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, 1.0F, 0.0F, (float) (Math.PI / 8), -0.58119464F));
        modelPartData.addOrReplaceChild("left_middle_hind_leg", modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, 1.0F, 0.0F, (float) (-Math.PI / 8), 0.58119464F));
        modelPartData.addOrReplaceChild("right_middle_front_leg", modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, 0.0F, 0.0F, (float) (-Math.PI / 8), -0.58119464F));
        modelPartData.addOrReplaceChild("left_middle_front_leg", modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, 0.0F, 0.0F, (float) (Math.PI / 8), 0.58119464F));
        modelPartData.addOrReplaceChild(
                PartNames.RIGHT_FRONT_LEG, modelPartBuilder, PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, 0.0F, (float) (-Math.PI / 4), (float) (-Math.PI / 4))
        );
        modelPartData.addOrReplaceChild(
                PartNames.LEFT_FRONT_LEG, modelPartBuilder2, PartPose.offsetAndRotation(4.0F, 15.0F, -1.0F, 0.0F, (float) (Math.PI / 4), (float) (Math.PI / 4))
        );

        PartDefinition mushroom1 = root.addOrReplaceChild("mushroom1", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, 4.0F, 0.0F, 8.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));
        mushroom1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 42).addBox(-5.0F, -2.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition mushroom2 = mushroom1.addOrReplaceChild("mushroom2", CubeListBuilder.create(), PartPose.offset(-2.0F, -1.0F, 11.0F));
        mushroom2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(5, 42).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(@NonNull LivingEntityRenderState livingEntityRenderState) {
        super.setupAnim(livingEntityRenderState);
        this.head.yRot = livingEntityRenderState.yRot * (float) (Math.PI / 180.0);
        this.head.xRot = livingEntityRenderState.xRot * (float) (Math.PI / 180.0);
        float f = livingEntityRenderState.walkAnimationPos * 0.6662F;
        float g = livingEntityRenderState.walkAnimationSpeed;
        float h = -(Mth.cos(f * 2.0F + 0.0F) * 0.4F) * g;
        float i = -(Mth.cos(f * 2.0F + (float) Math.PI) * 0.4F) * g;
        float j = -(Mth.cos(f * 2.0F + (float) (Math.PI / 2)) * 0.4F) * g;
        float k = -(Mth.cos(f * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * g;
        float l = Math.abs(Mth.sin(f + 0.0F) * 0.4F) * g;
        float m = Math.abs(Mth.sin(f + (float) Math.PI) * 0.4F) * g;
        float n = Math.abs(Mth.sin(f + (float) (Math.PI / 2)) * 0.4F) * g;
        float o = Math.abs(Mth.sin(f + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * g;
        this.rightHindLeg.yRot += h;
        this.leftHindLeg.yRot -= h;
        this.rightMiddleLeg.yRot += i;
        this.leftMiddleLeg.yRot -= i;
        this.rightMiddleFrontLeg.yRot += j;
        this.leftMiddleFrontLeg.yRot -= j;
        this.rightFrontLeg.yRot += k;
        this.leftFrontLeg.yRot -= k;
        this.rightHindLeg.zRot += l;
        this.leftHindLeg.zRot -= l;
        this.rightMiddleLeg.zRot += m;
        this.leftMiddleLeg.zRot -= m;
        this.rightMiddleFrontLeg.zRot += n;
        this.leftMiddleFrontLeg.zRot -= n;
        this.rightFrontLeg.zRot += o;
        this.leftFrontLeg.zRot -= o;
    }

}