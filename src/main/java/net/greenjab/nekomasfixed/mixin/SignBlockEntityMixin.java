package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.target_access_class.SignAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin extends BlockEntity implements SignAccess {

	@Shadow protected abstract void markUpdated();

	@Unique
	private boolean nekomasfixed$hideBackground = false;

	public SignBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Inject(at = @At("HEAD"), method = "saveAdditional")
	private void saveAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
		if (this.nekomasfixed$hideBackground) {
			tag.putBoolean("nekomasfixed.hide_background", true);
		}
	}

	@Inject(at = @At("HEAD"), method = "loadAdditional")
	private void loadAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
		nekomasfixed$hideBackground = tag.contains("nekomasfixed.hide_background") && tag.getBoolean("nekomasfixed.hide_background");
	}

	@Override
	public boolean nekomasfixed$isBackgroundHidden() {
		return nekomasfixed$hideBackground;
	}

	@Override
	public void nekomasfixed$setHideBackground(boolean hideBackground) {
		nekomasfixed$hideBackground = hideBackground;
		markUpdated();
	}
}