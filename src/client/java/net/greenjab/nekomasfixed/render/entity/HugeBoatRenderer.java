package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.greenjab.nekomasfixed.render.entity.model.HugeBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class HugeBoatRenderer extends BigBoatRenderer {

    public HugeBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        super(context, layer, new HugeBoatModel(context.bakeLayer(layer)));
    }

    @Override
    protected void renderBanners(BigBoat boat, ItemStack banner, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.translate(0.0F, 2.0F, -0.06F);
        renderBanner(boat, banner, poseStack, buffer, packedLight);
        poseStack.translate(0.0F, -1.15F, -1.44F);
        renderBanner(boat, banner, poseStack, buffer, packedLight);
    }
}
