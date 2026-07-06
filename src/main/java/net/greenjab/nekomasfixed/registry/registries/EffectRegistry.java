package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.LightningEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectRegistry {
    public static void registerEffects() {
        System.out.println("register Effects");
    }

    public static Holder<MobEffect> LIGHTNING = registerStatusEffect("lightning", new LightningEffect(MobEffectCategory.BENEFICIAL,0x98D982));

    private static Holder<MobEffect> registerStatusEffect(String name, MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, NekomasFixed.id(name), statusEffect);
    }
}
