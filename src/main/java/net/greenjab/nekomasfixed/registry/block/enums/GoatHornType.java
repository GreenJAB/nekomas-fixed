package net.greenjab.nekomasfixed.registry.block.enums;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.Instrument;
import net.minecraft.item.Instruments;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.LazyRegistryEntryReference;
import net.minecraft.util.StringIdentifiable;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public enum GoatHornType implements StringIdentifiable {
    CALL,
    SING,
    SEEK,
    FEEL,
    ADMIRE,
    YEARN,
    DREAM;


    public static RegistryKey<Instrument> getInstrument(GoatHornType type) {
        return switch (type) {
            case CALL -> Instruments.CALL_GOAT_HORN;
            case SING -> Instruments.SING_GOAT_HORN;
            case SEEK -> Instruments.SEEK_GOAT_HORN;
            case FEEL -> Instruments.FEEL_GOAT_HORN;
            case ADMIRE -> Instruments.ADMIRE_GOAT_HORN;
            case YEARN -> Instruments.YEARN_GOAT_HORN;
            case DREAM -> Instruments.DREAM_GOAT_HORN;
        };
    }

    public static @Nullable StatusEffectInstance getStatusEffect(GoatHornItem item){
        var component = item.getDefaultStack().get(DataComponentTypes.INSTRUMENT);
        if (component == null) return null;

        GoatHornType hornType = GoatHornType.fromInstrument(component.instrument());
        StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.INSTANT_HEALTH);
        int dur = 20*60;
        int rand = new Random().nextInt(1, 3);
        switch (hornType){
            case CALL -> effect = new StatusEffectInstance(StatusEffects.SPEED, dur, rand);
            case SING -> effect = new StatusEffectInstance(StatusEffects.RESISTANCE, dur, rand);
            case SEEK -> effect = new StatusEffectInstance(StatusEffects.STRENGTH, dur, rand);
            case FEEL -> effect = new StatusEffectInstance(StatusEffects.ABSORPTION, dur, rand);
            case ADMIRE -> effect = new StatusEffectInstance(StatusEffects.REGENERATION, dur, rand+3);
            case DREAM ->  effect = new StatusEffectInstance(StatusEffects.INVISIBILITY, dur, rand);
            case YEARN ->  effect = new StatusEffectInstance(StatusEffects.STRENGTH, dur, rand+1);
        }
        return effect;
    }

    public static GoatHornType fromInstrument(LazyRegistryEntryReference<Instrument> instrument) {
        var key = instrument.getKey().orElse(null);
        if (key == null) return CALL;

        if (key == Instruments.CALL_GOAT_HORN) return CALL;
        if (key == Instruments.SING_GOAT_HORN) return SING;
        if (key == Instruments.SEEK_GOAT_HORN) return SEEK;
        if (key == Instruments.FEEL_GOAT_HORN) return FEEL;
        if (key == Instruments.ADMIRE_GOAT_HORN) return ADMIRE;
        if (key == Instruments.DREAM_GOAT_HORN) return DREAM;
        if (key == Instruments.YEARN_GOAT_HORN) return YEARN;

        return CALL;
    }

    @Override
    public String asString() {
        return name().toLowerCase();
    }
}
