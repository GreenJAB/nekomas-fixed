package net.greenjab.nekomasfixed.registry.other;

import net.greenjab.nekomasfixed.registry.item.DyedBrushItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class DyedBrushBehaviour extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        ServerLevel level = source.level();
        BlockPos pos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        boolean used = stack.getItem() instanceof DyedBrushItem brush && DyedBrushItem.dye(level, pos, brush.getColor());
        setSuccess(used);
        if (used) {
            level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (stack.getDamageValue() + 1 >= stack.getMaxDamage()) return ItemStack.EMPTY;
            stack.setDamageValue(stack.getDamageValue() + 1);
        }
        return stack;
    }
}
