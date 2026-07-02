package net.greenjab.nekomasfixed.render.block.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;

@Environment(EnvType.CLIENT)
public class ClockBlockModel extends Model<Float> {

	public ClockBlockModel(ModelPart root) {
		super(root, RenderLayers::entitySolid);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(Float float_) {
		super.setAngles(float_);
	}
}
