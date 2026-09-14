package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.mixin.accessor.ObserverBlockAccessor;
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
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public class RedstoneStrikerItem extends FlintAndSteelItem {
    //Struck positions (dimension + pos) -> game time the full-power effect expires.
    public static final Map<GlobalPos, Long> STRUCK_WIRES = new HashMap<>();

    public RedstoneStrikerItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        GlobalPos globalPos = new GlobalPos(level.dimension(), pos);
        BlockState state = level.getBlockState(pos);
        level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F,
                level.getRandom().nextFloat() * 0.4F + 0.8F);
        if (player != null) {
            player.swing(context.getHand(), true);
            context.getItemInHand().hurtAndBreak(1, player, player.getEquipmentSlotForItem(context.getItemInHand()));
            STRUCK_WIRES.put(globalPos, level.getGameTime() + (player.isShiftKeyDown() ? 1 : 16));
        } else {
            STRUCK_WIRES.put(globalPos, level.getGameTime() + 16);
        }
        // Striking an observer directly fires its pulse (its startSignal is private).
        if (state.is(Blocks.OBSERVER) && level instanceof ServerLevel serverLevel) {
            if (state.getBlock() instanceof ObserverBlock observerBlock) {
                ((ObserverBlockAccessor) observerBlock).invokeStartSignal(serverLevel, pos);
            }
        }
        state.handleNeighborChanged(level, pos, Blocks.AIR, pos, false);
        // A directly-struck repeater/comparator is powered immediately (PR #41's approach).
        if (state.is(Blocks.REPEATER)) {
            level.setBlock(pos, state.setValue(DiodeBlock.POWERED, true), 3);
        } else if (state.is(Blocks.COMPARATOR)) {
            level.setBlock(pos, state.setValue(DiodeBlock.POWERED, true), 3);
            if (level.getBlockEntity(pos) instanceof ComparatorBlockEntity comparator) {
                comparator.setOutputSignal(15);
            }
        }
        level.updateNeighborsAt(pos, state.getBlock());
        return InteractionResult.SUCCESS;
    }
}