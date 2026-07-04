package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class SuspiciousSpiderEyesFeatureRenderer<M extends SuspiciousSpiderEntityModel> extends EyesFeatureRenderer<LivingEntityRenderState, M> {
    private static final RenderLayer SKIN = RenderLayers.eyes(NekomasFixed.id("textures/entity/suspicious_spider_eyes.png"));

    public SuspiciousSpiderEyesFeatureRenderer(FeatureRendererContext<LivingEntityRenderState, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
