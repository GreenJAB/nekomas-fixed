package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.cauldron.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {

    @Inject(method = "releaseOccupant", at = @At("HEAD"))
    private static void onReleaseBee(Level world, BlockPos pos, BlockState state,
                                     BeehiveBlockEntity.Occupant bee, @Nullable List<Entity> entities,
                                     BeehiveBlockEntity.BeeReleaseStatus beeState, @Nullable BlockPos flowerPos,
                                     CallbackInfoReturnable<Boolean> cir) {
        if (beeState != BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED) return;
        BlockPos belowPos = pos.below();
        BlockState belowState = world.getBlockState(belowPos);
        int i = 0;
        while (belowState.is(Blocks.AIR) && i<3) {
            belowPos = belowPos.below();
            belowState = world.getBlockState(belowPos);
            i++;
        }
        if (belowState.getBlock() == BlockRegistry.HONEY_CAULDRON && state.getValue(BeehiveBlock.HONEY_LEVEL) == 5) {
            incrementHoneyLevel(world, belowPos, belowState);
        }
        else if (belowState.getBlock() == Blocks.CAULDRON && state.getValue(BeehiveBlock.HONEY_LEVEL) == 5) {
            world.setBlockAndUpdate(belowPos, BlockRegistry.HONEY_CAULDRON.defaultBlockState()
                    .setValue(HoneyCauldronBlock.HONEY_LEVEL, 1));
            world.playSound(null, belowPos, SoundEvents.BEEHIVE_DRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

        }
    }

    @Unique
    private static void incrementHoneyLevel(Level world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) return;
        if (state.getBlock() != BlockRegistry.HONEY_CAULDRON) return;

        int currentLevel = state.getValue(HoneyCauldronBlock.HONEY_LEVEL);
        if (currentLevel >= HoneyCauldronBlock.MAX_LEVEL) return;

        world.setBlockAndUpdate(pos, state.setValue(HoneyCauldronBlock.HONEY_LEVEL, currentLevel + 1));
        world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}