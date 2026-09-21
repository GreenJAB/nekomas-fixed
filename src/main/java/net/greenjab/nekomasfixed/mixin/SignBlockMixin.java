package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.target_access_class.SignAccess;
import net.greenjab.nekomasfixed.registry.registries.SoundEventRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlock.class)
public abstract class SignBlockMixin {
    @Shadow protected abstract boolean otherPlayerIsEditingSign(Player player, SignBlockEntity blockEntity);

    @Inject(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), cancellable = true)
    private void onUse(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<ItemInteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.PHANTOM_MEMBRANE)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (
                    blockEntity instanceof SignBlockEntity signBlockEntity &&
                    signBlockEntity instanceof SignAccess nekomasfixedSign &&
                    !world.isClientSide &&
                    !nekomasfixedSign.nekomasfixed$isBackgroundHidden() &&
                    !this.otherPlayerIsEditingSign(player, signBlockEntity) &&
                    player.mayBuild()
            ) {
                nekomasfixedSign.nekomasfixed$setHideBackground(true);
                world.playSound(null, signBlockEntity.getBlockPos(), SoundEventRegistry.PHANTOM_MEMBRANE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                signBlockEntity.setChanged();
                world.sendBlockUpdated(pos, state, signBlockEntity.getBlockState(), 0);

                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }

                world.gameEvent(GameEvent.BLOCK_CHANGE, signBlockEntity.getBlockPos(), GameEvent.Context.of(player, signBlockEntity.getBlockState()));
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                cir.setReturnValue(ItemInteractionResult.SUCCESS);
            }
        }
    }
}
