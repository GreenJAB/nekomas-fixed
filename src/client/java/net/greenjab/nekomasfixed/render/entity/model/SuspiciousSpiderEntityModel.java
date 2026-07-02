package net.greenjab.nekomasfixed.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.math.MathHelper;

public class SuspiciousSpiderEntityModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart head;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart leg5;
    private final ModelPart leg6;
    private final ModelPart leg7;

    public SuspiciousSpiderEntityModel(ModelPart root) {
        super(root);
        ModelPart root1 = root.getChild("root");
        this.head = root1.getChild("head");
        this.leg0 = root1.getChild("leg0");
        this.leg1 = root1.getChild("leg1");
        this.leg2 = root1.getChild("leg2");
        this.leg3 = root1.getChild("leg3");
        this.leg4 = root1.getChild("leg4");
        this.leg5 = root1.getChild("leg5");
        this.leg6 = root1.getChild("leg6");
        this.leg7 = root1.getChild("leg7");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(1, 1).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 15.0F, 0.0F));

        root.addChild("head", ModelPartBuilder.create().uv(33, 5).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, -3.0F));
        root.addChild("body1", ModelPartBuilder.create().uv(1, 13).cuboid(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 9.0F));

        root.addChild("leg0", ModelPartBuilder.create().uv(19, 1).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, 2.0F, 0.0F, 0.7854F, -0.7854F));
        root.addChild("leg1", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, 0.0F, 2.0F, 0.0F, -0.7854F, 0.7854F));
        root.addChild("leg2", ModelPartBuilder.create().uv(19, 1).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, 1.0F, 0.0F, 0.3927F, -0.5812F));
        root.addChild("leg3", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, 0.0F, 1.0F, 0.0F, -0.3927F, 0.5812F));
        root.addChild("leg4", ModelPartBuilder.create().uv(19, 1).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, 0.0F, 0.0F, -0.3927F, -0.5812F));
        root.addChild("leg5", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.5812F));
        root.addChild("leg6", ModelPartBuilder.create().uv(19, 1).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, -1.0F, 0.0F, -0.7854F, -0.7854F));
        root.addChild("leg7", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, 0.0F, -1.0F, 0.0F, 0.7854F, 0.7854F));

        ModelPartData mushroom1 = root.addChild("mushroom1", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, 4.0F, 0.0F, 8.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -7.0F, 0.0F));
        mushroom1.addChild("cube_r1", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom1.addChild("cube_r2", ModelPartBuilder.create().uv(5, 42).cuboid(-5.0F, -2.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData mushroom2 = mushroom1.addChild("mushroom2", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, -1.0F, 11.0F));
        mushroom2.addChild("cube_r3", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        mushroom2.addChild("cube_r4", ModelPartBuilder.create().uv(5, 42).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        return TexturedModelData.of(modelData, 66, 51);
    }

    @Override
    public void setAngles(LivingEntityRenderState livingEntityRenderState) {
        super.setAngles(livingEntityRenderState);

        this.head.yaw = livingEntityRenderState.relativeHeadYaw * ((float)Math.PI / 180F);
        this.head.pitch = livingEntityRenderState.pitch * ((float)Math.PI / 180F);

        float f = livingEntityRenderState.limbSwingAnimationProgress * 0.6662F;
        float g = livingEntityRenderState.limbSwingAmplitude;

        float h = -(MathHelper.cos(f * 2.0F + 0.0F) * 0.4F) * g;
        float i = -(MathHelper.cos(f * 2.0F + (float)Math.PI) * 0.4F) * g;
        float j = -(MathHelper.cos(f * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * g;
        float k = -(MathHelper.cos(f * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * g;

        float l = Math.abs(MathHelper.sin(f + 0.0F) * 0.4F) * g;
        float m = Math.abs(MathHelper.sin(f + (float)Math.PI) * 0.4F) * g;
        float n = Math.abs(MathHelper.sin(f + ((float)Math.PI / 2F)) * 0.4F) * g;
        float o = Math.abs(MathHelper.sin(f + ((float)Math.PI * 1.5F)) * 0.4F) * g;

        this.leg0.yaw += h;
        this.leg1.yaw -= h;

        this.leg2.yaw += i;
        this.leg3.yaw -= i;

        this.leg4.yaw += j;
        this.leg5.yaw -= j;

        this.leg6.yaw += k;
        this.leg7.yaw -= k;

        this.leg0.roll += l;
        this.leg1.roll -= l;

        this.leg2.roll += m;
        this.leg3.roll -= m;

        this.leg4.roll += n;
        this.leg5.roll -= n;

        this.leg6.roll += o;
        this.leg7.roll -= o;
    }

}