package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    @Inject(method = "dispense", at = @At("HEAD"))
    private void onDispense(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof DispenserBlockEntity dispenser) {
            // Use public methods to access inventory
            for (int i = 0; i < dispenser.size(); i++) {
                ItemStack stack = dispenser.getStack(i);
                if (!stack.isEmpty()) {
                    System.out.println("Slot " + i + ": " + stack.getItem());
                }
            }

            // Get a random non-empty slot
            int slot = dispenser.chooseNonEmptySlot(world.random);
            if (slot >= 0) {
                ItemStack randomStack = dispenser.getStack(slot);
                System.out.println("Random item: " + randomStack.getItem());
            }

            // Modify inventory using public methods
            dispenser.setStack(0, new ItemStack(Items.DIAMOND, 1));
        }
    }
}
