package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.greenjab.nekomasfixed.registry.entity.wildfire.FireBomb;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class FireBombRenderer extends EntityRenderer<FireBomb> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/item/blaze_powder.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(TEXTURE);

    public FireBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(FireBomb entity, BlockPos pos) {
        return 15;
    }

    @Override
    public void render(FireBomb entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
        vertex(vertexConsumer, pose, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, pose, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, pose, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, pose, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
        consumer.addVertex(pose, x - 0.5F, y - 0.25F, 0.0F)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(FireBomb entity) {
        return TEXTURE;
    }
}
