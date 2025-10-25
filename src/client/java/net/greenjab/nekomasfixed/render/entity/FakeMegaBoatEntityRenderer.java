package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.FakeBoatEntity;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.greenjab.nekomasfixed.render.entity.model.MegaBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.FakeMegaBoatEntityRenderState;
import net.greenjab.nekomasfixed.render.entity.state.MegaBoatEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class FakeMegaBoatEntityRenderer extends EntityRenderer<FakeBoatEntity, FakeMegaBoatEntityRenderState> {

	public FakeMegaBoatEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}
	public void render(
			FakeMegaBoatEntityRenderState fakeMegaBoatEntityRenderState,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			CameraRenderState cameraRenderState
	) {
		super.render(fakeMegaBoatEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	public FakeMegaBoatEntityRenderState createRenderState() {
		return new FakeMegaBoatEntityRenderState();
	}
}
