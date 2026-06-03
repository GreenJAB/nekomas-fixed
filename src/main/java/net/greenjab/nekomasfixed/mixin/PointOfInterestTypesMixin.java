package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.MessyBedAccessor;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PointOfInterestTypes.class)
public class PointOfInterestTypesMixin {
    @Inject(method = "getTypeForState", at = @At("HEAD"), cancellable = true)
    private static void nekomasfixed$ignoreMessyBeds(BlockState state, CallbackInfoReturnable<Optional<RegistryEntry<PointOfInterestType>>> cir) {
        if (state.contains(MessyBedAccessor.IS_MESSY) && state.get(MessyBedAccessor.IS_MESSY) && state.getBlock() instanceof BedBlock) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
