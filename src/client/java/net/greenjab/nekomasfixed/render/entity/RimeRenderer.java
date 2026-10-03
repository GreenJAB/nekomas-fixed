package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.Rime;
import net.greenjab.nekomasfixed.render.entity.feature.RimeOuterLayer;
import net.greenjab.nekomasfixed.render.entity.model.BabyRimeModel;
import net.greenjab.nekomasfixed.render.entity.model.RimeModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class RimeRenderer extends AbstractZombieRenderer<Rime, ZombieRenderState, RimeModel> {
    private static final Identifier RIME_LOCATION = NekomasFixed.id("textures/entity/zombie/rime.png");
    private static final Identifier BABY_RIME_LOCATION = NekomasFixed.id("textures/entity/zombie/rime_baby.png");

    public RimeRenderer(final EntityRendererProvider.Context context) {
        super(context, new RimeModel(context.bakeLayer(ModModelLayerRegistry.RIME)), new BabyRimeModel(context.bakeLayer(ModModelLayerRegistry.RIME_BABY)), ArmorModelSet.bake(ModModelLayerRegistry.RIME_ARMOR, context.getModelSet(), RimeModel::new), ArmorModelSet.bake(ModModelLayerRegistry.RIME_BABY_ARMOR, context.getModelSet(), BabyRimeModel::new));
        this.addLayer(new RimeOuterLayer(this, context.getModelSet()));
    }

    public @NonNull ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    public @NonNull Identifier getTextureLocation(final ZombieRenderState state) {
        return state.isBaby ? BABY_RIME_LOCATION : RIME_LOCATION;
    }

    protected void setupRotations(final @NonNull ZombieRenderState state, final @NonNull PoseStack poseStack, final float bodyRot, final float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
    }

    protected HumanoidModel.@NonNull ArmPose getArmPose(final @NonNull Rime mob, final @NonNull HumanoidArm arm) {
        return super.getArmPose(mob, arm);
    }
}