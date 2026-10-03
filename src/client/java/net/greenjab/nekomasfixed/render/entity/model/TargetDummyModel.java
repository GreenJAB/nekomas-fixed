package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class TargetDummyModel extends TargetDummyArmorModel {

	public TargetDummyModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition modelPartData = modelData.getRoot();

		CubeDeformation dilation = CubeDeformation.NONE;
		PartDefinition modelPartData0 = modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition modelPartData1 = modelPartData.getChild(PartNames.RIGHT_ARM);
		modelPartData0.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
		modelPartData1.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);

		PartDefinition modelPartData2 = modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(1.9F, 12.0F, 0.0F));
		PartDefinition modelPartData3 = modelPartData.getChild(PartNames.RIGHT_LEG);
		modelPartData2.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
		modelPartData3.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
		PartDefinition modelPartData4 = modelPartData.getChild(PartNames.BODY);
		modelPartData4.addOrReplaceChild(PartNames.JACKET, CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void setupAnim(@NonNull TargetDummyRenderState targetDummyRenderState) {
		super.setupAnim(targetDummyRenderState);
	}
}