package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.HollowLogBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import static net.minecraft.client.renderer.entity.DisplayRenderer.BLOCK_DISPLAY_CONTEXT;

public class HollowLogBlockEntityRenderer implements BlockEntityRenderer<HollowLogBlockEntity, HollowLogBlockEntityRenderState>{
    protected final BlockModelResolver blockModelResolver;
    public HollowLogBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
    }

    @Override
    public @NonNull HollowLogBlockEntityRenderState createRenderState() {
        return new HollowLogBlockEntityRenderState();
    }

    public void extractRenderState(@NonNull HollowLogBlockEntity blockEntity,
                                   @NonNull HollowLogBlockEntityRenderState state,
                                   float tickProgress,
                                   @NonNull Vec3 cameraPos,
                                   @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {
        BlockEntityRenderState.extractBase(blockEntity, state, crumblingOverlayCommand);
        if (blockEntity.getStoredBlock().is(Blocks.AIR)) state.displayBlockModel.clear();
        else this.blockModelResolver.update(state.displayBlockModel, blockEntity.getStoredBlock(), BLOCK_DISPLAY_CONTEXT);
    }

    @Override
    public void submit(HollowLogBlockEntityRenderState state,
                       @NonNull PoseStack matrixStack,
                       @NonNull SubmitNodeCollector queue,
                       @NonNull CameraRenderState cameraState) {
        BlockModelRenderState displayBlockModel = state.displayBlockModel;
        if (!displayBlockModel.isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.125, 0.125, 0.125);
            matrixStack.scale(0.75f, 0.75f, 0.75f);
            displayBlockModel.submit(matrixStack, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            matrixStack.popPose();
        }
    }
}
