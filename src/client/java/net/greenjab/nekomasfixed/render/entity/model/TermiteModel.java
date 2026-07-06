package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.render.entity.animation.TermiteAnimations;
import net.greenjab.nekomasfixed.render.entity.state.TermiteRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class TermiteModel extends EntityModel<TermiteRenderState> {
    private final ModelPart head;
    private final ModelPart front_right_leg;
    private final ModelPart front_left_leg;
    private final ModelPart middle_right_leg;
    private final ModelPart middle_left_leg;
    private final ModelPart back_right_leg;
    private final ModelPart back_left_leg;

    private final KeyframeAnimation swipingAnimation;
    public TermiteModel(ModelPart root) {
        super(root);
        ModelPart bone = root.getChild("bone");
        ModelPart body = bone.getChild("body");
        this.head = body.getChild("head");
        ModelPart legs = body.getChild("legs");
        this.front_right_leg = legs.getChild("front_right_leg");
        this.front_left_leg = legs.getChild("front_left_leg");
        this.middle_right_leg = legs.getChild("middle_right_leg");
        this.middle_left_leg = legs.getChild("middle_left_leg");
        this.back_right_leg = legs.getChild("back_right_leg");
        this.back_left_leg = legs.getChild("back_left_leg");

        this.swipingAnimation = TermiteAnimations.ANIM_TERMITE_SWIPE.bake(root);
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone = modelPartData.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(17, 0).addBox(-2.0F, -2.75F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 0.0F));

        body.addOrReplaceChild("sack", CubeListBuilder.create().texOffs(4, 19).addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.5F, 2.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 0).addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.5F, -1.0F));

        PartDefinition antler = head.addOrReplaceChild("antler", CubeListBuilder.create(), PartPose.offset(1.5F, -1.5F, -4.0F));

        antler.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 7).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(1, 7).addBox(2.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        head.addOrReplaceChild("pincher", CubeListBuilder.create().texOffs(1, 6).addBox(-2.0F, -1.3333F, -0.9167F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 7).addBox(-2.0F, -0.3333F, -1.9167F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 8).addBox(-2.0F, -0.3333F, -1.9167F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(1, 8).addBox(1.0F, -0.3333F, -1.9167F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(1, 7).addBox(2.0F, -0.3333F, -1.9167F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 6).addBox(2.0F, -1.3333F, -0.9167F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.8333F, -4.0833F));

        PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(1.43F, -0.6F, -0.5F));

        PartDefinition front_right_leg = legs.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(14, 11).addBox(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, -1.0F));
        front_right_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(13, 17).addBox(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition front_left_leg = legs.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(13, 17).addBox(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));
        front_left_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(13, 17).addBox(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition middle_right_leg = legs.addOrReplaceChild("middle_right_leg", CubeListBuilder.create().texOffs(13, 17).addBox(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 1.0F));
        middle_right_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(13, 17).addBox(0.2248F, -0.6434F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-0.35F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition middle_left_leg = legs.addOrReplaceChild("middle_left_leg", CubeListBuilder.create().texOffs(13, 17).addBox(-1.18F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.5F, 0.0F, 1.0F, 0.0F, 3.1416F, 0.0F));
        middle_left_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(13, 17).addBox(0.4296F, -0.7868F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition back_right_leg = legs.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(4, 11).addBox(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 3.0F));
        back_right_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(13, 17).addBox(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition back_left_leg = legs.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(6, 18).addBox(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));
        back_left_leg.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(13, 17).addBox(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));
        return LayerDefinition.create(modelData, 32, 32);
    }


    @Override
    public void setupAnim(@NonNull TermiteRenderState state) {
        super.setupAnim(state);

        float swing = state.walkAnimationPos;
        float amount = state.walkAnimationSpeed;

        float speed = 4.0F;
        float degree = 1F;
        this.front_right_leg.xRot = Mth.cos(swing * speed) * degree * amount;
        this.middle_right_leg.xRot = Mth.cos(swing * speed + (float)Math.PI) * degree * amount;
        this.back_right_leg.xRot = Mth.cos(swing * speed) * degree * amount;
        this.front_left_leg.xRot = Mth.cos(swing * speed + (float)Math.PI) * degree * amount;
        this.middle_left_leg.xRot = Mth.cos(swing * speed) * degree * amount;
        this.back_left_leg.xRot = Mth.cos(swing * speed + (float)Math.PI) * degree * amount;

        float headYaw = Mth.clamp(state.yRot, -30.0F, 30.0F);
        float headPitch = Mth.clamp(state.xRot, -25.0F, 45.0F);
        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;

        this.swipingAnimation.apply(state.swipeAnimationState, state.ageInTicks);
    }

}
