package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.Derelict;
import net.greenjab.nekomasfixed.render.entity.model.DerelictModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DerelictOuterLayer extends RenderLayer<Derelict, ZombieModel<Derelict>> {
    private static final ResourceLocation DERELICT_OUTER_LAYER_LOCATION =
            NekomasFixed.id("textures/entity/zombie/derelict_outer_layer.png");
    private final DerelictModel model;

    public DerelictOuterLayer(final RenderLayerParent<Derelict, ZombieModel<Derelict>> renderer,
                              final net.minecraft.client.model.geom.EntityModelSet modelSet) {
        super(renderer);
        this.model = new DerelictModel(modelSet.bakeLayer(ModEntityRendererRegistry.DERELICT_OUTER_LAYER));
    }

    @Override
    public void render(@NonNull PoseStack poseStack, @NonNull MultiBufferSource buffer, int packedLight,
                       @NonNull Derelict entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, DERELICT_OUTER_LAYER_LOCATION,
                poseStack, buffer, packedLight, entity,
                limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, -1);
    }
}
