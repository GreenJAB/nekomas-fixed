package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.item.FlowerCrownItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class FlowerCrownLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final ModelPart group;

    public FlowerCrownLayer(RenderLayerParent<T, M> context, EntityModelSet entityModels) {
        super(context);
        this.group = entityModels.bakeLayer(ModEntityRendererRegistry.FLOWER_CROWN).getChild("head").getChild("group");
    }

    @Override
    public void render(@NonNull PoseStack poseStack, @NonNull MultiBufferSource buffer, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        if (!(entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof FlowerCrownItem crown)) {
            return;
        }
        poseStack.pushPose();
        this.getParentModel().head.translateAndRotate(poseStack);
        this.group.render(poseStack, buffer.getBuffer(RenderType.entityCutout(crown.variant.texture)),
                packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
        poseStack.popPose();
    }
}
