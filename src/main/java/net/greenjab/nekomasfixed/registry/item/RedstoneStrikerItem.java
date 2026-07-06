package net.greenjab.nekomasfixed.registry.item;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class RedstoneStrikerItem extends FlintAndSteelItem {
    public static final Map<GlobalPos, Long> STRUCK_WIRES = new HashMap<>();
    public RedstoneStrikerItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        GlobalPos Gpos = new GlobalPos(world.dimension(), pos);
        BlockState state = context.getLevel().getBlockState(pos);
        world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        if (player != null) {
            player.swing(player.getUsedItemHand(), true);
            context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
            STRUCK_WIRES.put(Gpos, world.getGameTime() + (player.isShiftKeyDown() ? 1 : 16));
        } else STRUCK_WIRES.put(Gpos, world.getGameTime() + 16);
        if (state.is(Blocks.OBSERVER) && world instanceof ServerLevel serverWorld)
            if (state.getBlock() instanceof ObserverBlock observerBlock) observerBlock.startSignal(serverWorld, world, pos);
        state.handleNeighborChanged(world, pos, Blocks.AIR, null, false);
        world.updateNeighborsAt(pos, state.getBlock());
        return InteractionResult.SUCCESS;
    }
}