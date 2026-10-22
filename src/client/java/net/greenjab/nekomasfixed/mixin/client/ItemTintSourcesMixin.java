package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.special.TippedArrowTint;
import net.minecraft.client.color.item.ItemTintSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemTintSources.class)
public class ItemTintSourcesMixin {
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void addTints(CallbackInfo ci) {
        ItemTintSources.ID_MAPPER.put(NekomasFixed.id("tipped_arrow"), TippedArrowTint.MAP_CODEC);
    }
}
