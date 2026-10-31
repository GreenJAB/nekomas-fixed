package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.BannerEffects;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.target_access.BannerAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerBlockEntity.class)
public class BannerBlockEntityMixin extends BlockEntity implements BannerAccess {
	@Unique
	private BannerEffects nekomasfixed$effects = BannerEffects.EMPTY;

	public BannerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public BannerEffects nekomasfixed$getBannerEffects() {
		return nekomasfixed$effects;
	}

	@Override
	public void nekomasfixed$setBannerEffects(BannerEffects effects) {
		nekomasfixed$effects = effects;
	}

	@Inject(at = @At("HEAD"), method = "writeData")
	private void writeData(WriteView view, CallbackInfo ci) {
		if (nekomasfixed$effects.isGlowing()) {
			view.putBoolean("nekomasfixed.glowing", true);
		}
		if (nekomasfixed$effects.isBackgroundHidden()) {
			view.putBoolean("nekomasfixed.hide_background", true);
		}
	}

	@Inject(at = @At("HEAD"), method = "readData")
	private void readData(ReadView view, CallbackInfo ci) {
		nekomasfixed$effects = new BannerEffects(
				view.getBoolean("nekomasfixed.glowing", false),
				view.getBoolean("nekomasfixed.hide_background", false)
		);
	}

	@Inject(at = @At("TAIL"), method = "readComponents")
	private void readComponents(ComponentsAccess components, CallbackInfo ci) {
		nekomasfixed$effects = components.getOrDefault(ComponentRegistry.BANNER_EFFECTS, BannerEffects.EMPTY);
	}

	@Inject(at = @At("TAIL"), method = "addComponents")
	private void addComponents(ComponentMap.Builder componentMapBuilder, CallbackInfo ci) {
		componentMapBuilder.add(ComponentRegistry.BANNER_EFFECTS, nekomasfixed$getBannerEffects());
	}

	@Inject(at = @At("TAIL"), method = "removeFromCopiedStackData")
	private void removeFromCopiedStackData(WriteView view, CallbackInfo ci) {
		view.remove("nekomasfixed.glowing");
		view.remove("nekomasfixed.hide_background");
	}
}