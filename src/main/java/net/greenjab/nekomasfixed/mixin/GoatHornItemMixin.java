package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.InstrumentComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatHornItem.class)
public class GoatHornItemMixin {
    @Inject(method = "use", at = @At("HEAD"))
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (world.isClient()) return;
        ItemStack itemStack = user.getStackInHand(hand);
        InstrumentComponent instrument = itemStack.get(DataComponentTypes.INSTRUMENT);
        if (instrument != null) {
            ServerWorld serverWorld = (ServerWorld) world;
            StatusEffectInstance glow = new StatusEffectInstance(StatusEffects.GLOWING, 30 * 20);
            for(Entity entity : serverWorld.iterateEntities()) {
                if (entity instanceof IronGolemEntity ironGolem) {
                    if (user.hasStatusEffect(StatusEffects.RAID_OMEN) || user.hasStatusEffect(StatusEffects.BAD_OMEN))
                        ironGolem.addStatusEffect(GoatHornType.fromInstrument(instrument).getStatusEffect());
                }
                if (entity instanceof TameableEntity tameable && tameable.isTamed()) tameable.addStatusEffect(glow);
                if (entity instanceof AbstractHorseEntity horse && horse.isTame()) horse.addStatusEffect(glow);
            }
        }
    }
}
