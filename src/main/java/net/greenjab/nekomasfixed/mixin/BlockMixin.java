package net.greenjab.nekomasfixed.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "playerDestroy", at = @At("HEAD"))
    private void customAfterBreak(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack destroyedWith, CallbackInfo ci) {
        if(state.is(Blocks.MAGMA_BLOCK) && player!=null){
            ItemStack stack = player.getMainHandItem();
            Holder<Enchantment> silkTouchEntry =
                    level.registryAccess().getOrThrow(Holder.direct(Enchantments.SILK_TOUCH).value());
            if(level.getRandom().nextInt(5)==0&&!stack.isEnchanted() && stack.getEnchantments().getLevel(silkTouchEntry)>0 || !stack.getEnchantments().keySet().contains(silkTouchEntry) || player.getMainHandItem().isEmpty()){
                player.level().setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
            }
        }
    }
}
