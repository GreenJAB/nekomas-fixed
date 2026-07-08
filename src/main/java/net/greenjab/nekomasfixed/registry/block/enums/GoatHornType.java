package net.greenjab.nekomasfixed.registry.block.enums;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.component.InstrumentComponent;
import org.jspecify.annotations.NonNull;

public enum GoatHornType implements StringRepresentable {
    CALL(Instruments.CALL_GOAT_HORN, new MobEffectInstance(MobEffects.SPEED, 20*60, 0)),
    PONDER(Instruments.PONDER_GOAT_HORN, new MobEffectInstance(MobEffects.RESISTANCE, 20*60, 0)),
    SING(Instruments.SING_GOAT_HORN, new MobEffectInstance(MobEffects.INSTANT_HEALTH, 20*60, 0)),
    SEEK(Instruments.SEEK_GOAT_HORN, new MobEffectInstance(MobEffects.STRENGTH, 20*60, 0)),
    FEEL(Instruments.FEEL_GOAT_HORN, new MobEffectInstance(MobEffects.ABSORPTION, 20*60, 0)),
    ADMIRE(Instruments.ADMIRE_GOAT_HORN, new MobEffectInstance(MobEffects.REGENERATION, 20*60, 0)),
    YEARN(Instruments.YEARN_GOAT_HORN, new MobEffectInstance(MobEffects.STRENGTH, 20*60, 0)),
    DREAM(Instruments.DREAM_GOAT_HORN, new MobEffectInstance(MobEffects.INVISIBILITY, 20*60, 0));

    private final ResourceKey<Instrument> instrument;
    private final MobEffectInstance effect;

    GoatHornType(ResourceKey<Instrument> instrument, MobEffectInstance effect) {
        this.instrument = instrument;
        this.effect = effect;
    }

    public ResourceKey<Instrument> getInstrument() {
        return this.instrument;
    }

    public MobEffectInstance getStatusEffect(){
        return this.effect;
    }

    public static GoatHornType fromInstrument(InstrumentComponent instrument) {
        ResourceKey<Instrument> key = instrument.instrument().unwrapKey().orElse(Instruments.CALL_GOAT_HORN);
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
    public @NonNull String getSerializedName() {
        return name().toLowerCase();
    }
}
