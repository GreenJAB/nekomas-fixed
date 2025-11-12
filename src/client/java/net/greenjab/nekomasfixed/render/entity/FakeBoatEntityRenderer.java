package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.FakeBoatEntity;
import net.greenjab.nekomasfixed.render.entity.state.FakeBoatEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class FakeBoatEntityRenderer extends EntityRenderer<FakeBoatEntity, FakeBoatEntityRenderState> {

	public FakeBoatEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}
	public void render(
			FakeBoatEntityRenderState fakeBoatEntityRenderState,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			CameraRenderState cameraRenderState
	) {
		super.render(fakeBoatEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	public FakeBoatEntityRenderState createRenderState() {
		return new FakeBoatEntityRenderState();
	}
}
