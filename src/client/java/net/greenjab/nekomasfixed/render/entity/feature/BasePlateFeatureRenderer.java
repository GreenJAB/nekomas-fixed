package net.greenjab.nekomasfixed.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.BasePlateEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyArmorEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BasePlateFeatureRenderer extends FeatureRenderer<TargetDummyEntityRenderState, TargetDummyArmorEntityModel> {
	private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/armorstand/wood.png");
	private final BasePlateEntityModel<?> model;

	public BasePlateFeatureRenderer(FeatureRendererContext<TargetDummyEntityRenderState, TargetDummyArmorEntityModel> context, LoadedEntityModels entityModels) {
		super(context);
		this.model = new BasePlateEntityModel<>(entityModels.getModelPart(EntityModelLayerRegistry.TARGET_DUMMY_BASE));
	}


	public void render(
			MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int i, TargetDummyEntityRenderState targetDummyRenderState, float f, float g
	) {
		if (!targetDummyRenderState.invisible) {
			int j = LivingEntityRenderer.getOverlay(targetDummyRenderState, 0.0F);
			RenderLayer renderLayer = RenderLayers.entitySolid(TEXTURE);
			orderedRenderCommandQueue.submitModel(
					this.model,
					targetDummyRenderState,
					matrixStack,
					renderLayer,
					i,
					j,
					targetDummyRenderState.outlineColor,
					null
			);
		}
	}
}
