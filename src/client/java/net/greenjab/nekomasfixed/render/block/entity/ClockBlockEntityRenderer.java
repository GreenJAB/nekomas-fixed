package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.block.FloorClockBlock;
import net.greenjab.nekomasfixed.registry.block.WallClockBlock;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.ClockBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ClockBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T, ClockBlockEntityRenderState> {
	private final ItemModelResolver itemModelManager;

	public ClockBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		this.itemModelManager = context.itemModelResolver();
	}


	public @NonNull ClockBlockEntityRenderState createRenderState() {
		return new ClockBlockEntityRenderState();
	}

	public void extractRenderState(
            @NonNull T clockBlockEntity,
            @NonNull ClockBlockEntityRenderState clockBlockEntityRenderState,
            float f,
            @NonNull Vec3 vec3d,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand
	) {
		BlockEntityRenderer.super.extractRenderState(clockBlockEntity, clockBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
		clockBlockEntityRenderState.poweredTicks = 0;
		BlockState blockState = clockBlockEntity.getBlockState();

		boolean bl = blockState.getBlock() instanceof WallClockBlock;
		clockBlockEntityRenderState.facing = bl ? blockState.getValue(WallClockBlock.FACING) : null;
		int i = bl ? RotationSegment.convertToSegment(clockBlockEntityRenderState.facing.getOpposite()) : blockState.getValue(FloorClockBlock.ROTATION);
		clockBlockEntityRenderState.yaw = RotationSegment.convertToDegrees(i);
		clockBlockEntityRenderState.wall = bl;

		if (clockBlockEntity instanceof ClockBlockEntity clockBlockEntity2) {
			clockBlockEntityRenderState.bell = clockBlockEntity2.hasBell();
			clockBlockEntityRenderState.timer = clockBlockEntity2.getTimer();
			clockBlockEntityRenderState.dayTime = clockBlockEntity2.getShowsTime() && !clockBlockEntity2.hasBell() ? (int) (int) ((clockBlockEntity2.level().getOverworldClockTime() + 6000) % 24000) :-1;

			ItemStackRenderState clockRenderState = new ItemStackRenderState();
			this.itemModelManager.updateForTopItem(clockRenderState, Items.CLOCK.getDefaultInstance(), ItemDisplayContext.FIXED, clockBlockEntity2.level(), clockBlockEntity2, HashCommon.long2int(clockBlockEntity.getBlockPos().asLong()));
			clockBlockEntityRenderState.clockRenderState = clockRenderState;
			ItemStackRenderState standRenderState = new ItemStackRenderState();
			this.itemModelManager.updateForTopItem(standRenderState, Items.SPRUCE_FENCE_GATE.getDefaultInstance(), ItemDisplayContext.FIXED, clockBlockEntity2.level(), clockBlockEntity2, HashCommon.long2int(clockBlockEntity.getBlockPos().asLong())+1 );
			clockBlockEntityRenderState.standRenderState = standRenderState;
			ItemStackRenderState bellRenderState = new ItemStackRenderState();
			this.itemModelManager.updateForTopItem(bellRenderState, Items.BELL.getDefaultInstance(), ItemDisplayContext.FIXED, clockBlockEntity2.level(), clockBlockEntity2, HashCommon.long2int(clockBlockEntity.getBlockPos().asLong())+1 );
			clockBlockEntityRenderState.bellRenderState = bellRenderState;

		}
	}

	public void submit(
            ClockBlockEntityRenderState clockBlockEntityRenderState,
            @NonNull PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		/*matrixStack.push();
		matrixStack.translate(0.5F, 0.5F, 0.5F);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-clockBlockEntityRenderState.yaw));
		matrixStack.translate(-0.5F, -0.5F, -0.5F);
		float f = clockBlockEntityRenderState.lidAnimationProgress;
		f = 1.0F - f;
		f = 1.0F - f * f * f;
		SpriteIdentifier spriteIdentifier = TextureRegistry.getClockTextureId(clockBlockEntityRenderState.variant);
		RenderLayer renderLayer = spriteIdentifier.getRenderLayer(RenderLayers::entityCutout);
		Sprite sprite = this.materials.getSprite(spriteIdentifier);
		orderedRenderCommandQueue.submitModel(
				this.clockModel,
				f,
				matrixStack,
				renderLayer,
				clockBlockEntityRenderState.lightmapCoordinates,
				OverlayTexture.DEFAULT_UV,
				-1,
				sprite,
				0,
				clockBlockEntityRenderState.crumblingOverlay
			);

		matrixStack.pop();*/
		int ii = ARGB.colorFromFloat(1, 1.0F, 1.0F, 1.0F);
		if (clockBlockEntityRenderState.dayTime !=-1) {
			int hour = clockBlockEntityRenderState.dayTime/1000;
			int min = ((clockBlockEntityRenderState.dayTime%1000)*60)/1000;
			String string = (hour<10?"0":"") + hour + ":" + (min<10?"0":"") + min;
				orderedRenderCommandQueue.submitNameTag(matrixStack, clockBlockEntityRenderState.wall ? new Vec3(-0.4 * Math.sin(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5, 0.75, 0.4 * Math.cos(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5) : new Vec3(0.5, 0.5, 0.5), 0,
						Component.nullToEmpty(string), true, ii, 100, cameraRenderState);

		} else {
			int time = clockBlockEntityRenderState.timer + 20;
			int min = time / 1200;
			int sec = (time - min * 1200) / 20;
			if (time > 20) {
				orderedRenderCommandQueue.submitNameTag(matrixStack, clockBlockEntityRenderState.wall ? new Vec3(-0.4 * Math.sin(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5, 0.75, 0.4 * Math.cos(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5) : new Vec3(0.5, 0.5, 0.5), 0,
						Component.nullToEmpty((min != 0 ? min + " Minute" + (min != 1 ? "s" : "") + ", " : "") + sec + " Second" + (sec != 1 ? "s" : "")), true, ii, 100, cameraRenderState);

			}
		}
		matrixStack.translate(0.5F, 0.5F, 0.5F);
		ItemStackRenderState clockRenderState = clockBlockEntityRenderState.clockRenderState;
		if (!clockBlockEntityRenderState.wall && clockBlockEntityRenderState.timer>-ClockBlockEntity.timerDuration&& clockBlockEntityRenderState.timer<0) {
			matrixStack.mulPose(Axis.YP.rotationDegrees(10*(clockBlockEntityRenderState.timer%2==0?1:-1)));
		}
		if (clockRenderState != null) {
			this.renderClock(clockBlockEntityRenderState, clockRenderState, matrixStack, orderedRenderCommandQueue, clockBlockEntityRenderState.yaw, clockBlockEntityRenderState.wall);
		}
		ItemStackRenderState standRenderState = clockBlockEntityRenderState.standRenderState;
		if (!clockBlockEntityRenderState.wall && standRenderState != null) {
			this.renderStand(clockBlockEntityRenderState, standRenderState, matrixStack, orderedRenderCommandQueue, clockBlockEntityRenderState.yaw);
		}
		ItemStackRenderState bellRenderState = clockBlockEntityRenderState.bellRenderState;
		if (!clockBlockEntityRenderState.wall && clockBlockEntityRenderState.bell && bellRenderState != null) {
			this.renderBell(clockBlockEntityRenderState, bellRenderState, matrixStack, orderedRenderCommandQueue, clockBlockEntityRenderState.yaw);
		}
	}

	private void renderClock(
			ClockBlockEntityRenderState state, ItemStackRenderState itemRenderState, PoseStack matrices, SubmitNodeCollector queue, float rotationDegrees, boolean wall
	) {
		Vec3 vec3d = new Vec3(0,  wall?0:-0.15, wall?0.46875:-0.1);
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(-rotationDegrees));
		matrices.translate(vec3d);
		if (!wall) matrices.mulPose(Axis.XP.rotationDegrees(30));
		float scale = wall?1:0.8f;
		matrices.scale(scale,scale,scale);
		itemRenderState.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
		matrices.popPose();
	}

	private void renderStand(
			ClockBlockEntityRenderState state, ItemStackRenderState itemRenderState, PoseStack matrices, SubmitNodeCollector queue, float rotationDegrees
	) {
		Vec3 vec3d = new Vec3(0, -0.35, 0.2);
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(-rotationDegrees));
		matrices.translate(vec3d);
		matrices.mulPose(Axis.XP.rotationDegrees(-30));
		float scale = 1f;
		matrices.scale(scale,1.6f*scale,scale);
		itemRenderState.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
		matrices.popPose();
	}

	private void renderBell(
			ClockBlockEntityRenderState state, ItemStackRenderState itemRenderState, PoseStack matrices, SubmitNodeCollector queue, float rotationDegrees
	) {
		Vec3 vec3d = new Vec3(0, 0.35, 0.1);
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(-rotationDegrees));
		matrices.translate(vec3d);
		matrices.mulPose(Axis.ZP.rotationDegrees(180));
		float scale = 0.5f;
		matrices.scale(scale,scale,scale);
		itemRenderState.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
		matrices.popPose();
	}

}
