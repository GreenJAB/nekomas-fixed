package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

	@Inject(method="hasGlint", at = @At(value = "HEAD"), cancellable = true)
	private void clockHasStoredTime(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.hasChangedComponent(OtherRegistry.STORED_TIME)) cir.setReturnValue(true);
	}

	@Inject(method="usageTick", at=@At("HEAD"))
	private void customUsageTick(
			World world,
			LivingEntity user,
			ItemStack stack,
			int remainingUseTicks,
			CallbackInfo ci
	) {
		if (!world.isClient() || !stack.isOf(Items.GOAT_HORN)) return;

		if (user.age % 3 == 0 && !world.isClient() ) {
            ServerWorld serverWorld = (ServerWorld) world;
			if(serverWorld.getStructureAccessor().getStructureContaining(user.getBlockPos(), StructureTags.VILLAGE).hasChildren()) {
				serverWorld.spawnParticles(
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