package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

public class SuspiciousSpiderEyesLayer extends EyesLayer<SuspiciousSpider, SuspiciousSpiderModel> {
    private static final RenderType SKIN =
            RenderType.eyes(NekomasFixed.id("textures/entity/suspicious_spider_eyes.png"));

    public SuspiciousSpiderEyesLayer(RenderLayerParent<SuspiciousSpider, SuspiciousSpiderModel> renderer) {
        super(renderer);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}
