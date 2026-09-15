package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.item.FlowerCrownItem;
import net.greenjab.nekomasfixed.render.entity.model.FlowerCrownModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jspecify.annotations.NonNull;

/**
 * FIX (see chat): every previous attempt was missing a step that Trinkets'
 * own ModelAttachementImpl / TrinketRenderLayer (decompiled/read directly
 * from its real 4.2.0-26.3 source) proves is required: a
 * poseStack.scale(1, -1, -1) Y/Z mirror flip applied AFTER walking to the
 * target part's transform and BEFORE submitting the attached geometry.
 * Without it, a separately-baked ModelPart parented under head renders
 * with the wrong orientation/shape even though it correctly tracks head
 * rotation and position -- which explains every earlier result: attached,
 * moving with the head, geometrically present, but visually wrong.
 *
 * Also following Trinkets' pattern of walking the parent chain manually
 * via repeated ModelPart.translateAndRotate(poseStack) calls (root -> head
 * -> group) rather than relying on submitModel's automatic recursive
 * render of the whole tree, which is what several earlier attempts here
 * used without success.
 */
@Environment(EnvType.CLIENT)
public class FlowerCrownLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    private final ModelPart head;
    private final ModelPart group;

    public FlowerCrownLayer(RenderLayerParent<AvatarRenderState, PlayerModel> context, EntityModelSet entityModels) {
        super(context);
        ModelPart bakedRoot = entityModels.bakeLayer(ModModelLayerRegistry.FLOWER_CROWN);
        this.head = bakedRoot.getChild("head");
        this.group = FlowerCrownModel.group(bakedRoot);
    }

    @Override
    public void submit(@NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, int light,
                       AvatarRenderState state, float yRot, float xRot) {
        if (state.isInvisible) return;
        if (!(state.headEquipment.getItem() instanceof FlowerCrownItem crown)) return;

        PlayerModel parentModel = this.getParentModel();

        poseStack.pushPose();

        // Walk to the REAL player's head transform (not our own baked
        // copy's head -- that one only exists to hold "group"'s pivot data).
        parentModel.root().translateAndRotate(poseStack);
        parentModel.head.translateAndRotate(poseStack);

        // The critical missing step (see class doc): mirror flip, applied
        // after reaching the target transform and before drawing.
        poseStack.scale(1.0F, -1.0F, -1.0F);

        System.out.println("Flower crown texture: " + crown.getVariant().getTexture());


        RenderType renderType = RenderTypes.entityCutout(crown.getVariant().getTexture());
        int overlay = LivingEntityRenderer.getOverlayCoords(state, 0.0F);
        submitNodeCollector.submitModelPart(this.group, poseStack, renderType, light, overlay, null,
                -1, null, state.outlineColor);

        poseStack.popPose();}
}