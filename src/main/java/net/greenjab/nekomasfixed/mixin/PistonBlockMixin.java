package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.RedstoneView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
    @Inject(method = "shouldExtend", at = @At("HEAD"), cancellable = true)
    protected void powerPiston(RedstoneView world, BlockPos pos, Direction pistonFace, CallbackInfoReturnable<Boolean> cir) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(new GlobalPos(((World)world).getRegistryKey(), pos))) {
            cir.setReturnValue(true);
        }
    }
}
