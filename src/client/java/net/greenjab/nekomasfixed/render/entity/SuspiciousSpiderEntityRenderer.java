package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider.SuspiciousSpiderEntity;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;

public class SuspiciousSpiderEntityRenderer extends MobEntityRenderer<SuspiciousSpiderEntity, LivingEntityRenderState, SuspiciousSpiderEntityModel> {
    private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/sus_spider/sus_spider.png");
    public SuspiciousSpiderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SuspiciousSpiderEntityModel(context.getPart(ModEntityLayerRegistry.SUS_SPIDER)), 0.5f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    protected float getLyingPositionRotationDegrees() {
        return 180.0F;
    }

    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }
}
