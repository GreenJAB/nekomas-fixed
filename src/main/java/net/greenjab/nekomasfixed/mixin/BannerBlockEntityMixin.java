package net.greenjab.nekomasfixed.mixin;
import net.greenjab.nekomasfixed.target_access_class.BannerAccess;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.BannerEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerBlockEntity.class)
public class BannerBlockEntityMixin extends BlockEntity implements BannerAccess {
	@Unique
	private BannerEffects nekomasfixed$effects = BannerEffects.empty();

	public BannerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public BannerEffects nekomasfixed$getBannerEffects() {
		return nekomasfixed$effects;
	}

	@Inject(at = @At("HEAD"), method = "saveAdditional")
	private void saveAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
		if (nekomasfixed$effects.isGlowing()) {
			tag.putBoolean("nekomasfixed.glowing", true);
		}
		if (nekomasfixed$effects.isBackgroundHidden()) {
			tag.putBoolean("nekomasfixed.hide_background", true);
		}
	}

	@Inject(at = @At("HEAD"), method = "loadAdditional")
	private void loadAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
		nekomasfixed$effects.setHideBackground(tag.contains("nekomasfixed.hide_background") && tag.getBoolean("nekomasfixed.hide_background"));
		nekomasfixed$effects.setGlowing(tag.contains("nekomasfixed.glowing") && tag.getBoolean("nekomasfixed.glowing"));
	}

	@Inject(at = @At("TAIL"), method = "applyImplicitComponents")
	private void readComponents(DataComponentInput componentInput, CallbackInfo ci) {
		nekomasfixed$effects = componentInput.getOrDefault(ComponentRegistry.BANNER_EFFECTS, BannerEffects.empty());
	}

	@Inject(at = @At("TAIL"), method = "collectImplicitComponents")
	private void collectImplicitComponents(DataComponentMap.Builder components, CallbackInfo ci) {
		components.set(ComponentRegistry.BANNER_EFFECTS, nekomasfixed$getBannerEffects());
	}

	@Inject(at = @At("TAIL"), method = "removeComponentsFromTag")
	private void removeComponentsFromTag(CompoundTag tag, CallbackInfo ci) {
		tag.remove("nekomasfixed.glowing");
		tag.remove("nekomasfixed.hide_background");
	}
}