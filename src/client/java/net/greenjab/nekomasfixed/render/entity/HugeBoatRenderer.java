package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.HugeBoat;
import net.greenjab.nekomasfixed.render.entity.model.HugeBoatModel;
import net.greenjab.nekomasfixed.render.entity.state.BigBoatRenderState;
import net.greenjab.nekomasfixed.render.entity.state.HugeBoatRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class HugeBoatRenderer extends BigBoatRenderer<HugeBoat, HugeBoatRenderState, HugeBoatModel<HugeBoatRenderState>> {

	public HugeBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, layer);
	}

	@Override
	public void renderBanners(BigBoatRenderState bigBoatRenderState, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue) {
		matrixStack.translate(0.0F, 2F, -0.06F);
		bigBoatRenderState.bannerRenderState
				.submit(matrixStack, orderedRenderCommandQueue, bigBoatRenderState.lightCoords, OverlayTexture.NO_OVERLAY, bigBoatRenderState.outlineColor);

		matrixStack.translate(0.0F, -1.15F, -1.44F);
		bigBoatRenderState.bannerRenderState
				.submit(matrixStack, orderedRenderCommandQueue, bigBoatRenderState.lightCoords, OverlayTexture.NO_OVERLAY, bigBoatRenderState.outlineColor);
	}

	@NotNull
	public HugeBoatModel<HugeBoatRenderState> getThisModel(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		return new HugeBoatModel<>(context.bakeLayer(layer));
	}

	public @NonNull HugeBoatRenderState createRenderState() {
		return new HugeBoatRenderState();
	}

	public void extractRenderState(@NonNull HugeBoat hugeBoat, @NonNull HugeBoatRenderState hugeBoatEntityRenderState, float f) {
		super.extractRenderState(hugeBoat, hugeBoatEntityRenderState, f);
		hugeBoatEntityRenderState.huge = true;
	}
}
