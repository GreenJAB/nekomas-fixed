package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.mixin.accessor.SnifferEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sniffer.class)
public class SnifferEntityMixin {

    @Inject(method = "dropSeed", at = @At("HEAD"), cancellable = true)
    private void dropCustomLoot(CallbackInfo ci) {
        Sniffer sniffer = (Sniffer)(Object)this;
        SnifferEntityAccessor accessor = (SnifferEntityAccessor) sniffer;

        Level world = sniffer.level();
        if (world instanceof ServerLevel serverWorld) {
            if (sniffer.getEntityData().get(SnifferEntityAccessor.getFinishDigTime()) == sniffer.tickCount) {
                BlockPos blockPos = accessor.invokeGetDigPos();

                Biome biome = serverWorld.getBiome(blockPos).value();
                String biomeName = serverWorld.getBiome(blockPos)
                        .unwrapKey()
                        .map(key -> key.identifier().toString())
                        .orElse("unknown");
                float temperature = biome.getBaseTemperature();

                ResourceKey<LootTable> lootTableKey;

                if (temperature <= 0.15f) {
                    lootTableKey = ResourceKey.create(Registries.LOOT_TABLE,
                            Identifier.withDefaultNamespace("gameplay/sniffer_digging_snowy"));
                }
                else {
                    if (biomeName.contains("des")) {
                        lootTableKey = ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.withDefaultNamespace("gameplay/sniffer_digging_desert"));
                    }
                    else if (biomeName.contains("badlands")) {
                        lootTableKey = ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.withDefaultNamespace("gameplay/sniffer_digging_badlands"));
                    }
                    else {
                        lootTableKey = BuiltInLootTables.SNIFFER_DIGGING;
                    }
                }

                sniffer.dropFromGiftLootTable(serverWorld, lootTableKey, (serverWorldx, itemStack) -> {
                    ItemEntity itemEntity = new ItemEntity(
                            sniffer.level(),
                            blockPos.getX(),
                            blockPos.getY(),
                            blockPos.getZ(),
                            itemStack
                    );
                    itemEntity.setDefaultPickUpDelay();
                    serverWorldx.addFreshEntity(itemEntity);
                });


                sniffer.playSound(SoundEvents.SNIFFER_DROP_SEED, 1.0F, 1.0F);
                ci.cancel();
            }
        }
    }
}