package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.target_access.DecoratedPotAccess;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mixin(DecoratedPotBlockEntity.class)
public class DecoratedPotBlockEntityMixin implements DecoratedPotAccess {
    @Unique
    private List<Boolean> nekomasfixed$sherdGlowOverrides = nekomasfixed$getEmptyGlowOverrides();

    @Unique
    private static List<Boolean> nekomasfixed$getEmptyGlowOverrides() {
        return List.of(false, false, false, false);
    }

    @Override
    public boolean nekomasfixed$getSherdGlow(int index) {
        return nekomasfixed$sherdGlowOverrides.get(index);
    }

    @Override
    public void nekomasfixed$setSherdGlow(int index, boolean glowing) {
        nekomasfixed$sherdGlowOverrides = new ArrayList<>(nekomasfixed$sherdGlowOverrides);
        nekomasfixed$sherdGlowOverrides.set(index, glowing);
    }

    @Inject(at = @At("TAIL"), method = "writeData")
    private void writeData(WriteView view, CallbackInfo ci) {
        if (
                nekomasfixed$getSherdGlow(0) ||
                nekomasfixed$getSherdGlow(1) ||
                nekomasfixed$getSherdGlow(2) ||
                nekomasfixed$getSherdGlow(3)
        ) {
            view.putByteArray(SHERD_GLOW_OVERRIDES_KEY, new byte[]{
                    (byte) (nekomasfixed$getSherdGlow(0) ? 1 : 0),
                    (byte) (nekomasfixed$getSherdGlow(1) ? 1 : 0),
                    (byte) (nekomasfixed$getSherdGlow(2) ? 1 : 0),
                    (byte) (nekomasfixed$getSherdGlow(3) ? 1 : 0)
            });
        }
    }

    @Inject(at = @At("TAIL"), method = "readData")
    private void readData(ReadView view, CallbackInfo ci) {
        if (view != null) {
            Optional<byte[]> optionalOverrides = view.getOptionalByteArray(SHERD_GLOW_OVERRIDES_KEY);
            if (optionalOverrides.isPresent()) {
                byte[] overrides = optionalOverrides.get();
                nekomasfixed$sherdGlowOverrides = IntStream.range(0, 4).mapToObj(i -> overrides[i] != 0).collect(Collectors.toList());
            }
        } else {
            nekomasfixed$sherdGlowOverrides = nekomasfixed$getEmptyGlowOverrides();
        }
    }

    @Inject(at = @At("TAIL"), method = "addComponents")
    protected void addComponents(ComponentMap.Builder componentMapBuilder, CallbackInfo ci) {
        componentMapBuilder.add(ComponentRegistry.SHERD_GLOW_OVERRIDES, nekomasfixed$sherdGlowOverrides);
    }

    @Inject(at = @At("TAIL"), method = "readComponents")
    protected void readComponents(ComponentsAccess components, CallbackInfo ci) {
        this.nekomasfixed$sherdGlowOverrides = components.get(ComponentRegistry.SHERD_GLOW_OVERRIDES);
        if (nekomasfixed$sherdGlowOverrides == null) {
            nekomasfixed$sherdGlowOverrides = nekomasfixed$getEmptyGlowOverrides();
        }
    }

    @Inject(at = @At("TAIL"), method = "removeFromCopiedStackData")
    protected void removeFromCopiedStackData(WriteView view, CallbackInfo ci) {
        view.remove(SHERD_GLOW_OVERRIDES_KEY);
    }
}
