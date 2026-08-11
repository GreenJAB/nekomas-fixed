package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.Derelict;
import net.greenjab.nekomasfixed.render.entity.feature.DerelictOuterLayer;
import net.greenjab.nekomasfixed.render.entity.model.BabyDerelictModel;
import net.greenjab.nekomasfixed.render.entity.model.DerelictModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DerelictRenderer extends AbstractZombieRenderer<Derelict, ZombieRenderState, DerelictModel> {
    private static final Identifier DERELICT_LOCATION = NekomasFixed.id("textures/entity/zombie/derelict.png");
    private static final Identifier BABY_DERELICT_LOCATION = NekomasFixed.id("textures/entity/zombie/derelict_baby.png");

    public DerelictRenderer(final EntityRendererProvider.Context context) {
        super(context, new DerelictModel(context.bakeLayer(ModModelLayerRegistry.DERELICT)), new BabyDerelictModel(context.bakeLayer(ModModelLayerRegistry.DERELICT_BABY)), ArmorModelSet.bake(ModModelLayerRegistry.DERELICT_ARMOR, context.getModelSet(), DerelictModel::new), ArmorModelSet.bake(ModModelLayerRegistry.DERELICT_BABY_ARMOR, context.getModelSet(), BabyDerelictModel::new));
        this.addLayer(new DerelictOuterLayer(this, context.getModelSet()));
    }

    public @NonNull ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    public @NonNull Identifier getTextureLocation(final ZombieRenderState state) {
        return state.isBaby ? BABY_DERELICT_LOCATION : DERELICT_LOCATION;
    }

    protected void setupRotations(final @NonNull ZombieRenderState state, final @NonNull PoseStack poseStack, final float bodyRot, final float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
    }

    protected HumanoidModel.@NonNull ArmPose getArmPose(final @NonNull Derelict mob, final @NonNull HumanoidArm arm) {
        return super.getArmPose(mob, arm);
    }
}