package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.FakeBoat;
import net.greenjab.nekomasfixed.render.entity.state.FakeBoatRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class FakeBoatRenderer extends EntityRenderer<FakeBoat, FakeBoatRenderState> {

	public FakeBoatRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	public void submit(
            @NonNull FakeBoatRenderState fakeBoatRenderState,
            @NonNull PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		super.submit(fakeBoatRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	public @NonNull FakeBoatRenderState createRenderState() {
		return new FakeBoatRenderState();
	}
}
