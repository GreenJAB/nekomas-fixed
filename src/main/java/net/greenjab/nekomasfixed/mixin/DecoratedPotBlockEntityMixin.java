package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.target_access_class.DecoratedPotAccess;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

@Mixin(DecoratedPotBlockEntity.class)
public class DecoratedPotBlockEntityMixin implements DecoratedPotAccess {
    @Unique
    private List<Boolean> nekomasfixed$sherdGlowOverrides = nekomasfixed$getEmptyGlowOverrides();

    @Unique
    private static List<Boolean> nekomasfixed$getEmptyGlowOverrides() {
        return Stream.of(false, false, false, false).collect(Collectors.toList());
    }

    @Override
    public boolean nekomasfixed$getSherdGlow(int index) {
        return nekomasfixed$sherdGlowOverrides.get(index);
    }

    @Override
    public void nekomasfixed$setSherdGlow(int index, boolean glowing) {
        nekomasfixed$sherdGlowOverrides.set(index, glowing);
    }

    @Inject(at = @At("TAIL"), method = "saveAdditional")
    private void writeNbt(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        if (
                nekomasfixed$getSherdGlow(0) ||
                nekomasfixed$getSherdGlow(1) ||
                nekomasfixed$getSherdGlow(2) ||
                nekomasfixed$getSherdGlow(3)
        ) {
            nbt.putByteArray(SHERD_GLOW_OVERRIDES_KEY, nekomasfixed$sherdGlowOverrides.stream().map(b -> b ? (byte) 1 : (byte) 0).toList());
        }
    }

    @Inject(at = @At("TAIL"), method = "loadAdditional")
    private void readNbt(CompoundTag nbt, HolderLookup.Provider registryLookup, CallbackInfo ci) {
        if (nbt != null && nbt.contains(SHERD_GLOW_OVERRIDES_KEY, CompoundTag.TAG_BYTE_ARRAY)) {
            byte[] overrides = nbt.getByteArray(SHERD_GLOW_OVERRIDES_KEY);
            nekomasfixed$sherdGlowOverrides = IntStream.range(0, 4).mapToObj(i -> overrides[i] != 0).collect(Collectors.toList());
        } else {
            nekomasfixed$sherdGlowOverrides = nekomasfixed$getEmptyGlowOverrides();
        }
    }

    @Inject(at = @At("TAIL"), method = "collectImplicitComponents")
    protected void addComponents(DataComponentMap.Builder componentMapBuilder, CallbackInfo ci) {
        componentMapBuilder.set(ComponentRegistry.SHERD_GLOW_OVERRIDES, nekomasfixed$sherdGlowOverrides);
    }

    @Inject(at = @At("TAIL"), method = "applyImplicitComponents")
    protected void readComponents(BlockEntity.DataComponentInput components, CallbackInfo ci) {
        this.nekomasfixed$sherdGlowOverrides = components.get(ComponentRegistry.SHERD_GLOW_OVERRIDES);
        if (nekomasfixed$sherdGlowOverrides == null) {
            nekomasfixed$sherdGlowOverrides = nekomasfixed$getEmptyGlowOverrides();
        }
    }

    @Inject(at = @At("TAIL"), method = "removeComponentsFromTag")
    protected void removeFromCopiedStackNbt(CompoundTag nbt, CallbackInfo ci) {
        nbt.remove(SHERD_GLOW_OVERRIDES_KEY);
    }
}
