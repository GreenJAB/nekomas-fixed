package net.greenjab.nekomasfixed.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RedstoneView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;


@Mixin(RedstoneView.class)
public interface RedstoneViewMixin {

	@Inject(method="getStrongRedstonePower", at = @At(value = "HEAD"))
	private void throwableBrick(BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir) {
		if (Objects.equals(pos, new BlockPos(38, 72, -91))) {
			System.out.println("hi");
		}
	}

}