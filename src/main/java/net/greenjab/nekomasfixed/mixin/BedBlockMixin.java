package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.MessyBedAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin extends Block {
    public BedBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void messyDefault(DyeColor color, BlockBehaviour.Properties properties, CallbackInfo ci) {
        registerDefaultState(defaultBlockState().setValue(MessyBedAccessor.MESSY, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void addMessy(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(MessyBedAccessor.MESSY);
    }

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void messy(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (level.isClientSide()) return;
        BlockPos otherPos = pos.relative(state.getValue(BedBlock.PART) == BedPart.FOOT
                ? state.getValue(BedBlock.FACING) : state.getValue(BedBlock.FACING).getOpposite());
        BlockState otherState = level.getBlockState(otherPos);
        if (player.isShiftKeyDown() && player.getMainHandItem().isEmpty() && state.getValue(MessyBedAccessor.MESSY)) {
            level.setBlockAndUpdate(pos, state.setValue(MessyBedAccessor.MESSY, false));
            level.setBlockAndUpdate(otherPos, otherState.setValue(MessyBedAccessor.MESSY, false));
            player.swing(InteractionHand.MAIN_HAND, true);
            cir.setReturnValue(InteractionResult.SUCCESS);
        } else if (level.isNight() && !state.getValue(MessyBedAccessor.MESSY) && !state.getValue(BedBlock.OCCUPIED)) {
            level.setBlockAndUpdate(pos, state.setValue(MessyBedAccessor.MESSY, true));
            level.setBlockAndUpdate(otherPos, otherState.setValue(MessyBedAccessor.MESSY, true));
        }
    }
}
