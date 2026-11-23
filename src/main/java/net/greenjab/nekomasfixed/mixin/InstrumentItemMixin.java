package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InstrumentItem.class)
public class InstrumentItemMixin {
    private static final double RANGE = 64;

    @Inject(method = "use", at = @At("HEAD"))
    private void hornEffects(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (level.isClientSide()) return;
        Holder<Instrument> instrument = player.getItemInHand(hand).get(DataComponents.INSTRUMENT);
        if (instrument == null) return;
        MobEffectInstance glow = new MobEffectInstance(MobEffects.GLOWING, 30 * 20);
        boolean omen = player.hasEffect(MobEffects.RAID_OMEN) || player.hasEffect(MobEffects.BAD_OMEN);
        for (TamableAnimal pet : level.getEntitiesOfClass(TamableAnimal.class, player.getBoundingBox().inflate(RANGE), TamableAnimal::isTame)) {
            pet.addEffect(new MobEffectInstance(glow));
        }
        for (AbstractHorse horse : level.getEntitiesOfClass(AbstractHorse.class, player.getBoundingBox().inflate(RANGE), AbstractHorse::isTamed)) {
            horse.addEffect(new MobEffectInstance(glow));
        }
        if (omen) {
            for (IronGolem golem : level.getEntitiesOfClass(IronGolem.class, player.getBoundingBox().inflate(RANGE))) {
                golem.addEffect(new MobEffectInstance(GoatHornType.fromInstrument(instrument).getStatusEffect()));
            }
        }
    }
}
