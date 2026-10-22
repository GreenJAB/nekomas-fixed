package net.greenjab.nekomasfixed.mixin;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.greenjab.nekomasfixed.registry.block.TerracottaDecoratedPotBlock;
import net.greenjab.nekomasfixed.registry.block.entity.TerracottaDecoratedPotBlockEntity;
import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Inject(at = @At("HEAD"), method = "useItemOn", cancellable = true)
    private void interactBlock(ServerPlayer player, Level level, ItemStack stack, InteractionHand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> info) {
        InteractionResult result = UseBlockCallback.EVENT.invoker().interact(player, level, hand, blockHitResult);

        Direction hitDir = blockHitResult.getDirection();
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);

        if (stack.is(Items.SHEARS) && player.isCrouching() && !stack.isEmpty()
                && level.getBlockEntity(pos) instanceof TerracottaDecoratedPotBlockEntity decoratedPot
                && !TerracottaDecoratedPotBlock.isEmptySide(hitDir, decoratedPot, state)) {
            PotEngravingDecoration engraved = decoratedPot.getEngravingDecorations();
            Direction facing = state.getValue(TerracottaDecoratedPotBlock.HORIZONTAL_FACING);
            String hitSide = TerracottaDecoratedPotBlock.getSideNameFromHit(hitDir, facing);

            Optional<Item> itemToDrop = switch (hitSide) {
                case "front" -> engraved.front();
                case "back" -> engraved.back();
                case "left" -> engraved.left();
                case "right" -> engraved.right();
                default -> Optional.empty();
            };

            if (itemToDrop.isPresent() && itemToDrop.get() != Items.AIR) {
                PotEngravingDecoration newEngraved = engraved.removeEngraving(hitSide);
                decoratedPot.engravingDecorations = newEngraved;
                ItemStack dropStack = itemToDrop.get().getDefaultInstance();
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, dropStack));
                stack.setDamageValue(stack.getDamageValue() - 1);
                decoratedPot.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

                if (level.isClientSide()) {
                    player.swing(player.getUsedItemHand(), SwingAnimation.DEFAULT, true);
                }
                info.setReturnValue(InteractionResult.SUCCESS);
                info.cancel();
                return;
            }
        }

        if (stack.getItem() instanceof BlockItem blockItem && player.isCrouching()
                && blockItem.getBlock().defaultBlockState().is(ModTags.CAN_BECOME_POT_FACE)
                && !stack.isEmpty()
                && level.getBlockEntity(pos) instanceof TerracottaDecoratedPotBlockEntity decoratedPot) {
            PotFaceDecoration newFaceDecoration = new PotFaceDecoration(Optional.of(blockItem.getBlock().defaultBlockState()));
            if (!decoratedPot.getPotFace().equals(newFaceDecoration)) {
                ItemStack oldFaceStack = decoratedPot.getPotFace().getSafeBlock().asItem().getDefaultInstance();
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, oldFaceStack));
                decoratedPot.faceDecoration = newFaceDecoration;
                decoratedPot.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                stack.shrink(1);

                if (level.isClientSide()) {
                    player.swing(player.getUsedItemHand(), SwingAnimation.DEFAULT, true);
                }
                info.setReturnValue(InteractionResult.SUCCESS);
                info.cancel();
                return;
            }
        }

        if (stack.is(ModTags.CAN_BE_ENGRAVED) && !stack.isEmpty()
                && level.getBlockEntity(pos) instanceof TerracottaDecoratedPotBlockEntity decoratedPot
                && !TerracottaDecoratedPotBlock.isEmptySide(hitDir, decoratedPot, state)) {

            Direction facing = state.getValue(TerracottaDecoratedPotBlock.HORIZONTAL_FACING);
            Item engravedItem = decoratedPot.getEngravingDecorations().getItemEngravedAt(hitDir, facing);

            if (engravedItem != Items.AIR && engravedItem == stack.getItem()) {
                info.setReturnValue(InteractionResult.FAIL);
                info.cancel();
                return;
            }

            if (hitDir.getAxis().isHorizontal() && player.isCrouching()) {
                PotEngravingDecoration newDeco = decoratedPot.getEngravingDecorations().engraveSideFacing(hitDir, facing, stack.getItem());
                decoratedPot.engravingDecorations = newDeco;
                decoratedPot.setChanged();
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                stack.shrink(1);

                if (level.isClientSide()) {
                    player.swing(player.getUsedItemHand(), SwingAnimation.DEFAULT,true);
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
}