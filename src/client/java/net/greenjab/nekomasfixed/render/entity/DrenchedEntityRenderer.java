package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.DrenchedEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedEntityRenderState;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.greenjab.nekomasfixed.registry.entity.DrenchedEntity;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DrenchedEntityRenderer extends HumanoidMobRenderer<DrenchedEntity, DrenchedEntityRenderState, DrenchedEntityModel> {

    private static final Identifier[] TEXTURES = new Identifier[]{
            NekomasFixed.id( "textures/entity/drenched/purple.png"),
            NekomasFixed.id( "textures/entity/drenched/red.png"),
            NekomasFixed.id(  "textures/entity/drenched/yellow.png")
    };

    public DrenchedEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DrenchedEntityModel(context.bakeLayer(ModEntityLayerRegistry.DRENCHED)), 0.5F);
        this.addLayer(
                new HumanoidArmorLayer<>(
                        this, ArmorModelSet.bake(ModelLayers.SKELETON_ARMOR, context.getModelSet(), DrenchedEntityModel::new), context.getEquipmentRenderer()
                )
        );
    }

    public void extractRenderState(@NonNull DrenchedEntity entity, @NonNull DrenchedEntityRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.variant = entity.getVariant();
        state.isAggressive = entity.isAggressive();
        state.isShaking = entity.isShaking();
        state.isHoldingBow = true;
    }

    @Override
    public @NonNull Identifier getTextureLocation(DrenchedEntityRenderState state) {
        if (state.variant < 0 || state.variant >= TEXTURES.length) {
            return TEXTURES[0];
        }
        return TEXTURES[state.variant];
    }

    @Override
    public @NonNull DrenchedEntityRenderState createRenderState() {
        return new DrenchedEntityRenderState();
    }

    protected void setupRotations(@NonNull DrenchedEntityRenderState drenchedEntityRenderState, @NonNull PoseStack matrixStack, float f, float g) {
        super.setupRotations(drenchedEntityRenderState, matrixStack, f, g);
        float h = drenchedEntityRenderState.swimAmount;
        if (h > 0.0F) {
            float i = -10.0F - drenchedEntityRenderState.xRot;
            float j = Mth.lerp(h, 0.0F, i);
            matrixStack.rotateAround(Axis.XP.rotationDegrees(j), 0.0F, drenchedEntityRenderState.boundingBoxHeight / 2.0F / g, 0.0F);
        }
    }
}