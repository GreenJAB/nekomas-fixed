package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.Derelict;
import net.greenjab.nekomasfixed.render.entity.feature.DerelictOuterLayer;
import net.greenjab.nekomasfixed.render.entity.model.DerelictModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DerelictRenderer extends AbstractZombieRenderer<Derelict, ZombieModel<Derelict>> {
    private static final ResourceLocation DERELICT_LOCATION = NekomasFixed.id("textures/entity/zombie/derelict.png");

    public DerelictRenderer(final EntityRendererProvider.Context context) {
        super(context,
                new DerelictModel(context.bakeLayer(ModEntityRendererRegistry.DERELICT)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)));
        this.addLayer(new DerelictOuterLayer(this, context.getModelSet()));
    }

    @Override
    public @NonNull ResourceLocation getTextureLocation(@NonNull Derelict entity) {
        return DERELICT_LOCATION;
    }
}
