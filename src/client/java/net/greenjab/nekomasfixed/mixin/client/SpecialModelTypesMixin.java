package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.entity.SoulfireShieldModelRenderer;
import net.greenjab.nekomasfixed.render.entity.SoulfireTridentModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.render.item.model.special.SpecialModelTypes.ID_MAPPER;

@Mixin(SpecialModelTypes.class)
public abstract class SpecialModelTypesMixin {

    @Inject(method="bootstrap", at = @At("HEAD"))
    private static void soulfireTridentSpecial(CallbackInfo ci) {
        ID_MAPPER.put(NekomasFixed.id("soulfire_trident"), SoulfireTridentModelRenderer.Unbaked.CODEC);
        ID_MAPPER.put(NekomasFixed.id("soulfire_shield"), SoulfireShieldModelRenderer.Unbaked.CODEC);
    }
}
