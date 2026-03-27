package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.block.RopeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class RopeItem extends BlockItem {
    public RopeItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    public @Nullable ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos clickedPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState clickedState = world.getBlockState(clickedPos);
        Block ropeBlock = this.getBlock();

        if (!clickedState.isOf(ropeBlock)) {
            System.out.println("not a rope");
            return context;
        }

        BlockPos.Mutable bottomPos = clickedPos.mutableCopy();
        while (world.getBlockState(bottomPos.down()).isOf(ropeBlock)) {
            System.out.println("is a rope aye");
            bottomPos.move(Direction.DOWN);
        }

        BlockPos placePos = bottomPos.down();
        if (!world.isInBuildLimit(placePos)) {
//            if (context.getPlayer() instanceof ServerPlayerEntity player) {
//                player.sendMessageToClient(
//                        Text.translatable("build.tooLow", placePos.getY()).formatted(Formatting.RED),
//                        true
//                );
//            }
            return null;
        }
        BlockState targetState = world.getBlockState(placePos);
        if (targetState.isAir() || targetState.canReplace(context)) {
            return ItemPlacementContext.offset(context, placePos, Direction.DOWN);
        }

        return null;
    }

    protected boolean checkStatePlacement() {
        return false;
    }
}
