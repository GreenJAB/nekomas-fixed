package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "onStruckByLightning", at = @At("HEAD"))
    private void tickThunder(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if (ModConfigValues.enableCopperBuff) {
            if ((Entity)(Object)this instanceof ServerPlayerEntity player) {
                int armor = getCopperArmor(player);
                if (armor > 0) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 3 * armor * 20, armor, false, false, false));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, armor, false, false, false));
                }
            }
        }
    }

    @Unique
    private static int getCopperArmor(LivingEntity entity) {
        int i =0;
        if (entity.getEquippedStack(EquipmentSlot.FEET).isOf(Items.COPPER_BOOTS)) i++;
        if (entity.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.COPPER_LEGGINGS)) i++;
        if (entity.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.COPPER_CHESTPLATE)) i++;
        if (entity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.COPPER_HELMET)) i++;
        return i;
    }
}
