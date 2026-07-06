package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.BigBoatEntity;
import net.greenjab.nekomasfixed.render.entity.model.BigBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.BigBoatEntityRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BigBoatEntityRenderer<T extends BigBoatEntity, S extends BigBoatEntityRenderState, M extends BigBoatEntityModel<S>> extends EntityRenderer<T, S> {
	private final Identifier texture;
	public final EntityModel<S> model;
	protected final ItemModelResolver itemModelResolver;
	
	public BigBoatEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context);
		this.itemModelResolver = context.getItemModelResolver();
		this.shadowRadius = 0.8F;
		this.texture = layer.model().withPath(path -> "textures/entity/" + path + ".png");
		this.model = getThisModel(context, layer);
	}

	@NotNull
	public M getThisModel(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		return (M)new BigBoatEntityModel<S>(context.bakeLayer(layer));
	}

	public void submit(
            S bigBoatEntityRenderState,
            PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		matrixStack.pushPose();
		matrixStack.translate(0.0F, 0.375F, 0.0F);
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - bigBoatEntityRenderState.yaw));
		float f = bigBoatEntityRenderState.damageWobbleTicks;
		if (f > 0.0F) {
			matrixStack.mulPose(
					Axis.XP
							.rotationDegrees(Mth.sin(f) * f * bigBoatEntityRenderState.damageWobbleStrength / 10.0F * bigBoatEntityRenderState.damageWobbleSide)
			);
		}

		if (!bigBoatEntityRenderState.submergedInWater && !Mth.equal(bigBoatEntityRenderState.bubbleWobble, 0.0F)) {
			matrixStack.mulPose(new Quaternionf().setAngleAxis(bigBoatEntityRenderState.bubbleWobble * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
		}

		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		orderedRenderCommandQueue.submitModel(
				this.getModel(),
				bigBoatEntityRenderState,
				matrixStack,
				this.getRenderLayer(),
				bigBoatEntityRenderState.lightCoords,
				OverlayTexture.NO_OVERLAY,
				bigBoatEntityRenderState.outlineColor,
				null
		);

		matrixStack.scale(-1.0F, -1.0F, 1.0F);

		renderBanners(bigBoatEntityRenderState, matrixStack, orderedRenderCommandQueue);

		matrixStack.popPose();

		super.submit(bigBoatEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	public void renderBanners(BigBoatEntityRenderState bigBoatEntityRenderState, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue) {
		matrixStack.translate(0.0F, 1F, 0.125F);
		bigBoatEntityRenderState.bannerRenderState
				.submit(matrixStack, orderedRenderCommandQueue, bigBoatEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, bigBoatEntityRenderState.outlineColor);

	}


	protected EntityModel<S> getModel() {
		return this.model;
	}

	protected RenderType getRenderLayer() {
		return this.model.renderType(this.texture);
	}

	public @NonNull S createRenderState() {
		return (S) new BigBoatEntityRenderState();
	}

	public void extractRenderState(@NonNull T bigBoatEntity, @NonNull S bigBoatEntityRenderState, float f) {
		super.extractRenderState(bigBoatEntity, bigBoatEntityRenderState, f);
		bigBoatEntityRenderState.yaw = bigBoatEntity.getYRot(f);
		bigBoatEntityRenderState.damageWobbleTicks = bigBoatEntity.getHurtTime() - f;
		bigBoatEntityRenderState.damageWobbleSide = bigBoatEntity.getHurtDir();
		bigBoatEntityRenderState.damageWobbleStrength = Math.max((bigBoatEntity.getDamage()/2.0f) - f, 0.0F);
		bigBoatEntityRenderState.bubbleWobble = bigBoatEntity.getBubbleAngle(f);
		bigBoatEntityRenderState.submergedInWater = bigBoatEntity.isUnderWater();
		bigBoatEntityRenderState.leftPaddleAngle = bigBoatEntity.getRowingTime(0, f);
		bigBoatEntityRenderState.rightPaddleAngle = bigBoatEntity.getRowingTime(1, f);

		bigBoatEntityRenderState.hasChest = bigBoatEntity.hasChest();
		bigBoatEntityRenderState.players = bigBoatEntity.countRowable();
		if (bigBoatEntity.getBanner().is(ItemTags.BANNERS)) {
			this.itemModelResolver.updateForNonLiving(bigBoatEntityRenderState.bannerRenderState, bigBoatEntity.getBanner(), ItemDisplayContext.HEAD, bigBoatEntity);
		} else {
			bigBoatEntityRenderState.bannerRenderState.clear();
		}
		bigBoatEntityRenderState.huge = false;
	}
}
