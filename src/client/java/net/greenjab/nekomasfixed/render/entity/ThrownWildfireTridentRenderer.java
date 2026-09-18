package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.WildfireTrident;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ThrownWildfireTridentRenderer extends EntityRenderer<WildfireTrident, ThrownTridentRenderState> {
	public static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wildfire_trident/default.png");
	private final TridentModel model;

	public ThrownWildfireTridentRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new TridentModel(context.bakeLayer(ModModelLayerRegistry.WILDFIRE_TRIDENT));
	}

	@Override
	public void submit(
			ThrownTridentRenderState tridentEntityRenderState,
			PoseStack poseStack,
			@NonNull SubmitNodeCollector submitNodeCollector,
			@NonNull CameraRenderState camera
	) {
		poseStack.pushPose();
		poseStack.rotateDegrees(Axis.YP, tridentEntityRenderState.yRot - 90.0F);
		poseStack.rotateDegrees(Axis.ZP, tridentEntityRenderState.xRot + 90.0F);

		RenderType baseRenderType = RenderTypes.entityCutout(TEXTURE);

		// 9 params: model, state, poseStack, renderType, lightCoords, overlayCoords, tintedColor, uvMapping, outlineColor
		submitNodeCollector.submitModel(
				this.model,
				Unit.INSTANCE,
				poseStack,
				baseRenderType,
				tridentEntityRenderState.lightCoords,
				OverlayTexture.NO_OVERLAY,
				-1,
				null,
				tridentEntityRenderState.outlineColor
		);

		if (tridentEntityRenderState.isFoil) {
			submitNodeCollector.submitModel(
					this.model,
					Unit.INSTANCE,
					poseStack,
					RenderTypes.entitySolidGlint(TEXTURE),
					tridentEntityRenderState.lightCoords,
					OverlayTexture.NO_OVERLAY,
					-1,
					null,
					tridentEntityRenderState.outlineColor
			);
		}

		poseStack.popPose();
		super.submit(tridentEntityRenderState, poseStack, submitNodeCollector, camera);
	}

	@Override
	public @NonNull ThrownTridentRenderState createRenderState() {
		return new ThrownTridentRenderState();
	}

	@Override
	public void extractRenderState(@NonNull WildfireTrident entity, @NonNull ThrownTridentRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.yRot = entity.getYRot(partialTicks);
		state.xRot = entity.getXRot(partialTicks);
		state.isFoil = entity.isEnchanted();
	}
}