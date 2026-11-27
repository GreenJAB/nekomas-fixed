package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.greenjab.nekomasfixed.render.entity.model.BigBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

public class BigBoatRenderer extends EntityRenderer<BigBoat> {
    private final BigBoatModel model;
    private final ResourceLocation texture;
    private final ItemRenderer itemRenderer;

    public BigBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        this(context, layer, new BigBoatModel(context.bakeLayer(layer)));
    }

    protected BigBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, BigBoatModel model) {
        super(context);
        this.shadowRadius = 0.8F;
        this.model = model;
        this.itemRenderer = context.getItemRenderer();
        this.texture = layer.getModel().withPath(path -> "textures/entity/" + path + ".png");
    }

    @Override
    public void render(BigBoat boat, float entityYaw, float partialTick, PoseStack poseStack, @NonNull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        float f = boat.getHurtTime() - partialTick;
        float g = Math.max(boat.getDamage() / 2.0F - partialTick, 0.0F);
        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * g / 10.0F * boat.getHurtDir()));
        }
        float h = boat.getBubbleAngle(partialTick);
        if (!boat.isUnderWater() && !Mth.equal(h, 0.0F)) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(h * ((float) Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.setupAnim(boat, partialTick, 0.0F, -0.1F, 0.0F, 0.0F);
        this.model.renderToBuffer(poseStack, buffer.getBuffer(this.model.renderType(this.texture)), packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        ItemStack banner = boat.getBanner();
        if (!banner.isEmpty()) renderBanners(boat, banner, poseStack, buffer, packedLight);
        poseStack.popPose();
        super.render(boat, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    protected void renderBanners(BigBoat boat, ItemStack banner, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.translate(0.0F, 1.0F, 0.125F);
        renderBanner(boat, banner, poseStack, buffer, packedLight);
    }

    protected void renderBanner(BigBoat boat, ItemStack banner, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.itemRenderer.renderStatic(banner, ItemDisplayContext.HEAD, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, boat.level(), boat.getId());
    }

    @Override
    public @NonNull ResourceLocation getTextureLocation(@NonNull BigBoat boat) {
        return this.texture;
    }
}
