package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.GoatHornBlock;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Inject(method = "useItemOn", at= @At("HEAD"), cancellable = true)
    private void customOnUseWithItem(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if(!level.getBlockState(pos).is(BlockTags.REPLACEABLE) && state.isFaceSturdy(level, pos, hitResult.getDirection())){
            if (itemStack.is(Items.GOAT_HORN)) {
                Direction direction = hitResult.getDirection();

                if (direction.getAxis().isVertical()) {
                    direction = player.getDirection();
                }

                Direction facing = direction.getOpposite();
                BlockPos placePos = pos.relative(direction);
                FluidState fluidState = level.getFluidState(placePos);

                InstrumentComponent component = itemStack.get(DataComponents.INSTRUMENT);
                if (component == null) return;

                GoatHornType hornType = GoatHornType.fromInstrument(component);

                BlockState newState = BlockRegistry.GOAT_HORN.defaultBlockState()
                        .setValue(GoatHornBlock.HORN, hornType)
                        .setValue(HorizontalDirectionalBlock.FACING, facing)
                        .setValue(GoatHornBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER);

                if (level.getBlockState(placePos).is(BlockTags.REPLACEABLE) && state.isFaceSturdy(level, pos, hitResult.getDirection())) {
                    level.setBlockAndUpdate(placePos, newState);
                    level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    player.getItemInHand(hand).consume(1, player);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }

}
