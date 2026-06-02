package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ObserverBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        if (player != null) {
            player.swingHand(player.getActiveHand(), true);
            context.getStack().damage(1, player, context.getHand().getEquipmentSlot());
            STRUCK_WIRES.put(pos.toImmutable(), world.getTime() + (player.isSneaking() ? 1 : 16));
        } else STRUCK_WIRES.put(pos.toImmutable(), world.getTime() + 16);
        if (state.isOf(Blocks.OBSERVER) && world instanceof ServerWorld serverWorld)
            //state.scheduledTick(serverWorld, pos, world.random);
            if (state.getBlock() instanceof ObserverBlock observerBlock) observerBlock.scheduleTick(serverWorld, world, pos);
        state.neighborUpdate(world, pos, Blocks.AIR, null, true);
        world.updateNeighbors(pos, state.getBlock());
        return ActionResult.SUCCESS;
    }
}