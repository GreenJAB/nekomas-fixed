package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class SuspiciousSpiderEntityRenderer extends MobRenderer<SuspiciousSpider, LivingEntityRenderState, SuspiciousSpiderModel> {
    private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/suspicious_spider/suspicious_spider.png");
    public SuspiciousSpiderEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SuspiciousSpiderModel(context.bakeLayer(ModModelLayerRegistry.SUSPICIOUS_SPIDER)), 0.5f);
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
