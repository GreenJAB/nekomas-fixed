package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.loottable.ConfigCondition;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootItemConditionTypes.class)
public class LootItemConditionTypesMixin {
    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void boot(Registry<MapCodec<? extends LootItemCondition>> registry, CallbackInfoReturnable<MapCodec<? extends LootItemCondition>> cir){
        Registry.register(registry, "config", ConfigCondition.CODEC);
    }

}
