package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.MegaBoatEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AbstractBoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BoatEntityRenderState;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MegaBoatEntityModel extends EntityModel<MegaBoatEntityRenderState> {

	private final ModelPart chest_bottom;
	private final ModelPart chest_lid;
	private final ModelPart chest_lock;
	private final ModelPart leftPaddle;
	private final ModelPart rightPaddle;
	private final ModelPart leftPaddle2;
	private final ModelPart rightPaddle2;
	private final ModelPart leftPaddle3;
	private final ModelPart rightPaddle3;

	public MegaBoatEntityModel(ModelPart modelPart) {
		super(modelPart);
		this.chest_bottom = modelPart.getChild("chest_bottom");
		this.chest_lid = modelPart.getChild("chest_lid");
		this.chest_lock = modelPart.getChild("chest_lock");

		this.leftPaddle = modelPart.getChild("left_paddle");
		this.rightPaddle = modelPart.getChild("right_paddle");
		this.leftPaddle2 = modelPart.getChild("left_paddle2");
		this.rightPaddle2 = modelPart.getChild("right_paddle2");
		this.leftPaddle3 = modelPart.getChild("left_paddle3");
		this.rightPaddle3 = modelPart.getChild("right_paddle3");
	}
	public void setAngles(MegaBoatEntityRenderState megaBoatEntityRenderState) {
		super.setAngles(megaBoatEntityRenderState);

		chest_bottom.visible = megaBoatEntityRenderState.hasChest;
		chest_lid.visible = megaBoatEntityRenderState.hasChest;
		chest_lock.visible = megaBoatEntityRenderState.hasChest;

		setPaddleAngles(megaBoatEntityRenderState.leftPaddleAngle, 0, this.leftPaddle, megaBoatEntityRenderState.players>0);
		setPaddleAngles(megaBoatEntityRenderState.rightPaddleAngle, 1, this.rightPaddle, megaBoatEntityRenderState.players>0);
		setPaddleAngles(megaBoatEntityRenderState.leftPaddleAngle, 0, this.leftPaddle2, megaBoatEntityRenderState.players>1);
		setPaddleAngles(megaBoatEntityRenderState.rightPaddleAngle, 1, this.rightPaddle2, megaBoatEntityRenderState.players>1);
		setPaddleAngles(megaBoatEntityRenderState.leftPaddleAngle, 0, this.leftPaddle3, megaBoatEntityRenderState.players>2);
		setPaddleAngles(megaBoatEntityRenderState.rightPaddleAngle, 1, this.rightPaddle3, megaBoatEntityRenderState.players>2);
	}

	private static void setPaddleAngles(float angle, int paddle, ModelPart modelPart, boolean active) {
		if (!active) angle = 0;
		modelPart.pitch = (float)Math.PI;
		modelPart.roll = MathHelper.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), (MathHelper.sin(-angle) + 1.0F) / 2.0F);
		modelPart.yaw = MathHelper.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), (MathHelper.sin(-angle + 1.0F) + 1.0F) / 2.0F);
		if (paddle == 1) {
			modelPart.yaw = (float) Math.PI - modelPart.yaw;
		} else {
			modelPart.roll = -modelPart.roll;
		}
	}

	private static void addParts(ModelPartData modelPartData) {

		modelPartData.addChild("boat_1", ModelPartBuilder.create().uv(0, 4).cuboid(-13F, 0F, -32F, 26F, 3F, 33F), ModelTransform.NONE);
		modelPartData.addChild("boat_2", ModelPartBuilder.create().uv(0, 0).cuboid(-13F, 0F, 1F, 26F, 3F, 33F), ModelTransform.NONE);
		modelPartData.addChild("boat_3", ModelPartBuilder.create().uv(0, 3).cuboid(12F, -6F, 34F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_4", ModelPartBuilder.create().uv(0, 3).cuboid(10F, -6F, 37F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_5", ModelPartBuilder.create().uv(0, 0).cuboid(9F, -6F, 40F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_6", ModelPartBuilder.create().uv(0, 0).cuboid(7F, -6F, 42F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_7", ModelPartBuilder.create().uv(0, 0).cuboid(5F, -6F, 44F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_8", ModelPartBuilder.create().uv(0, 0).cuboid(-7F, -6F, 44F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_9", ModelPartBuilder.create().uv(0, 0).cuboid(-7F, -6F, -44F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_10", ModelPartBuilder.create().uv(0, 0).cuboid(5F, -6F, -44F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_11", ModelPartBuilder.create().uv(0, 0).cuboid(-9F, -6F, 42F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_12", ModelPartBuilder.create().uv(0, 0).cuboid(-9F, -6F, -42F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_13", ModelPartBuilder.create().uv(0, 0).cuboid(7F, -6F, -42F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_14", ModelPartBuilder.create().uv(0, 0).cuboid(-11F, -6F, 40F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_15", ModelPartBuilder.create().uv(0, 0).cuboid(-11F, -6F, -40F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_16", ModelPartBuilder.create().uv(0, 0).cuboid(9F, -6F, -40F, 2F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_17", ModelPartBuilder.create().uv(0, 3).cuboid(-12F, -6F, 37F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_18", ModelPartBuilder.create().uv(0, 3).cuboid(-12F, -6F, -38F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_19", ModelPartBuilder.create().uv(0, 3).cuboid(10F, -6F, -38F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_20", ModelPartBuilder.create().uv(0, 3).cuboid(-14F, -6F, 34F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_21", ModelPartBuilder.create().uv(0, 3).cuboid(-14F, -6F, -35F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_22", ModelPartBuilder.create().uv(0, 3).cuboid(12F, -6F, -35F, 2F, 6F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_23", ModelPartBuilder.create().uv(0, 0).cuboid(-5F, -6F, 45F, 10F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_24", ModelPartBuilder.create().uv(0, 0).cuboid(-5F, -6F, -45F, 10F, 6F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_25", ModelPartBuilder.create().uv(0, 1).cuboid(-15F, -6F, -32F, 2F, 6F, 33F), ModelTransform.NONE);
		modelPartData.addChild("boat_26", ModelPartBuilder.create().uv(0, 1).cuboid(-15F, -6F, 1F, 2F, 6F, 33F), ModelTransform.NONE);
		modelPartData.addChild("boat_27", ModelPartBuilder.create().uv(0, 1).cuboid(13F, -6F, -32F, 2F, 6F, 33F), ModelTransform.NONE);
		modelPartData.addChild("boat_28", ModelPartBuilder.create().uv(0, 1).cuboid(13F, -6F, 1F, 2F, 6F, 33F), ModelTransform.NONE);
		modelPartData.addChild("boat_29", ModelPartBuilder.create().uv(15, 5).cuboid(-12F, 0F, -35F, 24F, 3F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_30", ModelPartBuilder.create().uv(15, 13).cuboid(-12F, 0F, 34F, 24F, 3F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_31", ModelPartBuilder.create().uv(17, 8).cuboid(-10F, 0F, -38F, 20F, 3F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_32", ModelPartBuilder.create().uv(1, 10).cuboid(-10F, 0F, 37F, 20F, 3F, 3F), ModelTransform.NONE);
		modelPartData.addChild("boat_33", ModelPartBuilder.create().uv(3, 11).cuboid(-9F, 0F, -40F, 18F, 3F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_34", ModelPartBuilder.create().uv(3, 8).cuboid(-9F, 0F, 40F, 18F, 3F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_35", ModelPartBuilder.create().uv(7, 12).cuboid(-7F, 0F, -42F, 14F, 3F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_36", ModelPartBuilder.create().uv(5, 6).cuboid(-7F, 0F, 42F, 14F, 3F, 2F), ModelTransform.NONE);
		modelPartData.addChild("boat_37", ModelPartBuilder.create().uv(0, 0).cuboid(-5F, 0F, -43F, 10F, 3F, 1F), ModelTransform.NONE);
		modelPartData.addChild("boat_38", ModelPartBuilder.create().uv(0, 0).cuboid(-5F, 0F, 44F, 10F, 3F, 1F), ModelTransform.NONE);

		modelPartData.addChild("stand_1", ModelPartBuilder.create().uv(48, 48).cuboid(-1F, -45F, 5F, 2F, 45F, 2F), ModelTransform.NONE);
		modelPartData.addChild("stand_2", ModelPartBuilder.create().uv(48, 48).cuboid(-1F, -35F, -18F, 2F, 35F, 2F), ModelTransform.NONE);


		float dist = 23f;
		modelPartData.addChild(
				"left_paddle",
				ModelPartBuilder.create().uv(62, 0).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(15.0F, -6.0F, -5.0F-dist, 0.0F, 0.0F, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"right_paddle",
				ModelPartBuilder.create().uv(62, 20).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(-15.0F, -6.0F, -5.0F-dist, 0.0F, (float) Math.PI, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"left_paddle2",
				ModelPartBuilder.create().uv(62, 0).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(15.0F, -6.0F, -5.0F, 0.0F, 0.0F, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"right_paddle2",
				ModelPartBuilder.create().uv(62, 20).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(-15.0F, -6.0F, -5.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"left_paddle3",
				ModelPartBuilder.create().uv(62, 0).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(15.0F, -6.0F, -5.0F+dist, 0.0F, 0.0F, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"right_paddle3",
				ModelPartBuilder.create().uv(62, 20).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(-15.0F, -6.0F, -5.0F+dist, 0.0F, (float) Math.PI, (float) (Math.PI / 16))
		);

	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		addParts(modelPartData);
		return TexturedModelData.of(modelData, 128, 64);
	}

	public static TexturedModelData getChestTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		addParts(modelPartData);
		modelPartData.addChild(
			EntityModelPartNames.CHEST_BOTTOM,
			ModelPartBuilder.create().uv(0, 76).cuboid(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F),
			ModelTransform.of(-6.0F, -8.0F, 31.0F, 0.0F, 0, 0.0F)
		);
		modelPartData.addChild(
			EntityModelPartNames.CHEST_LID,
			ModelPartBuilder.create().uv(0, 59).cuboid(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F),
			ModelTransform.of(-6.0F, -12.0F, 31.0F, 0f, 0, 0.0F)
		);
		modelPartData.addChild(
			EntityModelPartNames.CHEST_LOCK,
			ModelPartBuilder.create().uv(0, 59).cuboid(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F),
			ModelTransform.of(-1.0F, -9.0F, 30.0F, 0.0F, 0, 0.0F)
		);
		return TexturedModelData.of(modelData, 128, 128);
	}
}
