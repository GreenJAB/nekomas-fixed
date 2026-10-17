package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.Drenched;
import net.greenjab.nekomasfixed.render.entity.model.DrenchedModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class DrenchedRenderer extends SkeletonRenderer<Drenched> {
    private static final ResourceLocation[] TEXTURES = {
            NekomasFixed.id("textures/entity/drenched/purple.png"),
            NekomasFixed.id("textures/entity/drenched/red.png"),
            NekomasFixed.id("textures/entity/drenched/yellow.png")
    };

    public DrenchedRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModelLayers.SKELETON_INNER_ARMOR,
                ModelLayers.SKELETON_OUTER_ARMOR,
                new DrenchedModel(context.bakeLayer(ModEntityRendererRegistry.DRENCHED)));
    }

    @Override
    public ResourceLocation getTextureLocation(Drenched entity) {
        int variant = entity.getVariant();
        if (variant < 0 || variant >= TEXTURES.length) return TEXTURES[0];
        return TEXTURES[variant];
    }

    @Override
    protected void setupRotations(Drenched entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);

        if (entity.isPassenger()) {
            return;
        }

        float swimAmount = entity.getSwimAmount(partialTick);
        if (swimAmount > 0.0F) {
            float tilt = -10.0F - entity.getXRot();
            float rotation = Mth.lerp(swimAmount, 0.0F, tilt);
            poseStack.rotateAround(Axis.XP.rotationDegrees(rotation), 0.0F, entity.getBbHeight() / 2.0F / scale, 0.0F);
        }
    }
}
