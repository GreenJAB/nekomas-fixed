package net.greenjab.nekomasfixed.render.block.entity;

import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.block.ClockBlock;
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
import net.minecraft.item.ItemStack;
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
		clockBlockEntityRenderState.poweredTicks = 0;//clockBlockEntity.getPoweredTicks(f);
		BlockState blockState = clockBlockEntity.getCachedState();

		boolean bl = blockState.getBlock() instanceof WallClockBlock;
		clockBlockEntityRenderState.facing = bl ? blockState.get(WallClockBlock.FACING) : null;
		int i = bl ? RotationPropertyHelper.fromDirection(clockBlockEntityRenderState.facing.getOpposite()) : blockState.get(ClockBlock.ROTATION);
		clockBlockEntityRenderState.yaw = RotationPropertyHelper.toDegrees(i);
		clockBlockEntityRenderState.wall = bl;

		if (clockBlockEntity instanceof ClockBlockEntity clockBlockEntity2) {
			ItemStack itemStack = Items.CLOCK.getDefaultStack();
			if (!itemStack.isEmpty()) {
				ItemRenderState itemRenderState = new ItemRenderState();
				this.itemModelManager.clearAndUpdate(itemRenderState, itemStack, ItemDisplayContext.FIXED, clockBlockEntity2.getEntityWorld(), clockBlockEntity2, HashCommon.long2int(clockBlockEntity.getPos().asLong()));
				clockBlockEntityRenderState.itemRenderState = itemRenderState;
			}
			clockBlockEntityRenderState.timer = clockBlockEntity2.getTimer();
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
		int time = clockBlockEntityRenderState.timer+20;
		int min = time/1200;
		int sec = (time-min*1200)/20;
		//System.out.println(clockBlockEntityRenderState.timer + ", " + min + ", " + sec);
		if (time>0){
			orderedRenderCommandQueue.submitLabel(matrixStack, clockBlockEntityRenderState.wall?new Vec3d(-0.4*Math.sin(clockBlockEntityRenderState.yaw*Math.PI/180.0)+0.5, 0.75, 0.4*Math.cos(clockBlockEntityRenderState.yaw*Math.PI/180.0)+0.5):new Vec3d(0.5, 0.5, 0.5), 0,
					Text.of((min!=0?min+" Minute"+(min!=1?"s":"") + ", ":"") + sec + " Second"+(sec!=1?"s":"")), true, ii, 100, cameraRenderState);

		}


		ItemRenderState itemRenderState = clockBlockEntityRenderState.itemRenderState;
		if (itemRenderState != null) {
			this.renderItem(clockBlockEntityRenderState, itemRenderState, matrixStack, orderedRenderCommandQueue, clockBlockEntityRenderState.yaw, clockBlockEntityRenderState.wall);
		}
	}

	private void renderItem(
			ClockBlockEntityRenderState state, ItemRenderState itemRenderState, MatrixStack matrices, OrderedRenderCommandQueue queue, float rotationDegrees, boolean wall
	) {
		Vec3d vec3d = new Vec3d(0,  wall?0:-0.15, wall?0.46875:-0.2);
		matrices.push();
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotationDegrees));
		matrices.translate(vec3d);
		if (!wall) matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30));
		float scale = wall?1:0.8f;
		matrices.scale(scale,scale,scale);
		itemRenderState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
		matrices.pop();
	}

}
