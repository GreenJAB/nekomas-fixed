package net.greenjab.nekomasfixed.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {

    @Inject(method = "fallOn", at = @At("HEAD"), cancellable = true)
    private void preventTrample(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack boots = livingEntity.getItemBySlot(EquipmentSlot.FEET);

            Registry<Enchantment> enchantmentLookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Holder.Reference<Enchantment> featherFallingEntry = enchantmentLookup.getOrThrow(Enchantments.FEATHER_FALLING);
            int eLevel = EnchantmentHelper.getItemEnchantmentLevel(featherFallingEntry, boots);

            if (eLevel > 0) ci.cancel();
        }
    }
}