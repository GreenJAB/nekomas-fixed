package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IllagerEntityModel.class)
public abstract class IllagerEntityModelMixin {

    @WrapOperation(method = "setAngles(Lnet/minecraft/client/render/entity/state/IllagerEntityRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ArmPosing;meleeAttack(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/util/Arm;FF)V"))
    private <S extends IllagerEntityRenderState> void spearArmPose(ModelPart rightArm, ModelPart leftArm, Arm mainArm, float swingProgress, float animationProgress, Operation<Void> original, @Local(argsOnly = true) S state) {
        if(state.getItemStackForArm(mainArm).isIn(ItemTags.SPEARS))rightArm.pitch = (float) (Math.PI / 180.0) * (-25);
        else original.call(rightArm, leftArm, mainArm, swingProgress, animationProgress);
    }
}