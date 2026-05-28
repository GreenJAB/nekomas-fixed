package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class RedstoneStrikerItem extends FlintAndSteelItem {
    public static final Map<BlockPos, Long> STRUCK_WIRES = new HashMap<>();
    public RedstoneStrikerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        if(world.getBlockState(pos).isOf(Blocks.REDSTONE_WIRE)){
            STRUCK_WIRES.put(pos.toImmutable(), world.getTime() + 10);
            world.setBlockState(pos, state.with(RedstoneWireBlock.POWER, 15));
            world.updateNeighbors(pos, state.getBlock());
        }
        return ActionResult.SUCCESS;
    }
}