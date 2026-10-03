package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.HugeBoatRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class HugeBoatModel<S extends HugeBoatRenderState> extends BigBoatModel<S> {

	private final ModelPart leftPaddle3;
	private final ModelPart rightPaddle3;

	public HugeBoatModel(ModelPart modelPart) {
		super(modelPart);
		this.leftPaddle3 = modelPart.getChild("left_paddle3");
		this.rightPaddle3 = modelPart.getChild("right_paddle3");
	}

	public void setupAnim(@NonNull S hugeBoatEntityRenderState) {
		super.setupAnim(hugeBoatEntityRenderState);
		setPaddleAngles(hugeBoatEntityRenderState.leftPaddleAngle, 0, this.leftPaddle3, hugeBoatEntityRenderState.players>2);
		setPaddleAngles(hugeBoatEntityRenderState.rightPaddleAngle, 1, this.rightPaddle3, hugeBoatEntityRenderState.players>2);
	}

	public static void addParts(PartDefinition modelPartData) {
		modelPartData.addOrReplaceChild("boat_1", CubeListBuilder.create().texOffs(0, 4).addBox(-13F, 0F, -32F, 26F, 3F, 33F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_2", CubeListBuilder.create().texOffs(0, 0).addBox(-13F, 0F, 1F, 26F, 3F, 33F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_3", CubeListBuilder.create().texOffs(0, 3).addBox(12F, -6F, 34F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_4", CubeListBuilder.create().texOffs(0, 3).addBox(10F, -6F, 37F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_5", CubeListBuilder.create().texOffs(0, 0).addBox(9F, -6F, 40F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_6", CubeListBuilder.create().texOffs(0, 0).addBox(7F, -6F, 42F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_7", CubeListBuilder.create().texOffs(0, 0).addBox(5F, -6F, 44F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_8", CubeListBuilder.create().texOffs(0, 0).addBox(-7F, -6F, 44F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_9", CubeListBuilder.create().texOffs(0, 0).addBox(-7F, -6F, -44F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_10", CubeListBuilder.create().texOffs(0, 0).addBox(5F, -6F, -44F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_11", CubeListBuilder.create().texOffs(0, 0).addBox(-9F, -6F, 42F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_12", CubeListBuilder.create().texOffs(0, 0).addBox(-9F, -6F, -42F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_13", CubeListBuilder.create().texOffs(0, 0).addBox(7F, -6F, -42F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_14", CubeListBuilder.create().texOffs(0, 0).addBox(-11F, -6F, 40F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_15", CubeListBuilder.create().texOffs(0, 0).addBox(-11F, -6F, -40F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_16", CubeListBuilder.create().texOffs(0, 0).addBox(9F, -6F, -40F, 2F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_17", CubeListBuilder.create().texOffs(0, 3).addBox(-12F, -6F, 37F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_18", CubeListBuilder.create().texOffs(0, 3).addBox(-12F, -6F, -38F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_19", CubeListBuilder.create().texOffs(0, 3).addBox(10F, -6F, -38F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_20", CubeListBuilder.create().texOffs(0, 3).addBox(-14F, -6F, 34F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_21", CubeListBuilder.create().texOffs(0, 3).addBox(-14F, -6F, -35F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_22", CubeListBuilder.create().texOffs(0, 3).addBox(12F, -6F, -35F, 2F, 6F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_23", CubeListBuilder.create().texOffs(0, 0).addBox(-5F, -6F, 45F, 10F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_24", CubeListBuilder.create().texOffs(0, 0).addBox(-5F, -6F, -45F, 10F, 6F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_25", CubeListBuilder.create().texOffs(0, 1).addBox(-15F, -6F, -32F, 2F, 6F, 33F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_26", CubeListBuilder.create().texOffs(0, 1).addBox(-15F, -6F, 1F, 2F, 6F, 33F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_27", CubeListBuilder.create().texOffs(0, 1).addBox(13F, -6F, -32F, 2F, 6F, 33F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_28", CubeListBuilder.create().texOffs(0, 1).addBox(13F, -6F, 1F, 2F, 6F, 33F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_29", CubeListBuilder.create().texOffs(15, 5).addBox(-12F, 0F, -35F, 24F, 3F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_30", CubeListBuilder.create().texOffs(15, 13).addBox(-12F, 0F, 34F, 24F, 3F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_31", CubeListBuilder.create().texOffs(17, 8).addBox(-10F, 0F, -38F, 20F, 3F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_32", CubeListBuilder.create().texOffs(1, 10).addBox(-10F, 0F, 37F, 20F, 3F, 3F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_33", CubeListBuilder.create().texOffs(3, 11).addBox(-9F, 0F, -40F, 18F, 3F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_34", CubeListBuilder.create().texOffs(3, 8).addBox(-9F, 0F, 40F, 18F, 3F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_35", CubeListBuilder.create().texOffs(7, 12).addBox(-7F, 0F, -42F, 14F, 3F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_36", CubeListBuilder.create().texOffs(5, 6).addBox(-7F, 0F, 42F, 14F, 3F, 2F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_37", CubeListBuilder.create().texOffs(0, 0).addBox(-5F, 0F, -43F, 10F, 3F, 1F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_38", CubeListBuilder.create().texOffs(0, 0).addBox(-5F, 0F, 44F, 10F, 3F, 1F), PartPose.ZERO);

		modelPartData.addOrReplaceChild("stand_1", CubeListBuilder.create().texOffs(48, 64).addBox(-1F, -45F, 5F, 2F, 45F, 2F), PartPose.ZERO.scaled(0.999f));
		modelPartData.addOrReplaceChild("stand_2", CubeListBuilder.create().texOffs(48, 64).addBox(-1F, -35F, -18F, 2F, 35F, 2F), PartPose.ZERO.scaled(0.999f));

		float dist = 23f;
		modelPartData.addOrReplaceChild("left_paddle",
				CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(15.0F, -6.0F, -5.0F-dist, 0.0F, 0.0F, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("right_paddle",
				CubeListBuilder.create().texOffs(62, 20).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(-15.0F, -6.0F, -5.0F-dist, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("left_paddle2",
				CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(15.0F, -6.0F, -5.0F, 0.0F, 0.0F, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("right_paddle2",
				CubeListBuilder.create().texOffs(62, 20).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(-15.0F, -6.0F, -5.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("left_paddle3",
				CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(15.0F, -6.0F, -5.0F+dist, 0.0F, 0.0F, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("right_paddle3",
				CubeListBuilder.create().texOffs(62, 20).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(-15.0F, -6.0F, -5.0F+dist, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));
	}

	public static LayerDefinition getChestTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		addParts(modelPartData);
		modelPartData.addOrReplaceChild(PartNames.CHEST_BOTTOM,
				CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F),
				PartPose.offsetAndRotation(-6.0F, -8.0F, 31.0F, 0.0F, 0, 0.0F));
		modelPartData.addOrReplaceChild(PartNames.CHEST_LID,
				CubeListBuilder.create().texOffs(0, 75).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F),
				PartPose.offsetAndRotation(-6.0F, -12.0F, 31.0F, 0f, 0, 0.0F));
		modelPartData.addOrReplaceChild(PartNames.CHEST_LOCK,
				CubeListBuilder.create().texOffs(0, 75).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(-1.0F, -9.0F, 30.0F, 0.0F, 0, 0.0F));
		return LayerDefinition.create(modelData, 128, 128);
	}
}