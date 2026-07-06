package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

	@Inject(method="isFoil", at = @At(value = "HEAD"), cancellable = true)
	private void clockHasStoredTime(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.hasNonDefault(ComponentRegistry.STORED_TIME)) cir.setReturnValue(true);
	}

	@Inject(method="onUseTick", at=@At("HEAD"))
	private void customUsageTick(
			Level world,
			LivingEntity user,
			ItemStack stack,
			int remainingUseTicks,
			CallbackInfo ci
	) {
		if (!world.isClientSide() || !stack.is(Items.GOAT_HORN)) return;

		if (user.tickCount % 3 == 0 && !world.isClientSide() ) {
            ServerLevel serverWorld = (ServerLevel) world;
			if(serverWorld.structureManager().getStructureWithPieceAt(user.blockPosition(), StructureTags.VILLAGE).isValid()) {
				serverWorld.sendParticles(
						ParticleTypes.POOF,
						user.getX(),
						user.getY() + 0.5,
						user.getZ(),
						20,
						0.4, 0.2, 0.4,
						0.05
				);
			}
		}
	}
}