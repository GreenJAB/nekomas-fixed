package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WildfireShieldModelRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final ResourceLocation TEXTURE = NekomasFixed.id("textures/entity/wildfire_shield/default.png");
    public static final ResourceLocation TEXTURE_SOUL = NekomasFixed.id("textures/entity/wildfire_shield/soul.png");
    private ShieldModel model;

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        if (model == null) model = new ShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.SHIELD));
        ResourceLocation texture = stack.getDamageValue() < stack.getMaxDamage() / 2 ? TEXTURE : TEXTURE_SOUL;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, model.renderType(texture), true, stack.hasFoil());
        model.renderToBuffer(poseStack, vertexConsumer, light, overlay);
        poseStack.popPose();
    }
}
