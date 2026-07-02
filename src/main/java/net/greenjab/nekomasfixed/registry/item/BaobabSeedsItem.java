package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaobabSeedsItem extends Item {
    public BaobabSeedsItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        if(state.isIn(BlockTags.LEAVES)){
            BlockPos below = pos.down();
            if (world.getBlockState(below).isAir() || world.getBlockState(below).isIn(BlockTags.REPLACEABLE)) {
                world.setBlockState(below, BlockRegistry.BAOBAB_FRUIT.getDefaultState());
                context.getStack().decrementUnlessCreative(1, player);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
