package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.GoatHornBlock;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.InstrumentComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "onUseWithItem", at= @At("HEAD"), cancellable = true)
    private void customOnUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if(!world.getBlockState(pos).isIn(BlockTags.REPLACEABLE) && state.isSideSolidFullSquare(world, pos, hit.getSide())){
            if (stack.isOf(Items.GOAT_HORN)) {
                Direction direction = hit.getSide();

                if (direction.getAxis().isVertical()) {
                    direction = player.getHorizontalFacing();
                }

                Direction facing = direction.getOpposite();
                BlockPos placePos = pos.offset(direction);
                FluidState fluidState = world.getFluidState(placePos);

                InstrumentComponent component = stack.get(DataComponentTypes.INSTRUMENT);
                if (component == null) return;

                GoatHornType hornType = GoatHornType.fromInstrument(component);

                BlockState newState = BlockRegistry.GOAT_HORN.getDefaultState()
                        .with(GoatHornBlock.HORN, hornType)
                        .with(HorizontalFacingBlock.FACING, facing)
                        .with(GoatHornBlock.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

                if (world.getBlockState(placePos).isIn(BlockTags.REPLACEABLE) && state.isSideSolidFullSquare(world, pos, hit.getSide())) {
                    world.setBlockState(placePos, newState);
                    player.getMainHandStack().decrementUnlessCreative(1, player);
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }

}
