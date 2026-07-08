package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.WildFire.FireBombEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import org.jspecify.annotations.NonNull;


@Environment(EnvType.CLIENT)
public class FireBombEntityRenderer extends EntityRenderer<FireBombEntity, EntityRenderState> {
	private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/item/blaze_powder.png");
	private static final RenderType LAYER = RenderTypes.entityTranslucentEmissive(TEXTURE);

	public FireBombEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	protected int getBlockLightLevel(@NonNull FireBombEntity fireBombEntity, @NonNull BlockPos blockPos) {
		return 15;
	}

	@Override
	public void submit(@NonNull EntityRenderState renderState, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
		matrices.pushPose();
		matrices.mulPose(cameraState.orientation);
		queue.submitCustomGeometry(matrices, LAYER, (matricesEntry, vertexConsumer) -> {
			produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 0.0F, 0, 0, 1);
			produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 1.0F, 0, 1, 1);
			produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 1.0F, 1, 1, 0);
			produceVertex(vertexConsumer, matricesEntry, renderState.lightCoords, 0.0F, 1, 0, 0);
		});
		matrices.popPose();
		super.submit(renderState, matrices, queue, cameraState);
	}

	public static void produceVertex(VertexConsumer vertexConsumer, PoseStack.Pose matrix, int light, float x, int z, int textureU, int textureV) {
		vertexConsumer.addVertex(matrix, x - 0.5F, z - 0.25F, 0.0F)
				.setColor(CommonColors.WHITE)
				.setUv(textureU, textureV)
				.setOverlay(OverlayTexture.NO_OVERLAY)
				.setLight(light)
				.setNormal(matrix, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public @NonNull EntityRenderState createRenderState() {
		return new EntityRenderState();
	}
}