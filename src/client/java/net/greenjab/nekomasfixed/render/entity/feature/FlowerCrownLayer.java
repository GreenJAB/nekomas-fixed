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
 * FIX (see chat, two bugs found and fixed together):
 *
 * 1) THE REAL BUG for every "still broken" test: the previous version of
 *    this file computed renderType and overlay but then called
 *    poseStack.popPose() WITHOUT ever calling submitModelPart(...) --
 *    the crown was never actually submitted to render, at all, full stop.
 *    Every debug println placed right before that missing call correctly
 *    printed (confirming submit() runs and the item/texture checks pass),
 *    which is exactly why adding the println "worked" but the crown still
 *    never appeared -- there was nothing left in the method to draw it.
 *
 * 2) Separately (real for 26.3, but not what was causing the missing
 *    render): OrderedSubmitNodeCollector's submitModelPart/submitModel API
 *    changed between 26.2 and 26.3 -- TextureAtlasSprite was replaced by
 *    UvMapping and the crumblingOverlay parameter was removed from these
 *    overloads. The old 9-arg 26.2-style call would not have matched any
 *    26.3 overload. Fixed to the current 8-arg overload here regardless,
 *    since this mod now targets 26.3.
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

        // Mirror flip (see FlowerCrownLayer history in chat for why this
        // is needed -- confirmed against Trinkets' real source).
        poseStack.scale(1.0F, -1.0F, -1.0F);

        RenderType renderType = RenderTypes.entityCutout(crown.getVariant().getTexture());
        int overlay = LivingEntityRenderer.getOverlayCoords(state, 0.0F);

        // THE ACTUAL SUBMIT CALL -- this was missing entirely before.
        submitNodeCollector.submitModelPart(this.group, poseStack, renderType, light, overlay,
                null, -1, state.outlineColor);

        poseStack.popPose();
    }
}