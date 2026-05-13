package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.MoobloomEntityRenderState;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, MoobloomEntityRenderState, MoobloomEntityModel> {
    public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MoobloomEntityModel(context.getPart(MoobloomEntityModel.MOOBLOOM)), 0.5f);
    }

    @Override
    public Identifier getTexture(MoobloomEntityRenderState state) {
        String PATH = "textures/entity/moobloom/".concat(state.variantPath).concat(".png");
        String PATH_SHEARED = "textures/entity/moobloom/".concat(state.variantPath).concat("_sheared.png");
        return state.sheared ? Identifier.of("nekomasfixed", PATH_SHEARED) : Identifier.of("nekomasfixed", PATH);
    }

    @Override
    public void updateRenderState(MoobloomEntity entity, MoobloomEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.sheared = entity.getDataTracker().get(MoobloomEntity.SHEARED);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.runAnimationState.copyFrom(entity.runAnimationState);
        state.variantPath = entity.getDataTracker().get(MoobloomEntity.VARIANT);
    }

    @Override
    public MoobloomEntityRenderState createRenderState() {
        return new MoobloomEntityRenderState();
    }
}
