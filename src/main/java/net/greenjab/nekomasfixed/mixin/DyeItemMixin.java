package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.greenjab.nekomasfixed.util.ModColors.*;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Inject(method = "tryApplyToSign", at = @At("RETURN"), cancellable = true)
    private void changeDye(Level level, SignBlockEntity sign, boolean isFrontText, ItemStack item, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (item.is(ItemRegistry.AMBER_DYE)) {
            applyDye(sign, isFrontText, AMBER.getColor());
            cir.setReturnValue(true);
        }
        if (item.is(ItemRegistry.AQUA_DYE)) {
            applyDye(sign, isFrontText, AQUA.getColor());
            cir.setReturnValue(true);
        }
        if (item.is(ItemRegistry.INDIGO_DYE)) {
            applyDye(sign, isFrontText, INDIGO.getColor());
            cir.setReturnValue(true);
        }

        if (item.is(ItemRegistry.MAROON_DYE)) {
            applyDye(sign, isFrontText, MAROON.getColor());
            cir.setReturnValue(true);
        }
    }

    @Unique
    private void applyDye(SignBlockEntity sign, boolean front, int color) {
        SignText signText = sign.getText(front);
        for (int i = 0; i < 4; i++) {
            Component line = signText.getMessage(i, false);
            MutableComponent newLine = line.plainCopy();
            newLine.setStyle(line.getStyle().withColor(color));
            signText = signText.setMessage(i, newLine, newLine);
        }
        sign.setText(signText, front);
        sign.setChanged();
        assert sign.getLevel() != null;
        sign.getLevel().sendBlockUpdated(
                sign.getBlockPos(),
                sign.getBlockState(),
                sign.getBlockState(),
                Block.UPDATE_ALL
        );
    }

}
