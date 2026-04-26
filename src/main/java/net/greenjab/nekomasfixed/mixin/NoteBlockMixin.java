package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.GoatHornBlock;
import net.greenjab.nekomasfixed.registry.block.entity.EndermanHeadBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Instrument;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;


@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {

	@Shadow
	protected abstract void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player);

	@Inject(method = "onSyncedBlockEvent", at = @At("HEAD"), cancellable = true)
	private void goatHornPlay(BlockState state, World world, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> cir) {

		for (Direction dir : Direction.values()) {
			if (world.getBlockState(pos.offset(dir)).isOf(BlockRegistry.GOAT_HORN)) {

				BlockState goatHornState = world.getBlockState(pos.offset(dir));
				var key = GoatHornType.getInstrument(goatHornState.get(GoatHornBlock.HORN));

				RegistryEntry<Instrument> entry = world.getRegistryManager()
						.getOrThrow(net.minecraft.registry.RegistryKeys.INSTRUMENT)
						.getOrThrow(key);

				world.playSound(
						null,
						pos,
						entry.value().soundEvent().value(),
						SoundCategory.RECORDS,
						3.0F,
						1.0F
				);

				if (!world.isClient()) {
					ServerWorld serverWorld = (ServerWorld) world;
					Random random = new Random();

					serverWorld.spawnParticles(
							ParticleTypes.NOTE,
							pos.offset(dir).getX(),
							pos.offset(dir).getY() + 1 + random.nextDouble(0.5),
							pos.offset(dir).getZ(),
							2,
							0.1, 0.1, 0.1,
							0
					);
				}

			}
		}
	}

	@Inject(method="getCustomSound", at = @At(value = "HEAD"), cancellable = true)
	private void endermanHeadSound(World world, BlockPos pos, CallbackInfoReturnable<Identifier> cir) {
		if (world.getBlockEntity(pos.up()) instanceof EndermanHeadBlockEntity) cir.setReturnValue(SoundEvents.ENTITY_ENDERMAN_AMBIENT.id());
	}
}