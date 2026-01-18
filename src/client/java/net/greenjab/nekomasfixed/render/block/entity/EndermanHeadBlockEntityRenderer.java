package net.greenjab.nekomasfixed.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.block.AbstractEndermanHeadBlock;
import net.greenjab.nekomasfixed.registry.block.FloorEndermanHeadHead;
import net.greenjab.nekomasfixed.registry.block.WallEndermanHeadHead;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanEyesBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanHeadBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.state.EndermanHeadBlockEntityRenderState;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class EndermanHeadBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T, EndermanHeadBlockEntityRenderState> {

	private final EndermanHeadBlockModel<EndermanHeadBlockEntityRenderState> endermanHeadModel;
	private final EndermanEyesBlockModel<EndermanHeadBlockEntityRenderState> endermanEyesModel;

	private final Random random = Random.create();
	private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/enderman/enderman.png");
	private static final Identifier TEXTURE_EYES = Identifier.ofVanilla("textures/entity/enderman/enderman_eyes.png");

	public EndermanHeadBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.endermanHeadModel = new EndermanHeadBlockModel<>(context.getLayerModelPart(EntityModelLayerRegistry.ENDERMAN_HEAD));
		this.endermanEyesModel = new EndermanEyesBlockModel<>(context.getLayerModelPart(EntityModelLayerRegistry.ENDERMAN_HEAD));
	}


	public EndermanHeadBlockEntityRenderState createRenderState() {
		return new EndermanHeadBlockEntityRenderState();
	}

	public void updateRenderState(
		T endermanSkullBlockEntity,
		EndermanHeadBlockEntityRenderState endermanHeadBlockEntityRenderState,
		float f,
		Vec3d vec3d,
		@Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand
	) {
		BlockEntityRenderer.super.updateRenderState(endermanSkullBlockEntity, endermanHeadBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
		BlockState blockState = endermanSkullBlockEntity.getCachedState();

		boolean bl = blockState.getBlock() instanceof WallEndermanHeadHead;
		endermanHeadBlockEntityRenderState.facing = bl ? blockState.get(WallEndermanHeadHead.FACING) : null;
		int i = bl ? RotationPropertyHelper.fromDirection(endermanHeadBlockEntityRenderState.facing.getOpposite()) : blockState.get(FloorEndermanHeadHead.ROTATION);
		endermanHeadBlockEntityRenderState.yaw = RotationPropertyHelper.toDegrees(i);
		endermanHeadBlockEntityRenderState.wall = bl;
		endermanHeadBlockEntityRenderState.powered = blockState.get(AbstractEndermanHeadBlock.POWER)>0;

	}

	public void render(
		EndermanHeadBlockEntityRenderState endermanHeadBlockEntityRenderState,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		matrixStack.push();
		Direction dir = endermanHeadBlockEntityRenderState.facing;
		if (dir == null) {
			matrixStack.translate(0.5F, 0.0F, 0.5F);
		} else {
			matrixStack.translate(0.5F - dir.getOffsetX() * 0.2499F, 0.25F, 0.5F - dir.getOffsetZ() * 0.2499F);
		}

		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(endermanHeadBlockEntityRenderState.yaw));
		matrixStack.translate(-0.5F, -0.5f, -0.5F);
		if (endermanHeadBlockEntityRenderState.powered) {
			if (endermanHeadBlockEntityRenderState.wall) {
				matrixStack.translate(this.random.nextGaussian() * 0.02, this.random.nextGaussian() * 0.02, 0.0F);
				} else {
				matrixStack.translate(this.random.nextGaussian() * 0.02, 0.0F, this.random.nextGaussian() * 0.02);
			}
		}

		//SpriteIdentifier spriteIdentifier = TextureRegistry.getEndermanSkullTextureId(endermanSkullBlockEntityRenderState.variant);
		RenderLayer renderLayer = RenderLayers.entityCutoutNoCull(TEXTURE);
		RenderLayer renderLayerEyes = RenderLayers.eyes(TEXTURE_EYES);
		//Sprite sprite = this.materials.getSprite(spriteIdentifier);
		orderedRenderCommandQueue.submitModel(this.endermanHeadModel, endermanHeadBlockEntityRenderState, matrixStack,
				renderLayer, endermanHeadBlockEntityRenderState.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0, endermanHeadBlockEntityRenderState.crumblingOverlay);
		orderedRenderCommandQueue.submitModel(this.endermanEyesModel, endermanHeadBlockEntityRenderState, matrixStack,
				renderLayerEyes, endermanHeadBlockEntityRenderState.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0, endermanHeadBlockEntityRenderState.crumblingOverlay);
		matrixStack.pop();
	}

}
