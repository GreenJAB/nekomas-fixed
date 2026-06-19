package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.GoatHornBlock;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
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
        if(!world.isClient() && !world.getBlockState(pos).isIn(BlockTags.REPLACEABLE) && state.isSideSolidFullSquare(world, pos, hit.getSide())){
            if(stack.isOf(Items.CAKE)){

                if(world.getBlockEntity(pos.offset(hit.getSide())) instanceof StackedCakeBlockEntity blockEntity){
                    blockEntity.STACKED_CAKES.push(Blocks.CAKE.getDefaultState());
                }else if(world.getBlockState(pos.offset(hit.getSide()).down()).isSolid()){
                    world.setBlockState(pos.offset(hit.getSide()), BlockRegistry.STACKED_STRAWBERRY_CAKE.getDefaultState());
                    if(world.getBlockEntity(pos.offset(hit.getSide())) instanceof StackedCakeBlockEntity blockEntity){
                        blockEntity.STACKED_CAKES.push(Blocks.CAKE.getDefaultState());
                    }
                }
                player.swingHand(Hand.MAIN_HAND, true);
                player.getMainHandStack().decrementUnlessCreative(1, player);
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
            if (stack.isOf(Items.GOAT_HORN)) {
                Direction direction = hit.getSide();

                if (direction.getAxis().isVertical()) {
                    direction = player.getHorizontalFacing();
                }

                Direction facing = direction.getOpposite();
                BlockPos placePos = pos.offset(direction);

                var component = stack.get(DataComponentTypes.INSTRUMENT);
                if (component == null) return;

                GoatHornType hornType = GoatHornType.fromInstrument(component.instrument());

                BlockState newState = BlockRegistry.GOAT_HORN.getDefaultState()
                        .with(GoatHornBlock.HORN, hornType)
                        .with(HorizontalFacingBlock.FACING, facing);

                if (world.getBlockState(placePos).isReplaceable()) {
                    world.setBlockState(placePos, newState);
                    player.getMainHandStack().decrementUnlessCreative(1, player);
                    player.swingHand(hand, true);
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }

            if(stack.isOf(ItemRegistry.BAOBAB_SEEDS) && state.isIn(BlockTags.LEAVES)){
                BlockPos below = pos.down();
                if (world.getBlockState(below).isAir() || world.getBlockState(below).isIn(BlockTags.REPLACEABLE)) {
                    world.setBlockState(below, BlockRegistry.BAOBAB_FRUIT.getDefaultState());
                    player.swingHand(hand, true);
                    stack.decrementUnlessCreative(1, player);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

}
