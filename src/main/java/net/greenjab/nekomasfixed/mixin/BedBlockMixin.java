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
public class BedBlockMixin implements MessyBedAccessor {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void setDefaultState(DyeColor color, BlockBehaviour.Properties properties, CallbackInfo ci) {
        BedBlock self = (BedBlock)(Object)this;
        if (self.defaultBlockState().hasProperty(MessyBedAccessor.IS_MESSY))
            self.registerDefaultState(self.defaultBlockState().setValue(MessyBedAccessor.IS_MESSY, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(MessyBedAccessor.IS_MESSY);
    }

    @Inject(method =  "useWithoutItem", at = @At("HEAD"), cancellable = true)
        protected void onUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if(!level.isClientSide()){
            BlockPos otherPos = state.getValue(BedBlock.PART) == BedPart.FOOT ? pos.relative(state.getValue(BedBlock.FACING)) :pos.relative(state.getValue(BedBlock.FACING).getOpposite()) ;
            if(player.isShiftKeyDown() && player.getMainHandItem().isEmpty() && state.getValue(MessyBedAccessor.IS_MESSY)){
                BlockState otherState = level.getBlockState(otherPos);
                level.setBlockAndUpdate(pos, state.setValue(MessyBedAccessor.IS_MESSY, false));
                level.setBlockAndUpdate(otherPos, otherState.setValue(MessyBedAccessor.IS_MESSY, false));
                player.swing(InteractionHand.MAIN_HAND, true);
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }

            if(level.isDarkOutside() && !state.getValue(MessyBedAccessor.IS_MESSY) && !state.getValue(BedBlock.OCCUPIED)){
                BlockState otherState = level.getBlockState(otherPos);
                level.setBlockAndUpdate(pos, state.setValue(MessyBedAccessor.IS_MESSY, true));
                level.setBlockAndUpdate(otherPos, otherState.setValue(MessyBedAccessor.IS_MESSY, true));
            }
        }
    }
}
