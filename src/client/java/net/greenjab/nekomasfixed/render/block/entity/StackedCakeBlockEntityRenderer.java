package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.StackedCakeBlockEntityRenderState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;


public class StackedCakeBlockEntityRenderer implements BlockEntityRenderer<StackedCakeBlockEntity, StackedCakeBlockEntityRenderState> {

    public StackedCakeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void updateRenderState(StackedCakeBlockEntity blockEntity, StackedCakeBlockEntityRenderState state, float f, Vec3d vec3d, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlayCommand) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, f, vec3d, crumblingOverlayCommand);
        state.LAYER_2_STATE = blockEntity.LAYER_2_STATE;
        state.LAYER_3_STATE = blockEntity.LAYER_3_STATE;
        state.CANDLE_STATE = blockEntity.CANDLE_STATE;
    }

    @Override
    public StackedCakeBlockEntityRenderState createRenderState() {
        return new StackedCakeBlockEntityRenderState();
    }

    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }

    @Override
    public void render(StackedCakeBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        MinecraftClient client = MinecraftClient.getInstance();
        BlockRenderManager blockRenderManager = client.getBlockRenderManager();

        int i = 1;

        if (!state.LAYER_2_STATE.isOf(Blocks.AIR)) {
            matrices.push();
            float scale = (float)(1.0 - (0.2 * i));

            matrices.translate((1.0f - scale) / 2.0f, i * 0.5f, (1.0f - scale) / 2.0f);
            matrices.scale(scale, scale, scale);
            blockRenderManager.renderBlockAsEntity(
                    state.LAYER_2_STATE,
                    matrices,
                    client.getBufferBuilders().getEntityVertexConsumers(),
                    state.lightmapCoordinates,
                    OverlayTexture.DEFAULT_UV
            );
            matrices.pop();
            i=2;
            if (!state.LAYER_3_STATE.isOf(Blocks.AIR)) {
                matrices.push();
                scale = (float)(1.0 - (0.2 * i));

                matrices.translate((1.0f - scale) / 2.0f, i*0.5 -0.1, (1.0f - scale) / 2.0f);
                matrices.scale(scale, scale, scale);
                blockRenderManager.renderBlockAsEntity(
                        state.LAYER_3_STATE,
                        matrices,
                        client.getBufferBuilders().getEntityVertexConsumers(),
                        state.lightmapCoordinates,
                        OverlayTexture.DEFAULT_UV
                );
                matrices.pop();
                i=3;
            }
        }

        matrices.push();
        matrices.translate(0, i * 0.5f - ((i-1) * 0.1) - ((Math.max(0, i-2)) * 0.1), 0);
        blockRenderManager.renderBlockAsEntity(
                state.CANDLE_STATE,
                matrices,
                client.getBufferBuilders().getEntityVertexConsumers(),
                state.lightmapCoordinates,
                OverlayTexture.DEFAULT_UV
        );

        matrices.pop();
    }

}
