package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class TargetDummyArmorModel extends HumanoidModel<TargetDummyRenderState> {
	public TargetDummyArmorModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static ArmorModelSet<LayerDefinition> getEquipmentModelData(CubeDeformation hatDilation, CubeDeformation armorDilation) {
		return createArmorMeshSet(TargetDummyArmorModel::getTexturedModelData, ADULT_ARMOR_PARTS_PER_SLOT, hatDilation, armorDilation)
				.map( modelData -> LayerDefinition.create(modelData, 64, 32));
	}

	private static MeshDefinition getTexturedModelData(CubeDeformation dilation) {
        return HumanoidModel.createMesh(dilation, 0.0F);
	}

	public void setupAnim(@NonNull TargetDummyRenderState targetDummyRenderState) {
		super.setupAnim(targetDummyRenderState);
		this.head.xRot = (float) (Math.PI / 180.0) * targetDummyRenderState.headRotation.x();
		this.head.yRot = (float) (Math.PI / 180.0) * targetDummyRenderState.headRotation.y();
		this.head.zRot = (float) (Math.PI / 180.0) * targetDummyRenderState.headRotation.z();
		this.body.xRot = (float) (Math.PI / 180.0) * targetDummyRenderState.bodyRotation.x();
		this.body.yRot = (float) (Math.PI / 180.0) * targetDummyRenderState.bodyRotation.y();
		this.body.zRot = (float) (Math.PI / 180.0) * targetDummyRenderState.bodyRotation.z();
		this.leftArm.xRot = (float) (Math.PI / 180.0) * targetDummyRenderState.leftArmRotation.x();
		this.leftArm.yRot = (float) (Math.PI / 180.0) * targetDummyRenderState.leftArmRotation.y();
		this.leftArm.zRot = (float) (Math.PI / 180.0) * targetDummyRenderState.leftArmRotation.z();
		this.rightArm.xRot = (float) (Math.PI / 180.0) * targetDummyRenderState.rightArmRotation.x();
		this.rightArm.yRot = (float) (Math.PI / 180.0) * targetDummyRenderState.rightArmRotation.y();
		this.rightArm.zRot = (float) (Math.PI / 180.0) * targetDummyRenderState.rightArmRotation.z();
		this.leftLeg.xRot = (float) (Math.PI / 180.0) * targetDummyRenderState.leftLegRotation.x();
		this.leftLeg.yRot = (float) (Math.PI / 180.0) * targetDummyRenderState.leftLegRotation.y();
		this.leftLeg.zRot = (float) (Math.PI / 180.0) * targetDummyRenderState.leftLegRotation.z();
		this.rightLeg.xRot = (float) (Math.PI / 180.0) * targetDummyRenderState.rightLegRotation.x();
		this.rightLeg.yRot = (float) (Math.PI / 180.0) * targetDummyRenderState.rightLegRotation.y();
		this.rightLeg.zRot = (float) (Math.PI / 180.0) * targetDummyRenderState.rightLegRotation.z();
	}
}