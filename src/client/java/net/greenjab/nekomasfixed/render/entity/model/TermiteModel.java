package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.render.entity.animation.TermiteAnimations;
import net.greenjab.nekomasfixed.render.entity.state.TermiteRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TermiteModel<T extends HostileEntity> extends EntityModel<TermiteRenderState> {
    public static final EntityModelLayer TERMITE = new EntityModelLayer(Identifier.of("nekomasfixed", "termite"), "main");
    private final ModelPart bone;
    private final ModelPart body;
    private final ModelPart sack;
    private final ModelPart head;
    private final ModelPart antler;
    private final ModelPart pincher;
    private final ModelPart legs;
    private final ModelPart front_right_leg;
    private final ModelPart front_left_leg;
    private final ModelPart middle_right_leg;
    private final ModelPart middle_left_leg;
    private final ModelPart back_right_leg;
    private final ModelPart back_left_leg;
    private final Animation runAnimationState;
    private final Animation swipeAnimationState;
    private final Animation idleAnimationState;
    public TermiteModel(ModelPart root) {
        super(root);

        this.bone = root.getChild("bone");
        this.runAnimationState = TermiteAnimations.ANIM_TERMITE_RUN.createAnimation(root);
        this.swipeAnimationState = TermiteAnimations.ANIM_TERMITE_SWIPE.createAnimation(root);
        this.idleAnimationState = TermiteAnimations.ANIM_TERMITE_IDLE.createAnimation(root);
        this.body = this.bone.getChild("body");
        this.sack = this.body.getChild("sack");
        this.head = this.body.getChild("head");
        this.antler = this.head.getChild("antler");
        this.pincher = this.head.getChild("pincher");
        this.legs = this.body.getChild("legs");
        this.front_right_leg = this.legs.getChild("front_right_leg");
        this.front_left_leg = this.legs.getChild("front_left_leg");
        this.middle_right_leg = this.legs.getChild("middle_right_leg");
        this.middle_left_leg = this.legs.getChild("middle_left_leg");
        this.back_right_leg = this.legs.getChild("back_right_leg");
        this.back_left_leg = this.legs.getChild("back_left_leg");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData body = bone.addChild("body", ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -2.75F, -1.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -1.0F, 0.0F));

        ModelPartData sack = body.addChild("sack", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -1.5F, 2.5F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -1.5F, -1.0F));

        ModelPartData antler = head.addChild("antler", ModelPartBuilder.create(), ModelTransform.origin(1.5F, -1.5F, -4.0F));

        ModelPartData cube_r1 = antler.addChild("cube_r1", ModelPartBuilder.create().uv(20, 6).cuboid(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(20, 6).cuboid(2.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        ModelPartData pincher = head.addChild("pincher", ModelPartBuilder.create().uv(18, 20).cuboid(-2.0F, -1.3333F, -0.9167F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 21).cuboid(-2.0F, -0.3333F, -1.9167F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 14).cuboid(-2.0F, -0.3333F, -1.9167F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(16, 14).cuboid(1.0F, -0.3333F, -1.9167F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(2, 21).cuboid(2.0F, -0.3333F, -1.9167F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 20).cuboid(2.0F, -1.3333F, -0.9167F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.8333F, -4.0833F));

        ModelPartData legs = body.addChild("legs", ModelPartBuilder.create(), ModelTransform.origin(1.43F, -0.6F, -0.5F));

        ModelPartData front_right_leg = legs.addChild("front_right_leg", ModelPartBuilder.create().uv(16, 8).cuboid(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.origin(0.0F, 0.0F, -1.0F));

        ModelPartData cube_r2 = front_right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(12, 15).cuboid(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.11F)), ModelTransform.of(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        ModelPartData front_left_leg = legs.addChild("front_left_leg", ModelPartBuilder.create().uv(16, 12).cuboid(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-4.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r3 = front_left_leg.addChild("cube_r3", ModelPartBuilder.create().uv(16, 10).cuboid(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.11F)), ModelTransform.of(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        ModelPartData middle_right_leg = legs.addChild("middle_right_leg", ModelPartBuilder.create().uv(18, 14).cuboid(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.origin(0.0F, 0.0F, 1.0F));

        ModelPartData cube_r4 = middle_right_leg.addChild("cube_r4", ModelPartBuilder.create().uv(12, 17).cuboid(0.2248F, -0.6434F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.11F)), ModelTransform.of(-0.35F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        ModelPartData middle_left_leg = legs.addChild("middle_left_leg", ModelPartBuilder.create().uv(18, 18).cuboid(-1.18F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-3.5F, 0.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r5 = middle_left_leg.addChild("cube_r5", ModelPartBuilder.create().uv(18, 16).cuboid(0.4296F, -0.7868F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.11F)), ModelTransform.of(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        ModelPartData back_right_leg = legs.addChild("back_right_leg", ModelPartBuilder.create().uv(20, 0).cuboid(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.origin(0.0F, 0.0F, 3.0F));

        ModelPartData cube_r6 = back_right_leg.addChild("cube_r6", ModelPartBuilder.create().uv(12, 19).cuboid(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.11F)), ModelTransform.of(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));

        ModelPartData back_left_leg = legs.addChild("back_left_leg", ModelPartBuilder.create().uv(20, 4).cuboid(-1.68F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-4.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r7 = back_left_leg.addChild("cube_r7", ModelPartBuilder.create().uv(20, 2).cuboid(0.02F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.11F)), ModelTransform.of(-0.1F, -0.05F, 0.0F, 0.0F, 0.0F, 0.6109F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(TermiteRenderState state) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        // Head rotation
        this.setHeadAngles(state.bodyYaw, state.pitch);

        // Animations
        this.idleAnimationState.apply(state.idleAnimationState, state.age);
        this.runAnimationState.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 8f, 3.5f);
        this.swipeAnimationState.apply((long) state.age, 1.5f);
    }


    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        bone.render(matrices, vertexConsumer, light, overlay);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    public ModelPart getPart(){
        return bone;
    }
}
