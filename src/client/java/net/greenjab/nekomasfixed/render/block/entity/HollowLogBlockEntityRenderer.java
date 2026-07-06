package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.HollowLogBlockEntityRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class HollowLogBlockEntityRenderer implements BlockEntityRenderer<HollowLogBlockEntity, HollowLogBlockEntityRenderState>{

    public HollowLogBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public @NonNull HollowLogBlockEntityRenderState createRenderState() {
        return new HollowLogBlockEntityRenderState();
    }

    public void extractRenderState(HollowLogBlockEntity blockEntity,
                                   HollowLogBlockEntityRenderState state,
                                   float tickProgress,
                                   @NonNull Vec3 cameraPos,
                                   @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {

        state.blockState = blockEntity.getStoredBlock();
        BlockEntityRenderState.extractBase(blockEntity, state, crumblingOverlayCommand);
    }

    @Override
    public void submit(HollowLogBlockEntityRenderState state,
                       @NonNull PoseStack matrixStack,
                       @NonNull SubmitNodeCollector queue,
                       @NonNull CameraRenderState cameraState) {

        Minecraft client = Minecraft.getInstance();
        BlockRenderDispatcher blockRenderManager = client.getBlockRenderer();

        if (state.blockState == null) return;

        matrixStack.pushPose();
        matrixStack.translate(0.125, 0.125, 0.125);
        matrixStack.scale(0.75f, 0.75f, 0.75f);

        blockRenderManager.renderSingleBlock(
                state.blockState,
                matrixStack,
                client.renderBuffers().bufferSource(),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY
        );

        matrixStack.popPose();
    }
}
