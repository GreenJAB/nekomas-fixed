package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.MelonBlock;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/block/Blocks;register(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 0), slice = @Slice( from = @At( value = "FIELD",
                            target = "Lnet/minecraft/block/Blocks;PUMPKIN:Lnet/minecraft/block/Block;")))
    private static Block newMelon(RegistryKey<Block> key, AbstractBlock.Settings settings2) {
        return register(key, settings -> new MelonBlock(false, settings), settings2);
    }

    @Unique
    private static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }
}
