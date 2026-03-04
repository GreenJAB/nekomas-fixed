package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.SoulfireTridentEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.entity.state.TridentEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.RotationAxis;

import java.util.List;

@Environment(EnvType.CLIENT)
public class SoulfireTridentEntityRenderer extends EntityRenderer<SoulfireTridentEntity, TridentEntityRenderState> {
	public static final Identifier TEXTURE = NekomasFixed.id("textures/item/soulfire_trident_3d.png");
	private final TridentEntityModel model;

	public SoulfireTridentEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.model = new TridentEntityModel(context.getPart(EntityModelLayerRegistry.SOULFIRE_TRIDENT));
	}

	public void render(
			TridentEntityRenderState tridentEntityRenderState,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			CameraRenderState cameraRenderState
	) {
		matrixStack.push();
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(tridentEntityRenderState.yaw - 90.0F));
		matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tridentEntityRenderState.pitch + 90.0F));
		List<RenderLayer> list = ItemRenderer.getGlintRenderLayers(this.model.getLayer(TEXTURE), false, tridentEntityRenderState.enchanted);

		for (int i = 0; i < list.size(); i++) {
			orderedRenderCommandQueue.getBatchingQueue(i)
					.submitModel(
							this.model,
							Unit.INSTANCE,
							matrixStack,
							list.get(i),
							tridentEntityRenderState.light,
							OverlayTexture.DEFAULT_UV,
							-1,
							null,
							tridentEntityRenderState.outlineColor,
							null
					);
		}

		matrixStack.pop();
		super.render(tridentEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	public TridentEntityRenderState createRenderState() {
		return new TridentEntityRenderState();
	}

	public void updateRenderState(SoulfireTridentEntity tridentEntity, TridentEntityRenderState tridentEntityRenderState, float f) {
		super.updateRenderState(tridentEntity, tridentEntityRenderState, f);
		tridentEntityRenderState.yaw = tridentEntity.getLerpedYaw(f);
		tridentEntityRenderState.pitch = tridentEntity.getLerpedPitch(f);
		tridentEntityRenderState.enchanted = tridentEntity.isEnchanted();
	}
}
