package net.greenjab.nekomasfixed.render.block.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;

@Environment(EnvType.CLIENT)
public class ClamBlockModel extends Model<Float> {
	private final ModelPart lid;
	private final ModelPart lid_hinge;

	public ClamBlockModel(ModelPart root) {
		super(root, RenderLayers::entitySolid);
		this.lid = root.getChild("lid");
		this.lid_hinge = root.getChild("lid_hinge");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 4.0F, 14.0F, 2.0F, 12.0F), ModelTransform.NONE);
		modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 14).cuboid(1F, 0.0F, 3.0F, 14.0F, 2.0F, 12.0F), ModelTransform.origin(0.0F, 2.0F, 1.0F));
		modelPartData.addChild("bottom_hinge", ModelPartBuilder.create().uv(18, 28).cuboid(5.0F, 0.0F, 1.0F, 6.0F, 2.0F, 3.0F), ModelTransform.NONE);
		modelPartData.addChild("lid_hinge", ModelPartBuilder.create().uv(0, 28).cuboid(5.0F, 0.0F, 0.0F, 6.0F, 2.0F, 3.0F), ModelTransform.origin(0.0F, 2.0F, 1.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(Float float_) {
		super.setAngles(float_);
		this.lid.pitch = -(float_ * (float) (Math.PI / 2));
		this.lid_hinge.pitch = this.lid.pitch;
	}
}
