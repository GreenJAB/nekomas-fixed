package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedEntityRenderState;
import net.minecraft.client.render.entity.AbstractSkeletonEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.util.Identifier;
import net.greenjab.nekomasfixed.registry.entity.DrenchedEntity;

@Environment(EnvType.CLIENT)
public class DrenchedEntityRenderer extends AbstractSkeletonEntityRenderer<DrenchedEntity, DrenchedEntityRenderState> {

    private static final Identifier TEXTURE = Identifier.of("nekomasfixed", "textures/entity/drenched/purple.png");

    public DrenchedEntityRenderer(EntityRendererFactory.Context context) {
        super(context, EntityModelLayers.SKELETON_EQUIPMENT, new SkeletonEntityModel<>(context.getPart(EntityModelLayerRegistry.DRENCHED)));
    }

    @Override
    public Identifier getTexture(DrenchedEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public DrenchedEntityRenderState createRenderState() {
        return new DrenchedEntityRenderState();
    }
}