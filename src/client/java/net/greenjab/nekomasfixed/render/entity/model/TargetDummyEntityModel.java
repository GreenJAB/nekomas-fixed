package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

@Environment(EnvType.CLIENT)
public class TargetDummyEntityModel extends TargetDummyArmorEntityModel {

	public TargetDummyEntityModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static TexturedModelData getTexturedModelData(boolean slim) {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
		ModelPartData modelPartData = modelData.getRoot();

		Dilation dilation = Dilation.NONE;
		if (slim) {
			ModelPartData modelPartData2 = modelPartData.addChild(
					EntityModelPartNames.LEFT_ARM,
					ModelPartBuilder.create().uv(32, 48).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation),
					ModelTransform.origin(5.0F, 2.0F, 0.0F)
			);
			ModelPartData modelPartData3 = modelPartData.addChild(
					EntityModelPartNames.RIGHT_ARM,
					ModelPartBuilder.create().uv(40, 16).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation),
					ModelTransform.origin(-5.0F, 2.0F, 0.0F)
			);
			modelPartData2.addChild(
					"left_sleeve", ModelPartBuilder.create().uv(48, 48).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE
			);
			modelPartData3.addChild(
					"right_sleeve", ModelPartBuilder.create().uv(40, 32).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE
			);
		} else {
			ModelPartData modelPartData2 = modelPartData.addChild(
					EntityModelPartNames.LEFT_ARM,
					ModelPartBuilder.create().uv(32, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
					ModelTransform.origin(5.0F, 2.0F, 0.0F)
			);
			ModelPartData modelPartData3 = modelPartData.getChild(EntityModelPartNames.RIGHT_ARM);
			modelPartData2.addChild(
					"left_sleeve", ModelPartBuilder.create().uv(48, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE
			);
			modelPartData3.addChild(
					"right_sleeve", ModelPartBuilder.create().uv(40, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE
			);
		}

		ModelPartData modelPartData2 = modelPartData.addChild(
				EntityModelPartNames.LEFT_LEG,
				ModelPartBuilder.create().uv(16, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
				ModelTransform.origin(1.9F, 12.0F, 0.0F)
		);
		ModelPartData modelPartData3 = modelPartData.getChild(EntityModelPartNames.RIGHT_LEG);
		modelPartData2.addChild(
				"left_pants", ModelPartBuilder.create().uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE
		);
		modelPartData3.addChild(
				"right_pants", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE
		);
		ModelPartData modelPartData4 = modelPartData.getChild(EntityModelPartNames.BODY);
		modelPartData4.addChild(
				EntityModelPartNames.JACKET, ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation.add(0.25F)), ModelTransform.NONE
		);
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(TargetDummyEntityRenderState targetDummyEntityRenderState) {
		super.setAngles(targetDummyEntityRenderState);
	}

	public void setArmAngle(TargetDummyEntityRenderState targetDummyEntityRenderState, Arm arm, MatrixStack matrixStack) {
		ModelPart modelPart = this.getArm(arm);
		boolean bl = modelPart.visible;
		modelPart.visible = true;
		super.setArmAngle(targetDummyEntityRenderState, arm, matrixStack);
		modelPart.visible = bl;
	}
}