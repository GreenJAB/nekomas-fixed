package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.entity.EndermanHeadBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {

	@Inject(method="getCustomSoundId", at = @At(value = "HEAD"), cancellable = true)
	private void endermanHeadSound(Level world, BlockPos pos, CallbackInfoReturnable<Identifier> cir) {
		if (world.getBlockEntity(pos.above()) instanceof EndermanHeadBlockEntity) cir.setReturnValue(SoundEvents.ENDERMAN_AMBIENT.location());
	}
}