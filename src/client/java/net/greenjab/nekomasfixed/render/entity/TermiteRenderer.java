package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.TermiteModel;import net.greenjab.nekomasfixed.render.entity.state.TermiteRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.greenjab.nekomasfixed.registry.entity.Termite;
import org.jspecify.annotations.NonNull;

public class TermiteRenderer extends MobRenderer<Termite, TermiteRenderState, TermiteModel> {
    public TermiteRenderer(EntityRendererProvider.Context context) {
        super(context, new TermiteModel(context.bakeLayer(ModEntityLayerRegistry.TERMITE)), 0.25f);
    }

    @Override
    public @NonNull TermiteRenderState createRenderState() {
        return new TermiteRenderState();
    }


    @Override
    public @NonNull Identifier getTextureLocation(@NonNull TermiteRenderState state) {
        return NekomasFixed.id("textures/entity/termite/termite.png");
    }

    public void extractRenderState(@NonNull Termite entity, @NonNull TermiteRenderState state, float f) {
        super.extractRenderState(entity, state, f);
        state.swipeAnimationState.copyFrom(entity.swipeAnimationState);
    }
}
