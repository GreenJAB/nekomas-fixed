package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.BannerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {
    @Inject(method = "dropCustomDeathLoot", at = @At("HEAD"))
    private void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean killedByPlayer, CallbackInfo ci) {
        Mob mob = (Mob)(Object)this;
        if(mob instanceof Sheep sheep){
            ItemStack stack = ItemStack.EMPTY;
            if(sheep.getColor().getName().equals("amber")){
                stack = ItemRegistry.AMBER_WOOL.getDefaultInstance();
            }
            if(sheep.getColor().getName().equals("aqua")){
                stack = ItemRegistry.AQUA_WOOL.getDefaultInstance();
            }
            if(sheep.getColor().getName().equals("maroon")){
                stack = ItemRegistry.MAROON_WOOL.getDefaultInstance();
            }
            if(sheep.getColor().getName().equals("indigo")){
                stack = ItemRegistry.INDIGO_WOOL.getDefaultInstance();
            }

            for (EquipmentSlot slot : EquipmentSlot.VALUES) {
                float dropChance = mob.dropChances.byEquipment(slot);
                if (dropChance != 0.0F) {
                    boolean preserve = mob.dropChances.isPreserved(slot);
                    if (source.getEntity() instanceof LivingEntity livingSource && mob.level() instanceof ServerLevel serverLevel) {
                        dropChance = EnchantmentHelper.processEquipmentDropChance(serverLevel, livingSource, source, dropChance);
                    }

                    if (!stack.isEmpty()
                            && !EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)
                            && (killedByPlayer || preserve)
                            && mob.random.nextFloat() < dropChance) {
                        if (!preserve && stack.isDamageableItem()) {
                            stack.setDamageValue(stack.getMaxDamage() - mob.random.nextInt(1 + mob.random.nextInt(Math.max(stack.getMaxDamage() - 3, 1))));
                        }

                        mob.spawnAtLocation(level, stack);
                        mob.setItemSlot(slot, ItemStack.EMPTY);
                    }
                }
            }
        }


    }

}

