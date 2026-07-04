package net.greenjab.nekomasfixed.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.math.MathHelper;

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
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
        this.rightHindLeg = modelPart.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = modelPart.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightMiddleLeg = modelPart.getChild("right_middle_hind_leg");
        this.leftMiddleLeg = modelPart.getChild("left_middle_hind_leg");
        this.rightMiddleFrontLeg = modelPart.getChild("right_middle_front_leg");
        this.leftMiddleFrontLeg = modelPart.getChild("left_middle_front_leg");
        this.rightFrontLeg = modelPart.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = modelPart.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(32, 4).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F), ModelTransform.origin(0.0F, 15.0F, -3.0F)
        );
        ModelPartData root = modelPartData.addChild("body0", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), ModelTransform.origin(0.0F, 15.0F, 0.0F));
        modelPartData.addChild("body1", ModelPartBuilder.create().uv(0, 12).cuboid(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F), ModelTransform.origin(0.0F, 15.0F, 9.0F));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(18, 0).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(18, 0).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        modelPartData.addChild(
                EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.of(-4.0F, 15.0F, 2.0F, 0.0F, (float) (Math.PI / 4), (float) (-Math.PI / 4))
        );
        modelPartData.addChild(
                EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder2, ModelTransform.of(4.0F, 15.0F, 2.0F, 0.0F, (float) (-Math.PI / 4), (float) (Math.PI / 4))
        );
        modelPartData.addChild("right_middle_hind_leg", modelPartBuilder, ModelTransform.of(-4.0F, 15.0F, 1.0F, 0.0F, (float) (Math.PI / 8), -0.58119464F));
        modelPartData.addChild("left_middle_hind_leg", modelPartBuilder2, ModelTransform.of(4.0F, 15.0F, 1.0F, 0.0F, (float) (-Math.PI / 8), 0.58119464F));
        modelPartData.addChild("right_middle_front_leg", modelPartBuilder, ModelTransform.of(-4.0F, 15.0F, 0.0F, 0.0F, (float) (-Math.PI / 8), -0.58119464F));
        modelPartData.addChild("left_middle_front_leg", modelPartBuilder2, ModelTransform.of(4.0F, 15.0F, 0.0F, 0.0F, (float) (Math.PI / 8), 0.58119464F));
        modelPartData.addChild(
                EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.of(-4.0F, 15.0F, -1.0F, 0.0F, (float) (-Math.PI / 4), (float) (-Math.PI / 4))
        );
        modelPartData.addChild(
                EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder2, ModelTransform.of(4.0F, 15.0F, -1.0F, 0.0F, (float) (Math.PI / 4), (float) (Math.PI / 4))
        );

        ModelPartData mushroom1 = root.addChild("mushroom1", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, 4.0F, 0.0F, 8.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -7.0F, 0.0F));
        mushroom1.addChild("cube_r1", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom1.addChild("cube_r2", ModelPartBuilder.create().uv(5, 42).cuboid(-5.0F, -2.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData mushroom2 = mushroom1.addChild("mushroom2", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, -1.0F, 11.0F));
        mushroom2.addChild("cube_r3", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom2.addChild("cube_r4", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(LivingEntityRenderState livingEntityRenderState) {
        super.setAngles(livingEntityRenderState);
        this.head.yaw = livingEntityRenderState.relativeHeadYaw * (float) (Math.PI / 180.0);
        this.head.pitch = livingEntityRenderState.pitch * (float) (Math.PI / 180.0);
        float f = livingEntityRenderState.limbSwingAnimationProgress * 0.6662F;
        float g = livingEntityRenderState.limbSwingAmplitude;
        float h = -(MathHelper.cos(f * 2.0F + 0.0F) * 0.4F) * g;
        float i = -(MathHelper.cos(f * 2.0F + (float) Math.PI) * 0.4F) * g;
        float j = -(MathHelper.cos(f * 2.0F + (float) (Math.PI / 2)) * 0.4F) * g;
        float k = -(MathHelper.cos(f * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * g;
        float l = Math.abs(MathHelper.sin(f + 0.0F) * 0.4F) * g;
        float m = Math.abs(MathHelper.sin(f + (float) Math.PI) * 0.4F) * g;
        float n = Math.abs(MathHelper.sin(f + (float) (Math.PI / 2)) * 0.4F) * g;
        float o = Math.abs(MathHelper.sin(f + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * g;
        this.rightHindLeg.yaw += h;
        this.leftHindLeg.yaw -= h;
        this.rightMiddleLeg.yaw += i;
        this.leftMiddleLeg.yaw -= i;
        this.rightMiddleFrontLeg.yaw += j;
        this.leftMiddleFrontLeg.yaw -= j;
        this.rightFrontLeg.yaw += k;
        this.leftFrontLeg.yaw -= k;
        this.rightHindLeg.roll += l;
        this.leftHindLeg.roll -= l;
        this.rightMiddleLeg.roll += m;
        this.leftMiddleLeg.roll -= m;
        this.rightMiddleFrontLeg.roll += n;
        this.leftMiddleFrontLeg.roll -= n;
        this.rightFrontLeg.roll += o;
        this.leftFrontLeg.roll -= o;
    }

}