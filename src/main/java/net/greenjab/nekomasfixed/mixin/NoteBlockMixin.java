package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.entity.EndermanHeadBlockEntity;
import net.minecraft.block.NoteBlock;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {

	@Inject(method="getCustomSound", at = @At(value = "HEAD"), cancellable = true)
	private void endermanHeadSound(World world, BlockPos pos, CallbackInfoReturnable<Identifier> cir) {
		if (world.getBlockEntity(pos.up()) instanceof EndermanHeadBlockEntity) cir.setReturnValue(SoundEvents.ENTITY_ENDERMAN_AMBIENT.id());
	}
}