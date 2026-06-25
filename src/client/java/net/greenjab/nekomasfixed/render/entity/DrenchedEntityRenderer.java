package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedEntityRenderState;
import net.minecraft.client.render.entity.AbstractSkeletonEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.util.Identifier;
import net.greenjab.nekomasfixed.registry.entity.DrenchedEntity;

@Environment(EnvType.CLIENT)
public class DrenchedEntityRenderer extends AbstractSkeletonEntityRenderer<DrenchedEntity, DrenchedEntityRenderState> {

    private static final Identifier[] TEXTURES = new Identifier[]{
            NekomasFixed.id( "textures/entity/drenched/purple.png"),
            NekomasFixed.id( "textures/entity/drenched/red.png"),
            NekomasFixed.id(  "textures/entity/drenched/yellow.png")
    };

    public DrenchedEntityRenderer(EntityRendererFactory.Context context) {
        super(context, EntityModelLayers.SKELETON_EQUIPMENT, new SkeletonEntityModel<>(context.getPart(ModEntityLayerRegistry.DRENCHED)));
    }

    @Override
    public void updateRenderState(DrenchedEntity entity, DrenchedEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.variant = entity.getVariant();
    }

    @Override
    public Identifier getTexture(DrenchedEntityRenderState state) {
        if (state.variant < 0 || state.variant >= TEXTURES.length) {
            return TEXTURES[0];
        }
        return TEXTURES[state.variant];
    }

    @Override
    public DrenchedEntityRenderState createRenderState() {
        return new DrenchedEntityRenderState();
    }
}