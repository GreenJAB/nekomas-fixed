package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.greenjab.nekomasfixed.util.ModColors.*;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Inject(method = "useOnSign", at = @At("HEAD"), cancellable = true)
    private void changeDye(World world, SignBlockEntity signBlockEntity, boolean front, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = player.getStackInHand(player.getActiveHand());

        //AMBER
        if (stack.isOf(ItemRegistry.AMBER_DYE)) {
            applyDye(signBlockEntity, front, AMBER.getColor());
            cir.setReturnValue(true);
        }

        //AQUA
        if (stack.isOf(ItemRegistry.AQUA_DYE)) {
            applyDye(signBlockEntity, front, AQUA.getColor());
            cir.setReturnValue(true);
        }

        //INDIGO
        if (stack.isOf(ItemRegistry.INDIGO_DYE)) {
            applyDye(signBlockEntity, front, INDIGO.getColor());
            cir.setReturnValue(true);
        }

        //CRIMSON
        if (stack.isOf(ItemRegistry.CRIMSON_DYE)) {
            applyDye(signBlockEntity, front, CRIMSON.getColor());
            cir.setReturnValue(true);
        }

    }

    //Method to applyDye - DRY priciple
    private void applyDye(SignBlockEntity sign, boolean front, int color) {
        var signText = sign.getText(front);
        for (int i = 0; i < 4; i++) {
            Text line = signText.getMessage(i, false);
            MutableText newLine = line.copyContentOnly();
            newLine.setStyle(line.getStyle().withColor(color));
            signText = signText.withMessage(i, newLine, newLine);
        }
        sign.setText(signText, front);
        sign.markDirty();
        sign.getWorld().updateListeners(
                sign.getPos(),
                sign.getCachedState(),
                sign.getCachedState(),
                Block.NOTIFY_ALL
        );
    }

}
