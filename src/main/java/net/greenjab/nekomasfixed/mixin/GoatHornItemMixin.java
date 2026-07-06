package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InstrumentItem.class)
public class GoatHornItemMixin {
    @Inject(method = "use", at = @At("HEAD"))
    public void use(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (world.isClientSide()) return;
        ItemStack itemStack = user.getItemInHand(hand);
        InstrumentComponent instrument = itemStack.get(DataComponents.INSTRUMENT);
        if (instrument != null) {
            ServerLevel serverWorld = (ServerLevel) world;
            MobEffectInstance glow = new MobEffectInstance(MobEffects.GLOWING, 30 * 20);
            for(Entity entity : serverWorld.getAllEntities()) {
                if (entity instanceof IronGolem ironGolem) {
                    if (user.hasEffect(MobEffects.RAID_OMEN) || user.hasEffect(MobEffects.BAD_OMEN))
                        ironGolem.addEffect(GoatHornType.fromInstrument(instrument).getStatusEffect());
                }
                if (entity instanceof TamableAnimal tameable && tameable.isTame()) tameable.addEffect(glow);
                if (entity instanceof AbstractHorse horse && horse.isTamed()) horse.addEffect(glow);
            }
        }
    }
}
