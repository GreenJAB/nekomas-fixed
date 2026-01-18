package net.greenjab.nekomasfixed.render.block.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.block.entity.state.EndermanHeadBlockEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;

@Environment(EnvType.CLIENT)
public class EndermanHeadBlockModel<S extends EndermanHeadBlockEntityRenderState> extends Model<S> {
	private final ModelPart head;
	private final ModelPart mouth;

    public EndermanHeadBlockModel(ModelPart root) {
		super(root, RenderLayers::entitySolid);
		this.head = root.getChild("head");
		this.mouth = root.getChild("mouth");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild(
				"head", ModelPartBuilder.create().uv(0, 0).cuboid(4.0F, 0.0F, 4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.NONE
		);
		modelPartData.addChild(
				"mouth", ModelPartBuilder.create().uv(0, 16).cuboid(4.0F, 0.0F, 4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.5F)), ModelTransform.NONE
		);

		return TexturedModelData.of(modelData, 64, 32);
	}

	public void setAngles(S state) {
		super.setAngles(state);
		if (state.powered) {
			if (state.wall) {
				this.head.originY -= 2.5F;
				this.mouth.originY += 2.5F;
			} else {
				this.head.originY -= 5.0F;
			}
		}
	}
}
