package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

@Environment(EnvType.CLIENT)
public class BasePlateEntityModel<T extends TargetDummyEntityRenderState> extends TargetDummyArmorEntityModel {

	private final ModelPart basePlate;

	public BasePlateEntityModel(ModelPart modelPart) {
		super(modelPart);
		this.basePlate = modelPart.getChild("base_plate");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = PlayerEntityModel.getTexturedModelData(Dilation.NONE, false);
		ModelPartData modelPartData = modelData.getRoot().resetChildrenParts();
		modelPartData.addChild(
				"base_plate", ModelPartBuilder.create().uv(0, 32).cuboid(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F), ModelTransform.origin(0.0F, 12.0F, 0.0F)
		);
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(TargetDummyEntityRenderState targetDummyEntityRenderState) {
		super.setAngles(targetDummyEntityRenderState);
		this.basePlate.yaw = (float) (Math.PI / 180.0) * -targetDummyEntityRenderState.yaw;
	}
}