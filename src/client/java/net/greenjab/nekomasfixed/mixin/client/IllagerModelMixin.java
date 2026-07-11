package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IllagerModel.class)
public abstract class IllagerModelMixin {

    @WrapOperation(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/IllagerRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/AnimationUtils;swingWeaponDown(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/world/entity/HumanoidArm;FF)V"))
    private <S extends IllagerRenderState> void spearArmPose(ModelPart rightArm, ModelPart leftArm, HumanoidArm mainArm, float attackTime, float ageInTicks, Operation<Void> original, @Local(argsOnly = true) S state) {
        if(state.getUseItemStackForArm(mainArm).is(ItemTags.SPEARS))rightArm.xRot = (float) (Math.PI / 180.0) * (-25);
        else original.call(rightArm, leftArm, mainArm, attackTime, ageInTicks);
    }
}