package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.BasePlateModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyArmorModel;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyRenderState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BasePlateFeatureRenderer extends RenderLayer<TargetDummyRenderState, TargetDummyArmorModel> {
	private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/armorstand/wood.png");
	private final BasePlateModel model;

	public BasePlateFeatureRenderer(RenderLayerParent<TargetDummyRenderState, TargetDummyArmorModel> context, EntityModelSet entityModels) {
		super(context);
		this.model = new BasePlateModel(entityModels.bakeLayer(ModModelLayerRegistry.TARGET_DUMMY_BASE));
	}

	public void submit(@NonNull PoseStack matrixStack, @NonNull SubmitNodeCollector orderedRenderCommandQueue, int i,
	                   TargetDummyRenderState targetDummyRenderState, float f, float g) {
		if (targetDummyRenderState.isInvisible) return;
		int j = LivingEntityRenderer.getOverlayCoords(targetDummyRenderState, 0.0F);
		RenderType renderLayer = RenderTypes.entitySolid(TEXTURE);
		orderedRenderCommandQueue.submitModel(this.model, targetDummyRenderState, matrixStack, renderLayer, i, j,
				targetDummyRenderState.outlineColor, null);
	}
}
