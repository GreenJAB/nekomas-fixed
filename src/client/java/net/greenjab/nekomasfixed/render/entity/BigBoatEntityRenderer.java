package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.BigBoatEntity;
import net.greenjab.nekomasfixed.render.entity.model.BigBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.BigBoatEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class BigBoatEntityRenderer<T extends BigBoatEntity, S extends BigBoatEntityRenderState, M extends BigBoatEntityModel<S>> extends EntityRenderer<T, S> {
	private final Identifier texture;
	public final EntityModel<S> model;
	protected final ItemModelManager itemModelResolver;
	
	public BigBoatEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer layer) {
		super(context);
		this.itemModelResolver = context.getItemModelManager();
		this.shadowRadius = 0.8F;
		this.texture = layer.id().withPath(path -> "textures/entity/" + path + ".png");
		this.model = getThisModel(context, layer);
	}

	@NotNull
	public M getThisModel(EntityRendererFactory.Context context, EntityModelLayer layer) {
		return (M)new BigBoatEntityModel<S>(context.getPart(layer));
	}

	public void render(
			S bigBoatEntityRenderState,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			CameraRenderState cameraRenderState
	) {
		matrixStack.push();
		matrixStack.translate(0.0F, 0.375F, 0.0F);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - bigBoatEntityRenderState.yaw));
		float f = bigBoatEntityRenderState.damageWobbleTicks;
		if (f > 0.0F) {
			matrixStack.multiply(
					RotationAxis.POSITIVE_X
							.rotationDegrees(MathHelper.sin(f) * f * bigBoatEntityRenderState.damageWobbleStrength / 10.0F * bigBoatEntityRenderState.damageWobbleSide)
			);
		}

		if (!bigBoatEntityRenderState.submergedInWater && !MathHelper.approximatelyEquals(bigBoatEntityRenderState.bubbleWobble, 0.0F)) {
			matrixStack.multiply(new Quaternionf().setAngleAxis(bigBoatEntityRenderState.bubbleWobble * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
		}

		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		orderedRenderCommandQueue.submitModel(
				this.getModel(),
				bigBoatEntityRenderState,
				matrixStack,
				this.getRenderLayer(),
				bigBoatEntityRenderState.light,
				OverlayTexture.DEFAULT_UV,
				bigBoatEntityRenderState.outlineColor,
				null
		);

		matrixStack.scale(-1.0F, -1.0F, 1.0F);

		renderBanners(bigBoatEntityRenderState, matrixStack, orderedRenderCommandQueue);

		matrixStack.pop();

		super.render(bigBoatEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	public void renderBanners(BigBoatEntityRenderState bigBoatEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue) {
		matrixStack.translate(0.0F, 1F, 0.125F);
		bigBoatEntityRenderState.bannerRenderState
				.render(matrixStack, orderedRenderCommandQueue, bigBoatEntityRenderState.light, OverlayTexture.DEFAULT_UV, bigBoatEntityRenderState.outlineColor);

	}


	protected EntityModel<S> getModel() {
		return this.model;
	}

	protected RenderLayer getRenderLayer() {
		return this.model.getLayer(this.texture);
	}

	public S createRenderState() {
		return (S) new BigBoatEntityRenderState();
	}

	public void updateRenderState(T bigBoatEntity, S bigBoatEntityRenderState, float f) {
		super.updateRenderState(bigBoatEntity, bigBoatEntityRenderState, f);
		bigBoatEntityRenderState.yaw = bigBoatEntity.getLerpedYaw(f);
		bigBoatEntityRenderState.damageWobbleTicks = bigBoatEntity.getDamageWobbleTicks() - f;
		bigBoatEntityRenderState.damageWobbleSide = bigBoatEntity.getDamageWobbleSide();
		bigBoatEntityRenderState.damageWobbleStrength = Math.max((bigBoatEntity.getDamageWobbleStrength()/2.0f) - f, 0.0F);
		bigBoatEntityRenderState.bubbleWobble = bigBoatEntity.lerpBubbleWobble(f);
		bigBoatEntityRenderState.submergedInWater = bigBoatEntity.isSubmergedInWater();
		bigBoatEntityRenderState.leftPaddleAngle = bigBoatEntity.lerpPaddlePhase(0, f);
		bigBoatEntityRenderState.rightPaddleAngle = bigBoatEntity.lerpPaddlePhase(1, f);

		bigBoatEntityRenderState.hasChest = bigBoatEntity.hasChest();
		bigBoatEntityRenderState.players = bigBoatEntity.countRowable();//bigBoatEntity.getPlayerPassengers();
		if (bigBoatEntity.getBanner().isIn(ItemTags.BANNERS)) {
			this.itemModelResolver.updateForNonLivingEntity(bigBoatEntityRenderState.bannerRenderState, bigBoatEntity.getBanner(), ItemDisplayContext.HEAD, bigBoatEntity);
		} else {
			bigBoatEntityRenderState.bannerRenderState.clear();
		}
		bigBoatEntityRenderState.huge = false;
	}
}
