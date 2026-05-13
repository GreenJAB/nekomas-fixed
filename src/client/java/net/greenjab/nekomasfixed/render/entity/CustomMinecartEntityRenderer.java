package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.CustomMinecartEntity;
import net.greenjab.nekomasfixed.render.entity.model.CustomMinecartEntityModel;

import net.minecraft.client.render.entity.AbstractMinecartEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.MinecartEntityRenderState;
import net.minecraft.util.Identifier;

public class CustomMinecartEntityRenderer extends AbstractMinecartEntityRenderer<CustomMinecartEntity, MinecartEntityRenderState> {

    private static final Identifier TEXTURE = Identifier.of("nekomasfixed", "textures/entity/custom_minecart.png");

    public CustomMinecartEntityRenderer(EntityRendererFactory.Context context) {
        super(context, ModEntityLayerRegistry.CUSTOM_MINECART);
        CustomMinecartEntityModel model = new CustomMinecartEntityModel(context.getPart(ModEntityLayerRegistry.CUSTOM_MINECART));
        this.shadowRadius = 0.7f;
    }

    @Override
    public MinecartEntityRenderState createRenderState() {
        return new MinecartEntityRenderState();
    }

    @Override
    public void updateRenderState(CustomMinecartEntity entity, MinecartEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
}
    public Identifier getTexture(MinecartEntityRenderState state) {
        return TEXTURE;
    }
}