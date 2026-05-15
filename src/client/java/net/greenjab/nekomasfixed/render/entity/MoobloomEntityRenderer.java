package net.greenjab.nekomasfixed.render.entity;

import com.google.common.collect.Maps;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.MoobloomEntityRenderState;
import net.minecraft.client.model.BabyModelPair;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.CowEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CowVariant;
import net.minecraft.util.Identifier;

import java.util.Map;

public class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, MoobloomEntityRenderState, MoobloomEntityModel> {
    private final MoobloomEntityModel adultModel;
    private final MoobloomEntityModel babyModel;

    public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MoobloomEntityModel(context.getPart(MoobloomEntityModel.MOOBLOOM)), 0.5f);

        this.adultModel = new MoobloomEntityModel(context.getPart(MoobloomEntityModel.MOOBLOOM));
        this.babyModel = new MoobloomEntityModel(context.getPart(MoobloomEntityModel.MOOBLOOM_BABY));
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
