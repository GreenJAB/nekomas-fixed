package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EquipmentModelData;

@Environment(EnvType.CLIENT)
public class TargetDummyArmorEntityModel extends BipedEntityModel<TargetDummyEntityRenderState> {
	public TargetDummyArmorEntityModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static EquipmentModelData<TexturedModelData> getEquipmentModelData(Dilation hatDilation, Dilation armorDilation) {
		return createEquipmentModelData(TargetDummyArmorEntityModel::getTexturedModelData, hatDilation, armorDilation)
				.map( modelData -> TexturedModelData.of(modelData, 64, 32));
	}

	private static ModelData getTexturedModelData(Dilation dilation) {
        return BipedEntityModel.getModelData(dilation, 0.0F);
	}

	public void setAngles(TargetDummyEntityRenderState targetDummyEntityRenderState) {
		super.setAngles(targetDummyEntityRenderState);
		this.head.pitch = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.headRotation.pitch();
		this.head.yaw = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.headRotation.yaw();
		this.head.roll = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.headRotation.roll();
		this.body.pitch = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.bodyRotation.pitch();
		this.body.yaw = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.bodyRotation.yaw();
		this.body.roll = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.bodyRotation.roll();
		this.leftArm.pitch = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftArmRotation.pitch();
		this.leftArm.yaw = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftArmRotation.yaw();
		this.leftArm.roll = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftArmRotation.roll();
		this.rightArm.pitch = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightArmRotation.pitch();
		this.rightArm.yaw = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightArmRotation.yaw();
		this.rightArm.roll = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightArmRotation.roll();
		this.leftLeg.pitch = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftLegRotation.pitch();
		this.leftLeg.yaw = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftLegRotation.yaw();
		this.leftLeg.roll = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftLegRotation.roll();
		this.rightLeg.pitch = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightLegRotation.pitch();
		this.rightLeg.yaw = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightLegRotation.yaw();
		this.rightLeg.roll = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightLegRotation.roll();
	}
}