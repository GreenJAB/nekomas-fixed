package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.DrenchedEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedEntityRenderState;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.greenjab.nekomasfixed.registry.entity.DrenchedEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class DrenchedEntityRenderer extends BipedEntityRenderer<DrenchedEntity, DrenchedEntityRenderState, DrenchedEntityModel> {

    private static final Identifier[] TEXTURES = new Identifier[]{
            NekomasFixed.id( "textures/entity/drenched/purple.png"),
            NekomasFixed.id( "textures/entity/drenched/red.png"),
            NekomasFixed.id(  "textures/entity/drenched/yellow.png")
    };

    public DrenchedEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DrenchedEntityModel(context.getPart(ModEntityLayerRegistry.DRENCHED)), 0.5F);
        this.addFeature(
                new ArmorFeatureRenderer<>(
                        this, EquipmentModelData.mapToEntityModel(EntityModelLayers.SKELETON_EQUIPMENT, context.getEntityModels(), DrenchedEntityModel::new), context.getEquipmentRenderer()
                )
        );
    }

    @Override
    public void updateRenderState(DrenchedEntity entity, DrenchedEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.variant = entity.getVariant();
        state.attacking = entity.isAttacking();
        state.shaking = entity.isShaking();
        //state.holdingBow = entity.getMainHandStack().isOf(Items.BOW);
    }

    @Override
    public Identifier getTexture(DrenchedEntityRenderState state) {
        if (state.variant < 0 || state.variant >= TEXTURES.length) {
            return TEXTURES[0];
        }
        return TEXTURES[state.variant];
    }

    @Override
    public DrenchedEntityRenderState createRenderState() {
        return new DrenchedEntityRenderState();
    }

    protected void setupTransforms(DrenchedEntityRenderState drenchedEntityRenderState, MatrixStack matrixStack, float f, float g) {
        super.setupTransforms(drenchedEntityRenderState, matrixStack, f, g);
        float h = drenchedEntityRenderState.leaningPitch;
        if (h > 0.0F) {
            float i = -10.0F - drenchedEntityRenderState.pitch;
            float j = MathHelper.lerp(h, 0.0F, i);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(j), 0.0F, drenchedEntityRenderState.height / 2.0F / g, 0.0F);
        }
    }
}