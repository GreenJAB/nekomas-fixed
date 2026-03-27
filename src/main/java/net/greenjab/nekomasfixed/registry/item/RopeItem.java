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
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();

        if(!blockState.isOf(block)){return context;}
        else{
            BlockPos.Mutable mutable = blockPos.mutableCopy().move(Direction.DOWN);
            int i = blockPos.getY();
            while(i>world.getDimension().minY()){
                if (!world.isClient() && !world.isInBuildLimit(mutable)) {
                    //this is just garbage and send a BUILD LIMIT msg
                    PlayerEntity playerEntity = context.getPlayer();
                    int j = world.getTopYInclusive();
                    if (playerEntity instanceof ServerPlayerEntity && mutable.getY() > j) {
                        ((ServerPlayerEntity)playerEntity).sendMessageToClient(Text.translatable("build.tooHigh", new Object[]{j}).formatted(Formatting.RED), true);
                    }
                    break;
                }
                blockState = world.getBlockState(mutable);

                //MAIN STUFF
                if (!blockState.isOf(this.getBlock())) {
                    if (blockState.canReplace(context)) {
                        return ItemPlacementContext.offset(context, mutable, Direction.DOWN);
                    }
                    break;
                }
                //moving down
                mutable.move(Direction.DOWN);
            }
            return null;
        }
    }

    protected boolean checkStatePlacement() {
        return false;
    }
}
