package net.greenjab.nekomasfixed.registry.block.enums;

import net.minecraft.item.Instrument;
import net.minecraft.item.Instruments;
import net.minecraft.registry.entry.LazyRegistryEntryReference;
import net.minecraft.util.StringIdentifiable;

public enum GoatHornType implements StringIdentifiable {
    CALL,
    SING,
    SEEK,
    FEEL,
    ADMIRE,
    DREAM;

    public static GoatHornType fromInstrument(LazyRegistryEntryReference<Instrument> instrument) {
        var key = instrument.getKey().orElse(null);
        if (key == null) return CALL;

        if (key == Instruments.CALL_GOAT_HORN) return CALL;
        if (key == Instruments.SING_GOAT_HORN) return SING;
        if (key == Instruments.SEEK_GOAT_HORN) return SEEK;
        if (key == Instruments.FEEL_GOAT_HORN) return FEEL;
        if (key == Instruments.ADMIRE_GOAT_HORN) return ADMIRE;
        if (key == Instruments.DREAM_GOAT_HORN) return DREAM;

        return CALL;
    }

    @Override
    public String asString() {
        return name().toLowerCase();
    }
}
