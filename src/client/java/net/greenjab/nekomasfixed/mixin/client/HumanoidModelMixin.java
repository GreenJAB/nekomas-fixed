package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.model.HumanoidModel.ArmPose.BOW_AND_ARROW;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends HumanoidRenderState> {

    @Inject(method = "poseRightArm", at = @At(value = "HEAD"), cancellable = true)
    private void rightArmSlingshot(T state, CallbackInfo ci) {
        if (state.rightArmPose == BOW_AND_ARROW) {
            HumanoidModel<?> BEM = (HumanoidModel<?>) (Object) this;
            charge(BEM.rightArm, BEM.leftArm, BEM.head, state.maxCrossbowChargeDuration, state.ticksUsingItem, true, state.rightHandItemStack.is(ItemRegistry.SLINGSHOT));
            ci.cancel();
        }
    }

    @Inject(method = "poseLeftArm", at = @At(value = "HEAD"), cancellable = true)
    private void leftArmSlingshot(T state, CallbackInfo ci) {
        if (state.leftArmPose == BOW_AND_ARROW) {
            HumanoidModel<?> BEM = (HumanoidModel<?>) (Object) this;
            charge(BEM.rightArm, BEM.leftArm, BEM.head, state.maxCrossbowChargeDuration, state.ticksUsingItem, false, state.leftHandItemStack.is(ItemRegistry.SLINGSHOT));
            ci.cancel();
        }
    }

    @Unique
    private static void charge(ModelPart holdingArm, ModelPart pullingArm,ModelPart head, float crossbowPullTime, float f, boolean rightArm, boolean slingshot) {
        ModelPart modelPart = rightArm ? holdingArm : pullingArm;
        ModelPart modelPart2 = rightArm ? pullingArm : holdingArm;

        modelPart.xRot = Mth.clamp(head.xRot, -1.2F, 1.2F) - 1.4835298F;
        modelPart.yRot = 0.7f*head.yRot - (float) (Math.PI / 12) * (rightArm ? 1 : -1);

        modelPart2.xRot = modelPart.xRot+(slingshot?-0.7f:0);
        float g = Mth.clamp(f, 0.0F, crossbowPullTime);
        float h = g / crossbowPullTime;
        modelPart2.yRot = (float) (-Math.sin(modelPart2.xRot)*Mth.lerp(h, 0.4F, 0.85F) * (rightArm ? 1 : -1));
        modelPart2.zRot = (float) (Math.cos(modelPart2.xRot)*Mth.lerp(h, 0.4F, 0.85F) * (rightArm ? 1 : -1));
    }
}