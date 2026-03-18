package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ApplyEffectsConsumeEffect.class)
class ApplyEffectsConsumeEffectMixin {
    @Inject(method = "onConsume", at = @At("HEAD"),cancellable = true)
    private void onConsume(World world, ItemStack stack, LivingEntity user, CallbackInfoReturnable<Boolean> cir) {
        if(!stack.isEmpty() && stack.isOf(ItemRegistry.LIGHTNING_BOTLE)){
            LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);
            if(lightningEntity!=null){
                lightningEntity.refreshPositionAfterTeleport(user.getX(), user.getY(), user.getZ());
                world.spawnEntity(lightningEntity);
                if(user instanceof PlayerEntity){
                    if(user.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.COPPER_HELMET)&&
                            user.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.COPPER_CHESTPLATE)&&
                            user.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.COPPER_LEGGINGS)&&
                            user.getEquippedStack(EquipmentSlot.FEET).isOf(Items.COPPER_BOOTS)){
                        StatusEffectInstance st = new StatusEffectInstance(StatusEffects.SPEED, 10*20, 4, false, false, false);
                        StatusEffectInstance stH = new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1*20, 2, false, false, false);
                        user.setStatusEffect(st, user);
                        user.setStatusEffect(stH, user);
                    }
                }
            }
        }
    }
}
