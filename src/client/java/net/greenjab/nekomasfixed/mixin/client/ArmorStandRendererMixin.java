package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.entity.feature.GoatHornHelmetLayer;
import net.greenjab.nekomasfixed.render.entity.feature.FlowerCrownLayer;
import net.minecraft.client.model.object.armorstand.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public abstract class ArmorStandRendererMixin
        extends LivingEntityRenderer<ArmorStand, ArmorStandRenderState, ArmorStandArmorModel> {

    public ArmorStandRendererMixin(EntityRendererProvider.Context context, ArmorStandArmorModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addGoatHornHelmetLayer(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new GoatHornHelmetLayer<>(this, context.getModelSet()));
		this.addLayer(new FlowerCrownLayer<>(this, context.getModelSet()));
    }
}