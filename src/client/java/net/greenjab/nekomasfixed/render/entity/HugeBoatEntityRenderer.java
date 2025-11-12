package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.BigBoatEntity;
import net.greenjab.nekomasfixed.registry.entity.HugeBoatEntity;
import net.greenjab.nekomasfixed.render.entity.model.BigBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.HugeBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.BigBoatEntityRenderState;
import net.greenjab.nekomasfixed.render.entity.state.HugeBoatEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.ArmorStandEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class HugeBoatEntityRenderer extends BigBoatEntityRenderer<HugeBoatEntity, HugeBoatEntityRenderState, HugeBoatEntityModel> {

	public HugeBoatEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer layer) {
		super(context, layer);
	}

	@Override
	public void renderBanners(BigBoatEntityRenderState bigBoatEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue) {
		matrixStack.translate(0.0F, 2F, -0.06F);
		bigBoatEntityRenderState.bannerRenderState
				.render(matrixStack, orderedRenderCommandQueue, bigBoatEntityRenderState.light, OverlayTexture.DEFAULT_UV, bigBoatEntityRenderState.outlineColor);

		matrixStack.translate(0.0F, -1.15F, -1.44F);
		bigBoatEntityRenderState.bannerRenderState
				.render(matrixStack, orderedRenderCommandQueue, bigBoatEntityRenderState.light, OverlayTexture.DEFAULT_UV, bigBoatEntityRenderState.outlineColor);
	}

	@NotNull
	public HugeBoatEntityModel getThisModel(EntityRendererFactory.Context context, EntityModelLayer layer) {
		return new HugeBoatEntityModel(context.getPart(layer));
	}

	public HugeBoatEntityRenderState createRenderState() {
		return new HugeBoatEntityRenderState();
	}

	public void updateRenderState(HugeBoatEntity hugeBoatEntity, HugeBoatEntityRenderState hugeBoatEntityRenderState, float f) {
		super.updateRenderState(hugeBoatEntity, hugeBoatEntityRenderState, f);
		hugeBoatEntityRenderState.huge = true;
	}
}
