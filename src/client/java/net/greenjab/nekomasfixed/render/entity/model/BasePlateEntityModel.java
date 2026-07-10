package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.player.PlayerModel;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BasePlateEntityModel extends TargetDummyArmorEntityModel {

	private final ModelPart basePlate;

	public BasePlateEntityModel(ModelPart modelPart) {
		super(modelPart);
		this.basePlate = modelPart.getChild("base_plate");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = PlayerModel.createMesh(CubeDeformation.NONE, false);
		PartDefinition modelPartData = modelData.getRoot().clearRecursively();
		modelPartData.addOrReplaceChild("base_plate", CubeListBuilder.create().texOffs(0, 32)
				.addBox(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void setupAnim(@NonNull TargetDummyEntityRenderState targetDummyEntityRenderState) {
		super.setupAnim(targetDummyEntityRenderState);
		this.basePlate.yRot = (float) (Math.PI / 180.0) * -targetDummyEntityRenderState.yaw;
	}
}