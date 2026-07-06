package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class RopeItem extends BlockItem {
    public RopeItem(Block block, Item.Properties settings) {
        super(block, settings);
    }

    protected boolean canPlaceAt(Level world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.above());
        return blockState.is(BlockRegistry.ROPE) || blockState.is(BlockTags.LEAVES) || blockState.isFaceSturdy(world, pos, Direction.DOWN);
    }

    @Nullable
    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
        Level world = context.getLevel();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();
        if (!blockState.is(block)) {
            return canPlaceAt(context.getLevel(), blockPos.relative(context.getClickedFace()))?context:null;
        } else {
            Direction direction = Direction.DOWN;
            BlockPos.MutableBlockPos mutable = blockPos.mutable().move(direction);
            while (true) {
                if (!world.isClientSide() && !world.isInWorldBounds(mutable)) {
                    Player playerEntity = context.getPlayer();
                    int j = world.getMaxY();
                    if (playerEntity instanceof ServerPlayer && mutable.getY() > j) {
                        ((ServerPlayer)playerEntity).sendSystemMessage(Component.translatable("argument.pos.outofbounds").withStyle(ChatFormatting.RED), true);
                    }
                    break;
                }

                blockState = world.getBlockState(mutable);
                if (!blockState.is(this.getBlock())) {
                    if (blockState.canBeReplaced(context)) {
                        return BlockPlaceContext.at(context, mutable, direction);
                    }
                    break;
                }

                mutable.move(direction);
            }

            return null;
        }
    }

    protected boolean mustSurvive() {
        return false;
    }
}
