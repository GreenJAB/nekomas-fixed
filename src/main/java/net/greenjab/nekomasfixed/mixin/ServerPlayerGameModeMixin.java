package net.greenjab.nekomasfixed.mixin;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.greenjab.nekomasfixed.registry.block.TerracottaDecoratedPotBlock;
import net.greenjab.nekomasfixed.registry.block.entity.TerracottaDecoratedPotBlockEntity;
import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Inject(at = @At("HEAD"), method = "useItemOn", cancellable = true)
    private void interactBlock(ServerPlayer player, Level level, ItemStack stack, InteractionHand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> info) {
        InteractionResult result = UseBlockCallback.EVENT.invoker().interact(player, level, hand, blockHitResult);

        Direction hitDir = blockHitResult.getDirection();
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);

        if (stack.is(ModTags.CAN_BE_ENGRAVED) && !stack.isEmpty()
                && level.getBlockEntity(pos) instanceof TerracottaDecoratedPotBlockEntity decoratedPot
                && !this.isEmptySide(hitDir, decoratedPot, state) && !stack.equals(Objects.requireNonNull(decoratedPot.getEngravingDecorations().getItemEngravedAt(hitDir, state.getValue(TerracottaDecoratedPotBlock.HORIZONTAL_FACING))).getDefaultInstance())) {

            if (hitDir.getAxis().isHorizontal() && player.isCrouching()) {
                PotEngravingDecoration newDeco = decoratedPot.getEngravingDecorations()
                        .engraveSideFacing(hitDir, state.getValue(TerracottaDecoratedPotBlock.HORIZONTAL_FACING), stack.getItem());
                decoratedPot.engravingDecorations = newDeco;
                decoratedPot.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                stack.shrink(1);

                if (level.isClientSide()) {
                    player.swing(player.getUsedItemHand(), true);
                }

                info.setReturnValue(InteractionResult.SUCCESS);
                info.cancel();
                return;
            }
        }
        if (result != InteractionResult.PASS) {
            info.setReturnValue(result);
            info.cancel();
        }
    }


    private boolean isEmptySide(Direction ofHit, TerracottaDecoratedPotBlockEntity blockEntity, BlockState state) {
        Direction potFacing = state.getValue(TerracottaDecoratedPotBlock.HORIZONTAL_FACING);
        String sideName = getSideNameFromHit(ofHit, potFacing);
        PotEngravingDecoration decorations = blockEntity.getEngravingDecorations();

        if (sideName == null) return true;
        return isSideEmpty(blockEntity.getDecorations(), sideName);
    }

    private static String getSideNameFromHit(Direction hitFace, Direction potFacing) {
        if (hitFace.getAxis().isVertical()) {
            return null;
        }
        switch (potFacing) {
            case NORTH:
                if (hitFace == Direction.NORTH) return "front";
                if (hitFace == Direction.SOUTH) return "back";
                if (hitFace == Direction.WEST)  return "left";
                if (hitFace == Direction.EAST)  return "right";
                break;
            case SOUTH:
                if (hitFace == Direction.SOUTH) return "front";
                if (hitFace == Direction.NORTH) return "back";
                if (hitFace == Direction.EAST)  return "left";
                if (hitFace == Direction.WEST)  return "right";
                break;
            case EAST:
                if (hitFace == Direction.EAST)  return "front";
                if (hitFace == Direction.WEST)  return "back";
                if (hitFace == Direction.NORTH) return "left";
                if (hitFace == Direction.SOUTH) return "right";
                break;
            case WEST:
                if (hitFace == Direction.WEST)  return "front";
                if (hitFace == Direction.EAST)  return "back";
                if (hitFace == Direction.SOUTH) return "left";
                if (hitFace == Direction.NORTH) return "right";
                break;
            default: break;
        }
        return null;
    }

    private static boolean isSideEmpty(PotDecorations decorations, String side) {
        if (decorations == null || side == null) return true;
        return switch (side.toLowerCase()) {
            case "back"  -> decorations.back().isEmpty();
            case "left"  -> decorations.left().isEmpty();
            case "right" -> decorations.right().isEmpty();
            case "front" -> decorations.front().isEmpty();
            default -> true;
        };
    }
}
