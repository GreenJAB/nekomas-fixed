package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.SpearEntity;
import net.greenjab.nekomasfixed.render.entity.state.SpearRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;


@Environment(EnvType.CLIENT)
public class SpearRenderer extends EntityRenderer<SpearEntity, SpearRenderState> {
	private final ItemModelResolver itemModelManager;
	
	public SpearRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.itemModelManager = context.getItemModelResolver();
	}

	public @NonNull SpearRenderState createRenderState() {
		return new SpearRenderState();
	}

	public void extractRenderState(@NonNull SpearEntity spearEntity, @NonNull SpearRenderState spearRenderState, float f) {
		super.extractRenderState(spearEntity, spearRenderState, f);
		spearRenderState.duration = spearEntity.tickCount;
		spearRenderState.direction = spearEntity.getDirection();

		this.itemModelManager.updateForNonLiving(spearRenderState.itemStackRenderState, spearEntity.getStack(), ItemDisplayContext.FIXED, spearEntity);
	}

	public void submit(
            @NonNull SpearRenderState spearRenderState,
            @NonNull PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		super.submit(spearRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
		ItemStackRenderState itemStackRenderState = spearRenderState.itemStackRenderState;
		if (itemStackRenderState != null) {
			matrixStack.pushPose();
			matrixStack.translate(new Vec3(0, 0.3, 0));

			Direction dir = spearRenderState.direction;
			if (dir.getAxis().isHorizontal()) {
				matrixStack.translate(new Vec3(0, -0.13, 0));
				matrixStack.mulPose(Axis.ZP.rotationDegrees(90));
				matrixStack.mulPose(Axis.XP.rotationDegrees(-dir.toYRot()+90));
			} else if (dir == Direction.DOWN) {
				matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
			}

			matrixStack.translate(new Vec3(0, -1.1+Math.min(-Math.abs((spearRenderState.ageInTicks-10)/5)+2,1), 0));
			matrixStack.mulPose(Axis.YP.rotationDegrees(45));
			matrixStack.mulPose(Axis.ZP.rotationDegrees(-45));

			itemStackRenderState.submit(matrixStack, orderedRenderCommandQueue, spearRenderState.light, OverlayTexture.NO_OVERLAY, 0);
			matrixStack.mulPose(Axis.ZP.rotationDegrees(45));
			matrixStack.mulPose(Axis.YP.rotationDegrees(90));
			matrixStack.mulPose(Axis.ZP.rotationDegrees(-45));
			itemStackRenderState.submit(matrixStack, orderedRenderCommandQueue, spearRenderState.light, OverlayTexture.NO_OVERLAY, 0);
			matrixStack.popPose();
		}
	}
}
