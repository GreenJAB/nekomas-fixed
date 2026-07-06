package net.greenjab.nekomasfixed.render.block.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ClockBlockModel extends Model<Float> {

	public ClockBlockModel(ModelPart root) {
		super(root, RenderTypes::entitySolid);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		return LayerDefinition.create(modelData, 64, 64);
	}

	public void setupAnim(@NonNull Float float_) {
		super.setupAnim(float_);
	}
}
