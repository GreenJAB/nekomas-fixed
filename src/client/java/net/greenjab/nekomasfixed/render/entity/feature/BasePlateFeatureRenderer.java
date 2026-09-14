package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.greenjab.nekomasfixed.render.entity.model.BasePlateModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BasePlateFeatureRenderer extends RenderLayer<TargetDummy, TargetDummyModel> {

    // Vanilla armour-stand base plate texture.
    private static final ResourceLocation WOOD_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/armorstand/wood.png");

    private final BasePlateModel model;

    public BasePlateFeatureRenderer(net.minecraft.client.renderer.entity.RenderLayerParent<TargetDummy, TargetDummyModel> context,
                                    net.minecraft.client.model.geom.EntityModelSet entityModels) {
        super(context);
        this.model = new BasePlateModel(entityModels.bakeLayer(ModEntityRendererRegistry.TARGET_DUMMY_BASE));
    }

    @Override
    public void render(@NonNull PoseStack poseStack, @NonNull MultiBufferSource buffer, int packedLight,
                       TargetDummy targetDummy, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (targetDummy.isInvisible()) {
            return;
        }
        int overlay = LivingEntityRenderer.getOverlayCoords(targetDummy, 0.0F);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(WOOD_TEXTURE));
        this.model.setupAnim(targetDummy, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, overlay, 0xFFFFFFFF);
    }
}