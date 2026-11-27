package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.wildfire.WildfireEntity;
import net.greenjab.nekomasfixed.render.entity.model.WildfireModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WildfireRenderer extends MobRenderer<WildfireEntity, WildfireModel> {
    private static final ResourceLocation TEXTURE = NekomasFixed.id("textures/entity/wildfire/default.png");
    private static final ResourceLocation TEXTURE_SOUL = NekomasFixed.id("textures/entity/wildfire/soul.png");

    public WildfireRenderer(EntityRendererProvider.Context context) {
        super(context, new WildfireModel(context.bakeLayer(ModEntityRendererRegistry.WILDFIRE)), 0.5F);
    }

    @Override
    protected int getBlockLightLevel(WildfireEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(WildfireEntity entity) {
        return entity.isSoulActive() ? TEXTURE_SOUL : TEXTURE;
    }
}
