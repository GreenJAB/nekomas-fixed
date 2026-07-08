package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.WildfireTridentEntity;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class WildfireTridentEntityRenderer extends EntityRenderer<WildfireTridentEntity, ThrownTridentRenderState> {
	public static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wildfire_trident/default.png");
	private final TridentModel model;

	public WildfireTridentEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new TridentModel(context.bakeLayer(ModEntityLayerRegistry.WILDFIRE_TRIDENT));
	}

	public void submit(
            ThrownTridentRenderState tridentEntityRenderState,
            PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		matrixStack.pushPose();
		matrixStack.mulPose(Axis.YP.rotationDegrees(tridentEntityRenderState.yRot - 90.0F));
		matrixStack.mulPose(Axis.ZP.rotationDegrees(tridentEntityRenderState.xRot + 90.0F));
		orderedRenderCommandQueue.order(0)
				.submitModel(this.model, Unit.INSTANCE, matrixStack, TEXTURE, tridentEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, tridentEntityRenderState.outlineColor, null);
		if (tridentEntityRenderState.isFoil) {
			orderedRenderCommandQueue.order(1)
					.submitModel(
							this.model,
							Unit.INSTANCE,
							matrixStack,
							ItemFeatureRenderer.getFoilRenderType(this.model.renderType(TEXTURE), false),
							tridentEntityRenderState.lightCoords,
							OverlayTexture.NO_OVERLAY,
							tridentEntityRenderState.outlineColor,
							null
					);
		}

		matrixStack.popPose();
		super.submit(tridentEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	public @NonNull ThrownTridentRenderState createRenderState() {
		return new ThrownTridentRenderState();
	}

	public void extractRenderState(@NonNull WildfireTridentEntity tridentEntity, @NonNull ThrownTridentRenderState tridentEntityRenderState, float f) {
		super.extractRenderState(tridentEntity, tridentEntityRenderState, f);
		tridentEntityRenderState.yRot = tridentEntity.getYRot(f);
		tridentEntityRenderState.xRot = tridentEntity.getXRot(f);
		tridentEntityRenderState.isFoil = tridentEntity.isEnchanted();
	}
}
