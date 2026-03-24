package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void customAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
        if(state.isOf(Blocks.MAGMA_BLOCK) && player!=null){
            ItemStack stack = player.getMainHandStack();
            RegistryEntry<Enchantment> silkTouchEntry =
                    world.getRegistryManager().getEntryOrThrow(RegistryEntry.of(Enchantments.SILK_TOUCH).value());

            if(!stack.hasEnchantments() && stack.getEnchantments().getLevel(silkTouchEntry)>0){
                player.getEntityWorld().setBlockState(pos, Blocks.LAVA.getDefaultState());
            }
        }
    }
}
