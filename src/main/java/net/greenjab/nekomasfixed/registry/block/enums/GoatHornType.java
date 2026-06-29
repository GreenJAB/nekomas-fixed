package net.greenjab.nekomasfixed.registry.block.enums;

import net.minecraft.component.type.InstrumentComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Instrument;
import net.minecraft.item.Instruments;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.StringIdentifiable;

public enum GoatHornType implements StringIdentifiable {
    CALL(Instruments.CALL_GOAT_HORN, new StatusEffectInstance(StatusEffects.SPEED, 20*60, 0)),
    PONDER(Instruments.PONDER_GOAT_HORN, new StatusEffectInstance(StatusEffects.RESISTANCE, 20*60, 0)),
    SING(Instruments.SING_GOAT_HORN, new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 20*60, 0)),
    SEEK(Instruments.SEEK_GOAT_HORN, new StatusEffectInstance(StatusEffects.STRENGTH, 20*60, 0)),
    FEEL(Instruments.FEEL_GOAT_HORN, new StatusEffectInstance(StatusEffects.ABSORPTION, 20*60, 0)),
    ADMIRE(Instruments.ADMIRE_GOAT_HORN, new StatusEffectInstance(StatusEffects.REGENERATION, 20*60, 0)),
    YEARN(Instruments.YEARN_GOAT_HORN, new StatusEffectInstance(StatusEffects.STRENGTH, 20*60, 0)),
    DREAM(Instruments.DREAM_GOAT_HORN, new StatusEffectInstance(StatusEffects.INVISIBILITY, 20*60, 0));

    private final RegistryKey<Instrument> instrument;
    private final StatusEffectInstance effect;

    GoatHornType(RegistryKey<Instrument> instrument, StatusEffectInstance effect) {
        this.instrument = instrument;
        this.effect = effect;
    }

    public RegistryKey<Instrument> getInstrument() {
        return this.instrument;
    }

    public StatusEffectInstance getStatusEffect(){
        return this.effect;
    }

    public static GoatHornType fromInstrument(InstrumentComponent instrument) {
        RegistryKey<Instrument> key = instrument.instrument().getKey().orElse(Instruments.CALL_GOAT_HORN);
        if (key == Instruments.CALL_GOAT_HORN) return CALL;
        if (key == Instruments.SING_GOAT_HORN) return SING;
        if (key == Instruments.SEEK_GOAT_HORN) return SEEK;
        if (key == Instruments.FEEL_GOAT_HORN) return FEEL;
        if (key == Instruments.PONDER_GOAT_HORN) return PONDER;
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
