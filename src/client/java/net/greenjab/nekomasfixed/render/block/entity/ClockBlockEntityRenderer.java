package net.greenjab.nekomasfixed.render.block.entity;

import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.block.FloorClockBlock;
import net.greenjab.nekomasfixed.registry.block.WallClockBlock;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.ClockBlockEntityRenderState;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ClockBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T, ClockBlockEntityRenderState> {
	private final ItemModelManager itemModelManager;

	public ClockBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.itemModelManager = context.itemModelManager();
	}


	public ClockBlockEntityRenderState createRenderState() {
		return new ClockBlockEntityRenderState();
	}

	public void updateRenderState(
		T clockBlockEntity,
		ClockBlockEntityRenderState clockBlockEntityRenderState,
		float f,
		Vec3d vec3d,
		@Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand
	) {
		BlockEntityRenderer.super.updateRenderState(clockBlockEntity, clockBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
		clockBlockEntityRenderState.poweredTicks = 0;
		BlockState blockState = clockBlockEntity.getCachedState();

		boolean bl = blockState.getBlock() instanceof WallClockBlock;
		clockBlockEntityRenderState.facing = bl ? blockState.get(WallClockBlock.FACING) : null;
		int i = bl ? RotationPropertyHelper.fromDirection(clockBlockEntityRenderState.facing.getOpposite()) : blockState.get(FloorClockBlock.ROTATION);
		clockBlockEntityRenderState.yaw = RotationPropertyHelper.toDegrees(i);
		clockBlockEntityRenderState.wall = bl;

		if (clockBlockEntity instanceof ClockBlockEntity clockBlockEntity2) {
			clockBlockEntityRenderState.bell = clockBlockEntity2.hasBell();
			clockBlockEntityRenderState.timer = clockBlockEntity2.getTimer();
			clockBlockEntityRenderState.dayTime = clockBlockEntity2.getShowsTime() && !clockBlockEntity2.hasBell() ? (int) (int) ((clockBlockEntity2.getEntityWorld().getTimeOfDay() + 6000) % 24000) :-1;

			ItemRenderState clockRenderState = new ItemRenderState();
			this.itemModelManager.clearAndUpdate(clockRenderState, Items.CLOCK.getDefaultStack(), ItemDisplayContext.FIXED, clockBlockEntity2.getEntityWorld(), clockBlockEntity2, HashCommon.long2int(clockBlockEntity.getPos().asLong()));
			clockBlockEntityRenderState.clockRenderState = clockRenderState;
			ItemRenderState standRenderState = new ItemRenderState();
			this.itemModelManager.clearAndUpdate(standRenderState, Items.SPRUCE_FENCE_GATE.getDefaultStack(), ItemDisplayContext.FIXED, clockBlockEntity2.getEntityWorld(), clockBlockEntity2, HashCommon.long2int(clockBlockEntity.getPos().asLong())+1 );
			clockBlockEntityRenderState.standRenderState = standRenderState;
			ItemRenderState bellRenderState = new ItemRenderState();
			this.itemModelManager.clearAndUpdate(bellRenderState, Items.BELL.getDefaultStack(), ItemDisplayContext.FIXED, clockBlockEntity2.getEntityWorld(), clockBlockEntity2, HashCommon.long2int(clockBlockEntity.getPos().asLong())+1 );
			clockBlockEntityRenderState.bellRenderState = bellRenderState;

		}
	}

	public void render(
		ClockBlockEntityRenderState clockBlockEntityRenderState,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
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
		int ii = ColorHelper.fromFloats(1, 1.0F, 1.0F, 1.0F);
		if (clockBlockEntityRenderState.dayTime !=-1) {
			int hour = clockBlockEntityRenderState.dayTime/1000;
			int min = ((clockBlockEntityRenderState.dayTime%1000)*60)/1000;
			String string = (hour<10?"0":"") + hour + ":" + (min<10?"0":"") + min;
				orderedRenderCommandQueue.submitLabel(matrixStack, clockBlockEntityRenderState.wall ? new Vec3d(-0.4 * Math.sin(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5, 0.75, 0.4 * Math.cos(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5) : new Vec3d(0.5, 0.5, 0.5), 0,
						Text.of(string), true, ii, 100, cameraRenderState);

		} else {
			int time = clockBlockEntityRenderState.timer + 20;
			int min = time / 1200;
			int sec = (time - min * 1200) / 20;
			if (time > 20) {
				orderedRenderCommandQueue.submitLabel(matrixStack, clockBlockEntityRenderState.wall ? new Vec3d(-0.4 * Math.sin(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5, 0.75, 0.4 * Math.cos(clockBlockEntityRenderState.yaw * Math.PI / 180.0) + 0.5) : new Vec3d(0.5, 0.5, 0.5), 0,
						Text.of((min != 0 ? min + " Minute" + (min != 1 ? "s" : "") + ", " : "") + sec + " Second" + (sec != 1 ? "s" : "")), true, ii, 100, cameraRenderState);

			}
		}
		matrixStack.translate(0.5F, 0.5F, 0.5F);
		ItemRenderState clockRenderState = clockBlockEntityRenderState.clockRenderState;
		if (!clockBlockEntityRenderState.wall && clockBlockEntityRenderState.timer>-ClockBlockEntity.timerDuration&& clockBlockEntityRenderState.timer<0) {
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(10*(clockBlockEntityRenderState.timer%2==0?1:-1)));
		}
		if (clockRenderState != null) {
			this.renderClock(clockBlockEntityRenderState, clockRenderState, matrixStack, orderedRenderCommandQueue, clockBlockEntityRenderState.yaw, clockBlockEntityRenderState.wall);
		}
		ItemRenderState standRenderState = clockBlockEntityRenderState.standRenderState;
		if (!clockBlockEntityRenderState.wall && standRenderState != null) {
			this.renderStand(clockBlockEntityRenderState, standRenderState, matrixStack, orderedRenderCommandQueue, clockBlockEntityRenderState.yaw);
		}
		ItemRenderState bellRenderState = clockBlockEntityRenderState.bellRenderState;
		if (!clockBlockEntityRenderState.wall && clockBlockEntityRenderState.bell && bellRenderState != null) {
			this.renderBell(clockBlockEntityRenderState, bellRenderState, matrixStack, orderedRenderCommandQueue, clockBlockEntityRenderState.yaw);
		}
	}

	private void renderClock(
			ClockBlockEntityRenderState state, ItemRenderState itemRenderState, MatrixStack matrices, OrderedRenderCommandQueue queue, float rotationDegrees, boolean wall
	) {
		Vec3d vec3d = new Vec3d(0,  wall?0:-0.15, wall?0.46875:-0.1);
		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotationDegrees));
		matrices.translate(vec3d);
		if (!wall) matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30));
		float scale = wall?1:0.8f;
		matrices.scale(scale,scale,scale);
		itemRenderState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
		matrices.pop();
	}

	private void renderStand(
			ClockBlockEntityRenderState state, ItemRenderState itemRenderState, MatrixStack matrices, OrderedRenderCommandQueue queue, float rotationDegrees
	) {
		Vec3d vec3d = new Vec3d(0, -0.35, 0.2);
		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotationDegrees));
		matrices.translate(vec3d);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-30));
		float scale = 1f;
		matrices.scale(scale,1.6f*scale,scale);
		itemRenderState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
		matrices.pop();
	}

	private void renderBell(
			ClockBlockEntityRenderState state, ItemRenderState itemRenderState, MatrixStack matrices, OrderedRenderCommandQueue queue, float rotationDegrees
	) {
		Vec3d vec3d = new Vec3d(0, 0.35, 0.1);
		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotationDegrees));
		matrices.translate(vec3d);
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
		float scale = 0.5f;
		matrices.scale(scale,scale,scale);
		itemRenderState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
		matrices.pop();
	}

}
