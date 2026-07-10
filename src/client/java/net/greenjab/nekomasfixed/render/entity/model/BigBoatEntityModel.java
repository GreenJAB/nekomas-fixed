package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.BigBoatEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BigBoatEntityModel<S extends BigBoatEntityRenderState> extends EntityModel<S> {

	private final ModelPart chest_bottom;
	private final ModelPart chest_lid;
	private final ModelPart chest_lock;
	private final ModelPart leftPaddle;
	private final ModelPart rightPaddle;
	private final ModelPart leftPaddle2;
	private final ModelPart rightPaddle2;

	public BigBoatEntityModel(ModelPart modelPart) {
		super(modelPart);
		this.chest_bottom = modelPart.getChild("chest_bottom");
		this.chest_lid = modelPart.getChild("chest_lid");
		this.chest_lock = modelPart.getChild("chest_lock");

		this.leftPaddle = modelPart.getChild("left_paddle");
		this.rightPaddle = modelPart.getChild("right_paddle");
		this.leftPaddle2 = modelPart.getChild("left_paddle2");
		this.rightPaddle2 = modelPart.getChild("right_paddle2");
	}
	public void setupAnim(@NonNull S bigBoatEntityRenderState) {
		super.setupAnim(bigBoatEntityRenderState);
		chest_bottom.visible = bigBoatEntityRenderState.hasChest;
		chest_lid.visible = bigBoatEntityRenderState.hasChest;
		chest_lock.visible = bigBoatEntityRenderState.hasChest;
		setPaddleAngles(bigBoatEntityRenderState.leftPaddleAngle, 0, this.leftPaddle, bigBoatEntityRenderState.players>0);
		setPaddleAngles(bigBoatEntityRenderState.rightPaddleAngle, 1, this.rightPaddle, bigBoatEntityRenderState.players>0);
		setPaddleAngles(bigBoatEntityRenderState.leftPaddleAngle, 0, this.leftPaddle2, bigBoatEntityRenderState.players>1);
		setPaddleAngles(bigBoatEntityRenderState.rightPaddleAngle, 1, this.rightPaddle2, bigBoatEntityRenderState.players>1);
	}

	public static void setPaddleAngles(float angle, int paddle, ModelPart modelPart, boolean active) {
		if (!active) angle = 0;
		modelPart.xRot = (float)Math.PI;
		modelPart.zRot = Mth.clampedLerp((Mth.sin(-angle) + 1.0F) / 2.0F, (float) (-Math.PI / 3), (float) (-Math.PI / 12));
		modelPart.yRot = Mth.clampedLerp((Mth.sin(-angle + 1.0F) + 1.0F) / 2.0F, (float) (-Math.PI / 4), (float) (Math.PI / 4));
		if (paddle == 1) modelPart.yRot = (float) Math.PI - modelPart.yRot;
		else modelPart.zRot = -modelPart.zRot;
	}

	public static void addParts(PartDefinition modelPartData) {
		modelPartData.addOrReplaceChild("boat_1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -0.0F, 16.0F, 3.0F, 23.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -23.0F, 16.0F, 3.0F, 23.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_3", CubeListBuilder.create().texOffs(0, 3).addBox(7.0F, -6.0F, 23.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_4", CubeListBuilder.create().texOffs(0, 3).addBox(-9.0F, -6.0F, 23.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_5", CubeListBuilder.create().texOffs(0, 3).addBox(-9.0F, -6.0F, -26.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_6", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -28.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_7", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -30.0F, 10.0F, 6.0F, 2.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_8", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, 28.0F, 10.0F, 6.0F, 2.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_9", CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -6.0F, -28.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_10", CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -6.0F, 26.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_11", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, 26.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_12", CubeListBuilder.create().texOffs(0, 3).addBox(7.0F, -6.0F, -26.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_13", CubeListBuilder.create().texOffs(0, 0).addBox(8.0F, -6.0F, -23.0F, 2.0F, 6.0F, 46.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_14", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -6.0F, -23.0F, 2.0F, 6.0F, 46.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_15", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, 23.0F, 14.0F, 3.0F, 3.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_16", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, -26.0F, 14.0F, 3.0F, 3.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_17", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -28.0F, 10.0F, 3.0F, 1.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_18", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -27.0F, 12.0F, 3.0F, 1.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_19", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, 26.0F, 12.0F, 3.0F, 1.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("boat_20", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, 27.0F, 10.0F, 3.0F, 1.0F), PartPose.ZERO);

		modelPartData.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(48, 64).addBox(-1.0F, -45F, 8.0F, 2.0F, 45.0F, 2.0F), PartPose.ZERO.scaled(0.999f));

		float dist = 20f;
		modelPartData.addOrReplaceChild("left_paddle",
				CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(10.0F, -6.0F, -0.0F-dist, 0.0F, 0.0F, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("right_paddle",
				CubeListBuilder.create().texOffs(62, 20).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(-10.0F, -6.0F, -0.0F-dist, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("left_paddle2",
				CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(10.0F, -6.0F, -0.0F, 0.0F, 0.0F, (float) (Math.PI / 16)));
		modelPartData.addOrReplaceChild("right_paddle2",
				CubeListBuilder.create().texOffs(62, 20).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				PartPose.offsetAndRotation(-10.0F, -6.0F, -0.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));
	}

	public static LayerDefinition getChestTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		addParts(modelPartData);
		modelPartData.addOrReplaceChild(PartNames.CHEST_BOTTOM,
			CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F),
			PartPose.offsetAndRotation(-6.0F, -8.0F, 13.0F, 0.0F, 0, 0.0F));
		modelPartData.addOrReplaceChild(PartNames.CHEST_LID,
			CubeListBuilder.create().texOffs(0, 75).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F),
			PartPose.offsetAndRotation(-6.0F, -12.0F, 13.0F, 0f, 0, 0.0F));
		modelPartData.addOrReplaceChild(PartNames.CHEST_LOCK,
			CubeListBuilder.create().texOffs(0, 75).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F),
			PartPose.offsetAndRotation(-1.0F, -9.0F, 12.0F, 0.0F, 0, 0.0F));
		return LayerDefinition.create(modelData, 128, 128);
	}
}
