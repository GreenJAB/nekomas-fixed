package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.greenjab.nekomasfixed.render.entity.model.MegaBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.MegaBoatEntityRenderState;
import net.minecraft.block.*;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class MegaBoatEntityRenderer extends EntityRenderer<MegaBoatEntity, MegaBoatEntityRenderState> {
	private final Identifier texture;
	private final EntityModel<MegaBoatEntityRenderState> model;
	protected final ItemModelManager itemModelResolver;
	
	public MegaBoatEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer layer) {
		super(context);
		this.itemModelResolver = context.getItemModelManager();
		this.shadowRadius = 0.8F;
		this.texture = layer.id().withPath(path -> "textures/entity/" + path + ".png");
		this.model = new MegaBoatEntityModel(context.getPart(layer));
	}

	public void render(
			MegaBoatEntityRenderState megaBoatEntityRenderState,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			CameraRenderState cameraRenderState
	) {
		matrixStack.push();
		matrixStack.translate(0.0F, 0.375F, 0.0F);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - megaBoatEntityRenderState.yaw));
		float f = megaBoatEntityRenderState.damageWobbleTicks;
		if (f > 0.0F) {
			matrixStack.multiply(
					RotationAxis.POSITIVE_X
							.rotationDegrees(MathHelper.sin(f) * f * megaBoatEntityRenderState.damageWobbleStrength / 10.0F * megaBoatEntityRenderState.damageWobbleSide)
			);
		}

		if (!megaBoatEntityRenderState.submergedInWater && !MathHelper.approximatelyEquals(megaBoatEntityRenderState.bubbleWobble, 0.0F)) {
			matrixStack.multiply(new Quaternionf().setAngleAxis(megaBoatEntityRenderState.bubbleWobble * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
		}

		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		orderedRenderCommandQueue.submitModel(
				this.getModel(),
				megaBoatEntityRenderState,
				matrixStack,
				this.getRenderLayer(),
				megaBoatEntityRenderState.light,
				OverlayTexture.DEFAULT_UV,
				megaBoatEntityRenderState.outlineColor,
				null
		);

		matrixStack.scale(-1.0F, -1.0F, 1.0F);

		matrixStack.translate(0.0F, 2F, -0.06F);
		megaBoatEntityRenderState.bannerRenderState
				.render(matrixStack, orderedRenderCommandQueue, megaBoatEntityRenderState.light, OverlayTexture.DEFAULT_UV, megaBoatEntityRenderState.outlineColor);

		matrixStack.translate(0.0F, -1.15F, -1.44F);
		megaBoatEntityRenderState.bannerRenderState
				.render(matrixStack, orderedRenderCommandQueue, megaBoatEntityRenderState.light, OverlayTexture.DEFAULT_UV, megaBoatEntityRenderState.outlineColor);

		matrixStack.pop();

		super.render(megaBoatEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}


	protected EntityModel<MegaBoatEntityRenderState> getModel() {
		return this.model;
	}

	protected RenderLayer getRenderLayer() {
		return this.model.getLayer(this.texture);
	}

	public MegaBoatEntityRenderState createRenderState() {
		return new MegaBoatEntityRenderState();
	}

	public void updateRenderState(MegaBoatEntity megaBoatEntity, MegaBoatEntityRenderState megaBoatEntityRenderState, float f) {
		super.updateRenderState(megaBoatEntity, megaBoatEntityRenderState, f);
		megaBoatEntityRenderState.yaw = megaBoatEntity.getLerpedYaw(f);
		megaBoatEntityRenderState.damageWobbleTicks = megaBoatEntity.getDamageWobbleTicks() - f;
		megaBoatEntityRenderState.damageWobbleSide = megaBoatEntity.getDamageWobbleSide();
		megaBoatEntityRenderState.damageWobbleStrength = Math.max((megaBoatEntity.getDamageWobbleStrength()/2.0f) - f, 0.0F);
		megaBoatEntityRenderState.bubbleWobble = megaBoatEntity.lerpBubbleWobble(f);
		megaBoatEntityRenderState.submergedInWater = megaBoatEntity.isSubmergedInWater();
		megaBoatEntityRenderState.leftPaddleAngle = megaBoatEntity.lerpPaddlePhase(0, f);
		megaBoatEntityRenderState.rightPaddleAngle = megaBoatEntity.lerpPaddlePhase(1, f);

		megaBoatEntityRenderState.hasChest = megaBoatEntity.hasChest();
		megaBoatEntityRenderState.players = megaBoatEntity.countRowable();//megaBoatEntity.getPlayerPassengers();
		if (megaBoatEntity.getBanner().isIn(ItemTags.BANNERS)) {
			this.itemModelResolver.updateForNonLivingEntity(megaBoatEntityRenderState.bannerRenderState, megaBoatEntity.getBanner(), ItemDisplayContext.HEAD, megaBoatEntity);
		} else {
			megaBoatEntityRenderState.bannerRenderState.clear();
		}
	}
}
