package net.greenjab.nekomasfixed.render.block.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ClamBlockModel extends Model<Float> {
	private final ModelPart lid;
	private final ModelPart lid_hinge;

	public ClamBlockModel(ModelPart root) {
		super(root, RenderTypes::entitySolid);
		this.lid = root.getChild("lid");
		this.lid_hinge = root.getChild("lid_hinge");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		modelPartData.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 4.0F, 14.0F, 2.0F, 12.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 14).addBox(1F, 0.0F, 3.0F, 14.0F, 2.0F, 12.0F), PartPose.offset(0.0F, 2.0F, 1.0F));
		modelPartData.addOrReplaceChild("bottom_hinge", CubeListBuilder.create().texOffs(18, 28).addBox(5.0F, 0.0F, 1.0F, 6.0F, 2.0F, 3.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("lid_hinge", CubeListBuilder.create().texOffs(0, 28).addBox(5.0F, 0.0F, 0.0F, 6.0F, 2.0F, 3.0F), PartPose.offset(0.0F, 2.0F, 1.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	public void setupAnim(@NonNull Float float_) {
		super.setupAnim(float_);
		this.lid.xRot = -(float_ * (float) (Math.PI / 2));
		this.lid_hinge.xRot = this.lid.xRot;
	}
}
