package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.SpearEntity;
import net.greenjab.nekomasfixed.render.entity.state.SpearEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.*;


@Environment(EnvType.CLIENT)
public class SpearEntityRenderer extends EntityRenderer<SpearEntity, SpearEntityRenderState> {
	private final ItemModelManager itemModelManager;
	
	public SpearEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.itemModelManager = context.getItemModelManager();
	}

	public SpearEntityRenderState createRenderState() {
		return new SpearEntityRenderState();
	}

	public void updateRenderState(SpearEntity spearEntity, SpearEntityRenderState spearEntityRenderState, float f) {
		super.updateRenderState(spearEntity, spearEntityRenderState, f);
		spearEntityRenderState.duration = spearEntity.age;
		spearEntityRenderState.direction = spearEntity.getDirection();

		this.itemModelManager.updateForNonLivingEntity(spearEntityRenderState.spearRenderState, spearEntity.getStack(), ItemDisplayContext.FIXED, spearEntity);
	}

	public void render(
			SpearEntityRenderState spearEntityRenderState,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			CameraRenderState cameraRenderState
	) {
		super.render(spearEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
		ItemRenderState spearRenderState = spearEntityRenderState.spearRenderState;
		if (spearRenderState != null) {
			matrixStack.push();
			matrixStack.translate(new Vec3d(0, 0.3, 0));

			Direction dir = spearEntityRenderState.direction;
			if (dir.getAxis().isHorizontal()) {
				matrixStack.translate(new Vec3d(0, -0.13, 0));
				matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
				matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-dir.getPositiveHorizontalDegrees()+90));
			} else if (dir == Direction.DOWN) {
				matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
			}

			matrixStack.translate(new Vec3d(0, -1.1+Math.min(-Math.abs((spearEntityRenderState.age-10)/5)+2,1), 0));
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-45));

			spearRenderState.render(matrixStack, orderedRenderCommandQueue, spearEntityRenderState.light, OverlayTexture.DEFAULT_UV, 0);
			matrixStack.pop();
		}
	}
}
