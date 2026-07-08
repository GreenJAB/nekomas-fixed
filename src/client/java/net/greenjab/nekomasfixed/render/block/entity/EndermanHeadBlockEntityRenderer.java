package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.block.AbstractEndermanHeadBlock;
import net.greenjab.nekomasfixed.registry.block.FloorEndermanHeadHead;
import net.greenjab.nekomasfixed.registry.block.WallEndermanHeadHead;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanEyesBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.model.EndermanHeadBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.state.EndermanHeadBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class EndermanHeadBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T, EndermanHeadBlockEntityRenderState> {

	private final EndermanHeadBlockModel<EndermanHeadBlockEntityRenderState> endermanHeadModel;
	private final EndermanEyesBlockModel<EndermanHeadBlockEntityRenderState> endermanEyesModel;

	private final RandomSource random = RandomSource.create();
	private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/enderman/enderman.png");
	private static final Identifier TEXTURE_EYES = Identifier.withDefaultNamespace("textures/entity/enderman/enderman_eyes.png");

	public EndermanHeadBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		this.endermanHeadModel = new EndermanHeadBlockModel<>(context.bakeLayer(ModEntityLayerRegistry.ENDERMAN_HEAD));
		this.endermanEyesModel = new EndermanEyesBlockModel<>(context.bakeLayer(ModEntityLayerRegistry.ENDERMAN_HEAD));
	}


	public @NonNull EndermanHeadBlockEntityRenderState createRenderState() {
		return new EndermanHeadBlockEntityRenderState();
	}

	public void extractRenderState(
            @NonNull T endermanSkullBlockEntity,
            @NonNull EndermanHeadBlockEntityRenderState endermanHeadBlockEntityRenderState,
            float f,
            @NonNull Vec3 vec3d,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand
	) {
		BlockEntityRenderer.super.extractRenderState(endermanSkullBlockEntity, endermanHeadBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
		BlockState blockState = endermanSkullBlockEntity.getBlockState();

		boolean bl = blockState.getBlock() instanceof WallEndermanHeadHead;
		endermanHeadBlockEntityRenderState.facing = bl ? blockState.getValue(WallEndermanHeadHead.FACING) : null;
		int i = bl ? RotationSegment.convertToSegment(endermanHeadBlockEntityRenderState.facing.getOpposite()) : blockState.getValue(FloorEndermanHeadHead.ROTATION);
		endermanHeadBlockEntityRenderState.yaw = RotationSegment.convertToDegrees(i);
		endermanHeadBlockEntityRenderState.wall = bl;
		endermanHeadBlockEntityRenderState.powered = blockState.getValue(AbstractEndermanHeadBlock.POWER)>0;

	}

	public void submit(
            EndermanHeadBlockEntityRenderState endermanHeadBlockEntityRenderState,
            PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		matrixStack.pushPose();
		Direction dir = endermanHeadBlockEntityRenderState.facing;
		if (dir == null) {
			matrixStack.translate(0.5F, 0.0F, 0.5F);
		} else {
			matrixStack.translate(0.5F - dir.getStepX() * 0.2499F, 0.25F, 0.5F - dir.getStepZ() * 0.2499F);
		}

		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		matrixStack.mulPose(Axis.YP.rotationDegrees(endermanHeadBlockEntityRenderState.yaw));
		matrixStack.translate(-0.5F, -0.5f, -0.5F);
		if (endermanHeadBlockEntityRenderState.powered) {
			if (endermanHeadBlockEntityRenderState.wall) {
				matrixStack.translate(this.random.nextGaussian() * 0.02, this.random.nextGaussian() * 0.02, 0.0F);
				} else {
				matrixStack.translate(this.random.nextGaussian() * 0.02, 0.0F, this.random.nextGaussian() * 0.02);
			}
		}

		RenderType renderLayer = RenderTypes.entityCutout(TEXTURE);
		RenderType renderLayerEyes = RenderTypes.eyes(TEXTURE_EYES);
		orderedRenderCommandQueue.submitModel(this.endermanHeadModel, endermanHeadBlockEntityRenderState, matrixStack,
				renderLayer, endermanHeadBlockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0, endermanHeadBlockEntityRenderState.breakProgress);
		orderedRenderCommandQueue.submitModel(this.endermanEyesModel, endermanHeadBlockEntityRenderState, matrixStack,
				renderLayerEyes, endermanHeadBlockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0, endermanHeadBlockEntityRenderState.breakProgress);
		matrixStack.popPose();
	}

}
