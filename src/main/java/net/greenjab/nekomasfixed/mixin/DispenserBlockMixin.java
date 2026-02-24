package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

import java.util.Arrays;
import java.util.List;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    @Inject(method = "dispense", at = @At("HEAD"))
    private void onDispense(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof DispenserBlockEntity dispenser) {

            Block[] allCauldronState = {
                    BlockRegistry.ICE_CAULDRON,
                    BlockRegistry.HONEY_CAULDRON,
                    BlockRegistry.SLIME_CAULDRON,
                    BlockRegistry.MAGMA_CAULDRON,
                    Blocks.LAVA_CAULDRON,
                    Blocks.WATER_CAULDRON,
                    Blocks.POWDER_SNOW_CAULDRON,
            };

            if(!world.isClient()){
                BlockState belowState = world.getBlockState(pos.down());
                Block belowBlock = belowState.getBlock();
                Block aboveBlock = world.getBlockState(pos.up()).getBlock();
                Block leftBlock = world.getBlockState(pos.west()).getBlock();
                Block rightBlock = world.getBlockState(pos.east()).getBlock();

                boolean isCauldronNear = Arrays.stream(allCauldronState)
                        .anyMatch(cauldron -> cauldron == belowBlock || cauldron == aboveBlock || cauldron == leftBlock || cauldron == rightBlock);
                System.out.println(isCauldronNear);
                if (isCauldronNear) {
                    System.out.println("Found a cauldron below the dispenser!");
                    for (int i = 0; i < dispenser.size(); i++) {
                        ItemStack stack = dispenser.getStack(i);
                        if (stack.isOf(Items.BUCKET)) {
                            System.out.println("Bucket in slot " + i + " can be used with cauldron below!");
                            // Your logic here
                        }
                    }
                }
            }
        }
    }
}