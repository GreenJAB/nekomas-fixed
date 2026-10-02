package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.item.FlowerCrownItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class FlowerCrownLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>>
        extends RenderLayer<S, M> {

    private final ModelPart group;

    public FlowerCrownLayer(RenderLayerParent<S, M> context, EntityModelSet entityModels) {
        super(context);
        ModelPart baked = entityModels.bakeLayer(ModModelLayerRegistry.FLOWER_CROWN);
        this.group = baked.getChild("head").getChild("group");
    }

    @Override
    public void submit(@NonNull PoseStack poseStack,
                       @NonNull SubmitNodeCollector submitNodeCollector,
                       int light,
                       @NonNull S state,
                       float yRot,
                       float xRot) {
        if (state.isInvisible) return;
        if (!(state.headEquipment.getItem() instanceof FlowerCrownItem crown)) return;

        M parentModel = this.getParentModel();

        poseStack.pushPose();
        parentModel.root().translateAndRotate(poseStack);
        parentModel.head.translateAndRotate(poseStack);

        Identifier texture = crown.getVariant().getTexture().withSuffix(".png");
        RenderType renderType = RenderTypes.entityCutout(texture);
        int overlay = LivingEntityRenderer.getOverlayCoords(state, 0.0F);

        submitNodeCollector.submitModelPart(
                this.group,
                poseStack,
                renderType,
                light,
                overlay,
                null,
                -1,
                state.outlineColor
        );

        poseStack.popPose();
    }
}