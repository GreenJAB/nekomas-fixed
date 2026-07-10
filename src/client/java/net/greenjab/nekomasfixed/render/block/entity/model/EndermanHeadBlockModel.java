package net.greenjab.nekomasfixed.render.block.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.block.entity.state.EndermanHeadBlockEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class EndermanHeadBlockModel<S extends EndermanHeadBlockEntityRenderState> extends Model<S> {
	private final ModelPart head;
	private final ModelPart mouth;

    public EndermanHeadBlockModel(ModelPart root) {
		super(root, RenderTypes::entitySolid);
		this.head = root.getChild("head");
		this.mouth = root.getChild("mouth");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(4.0F, 0.0F, 4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 16)
				.addBox(4.0F, 0.0F, 4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)), PartPose.ZERO);

		return LayerDefinition.create(modelData, 64, 32);
	}

	public void setupAnim(@NonNull S state) {
		super.setupAnim(state);
		if (state.powered) {
			if (state.wall) {
				this.head.y -= 2.5F;
				this.mouth.y += 2.5F;
			} else this.head.y -= 5.0F;
		}
	}
}
