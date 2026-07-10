package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.entity.WildfireShieldModelRenderer;
import net.greenjab.nekomasfixed.render.entity.WildfireTridentModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.renderer.special.SpecialModelRenderers.ID_MAPPER;

@Mixin(SpecialModelRenderers.class)
public abstract class SpecialModelRenderersMixin {

    @Inject(method="bootstrap", at = @At("HEAD"))
    private static void specialRendering(CallbackInfo ci) {
        ID_MAPPER.put(NekomasFixed.id("wildfire_trident"), WildfireTridentModelRenderer.Unbaked.CODEC);
        ID_MAPPER.put(NekomasFixed.id("wildfire_shield"), WildfireShieldModelRenderer.Unbaked.CODEC);
    }
}
