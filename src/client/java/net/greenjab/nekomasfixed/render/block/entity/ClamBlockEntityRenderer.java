package net.greenjab.nekomasfixed.render.block.entity;

import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;
import net.greenjab.nekomasfixed.registry.block.ClamBlock;
import net.greenjab.nekomasfixed.registry.block.entity.ClamBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.state.ClamBlockEntityRenderState;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ClamBlockEntityRenderer<T extends BlockEntity & LidOpenable> implements BlockEntityRenderer<T, ClamBlockEntityRenderState> {
	private final SpriteHolder materials;
	private final ClamBlockModel clamModel;
	private final ItemModelManager itemModelManager;

	public ClamBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.materials = context.spriteHolder();
		this.clamModel = new ClamBlockModel(context.getLayerModelPart(EntityModelLayerRegistry.CLAM));
		this.itemModelManager = context.itemModelManager();
	}


	public ClamBlockEntityRenderState createRenderState() {
		return new ClamBlockEntityRenderState();
	}

	public void updateRenderState(
		T blockEntity,
		ClamBlockEntityRenderState clamBlockEntityRenderState,
		float f,
		Vec3d vec3d,
		@Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand
	) {
		BlockEntityRenderer.super.updateRenderState(blockEntity, clamBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
		boolean bl = blockEntity.getWorld() != null;
		BlockState blockState = bl ? blockEntity.getCachedState() : (BlockState) BlockRegistry.CLAM.getDefaultState().with(ClamBlock.FACING, Direction.SOUTH);
		clamBlockEntityRenderState.yaw = ((Direction)blockState.get(ClamBlock.FACING)).getPositiveHorizontalDegrees();
		clamBlockEntityRenderState.variant = this.getVariant(blockEntity);

		clamBlockEntityRenderState.lidAnimationProgress = ClamBlock.getAnimationProgressRetriever(blockEntity).getFallback().get(f);

		if (clamBlockEntityRenderState.lidAnimationProgress > 0 && blockEntity instanceof ClamBlockEntity clamBlockEntity) {
			DefaultedList<ItemStack> defaultedList = clamBlockEntity.getHeldStacks();
			ItemStack itemStack = defaultedList.get(0);
			if (!itemStack.isEmpty()) {
				ItemRenderState itemRenderState = new ItemRenderState();
				this.itemModelManager.clearAndUpdate(itemRenderState, itemStack, ItemDisplayContext.FIXED, clamBlockEntity.getEntityWorld(), clamBlockEntity, HashCommon.long2int(clamBlockEntity.getPos().asLong()));
				clamBlockEntityRenderState.itemRenderState = itemRenderState;
			}
		}
	}

	public void render(
		ClamBlockEntityRenderState clamBlockEntityRenderState,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		matrixStack.push();
		matrixStack.translate(0.5F, 0.5F, 0.5F);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-clamBlockEntityRenderState.yaw));
		matrixStack.translate(-0.5F, -0.5F, -0.5F);
		float f = clamBlockEntityRenderState.lidAnimationProgress;
		f = 1.0F - f;
		f = 1.0F - f * f * f;
		SpriteIdentifier spriteIdentifier = TextureRegistry.getClamTextureId(clamBlockEntityRenderState.variant);
		RenderLayer renderLayer = spriteIdentifier.getRenderLayer(RenderLayer::getEntityCutout);
		Sprite sprite = this.materials.getSprite(spriteIdentifier);
		orderedRenderCommandQueue.submitModel(
				this.clamModel,
				f,
				matrixStack,
				renderLayer,
				clamBlockEntityRenderState.lightmapCoordinates,
				OverlayTexture.DEFAULT_UV,
				-1,
				sprite,
				0,
				clamBlockEntityRenderState.crumblingOverlay
			);

		matrixStack.pop();

		if (clamBlockEntityRenderState.lidAnimationProgress>0) {
			Direction direction = clamBlockEntityRenderState.blockState.get(ClamBlock.FACING);
			float d = direction.getAxis().isHorizontal() ? -direction.getPositiveHorizontalDegrees() : 180.0F;

			ItemRenderState itemRenderState = clamBlockEntityRenderState.itemRenderState;
			if (itemRenderState != null) {
				this.renderItem(clamBlockEntityRenderState, itemRenderState, matrixStack, orderedRenderCommandQueue, d);
			}
		}
	}

	private void renderItem(
			ClamBlockEntityRenderState state, ItemRenderState itemRenderState, MatrixStack matrices, OrderedRenderCommandQueue queue, float rotationDegrees
	) {
		Vec3d vec3d = new Vec3d(0, -0.37, -0.11);
		matrices.push();
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationDegrees+180));
		matrices.translate(vec3d);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
		matrices.scale(0.5F, 0.5F, 0.5F);
		Box box = itemRenderState.getModelBoundingBox();
		itemRenderState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
		matrices.pop();
	}

	private ClamBlockEntityRenderState.Variant getVariant(BlockEntity blockEntity) {
		if (blockEntity.getCachedState().getBlock() instanceof ClamBlock clamBlock) {
			return switch (clamBlock.getClamType()) {
				case REGULAR -> ClamBlockEntityRenderState.Variant.REGULAR;
				case BLUE -> ClamBlockEntityRenderState.Variant.BLUE;
				case PINK -> ClamBlockEntityRenderState.Variant.PINK;
				case PURPLE -> ClamBlockEntityRenderState.Variant.PURPLE;
            };
		}
		return ClamBlockEntityRenderState.Variant.REGULAR;
	}
}
