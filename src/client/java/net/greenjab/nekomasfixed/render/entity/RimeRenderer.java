package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.Rime;
import net.greenjab.nekomasfixed.render.entity.feature.RimeOuterLayer;
import net.greenjab.nekomasfixed.render.entity.model.RimeModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class RimeRenderer extends AbstractZombieRenderer<Rime, ZombieModel<Rime>> {
    // Adults and babies share the adult texture: 1.21.1 renders baby zombies as the
    // split-scaled adult mesh, so main's 26.x rime_baby.png layout must not be used.
    private static final ResourceLocation RIME_LOCATION = NekomasFixed.id("textures/entity/zombie/rime.png");

    public RimeRenderer(final EntityRendererProvider.Context context) {
        super(context,
                new RimeModel(context.bakeLayer(ModEntityRendererRegistry.RIME)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)));
        this.addLayer(new RimeOuterLayer(this, context.getModelSet()));
    }

    @Override
    public @NonNull ResourceLocation getTextureLocation(@NonNull Rime entity) {
        return RIME_LOCATION;
    }
}
