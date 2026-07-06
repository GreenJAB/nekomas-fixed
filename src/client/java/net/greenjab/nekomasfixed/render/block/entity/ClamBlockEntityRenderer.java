package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;
import net.greenjab.nekomasfixed.registry.block.ClamBlock;
import net.greenjab.nekomasfixed.registry.block.entity.ClamBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.block.entity.state.ClamBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ClamBlockEntityRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<@NonNull T, ClamBlockEntityRenderState> {
	private final MaterialSet materials;
	private final ClamBlockModel clamModel;
	private final ItemModelResolver itemModelManager;

	public ClamBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		this.materials = context.materials();
		this.clamModel = new ClamBlockModel(context.bakeLayer(ModEntityLayerRegistry.CLAM));
		this.itemModelManager = context.itemModelResolver();
	}


	public @NonNull ClamBlockEntityRenderState createRenderState() {
		return new ClamBlockEntityRenderState();
	}

	public void extractRenderState(
            @NonNull T blockEntity,
            @NonNull ClamBlockEntityRenderState clamBlockEntityRenderState,
            float f,
            @NonNull Vec3 vec3d,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand
	) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, clamBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
		boolean bl = blockEntity.getLevel() != null;
		BlockState blockState = bl ? blockEntity.getBlockState() : BlockRegistry.CLAM.defaultBlockState().setValue(ClamBlock.FACING, Direction.SOUTH);
		clamBlockEntityRenderState.yaw = (blockState.getValue(ClamBlock.FACING)).toYRot();
		clamBlockEntityRenderState.variant = this.getVariant(blockEntity);

		clamBlockEntityRenderState.lidAnimationProgress = ClamBlock.getAnimationProgressRetriever(blockEntity).getFallback().get(f);

		if (clamBlockEntityRenderState.lidAnimationProgress > 0 && blockEntity instanceof ClamBlockEntity clamBlockEntity) {
			NonNullList<ItemStack> defaultedList = clamBlockEntity.getItems();
			ItemStack itemStack = defaultedList.get(0);
			if (!itemStack.isEmpty()) {
				ItemStackRenderState itemRenderState = new ItemStackRenderState();
				this.itemModelManager.updateForTopItem(itemRenderState, itemStack, ItemDisplayContext.FIXED, clamBlockEntity.level(), clamBlockEntity, HashCommon.long2int(clamBlockEntity.getBlockPos().asLong()));
				clamBlockEntityRenderState.itemRenderState = itemRenderState;
			}
		}
	}

	public void submit(
            ClamBlockEntityRenderState clamBlockEntityRenderState,
            PoseStack matrixStack,
            SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		matrixStack.pushPose();
		matrixStack.translate(0.5F, 0.5F, 0.5F);
		matrixStack.mulPose(Axis.YP.rotationDegrees(-clamBlockEntityRenderState.yaw));
		matrixStack.translate(-0.5F, -0.5F, -0.5F);
		float f = clamBlockEntityRenderState.lidAnimationProgress;
		f = 1.0F - f;
		f = 1.0F - f * f * f;
		Material spriteIdentifier = TextureRegistry.getClamTextureId(clamBlockEntityRenderState.variant);
		RenderType renderLayer = spriteIdentifier.renderType(RenderTypes::entityCutout);
		TextureAtlasSprite sprite = this.materials.get(spriteIdentifier);
		orderedRenderCommandQueue.submitModel(
				this.clamModel,
				f,
				matrixStack,
				renderLayer,
				clamBlockEntityRenderState.lightCoords,
				OverlayTexture.NO_OVERLAY,
				-1,
				sprite,
				0,
				clamBlockEntityRenderState.breakProgress
			);

		matrixStack.popPose();

		if (clamBlockEntityRenderState.lidAnimationProgress>0) {
			Direction direction = clamBlockEntityRenderState.blockState.getValue(ClamBlock.FACING);
			float d = direction.getAxis().isHorizontal() ? -direction.toYRot() : 180.0F;

			ItemStackRenderState itemRenderState = clamBlockEntityRenderState.itemRenderState;
			if (itemRenderState != null) {
				this.renderItem(clamBlockEntityRenderState, itemRenderState, matrixStack, orderedRenderCommandQueue, d);
			}
		}
	}

	private void renderItem(
			ClamBlockEntityRenderState state, ItemStackRenderState itemRenderState, PoseStack matrices, SubmitNodeCollector queue, float rotationDegrees
	) {
		Vec3 vec3d = new Vec3(0, -0.37, -0.11);
		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);
		matrices.mulPose(Axis.YP.rotationDegrees(rotationDegrees+180));
		matrices.translate(vec3d);
		matrices.mulPose(Axis.XP.rotationDegrees(90));
		matrices.scale(0.5F, 0.5F, 0.5F);
		itemRenderState.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
		matrices.popPose();
	}

	private ClamBlockEntityRenderState.Variant getVariant(BlockEntity blockEntity) {
		if (blockEntity.getBlockState().getBlock() instanceof ClamBlock clamBlock) {
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
