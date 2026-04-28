package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SoupCauldronBlockEntity extends BlockEntity {
    private final List<ItemStack> inputs = new ArrayList<>();
    private boolean hasStirred = false;

    public SoupCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    public void addInput(ItemStack stack) {
        if (stack.isOf(Items.AIR) || hasStirred) {
            return;
        }
        for (ItemStack existing : inputs) {
            if (ItemStack.areItemsAndComponentsEqual(existing, stack)) {
                return;
            }
        }

        if (inputs.size() < 4) {
            inputs.add(stack.copyWithCount(1));
            markDirty();
        }
    }

    public boolean canBePicked(){return this.hasStirred;}
    public void setHasStirred(boolean val){ this.hasStirred = val;}

    public List<ItemStack> getInputs() {return inputs;}
}
