package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.MessyBedAccessor;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PoiTypes.class)
public class PoiTypesMixin {
    @Inject(method = "forState", at = @At("HEAD"), cancellable = true)
    private static void ignoreMessyBeds(BlockState state, CallbackInfoReturnable<Optional<Holder<PoiType>>> cir) {
        if (state.getBlock() instanceof BedBlock && state.getValue(MessyBedAccessor.MESSY)) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
