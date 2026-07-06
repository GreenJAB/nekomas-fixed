package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderEntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jspecify.annotations.NonNull;

public class SuspiciousSpiderEyesFeatureRenderer<M extends SuspiciousSpiderEntityModel> extends EyesLayer<LivingEntityRenderState, M> {
    private static final RenderType SKIN = RenderTypes.eyes(NekomasFixed.id("textures/entity/suspicious_spider_eyes.png"));

    public SuspiciousSpiderEyesFeatureRenderer(RenderLayerParent<LivingEntityRenderState, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public @NonNull RenderType renderType() {
        return SKIN;
    }
}
