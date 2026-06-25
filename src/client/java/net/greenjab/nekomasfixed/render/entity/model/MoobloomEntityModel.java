package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.render.entity.state.MoobloomEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BabyModelTransformer;
import net.minecraft.client.render.entity.model.ModelTransformer;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.util.math.MathHelper;

import java.util.Set;

public class MoobloomEntityModel extends QuadrupedEntityModel<MoobloomEntityRenderState> {
    public static final ModelTransformer BABY_TRANSFORMER = new BabyModelTransformer(false, 8.0F, 6.0F, Set.of("head"));

    private final ModelPart head;
    protected boolean child;
    private final ModelPart right_hind_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_front_leg;
    public MoobloomEntityModel(ModelPart root) {
        super(root);
        this.head = this.root.getChild("head");
        this.right_hind_leg = this.root.getChild("right_hind_leg");
        this.left_hind_leg = this.root.getChild("left_hind_leg");
        this.right_front_leg = this.root.getChild("right_front_leg");
        this.left_front_leg = this.root.getChild("left_front_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
    }

    public static TexturedModelData getBabyTexturedModelData() {
        ModelData modelData = getModelData();
        modelData.transform(BABY_TRANSFORMER);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 2.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(22, 0).cuboid(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.0F))
                .uv(22, 0).cuboid(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 4.0F, -8.0F) );
        ModelPartData body = modelPartData.addChild("body",ModelPartBuilder.create(),ModelTransform.origin(0.0F, 5.0F, 2.0F));
        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(-4.0F, 12.0F, 7.0F));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(4.0F, 12.0F, 7.0F));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(-4.0F, 12.0F, -5.0F));
        modelPartData.addChild("left_front_leg",ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(4.0F, 12.0F, -5.0F));

        ModelPartData rotation = body.addChild("rotation", ModelPartBuilder.create().uv(18, 4).cuboid(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new Dilation(0.0F))
                .uv(52, 0).cuboid(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData flower1 = rotation.addChild("flower1", ModelPartBuilder.create().uv(0, 32).cuboid(-7.975F, -16.0F, 1.2F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.025F, -2.0F, 2.8F, -1.5708F, 0.0F, 0.0F));
        flower1.addChild("cube_r1", ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.025F, -8.0F, 1.2F, 0.0F, 3.1416F, 0.0F));

        ModelPartData flower2 = rotation.addChild("flower2", ModelPartBuilder.create().uv(0, 32).cuboid(-5.95F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.95F, 5.0F, 3.0F, -1.5708F, 0.0F, 0.7854F));
        flower2.addChild("cube_r2", ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(2.05F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        
        ModelPartData flower3 = head.addChild("flower3", ModelPartBuilder.create().uv(0, 32).cuboid(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, -3.2F, 0.0F, -0.576F, 0.0F));
        flower3.addChild("cube_r3", ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return modelData;
    }

    @Override
    public void setAngles(MoobloomEntityRenderState state) {
        this.child = state.baby;

        super.setAngles(state);

        float swing = state.limbSwingAnimationProgress;
        float amount = state.limbSwingAmplitude;

        this.right_hind_leg.pitch = MathHelper.cos(swing * 0.6662F) * 1.4F * amount;
        this.left_hind_leg.pitch  = MathHelper.cos(swing * 0.6662F + (float)Math.PI) * 1.4F * amount;

        this.right_front_leg.pitch = MathHelper.cos(swing * 0.6662F + (float)Math.PI) * 1.4F * amount;
        this.left_front_leg.pitch  = MathHelper.cos(swing * 0.6662F) * 1.4F * amount;

        this.setHeadAngles(state.bodyYaw, state.pitch);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    public ModelPart getHead() {
        return this.head;
    }
}
