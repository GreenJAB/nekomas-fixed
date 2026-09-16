import java.util.HashMap;
import java.util.Map;

import alternate.current.interfaces.mixin.IServerLevel;
import alternate.current.wire.WireHandler;
import net.greenjab.nekomasfixed.NekomasFixed;
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
import net.minecraft.world.level.block.RedStoneWireBlock;
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
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        GlobalPos Gpos = new GlobalPos(level.dimension(), pos);
        BlockState state = context.getLevel().getBlockState(pos);
        level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
        if (player != null) {
            player.swing(player.getUsedItemHand(), true);
            context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
            STRUCK_WIRES.put(Gpos, level.getGameTime() + (player.isShiftKeyDown() ? 1 : 16));
            if(NekomasFixed.isAlternate() && level instanceof ServerLevel serverLevel){
                IServerLevel iServerLevel = (IServerLevel)serverLevel;
                WireHandler handler = iServerLevel.alternate_current$getWireHandler();
                if(state.is(Blocks.REDSTONE_WIRE)){
                    BlockState nxt = level.getBlockState(pos).setValue(RedStoneWireBlock.POWER, 15);

                    handler.onWireRemoved(pos, state);
                    handler.onWireAdded(pos, nxt);
                    handler.onWireUpdated(pos, nxt, null);
                }

            }
        } else STRUCK_WIRES.put(Gpos, level.getGameTime() + 16);
        if (state.is(Blocks.OBSERVER) && level instanceof ServerLevel serverLevel)
            if (state.getBlock() instanceof ObserverBlock observerBlock) observerBlock.startSignal(serverLevel, level, pos);
        state.handleNeighborChanged(level, pos, Blocks.AIR, null, false);
        level.updateNeighborsAt(pos, state.getBlock());

        if(level instanceof ServerLevel){
            state.handleNeighborChanged(level, pos, Blocks.AIR, null, false);
            level.updateNeighborsAt(pos, state.getBlock());
        }
        return InteractionResult.SUCCESS;
    }

//    static boolean setWireState(ServerLevel level, BlockPos pos, BlockState state, boolean updateNeighborShapes) {
//        int y = pos.getY();
//        if (y >= level.getMinY() && y <= level.getMaxY()) {
//            int x = pos.getX();
//            int z = pos.getZ();
//            int index = level.getSectionIndex(y);
//            ChunkAccess chunk = level.getChunk(x >> 4, z >> 4, ChunkStatus.FULL, true);
//            LevelChunkSection section = chunk.getSections()[index];
//            if (section == null) {
//                return false;
//            } else {
//                BlockState prevState = section.setBlockState(x & 15, y & 15, z & 15, state);
//
//                if (state == prevState) {
//                    return false;
//                } else {
//                    level.getChunkSource().blockChanged(pos);
//                    chunk.markUnsaved();
//                    if (updateNeighborShapes) {
//                        prevState.updateIndirectNeighbourShapes(level, pos, 2);
//                        state.updateNeighbourShapes(level, pos, 2);
//                        state.updateIndirectNeighbourShapes(level, pos, 2);
//                    }
//                    return true;
//                }
//            }
//        } else {
//            return false;
//        }
//    }
}