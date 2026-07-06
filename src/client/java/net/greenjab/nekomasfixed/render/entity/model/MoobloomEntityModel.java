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
import net.minecraft.util.Mth;
import java.util.Set;

public class MoobloomEntityModel extends QuadrupedModel<MoobloomEntityRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(false, 8.0F, 6.0F, Set.of("head"));

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

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -8.0F) );
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

        PartDefinition flower1 = rotation.addOrReplaceChild("flower1", CubeListBuilder.create().texOffs(0, 32).addBox(-7.975F, -16.0F, 1.2F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.025F, -2.0F, 2.8F, -1.5708F, 0.0F, 0.0F));
        flower1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, -8.0F, 1.2F, 0.0F, 3.1416F, 0.0F));

        PartDefinition flower2 = rotation.addOrReplaceChild("flower2", CubeListBuilder.create().texOffs(0, 32).addBox(-5.95F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.95F, 5.0F, 3.0F, -1.5708F, 0.0F, 0.7854F));
        flower2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.05F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        
        PartDefinition flower3 = head.addOrReplaceChild("flower3", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -3.2F, 0.0F, -0.576F, 0.0F));
        flower3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return modelData;
    }

    @Override
    public void setupAnim(MoobloomEntityRenderState state) {
        this.child = state.baby;

        super.setupAnim(state);

        float swing = state.walkAnimationPos;
        float amount = state.walkAnimationSpeed;

        this.right_hind_leg.xRot = Mth.cos(swing * 0.6662F) * 1.4F * amount;
        this.left_hind_leg.xRot  = Mth.cos(swing * 0.6662F + (float)Math.PI) * 1.4F * amount;

        this.right_front_leg.xRot = Mth.cos(swing * 0.6662F + (float)Math.PI) * 1.4F * amount;
        this.left_front_leg.xRot  = Mth.cos(swing * 0.6662F) * 1.4F * amount;

        this.setHeadAngles(state.bodyRot, state.xRot);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 45.0F);

        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }

    public ModelPart getHead() {
        return this.head;
    }
}
