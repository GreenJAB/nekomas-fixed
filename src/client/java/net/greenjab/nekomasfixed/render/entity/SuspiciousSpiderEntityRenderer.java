package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class SuspiciousSpiderEntityRenderer extends MobRenderer<SuspiciousSpider, LivingEntityRenderState, SuspiciousSpiderEntityModel> {
    private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/suspicious_spider/suspicious_spider.png");
    public SuspiciousSpiderEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SuspiciousSpiderEntityModel(context.bakeLayer(ModEntityLayerRegistry.SUSPICIOUS_SPIDER)), 0.5f);
        this.addLayer(new SuspiciousSpiderEyesFeatureRenderer<>(this));
    }

    @Override
    public @NonNull LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    protected float getFlipDegrees() {
        return 180.0F;
    }

    public @NonNull Identifier getTextureLocation(@NonNull LivingEntityRenderState state) {
        return TEXTURE;
    }
}
