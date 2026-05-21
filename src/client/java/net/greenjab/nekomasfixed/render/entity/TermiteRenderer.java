package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.render.entity.model.TermiteModel;import net.greenjab.nekomasfixed.render.entity.state.TermiteRenderState;
import net.greenjab.nekomasfixed.registry.entity.Termite.TermiteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TermiteRenderer extends MobEntityRenderer<TermiteEntity, TermiteRenderState, TermiteModel> {
    public TermiteRenderer(EntityRendererFactory.Context context) {
        super(context, new TermiteModel(context.getPart(TermiteModel.TERMITE)), 0.25f);
    }

    @Override
    public TermiteRenderState createRenderState() {
        return new TermiteRenderState();
    }


    @Override
    public Identifier getTexture(TermiteRenderState state) {
        return Identifier.of("nekomasfixed", "textures/entity/termite/termite.png");
    }


}
