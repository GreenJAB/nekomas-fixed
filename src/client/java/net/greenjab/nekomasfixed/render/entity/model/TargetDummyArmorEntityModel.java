package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class TargetDummyArmorEntityModel extends HumanoidModel<TargetDummyEntityRenderState> {
	public TargetDummyArmorEntityModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static ArmorModelSet<LayerDefinition> getEquipmentModelData(CubeDeformation hatDilation, CubeDeformation armorDilation) {
		return createArmorMeshSet(TargetDummyArmorEntityModel::getTexturedModelData, ADULT_ARMOR_PARTS_PER_SLOT, hatDilation, armorDilation)
				.map( modelData -> LayerDefinition.create(modelData, 64, 32));
	}

	private static MeshDefinition getTexturedModelData(CubeDeformation dilation) {
        return HumanoidModel.createMesh(dilation, 0.0F);
	}

	public void setupAnim(@NonNull TargetDummyEntityRenderState targetDummyEntityRenderState) {
		super.setupAnim(targetDummyEntityRenderState);
		this.head.xRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.headRotation.x();
		this.head.yRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.headRotation.y();
		this.head.zRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.headRotation.z();
		this.body.xRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.bodyRotation.x();
		this.body.yRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.bodyRotation.y();
		this.body.zRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.bodyRotation.z();
		this.leftArm.xRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftArmRotation.x();
		this.leftArm.yRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftArmRotation.y();
		this.leftArm.zRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftArmRotation.z();
		this.rightArm.xRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightArmRotation.x();
		this.rightArm.yRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightArmRotation.y();
		this.rightArm.zRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightArmRotation.z();
		this.leftLeg.xRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftLegRotation.x();
		this.leftLeg.yRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftLegRotation.y();
		this.leftLeg.zRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.leftLegRotation.z();
		this.rightLeg.xRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightLegRotation.x();
		this.rightLeg.yRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightLegRotation.y();
		this.rightLeg.zRot = (float) (Math.PI / 180.0) * targetDummyEntityRenderState.rightLegRotation.z();
	}
}