package net.greenjab.nekomasfixed.render.entity;

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
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ThrownWildfireTridentRenderer extends EntityRenderer<WildfireTrident, ThrownTridentRenderState> {
	public static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wildfire_trident/default.png");
	private final TridentModel model;

	public ThrownWildfireTridentRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new TridentModel(context.bakeLayer(ModModelLayerRegistry.WILDFIRE_TRIDENT));
	}

	public void submit(
            ThrownTridentRenderState tridentEntityRenderState,
            PoseStack poseStack,
            @NonNull SubmitNodeCollector submitNodeCollector,
            @NonNull CameraRenderState camera
	) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(tridentEntityRenderState.yRot - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(tridentEntityRenderState.xRot + 90.0F));
		submitNodeCollector.order(0)
				.submitModel(this.model, Unit.INSTANCE, poseStack, TEXTURE, tridentEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, tridentEntityRenderState.outlineColor, null);
		if (tridentEntityRenderState.isFoil) {
			submitNodeCollector.order(1)
					.submitModel(this.model, Unit.INSTANCE, poseStack, RenderTypes.entityGlint(), tridentEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, tridentEntityRenderState.outlineColor, null);
		}
		poseStack.popPose();
		super.submit(tridentEntityRenderState, poseStack, submitNodeCollector, camera);
	}

	public @NonNull ThrownTridentRenderState createRenderState() {
		return new ThrownTridentRenderState();
	}

	public void extractRenderState(@NonNull WildfireTrident entity, @NonNull ThrownTridentRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.yRot = entity.getYRot(partialTicks);
		state.xRot = entity.getXRot(partialTicks);
		state.isFoil = entity.isEnchanted();
	}
}
