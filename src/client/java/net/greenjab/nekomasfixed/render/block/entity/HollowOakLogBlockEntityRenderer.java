package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.HollowOakLogBlockEntityRenderState;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;

public class HollowOakLogBlockEntityRenderer implements BlockEntityRenderer<HollowOakLogBlockEntity, HollowOakLogBlockEntityRenderState>{

    @Override
    public HollowOakLogBlockEntityRenderState createRenderState() {
        return new HollowOakLogBlockEntityRenderState();
    }

    @Override
    public void render(HollowOakLogBlockEntityRenderState state, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        MinecraftClient client = MinecraftClient.getInstance();
        BlockRenderManager blockRenderManager = client.getBlockRenderManager();
        BlockState stateOfBlock = state.blockState;
        if(stateOfBlock==null)return;
        matrixStack.push();
        matrixStack.translate(0, 0.35, 0);
        matrixStack.scale(0.8f, 0.5f, 0.8f);

        blockRenderManager.renderBlockAsEntity(
                stateOfBlock,
                matrixStack,
                client.getBufferBuilders().getEntityVertexConsumers(),
                state.lightmapCoordinates,
                OverlayTexture.DEFAULT_UV
        );

        matrixStack.pop();

    }
}
