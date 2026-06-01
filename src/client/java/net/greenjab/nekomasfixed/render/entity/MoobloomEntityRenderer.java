package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.MoobloomEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, MoobloomEntityRenderState, MoobloomEntityModel> {
    private final MoobloomEntityModel adultModel;
    private final MoobloomEntityModel babyModel;

    public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MoobloomEntityModel(context.getPart(ModEntityLayerRegistry.MOOBLOOM)), 0.5f);

        this.adultModel = new MoobloomEntityModel(context.getPart(ModEntityLayerRegistry.MOOBLOOM));
        this.babyModel = new MoobloomEntityModel(context.getPart(ModEntityLayerRegistry.MOOBLOOM_BABY));
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
        state.baby = entity.isBaby();
    }

    //done a lot of stuff x 2

    @Override
    public MoobloomEntityRenderState createRenderState() {
        return new MoobloomEntityRenderState();
    }


    @Override
    public void render(MoobloomEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();
        if (state.baby) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(state, matrices, queue, cameraState);
        matrices.pop();
    }

}
