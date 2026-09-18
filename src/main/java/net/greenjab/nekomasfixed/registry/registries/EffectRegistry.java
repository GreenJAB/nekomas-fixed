package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.LightningEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectRegistry {
    public static final Holder<MobEffect> LIGHTNING = register("lightning", new LightningEffect(MobEffectCategory.BENEFICIAL, 0x98D982));

    public static void registerEffects() {
        NekomasFixed.LOGGER.info("Registering effects");
    }

    private static Holder<MobEffect> register(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, NekomasFixed.id(name), effect);
    }
}