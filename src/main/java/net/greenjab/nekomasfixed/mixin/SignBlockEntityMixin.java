package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.target_access.SignAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin extends BlockEntity implements SignAccess {

	@Unique
	private boolean nekomasfixed$hideBackground = false;

	public SignBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Inject(at = @At("HEAD"), method = "writeData")
	private void writeData(WriteView view, CallbackInfo ci) {
		if (this.nekomasfixed$hideBackground) view.putBoolean("nekomasfixed.hide_background", true);
	}

	@Inject(at = @At("HEAD"), method = "readData")
	private void readData(ReadView view, CallbackInfo ci) {
		nekomasfixed$hideBackground = view.getBoolean("nekomasfixed.hide_background", false);
	}

	@Override
	public boolean nekomasfixed$isBackgroundHidden() {
		return nekomasfixed$hideBackground;
	}

	@Override
	public void nekomasfixed$setHideBackground(boolean hideBackground) {
		nekomasfixed$hideBackground = hideBackground;
		markDirty();
	}

}