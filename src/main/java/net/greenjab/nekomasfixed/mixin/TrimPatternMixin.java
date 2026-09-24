package net.greenjab.nekomasfixed.mixin;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrimPatterns.class)
public class TrimPatternMixin {
    @Unique
    @Final
    private static final ResourceKey<TrimPattern> JEWEL = TrimPatterns.registryKey("jewel");

    @Inject(method = "bootstrap", at = @At("HEAD"), cancellable = true)
    private static void bootstrap(BootstrapContext<TrimPattern> context, CallbackInfo ci) {
        TrimPatterns.register(context, JEWEL);
    }
}
