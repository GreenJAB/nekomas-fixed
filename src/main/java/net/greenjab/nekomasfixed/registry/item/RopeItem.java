package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
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

    protected boolean canPlaceAt(World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return blockState.isOf(BlockRegistry.ROPE) || blockState.isIn(BlockTags.LEAVES) || blockState.isSideSolidFullSquare(world, pos, Direction.DOWN);
    }

    @Nullable
    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos blockPos = context.getBlockPos().offset(context.getSide().getOpposite());
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();
        if (!blockState.isOf(block)) {
            return canPlaceAt(context.getWorld(), blockPos.offset(context.getSide()))?context:null;
        } else {
            Direction direction = Direction.DOWN;
            BlockPos.Mutable mutable = blockPos.mutableCopy().move(direction);
            while (true) {
                if (!world.isClient() && !world.isInBuildLimit(mutable)) {
                    PlayerEntity playerEntity = context.getPlayer();
                    int j = world.getTopYInclusive();
                    if (playerEntity instanceof ServerPlayerEntity && mutable.getY() > j) {
                        ((ServerPlayerEntity)playerEntity).sendMessageToClient(Text.translatable("argument.pos.outofbounds").formatted(Formatting.RED), true);
                    }
                    break;
                }

                blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this.getBlock())) {
                    if (blockState.canReplace(context)) {
                        return ItemPlacementContext.offset(context, mutable, direction);
                    }
                    break;
                }

                mutable.move(direction);
            }

            return null;
        }
    }

    protected boolean checkStatePlacement() {
        return false;
    }
}
