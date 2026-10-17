package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.Rime;
import net.greenjab.nekomasfixed.render.entity.model.RimeModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class RimeOuterLayer extends RenderLayer<Rime, ZombieModel<Rime>> {
    // Baby rime shares the adult outer texture (same 1.21.1 baby-mesh rationale as RimeRenderer).
    private static final ResourceLocation RIME_OUTER_LAYER_LOCATION =
            NekomasFixed.id("textures/entity/zombie/rime_outer_layer.png");
    private final RimeModel model;

    public RimeOuterLayer(final RenderLayerParent<Rime, ZombieModel<Rime>> renderer,
                          final EntityModelSet modelSet) {
        super(renderer);
        this.model = new RimeModel(modelSet.bakeLayer(ModEntityRendererRegistry.RIME_OUTER_LAYER));
    }

    @Override
    public void render(@NonNull PoseStack poseStack, @NonNull MultiBufferSource buffer, int packedLight,
                       @NonNull Rime entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, RIME_OUTER_LAYER_LOCATION,
                poseStack, buffer, packedLight, entity,
                limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, -1);
    }
}
