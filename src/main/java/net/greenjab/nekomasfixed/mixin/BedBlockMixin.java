package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.MessyBedAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class BedBlockMixin implements MessyBedAccessor {


    @Inject(method = "<init>", at = @At("TAIL"))
    private void nekomasfixed$setDefaultState(DyeColor color, AbstractBlock.Settings settings, CallbackInfo ci) {
        BedBlock self = (BedBlock)(Object)this;

        self.setDefaultState(self.getDefaultState().with(MessyBedAccessor.IS_MESSY, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(MessyBedAccessor.IS_MESSY);
    }

    @Inject(method =  "onUse", at = @At("HEAD"), cancellable = true)
        protected void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if(!world.isClient()){
            BlockPos otherPos = state.get(BedBlock.PART) == BedPart.FOOT ? pos.offset(state.get(BedBlock.FACING)) :pos.offset(state.get(BedBlock.FACING).getOpposite()) ;
            if(player.isSneaking() && player.getMainHandStack().isEmpty() && state.get(MessyBedAccessor.IS_MESSY)){
                BlockState otherState = world.getBlockState(otherPos);
                world.setBlockState(pos, state.with(MessyBedAccessor.IS_MESSY, false));
                world.setBlockState(otherPos, otherState.with(MessyBedAccessor.IS_MESSY, false));
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }

            if(world.isNight() && !state.get(MessyBedAccessor.IS_MESSY) && !state.get(BedBlock.OCCUPIED)){
                BlockState otherState = world.getBlockState(otherPos);
                world.setBlockState(pos, state.with(MessyBedAccessor.IS_MESSY, true));
                world.setBlockState(otherPos, otherState.with(MessyBedAccessor.IS_MESSY, true));
            }
        }
    }

    @Override
    public BooleanProperty nekomasfixed$getMessyBed() {
        return MessyBedAccessor.IS_MESSY;
    }
}
