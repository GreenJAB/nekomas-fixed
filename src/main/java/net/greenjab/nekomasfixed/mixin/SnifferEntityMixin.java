package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnifferEntity.class)
public class SnifferEntityMixin {

    @Inject(method = "dropSeeds", at = @At("HEAD"), cancellable = true)
    private void dropCustomLoot(CallbackInfo ci) {
        SnifferEntity sniffer = (SnifferEntity)(Object)this;
        net.greenjab.nekomasfixed.mixin.SnifferEntityAccessor accessor = (net.greenjab.nekomasfixed.mixin.SnifferEntityAccessor) sniffer;

        World world = sniffer.getEntityWorld();
        if (world instanceof ServerWorld serverWorld) {
            if (sniffer.getDataTracker().get(net.greenjab.nekomasfixed.mixin.SnifferEntityAccessor.getFinishDigTime()) == sniffer.age) {
                BlockPos blockPos = accessor.invokeGetDigPos();
                Biome biome = serverWorld.getBiome(blockPos).value();
                System.out.println(biome.toString());
                sniffer.forEachGiftedItem(serverWorld, LootTables.SNIFFER_DIGGING_GAMEPLAY, (serverWorldx, itemStack) -> {
                    ItemEntity itemEntity = new ItemEntity(
                            sniffer.getEntityWorld(),
                            blockPos.getX(),
                            blockPos.getY(),
                            blockPos.getZ(),
                            itemStack
                    );
                    itemEntity.setToDefaultPickupDelay();
                    serverWorldx.spawnEntity(itemEntity);
                });

                sniffer.playSound(SoundEvents.ENTITY_SNIFFER_DROP_SEED, 1.0F, 1.0F);
                ci.cancel();
            }
        }
    }
}