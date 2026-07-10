package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.HugeBoat;
import net.greenjab.nekomasfixed.render.entity.model.HugeBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.BigBoatEntityRenderState;
import net.greenjab.nekomasfixed.render.entity.state.HugeBoatEntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class HugeBoatEntityRenderer extends BigBoatEntityRenderer<HugeBoat, HugeBoatEntityRenderState, HugeBoatEntityModel<HugeBoatEntityRenderState>> {

	public HugeBoatEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, layer);
	}

	@Override
	public void renderBanners(BigBoatEntityRenderState bigBoatEntityRenderState, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue) {
		matrixStack.translate(0.0F, 2F, -0.06F);
		bigBoatEntityRenderState.bannerRenderState
				.submit(matrixStack, orderedRenderCommandQueue, bigBoatEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, bigBoatEntityRenderState.outlineColor);

		matrixStack.translate(0.0F, -1.15F, -1.44F);
		bigBoatEntityRenderState.bannerRenderState
				.submit(matrixStack, orderedRenderCommandQueue, bigBoatEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, bigBoatEntityRenderState.outlineColor);
	}

	@NotNull
	public HugeBoatEntityModel<HugeBoatEntityRenderState> getThisModel(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		return new HugeBoatEntityModel<>(context.bakeLayer(layer));
	}

	public @NonNull HugeBoatEntityRenderState createRenderState() {
		return new HugeBoatEntityRenderState();
	}

	public void extractRenderState(@NonNull HugeBoat hugeBoat, @NonNull HugeBoatEntityRenderState hugeBoatEntityRenderState, float f) {
		super.extractRenderState(hugeBoat, hugeBoatEntityRenderState, f);
		hugeBoatEntityRenderState.huge = true;
	}
}
