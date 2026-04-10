package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registry.block.entity.HollowJungleLogBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.HollowJungleLogBlockEntityRenderState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;

public class HollowJungleLogBlockEntityRenderer implements BlockEntityRenderer<HollowJungleLogBlockEntity, HollowJungleLogBlockEntityRenderState>{

    @Override
    public HollowJungleLogBlockEntityRenderState createRenderState() {
        return new HollowJungleLogBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(HollowJungleLogBlockEntity blockEntity,
                                  HollowJungleLogBlockEntityRenderState state,
                                  float tickProgress,
                                  Vec3d cameraPos,
                                  ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {

        state.blockState = blockEntity.getStoredBlock();
        BlockEntityRenderState.updateBlockEntityRenderState(blockEntity, state, crumblingOverlay);
    }

    @Override
    public void render(HollowJungleLogBlockEntityRenderState state,
                       MatrixStack matrixStack,
                       OrderedRenderCommandQueue queue,
                       CameraRenderState cameraState) {

        MinecraftClient client = MinecraftClient.getInstance();
        BlockRenderManager blockRenderManager = client.getBlockRenderManager();

        if (state.blockState == null) return;

        matrixStack.push();
        matrixStack.translate(0.02, 0.15, 0.02);
        matrixStack.scale(0.90f, 0.7f, 0.90f);

        blockRenderManager.renderBlockAsEntity(
                state.blockState,
                matrixStack,
                client.getBufferBuilders().getEntityVertexConsumers(),
                state.lightmapCoordinates,
                OverlayTexture.DEFAULT_UV
        );

        matrixStack.pop();
    }
}
