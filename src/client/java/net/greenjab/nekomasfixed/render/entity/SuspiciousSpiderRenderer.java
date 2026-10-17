package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SuspiciousSpiderRenderer extends MobRenderer<SuspiciousSpider, SuspiciousSpiderModel> {
    private static final ResourceLocation TEXTURE =
            NekomasFixed.id("textures/entity/suspicious_spider/suspicious_spider.png");

    public SuspiciousSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new SuspiciousSpiderModel(context.bakeLayer(ModEntityRendererRegistry.SUSPICIOUS_SPIDER)), 0.5F);
        this.addLayer(new SuspiciousSpiderEyesLayer(this));
    }

    @Override
    protected float getFlipDegrees(SuspiciousSpider entity) {
        return 180.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(SuspiciousSpider entity) {
        return TEXTURE;
    }
}
