package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.BigBoatEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

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
	public void setAngles(S bigBoatEntityRenderState) {
		super.setAngles(bigBoatEntityRenderState);

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
		modelPart.pitch = (float)Math.PI;
		modelPart.roll = MathHelper.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), (MathHelper.sin(-angle) + 1.0F) / 2.0F);
		modelPart.yaw = MathHelper.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), (MathHelper.sin(-angle + 1.0F) + 1.0F) / 2.0F);
		if (paddle == 1) {
			modelPart.yaw = (float) Math.PI - modelPart.yaw;
		} else {
			modelPart.roll = -modelPart.roll;
		}
	}

	public static void addParts(ModelPartData modelPartData) {

		modelPartData.addChild("boat_1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -0.0F, 16.0F, 3.0F, 23.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -23.0F, 16.0F, 3.0F, 23.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_3", ModelPartBuilder.create().uv(0, 3).cuboid(7.0F, -6.0F, 23.0F, 2.0F, 6.0F, 3.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_4", ModelPartBuilder.create().uv(0, 3).cuboid(-9.0F, -6.0F, 23.0F, 2.0F, 6.0F, 3.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_5", ModelPartBuilder.create().uv(0, 3).cuboid(-9.0F, -6.0F, -26.0F, 2.0F, 6.0F, 3.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_6", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.0F, -28.0F, 2.0F, 6.0F, 2.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_7", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -6.0F, -30.0F, 10.0F, 6.0F, 2.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_8", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -6.0F, 28.0F, 10.0F, 6.0F, 2.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_9", ModelPartBuilder.create().uv(0, 0).cuboid(5.0F, -6.0F, -28.0F, 2.0F, 6.0F, 2.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_10", ModelPartBuilder.create().uv(0, 0).cuboid(5.0F, -6.0F, 26.0F, 2.0F, 6.0F, 2.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_11", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.0F, 26.0F, 2.0F, 6.0F, 2.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_12", ModelPartBuilder.create().uv(0, 3).cuboid(7.0F, -6.0F, -26.0F, 2.0F, 6.0F, 3.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_13", ModelPartBuilder.create().uv(0, 0).cuboid(8.0F, -6.0F, -23.0F, 2.0F, 6.0F, 46.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_14", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -6.0F, -23.0F, 2.0F, 6.0F, 46.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_15", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, 0.0F, 23.0F, 14.0F, 3.0F, 3.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_16", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, 0.0F, -26.0F, 14.0F, 3.0F, 3.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_17", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, -28.0F, 10.0F, 3.0F, 1.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_18", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -27.0F, 12.0F, 3.0F, 1.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_19", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, 26.0F, 12.0F, 3.0F, 1.0F), ModelTransform.NONE);
		modelPartData.addChild("boat_20", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, 27.0F, 10.0F, 3.0F, 1.0F), ModelTransform.NONE);

		modelPartData.addChild("stand", ModelPartBuilder.create().uv(48, 64).cuboid(-1.0F, -45F, 8.0F, 2.0F, 45.0F, 2.0F), ModelTransform.NONE.scaled(0.999f));


		float dist = 20f;
		modelPartData.addChild(
				"left_paddle",
				ModelPartBuilder.create().uv(62, 0).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(10.0F, -6.0F, -0.0F-dist, 0.0F, 0.0F, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"right_paddle",
				ModelPartBuilder.create().uv(62, 20).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(-10.0F, -6.0F, -0.0F-dist, 0.0F, (float) Math.PI, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"left_paddle2",
				ModelPartBuilder.create().uv(62, 0).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(10.0F, -6.0F, -0.0F, 0.0F, 0.0F, (float) (Math.PI / 16))
		);
		modelPartData.addChild(
				"right_paddle2",
				ModelPartBuilder.create().uv(62, 20).cuboid(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).cuboid(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
				ModelTransform.of(-10.0F, -6.0F, -0.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 16))
		);
	}

	public static TexturedModelData getChestTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		addParts(modelPartData);
		modelPartData.addChild(
			EntityModelPartNames.CHEST_BOTTOM,
			ModelPartBuilder.create().uv(0, 92).cuboid(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F),
			ModelTransform.of(-6.0F, -8.0F, 13.0F, 0.0F, 0, 0.0F)
		);
		modelPartData.addChild(
			EntityModelPartNames.CHEST_LID,
			ModelPartBuilder.create().uv(0, 75).cuboid(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F),
			ModelTransform.of(-6.0F, -12.0F, 13.0F, 0f, 0, 0.0F)
		);
		modelPartData.addChild(
			EntityModelPartNames.CHEST_LOCK,
			ModelPartBuilder.create().uv(0, 75).cuboid(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F),
			ModelTransform.of(-1.0F, -9.0F, 12.0F, 0.0F, 0, 0.0F)
		);
		return TexturedModelData.of(modelData, 128, 128);
	}
}
