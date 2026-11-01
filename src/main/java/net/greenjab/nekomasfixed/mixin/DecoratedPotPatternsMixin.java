package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.DecoratedPotPattern;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin {
    @WrapOperation(
            method = "registerAndGetDefault",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/DecoratedPotPatterns;register(Lnet/minecraft/registry/Registry;Lnet/minecraft/registry/RegistryKey;Ljava/lang/String;)Lnet/minecraft/block/DecoratedPotPattern;")
    )
    private static DecoratedPotPattern registerGlowingPatterns(Registry<DecoratedPotPattern> registry, RegistryKey<DecoratedPotPattern> key, String id, Operation<DecoratedPotPattern> original) {
        RegistryKey<DecoratedPotPattern> glowingKey = RegistryKey.of(registry.getKey(), key.getValue().withSuffixedPath("_glowing"));
        String glowingId = id + "_glowing";
        original.call(registry, glowingKey, glowingId);
        return original.call(registry, key, id);
    }
}
