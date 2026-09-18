package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.moobloom.Moobloom;
import net.greenjab.nekomasfixed.render.entity.model.BabyMoobloomModel;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class MoobloomRenderer extends MobRenderer<Moobloom, MoobloomModel> {
    private final MoobloomModel adultModel;
    private final MoobloomModel babyModel;

    public MoobloomRenderer(EntityRendererProvider.Context context) {
        super(context, new MoobloomModel(context.bakeLayer(ModEntityRendererRegistry.MOOBLOOM)), 0.7F);
        this.adultModel = this.model;
        this.babyModel = new BabyMoobloomModel(context.bakeLayer(ModEntityRendererRegistry.MOOBLOOM_BABY));
    }

    @Override
    public @NonNull ResourceLocation getTextureLocation(@NonNull Moobloom entity) {
        String base = "textures/entity/moobloom/".concat(entity.getVariantPath());
        if (entity.isBaby()) {
            return NekomasFixed.id(base.concat("_baby.png"));
        }
        return NekomasFixed.id(base.concat(entity.isSheared() ? "_sheared.png" : ".png"));
    }

    // Pick the pre-baked baby mesh (with its own UVs) instead of letting the adult
    // mesh be ageable-scaled onto the differently-laid-out _baby texture.
    @Override
    public void render(@NonNull Moobloom entity, float entityYaw, float partialTicks,
                       @NonNull PoseStack poseStack, @NonNull MultiBufferSource buffer, int packedLight) {
        this.model = entity.isBaby() ? this.babyModel : this.adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}