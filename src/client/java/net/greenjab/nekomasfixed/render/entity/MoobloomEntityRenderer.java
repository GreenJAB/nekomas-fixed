package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.Moobloom;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.MoobloomEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class MoobloomEntityRenderer extends MobRenderer<Moobloom, MoobloomEntityRenderState, MoobloomEntityModel> {

    public MoobloomEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MoobloomEntityModel(context.bakeLayer(ModEntityLayerRegistry.MOOBLOOM)), 0.5f);
    }

    @Override
    public @NonNull Identifier getTextureLocation(MoobloomEntityRenderState state) {
        String PATH = "textures/entity/moobloom/".concat(state.variantPath).concat(".png");
        String PATH_SHEARED = "textures/entity/moobloom/".concat(state.variantPath).concat("_sheared.png");
        return state.sheared ? NekomasFixed.id(PATH_SHEARED) : NekomasFixed.id(PATH);
    }

    public void extractRenderState(@NonNull Moobloom entity, @NonNull MoobloomEntityRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.sheared = entity.getEntityData().get(Moobloom.SHEARED);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.runAnimationState.copyFrom(entity.runAnimationState);
        state.variantPath = entity.getEntityData().get(Moobloom.VARIANT);
        state.baby = entity.isBaby();
    }

    @Override
    public @NonNull MoobloomEntityRenderState createRenderState() {
        return new MoobloomEntityRenderState();
    }

    @Override
    public void submit(MoobloomEntityRenderState state, PoseStack matrices, @NonNull SubmitNodeCollector queue, @NonNull CameraRenderState cameraState) {
        matrices.pushPose();
        if (state.baby) matrices.scale(0.5F, 0.5F, 0.5F);
        super.submit(state, matrices, queue, cameraState);
        matrices.popPose();
    }
}
