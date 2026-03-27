package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class RopeItem extends BlockItem {
    public RopeItem(Block block, Item.Settings settings) {
        super(BlockRegistry.ROPE, settings);
    }

    public @Nullable ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos clickedPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState clickedState = world.getBlockState(clickedPos);
        Block ropeBlock = this.getBlock();

        if (!clickedState.isOf(ropeBlock)) {
            System.out.println("an item used " + clickedState);
            return context;
        }

        int i = clickedPos.getY();
        while(!world.getBlockState(new BlockPos(clickedPos.getX(), i, clickedPos.getZ())).isOf(BlockRegistry.ROPE)){
            BlockPos placePos = new BlockPos(clickedPos.getX(), i, clickedPos.getZ());
            BlockState targetState = world.getBlockState(placePos);
        if (targetState.isAir() || targetState.canReplace(context)) {
            return ItemPlacementContext.offset(context, placePos, Direction.DOWN);
        }
        --i;
        }

//        BlockPos.Mutable bottomPos = clickedPos.mutableCopy();
//        while (world.getBlockState(bottomPos.down()).isOf(BlockRegistry.ROPE)) {
//            System.out.println("is a rope ");
//            bottomPos.move(Direction.DOWN);
//        }
//
//        BlockPos placePos = bottomPos.down();
////        if (!world.isInBuildLimit(placePos)) {
////            if (context.getPlayer() instanceof ServerPlayerEntity player) {
////                player.sendMessageToClient(
////                        Text.translatable("build.tooLow", placePos.getY()).formatted(Formatting.RED),
////                        true
////                );
////            }
////            return null;
////        }
//        BlockState targetState = world.getBlockState(placePos);
//        if (targetState.isAir() || targetState.canReplace(context)) {
//            return ItemPlacementContext.offset(context, placePos, Direction.DOWN);
//        }

        return null;
    }

    protected boolean checkStatePlacement() {
        return false;
    }
}
