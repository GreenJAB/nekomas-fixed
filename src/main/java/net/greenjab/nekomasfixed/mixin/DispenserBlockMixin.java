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

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {



    @Inject(method = "dispense", at = @At("HEAD"))
    private void onDispense(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci) {
        final Block[] CAULDRON_TYPES = {
                BlockRegistry.ICE_CAULDRON,
                BlockRegistry.HONEY_CAULDRON,
                BlockRegistry.SLIME_CAULDRON,
                BlockRegistry.MAGMA_CAULDRON,
                Blocks.LAVA_CAULDRON,
                Blocks.WATER_CAULDRON,
                Blocks.POWDER_SNOW_CAULDRON,
        };
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof DispenserBlockEntity dispenser) {

            if(!world.isClient()){
                BlockState belowState = world.getBlockState(pos.down());
                BlockState aboveState = world.getBlockState(pos.up());
                BlockState westState = world.getBlockState(pos.west());
                BlockState eastState = world.getBlockState(pos.east());
                BlockState northState = world.getBlockState(pos.north());
                BlockState southState = world.getBlockState(pos.south());

                Block belowBlock = belowState.getBlock();
                Block aboveBlock = aboveState.getBlock();
                Block westBlock = westState.getBlock();
                Block eastBlock = eastState.getBlock();
                Block northBlock = northState.getBlock();
                Block southBlock = southState.getBlock();

                boolean belowMatch = Arrays.stream(CAULDRON_TYPES).anyMatch(c -> c == belowBlock);
                boolean aboveMatch = Arrays.stream(CAULDRON_TYPES).anyMatch(c -> c == aboveBlock);
                boolean westMatch = Arrays.stream(CAULDRON_TYPES).anyMatch(c -> c == westBlock);
                boolean eastMatch = Arrays.stream(CAULDRON_TYPES).anyMatch(c -> c == eastBlock);
                boolean northMatch = Arrays.stream(CAULDRON_TYPES).anyMatch(c -> c == northBlock);
                boolean southMatch = Arrays.stream(CAULDRON_TYPES).anyMatch(c -> c == southBlock);


                boolean isCauldronNear = belowMatch || aboveMatch || westMatch || eastMatch || northMatch || southMatch;
                System.out.println("Final result: " + isCauldronNear);

                if (isCauldronNear) {
                    for (int i = 0; i < dispenser.size(); i++) {
                        ItemStack stack = dispenser.getStack(i);
                        if (stack.isOf(Items.BUCKET)) {
                            System.out.println("Bucket in slot " + i);

                        }
                    }
                }
            }
        }
    }
}