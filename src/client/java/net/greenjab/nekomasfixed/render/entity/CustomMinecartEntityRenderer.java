package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.registry.entity.CustomMinecartEntity;
import net.greenjab.nekomasfixed.render.entity.model.CustomMinecartEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.CustomMinecartEntityRenderState;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.AbstractBoatEntityRenderer;
import net.minecraft.client.render.entity.AbstractMinecartEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BoatEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class CustomMinecartEntityRenderer extends AbstractMinecartEntityRenderer<CustomMinecartEntity, CustomMinecartEntityRenderState> {

    private static final Identifier TEXTURE =
            Identifier.of("nekomasfixed", "textures/entity/custom_minecart.png");

    private final CustomMinecartEntityModel model;

    public CustomMinecartEntityRenderer(EntityRendererFactory.Context context) {
        super(context, CustomMinecartEntityModel.CUSTOM_MINECART);

        this.model = new CustomMinecartEntityModel(
                context.getPart(CustomMinecartEntityModel.CUSTOM_MINECART)
        );

        this.shadowRadius = 0.7f;
    }


    @Override
    public CustomMinecartEntityRenderState createRenderState() {
        return new CustomMinecartEntityRenderState();
    }

    @Override
    public void updateRenderState(CustomMinecartEntity entity, CustomMinecartEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);

        state.wheelRotation = entity.wheelRotation;}



    public Identifier getTexture(CustomMinecartEntityRenderState state) {
        return TEXTURE;
    }
}