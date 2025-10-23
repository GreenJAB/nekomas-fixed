package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.greenjab.nekomasfixed.render.entity.model.MegaBoatEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.MegaBoatEntityRenderState;
import net.minecraft.block.*;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BannerBlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.AbstractBoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.CopperGolemHeadBlockFeatureRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.CopperGolemEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class MegaBoatEntityRenderer extends EntityRenderer<MegaBoatEntity, MegaBoatEntityRenderState> {
	private final Model.SinglePartModel waterMaskModel;
	private final Identifier texture;
	private final EntityModel<MegaBoatEntityRenderState> model;
	
	public MegaBoatEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer layer) {
		super(context);
		this.shadowRadius = 0.8F;
		this.texture = layer.id().withPath(path -> "textures/entity/" + path + ".png");
		this.waterMaskModel = new Model.SinglePartModel(context.getPart(EntityModelLayerRegistry.MEGA_BOAT), id -> RenderLayer.getWaterMask());
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
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
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
		this.renderWaterMask(megaBoatEntityRenderState, matrixStack, orderedRenderCommandQueue, megaBoatEntityRenderState.light);
		matrixStack.pop();

		this.renderBanner(matrixStack, orderedRenderCommandQueue, megaBoatEntityRenderState.banner, megaBoatEntityRenderState, cameraRenderState);

		super.render(megaBoatEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	private void renderBanner(
			MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack banner, MegaBoatEntityRenderState megaBoatEntityRenderState, CameraRenderState cameraRenderState
			//MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, Optional<BlockState> optional, EquipmentSlot slot, MegaBoatEntityRenderState megaBoatEntityRenderState
	) {
		if (!banner.isEmpty()) {
			Optional<BlockState> optional = Optional.of(banner).flatMap(stack -> {
						if (stack.getItem() instanceof BlockItem blockItem) {
							BlockStateComponent blockStateComponent = (BlockStateComponent)stack.getOrDefault(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT);
							return Optional.of(blockStateComponent.applyToState(blockItem.getBlock().getDefaultState()));
						} else {
							return Optional.empty();
						}
			});
			BlockState blockState = optional.get();
			matrices.push();
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-megaBoatEntityRenderState.yaw));
			matrices.translate(-0.5, 0.2, -0.5);
			orderedRenderCommandQueue.submitBlock(matrices, blockState, megaBoatEntityRenderState.light, OverlayTexture.DEFAULT_UV, megaBoatEntityRenderState.outlineColor);
			matrices.pop();
		}
		/*if (banner.isIn(ItemTags.BANNERS)) {
			if (banner.getItem() instanceof BannerItem bannerItem){
				BannerBlockEntityRenderState bannerBlockEntityRenderState = new BannerBlockEntityRenderState();
				bannerBlockEntityRenderState.dyeColor = bannerItem.getColor();
				bannerBlockEntityRenderState.bannerPatterns = banner.getComponents().get(DataComponentTypes.BANNER_PATTERNS);
				bannerBlockEntityRenderState.pitch = 0;
				bannerBlockEntityRenderState.yaw = 0;
				bannerBlockEntityRenderState.standing = false;
				BannerBlockEntityRenderer.render(
						bannerBlockEntityRenderState,
						matrices,
						orderedRenderCommandQueue,
						cameraRenderState
				);
			}

		}*/
	}

	protected EntityModel<MegaBoatEntityRenderState> getModel() {
		return this.model;
	}

	protected RenderLayer getRenderLayer() {
		return this.model.getLayer(this.texture);
	}

	protected void renderWaterMask(MegaBoatEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light) {
		if (!state.submergedInWater) {
			orderedRenderCommandQueue.submitModel(
					this.waterMaskModel, Unit.INSTANCE, matrices, this.waterMaskModel.getLayer(this.texture), light, OverlayTexture.DEFAULT_UV, state.outlineColor, null
			);
		}
	}

	public MegaBoatEntityRenderState createRenderState() {
		return new MegaBoatEntityRenderState();
	}

	public void updateRenderState(MegaBoatEntity megaBoatEntity, MegaBoatEntityRenderState MegaBoatEntityRenderState, float f) {
		super.updateRenderState(megaBoatEntity, MegaBoatEntityRenderState, f);
		MegaBoatEntityRenderState.yaw = megaBoatEntity.getLerpedYaw(f);
		MegaBoatEntityRenderState.damageWobbleTicks = megaBoatEntity.getDamageWobbleTicks() - f;
		MegaBoatEntityRenderState.damageWobbleSide = megaBoatEntity.getDamageWobbleSide();
		MegaBoatEntityRenderState.damageWobbleStrength = Math.max(megaBoatEntity.getDamageWobbleStrength() - f, 0.0F);
		MegaBoatEntityRenderState.bubbleWobble = megaBoatEntity.lerpBubbleWobble(f);
		MegaBoatEntityRenderState.submergedInWater = megaBoatEntity.isSubmergedInWater();
		MegaBoatEntityRenderState.leftPaddleAngle = megaBoatEntity.lerpPaddlePhase(0, f);
		MegaBoatEntityRenderState.rightPaddleAngle = megaBoatEntity.lerpPaddlePhase(1, f);
		MegaBoatEntityRenderState.players = megaBoatEntity.getPlayerPassengers();
		MegaBoatEntityRenderState.banner = megaBoatEntity.getBanner();
		/*MegaBoatEntityRenderState.banner = Optional.of(megaBoatEntity.getBanner()).flatMap(stack -> {
			if (stack.getItem() instanceof BlockItem blockItem) {
				BlockStateComponent blockStateComponent = (BlockStateComponent)stack.getOrDefault(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT);
				return Optional.of(blockStateComponent.applyToState(blockItem.getBlock().getDefaultState());
			} else {
				return Optional.empty();
			}
		});*/
	}
}
