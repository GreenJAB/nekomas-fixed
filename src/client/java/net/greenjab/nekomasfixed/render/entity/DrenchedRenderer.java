package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.DrenchedModel;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedRenderState;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.greenjab.nekomasfixed.registry.entity.Drenched;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DrenchedRenderer extends HumanoidMobRenderer<Drenched, DrenchedRenderState, DrenchedModel> {

    private static final Identifier[] TEXTURES = new Identifier[]{
            NekomasFixed.id( "textures/entity/drenched/purple.png"),
            NekomasFixed.id( "textures/entity/drenched/red.png"),
            NekomasFixed.id(  "textures/entity/drenched/yellow.png")
    };

    public DrenchedRenderer(EntityRendererProvider.Context context) {
        super(context, new DrenchedModel(context.bakeLayer(ModModelLayerRegistry.DRENCHED)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                ArmorModelSet.bake(ModelLayers.SKELETON_ARMOR, context.getModelSet(), DrenchedModel::new),
                context.getEquipmentRenderer()));
    }

    public void extractRenderState(@NonNull Drenched entity, @NonNull DrenchedRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.variant = entity.getVariant();
        state.isAggressive = entity.isAggressive();
        state.isShaking = entity.isShaking();
        state.isHoldingBow = true;
    }

    @Override
    public @NonNull Identifier getTextureLocation(DrenchedRenderState state) {
        if (state.variant < 0 || state.variant >= TEXTURES.length) return TEXTURES[0];
        return TEXTURES[state.variant];
    }

    @Override
    public @NonNull DrenchedRenderState createRenderState() {
        return new DrenchedRenderState();
    }

    protected void setupRotations(@NonNull DrenchedRenderState drenchedRenderState, @NonNull PoseStack matrixStack, float f, float g) {
        super.setupRotations(drenchedRenderState, matrixStack, f, g);
        float h = drenchedRenderState.swimAmount;
        if (h > 0.0F) {
            float i = -10.0F - drenchedRenderState.xRot;
            float j = Mth.lerp(h, 0.0F, i);
            matrixStack.rotateAround(Axis.XP.rotationDegrees(j), 0.0F, drenchedRenderState.boundingBoxHeight / 2.0F / g, 0.0F);
        }
    }
}