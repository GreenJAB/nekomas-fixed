package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.Moobloom;
import net.greenjab.nekomasfixed.render.entity.model.BabyMoobloomModel;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomModel;
import net.greenjab.nekomasfixed.render.entity.state.MoobloomRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class MoobloomRenderer extends AgeableMobRenderer<Moobloom, MoobloomRenderState, MoobloomModel> {

    public MoobloomRenderer(EntityRendererProvider.Context context) {
        super(context, new MoobloomModel(context.bakeLayer(ModModelLayerRegistry.MOOBLOOM)), new BabyMoobloomModel(context.bakeLayer(ModModelLayerRegistry.MOOBLOOM_BABY)), 0.7F);
    }

    @Override
    public @NonNull Identifier getTextureLocation(MoobloomRenderState state) {
        String PATH = "textures/entity/moobloom/".concat(state.variantPath).concat(".png");
        String PATH_BABY = "textures/entity/moobloom/".concat(state.variantPath).concat("_baby.png");
        String PATH_SHEARED = "textures/entity/moobloom/".concat(state.variantPath).concat("_sheared.png");
        return state.isBaby ? NekomasFixed.id(PATH_BABY) : (state.sheared ? NekomasFixed.id(PATH_SHEARED) : NekomasFixed.id(PATH));
    }

    public void extractRenderState(@NonNull Moobloom entity, @NonNull MoobloomRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.sheared = entity.getEntityData().get(Moobloom.SHEARED);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.runAnimationState.copyFrom(entity.runAnimationState);
        state.variantPath = entity.getEntityData().get(Moobloom.VARIANT);
        state.baby = entity.isBaby();
    }

    @Override
    public @NonNull MoobloomRenderState createRenderState() {
        return new MoobloomRenderState();
    }

    @Override
    public void submit(@NonNull MoobloomRenderState state, PoseStack matrices, @NonNull SubmitNodeCollector queue, @NonNull CameraRenderState cameraState) {
        matrices.pushPose();
        super.submit(state, matrices, queue, cameraState);
        matrices.popPose();
    }
}
