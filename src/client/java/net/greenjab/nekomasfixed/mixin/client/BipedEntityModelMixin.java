package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose.BOW_AND_ARROW;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin <T extends BipedEntityRenderState> {

    @Inject(method = "positionRightArm", at = @At(value = "HEAD"), cancellable = true)
    private void rightArmSlingshot(T state, CallbackInfo ci) {
        if (state.rightArmPose == BOW_AND_ARROW) {
            BipedEntityModel<?> BEM = (BipedEntityModel<?>) (Object) this;
            charge(BEM.rightArm, BEM.leftArm, BEM.head, state.crossbowPullTime, state.itemUseTime, true, state.rightHandItem.isOf(ItemRegistry.SLINGSHOT));
            ci.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At(value = "HEAD"), cancellable = true)
    private void leftArmSlingshot(T state, CallbackInfo ci) {
        if (state.leftArmPose == BOW_AND_ARROW) {
            BipedEntityModel<?> BEM = (BipedEntityModel<?>) (Object) this;
            //if state.activeHand == Hand.MAIN_HAND
            charge(BEM.rightArm, BEM.leftArm, BEM.head, state.crossbowPullTime, state.itemUseTime, false, state.leftHandItem.isOf(ItemRegistry.SLINGSHOT));
            ci.cancel();
        }
    }

    @Unique
    private static void charge(ModelPart holdingArm, ModelPart pullingArm,ModelPart head, float crossbowPullTime, float f, boolean rightArm, boolean slingshot) {
        ModelPart modelPart = rightArm ? holdingArm : pullingArm;
        ModelPart modelPart2 = rightArm ? pullingArm : holdingArm;

        modelPart.pitch = MathHelper.clamp(head.pitch, -1.2F, 1.2F) - 1.4835298F;
        modelPart.yaw = 0.7f*head.yaw - (float) (Math.PI / 12) * (rightArm ? 1 : -1);

        modelPart2.pitch = modelPart.pitch+(slingshot?-0.7f:0);
        float g = MathHelper.clamp(f, 0.0F, crossbowPullTime);
        float h = g / crossbowPullTime;
        modelPart2.yaw = (float) (-Math.sin(modelPart2.pitch)*MathHelper.lerp(h, 0.4F, 0.85F) * (rightArm ? 1 : -1));
        modelPart2.roll = (float) (Math.cos(modelPart2.pitch)*MathHelper.lerp(h, 0.4F, 0.85F) * (rightArm ? 1 : -1));

    }
}