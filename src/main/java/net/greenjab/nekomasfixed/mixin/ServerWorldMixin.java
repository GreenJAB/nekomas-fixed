package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.chunk.WorldChunk;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerWorld.class)
abstract
class ServerWorldMixin {
    @Shadow
    public abstract @Nullable ServerPlayerEntity getRandomAlivePlayer();

    @Inject(method = "tickThunder", at = @At("HEAD"), cancellable = true)
    private void tickThunder(WorldChunk chunk, CallbackInfo ci) {
        ChunkPos chunkPos = chunk.getPos();
        ServerWorld serverWorld = (ServerWorld) (Object)this;
        boolean bl = serverWorld.isRaining();
        Profiler profiler = Profilers.get();
        profiler.push("thunder");
        ServerPlayerEntity player = this.getRandomAlivePlayer();
        if (bl && serverWorld.isThundering() && serverWorld.random.nextInt(10000) == 0 &&
               player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.COPPER_HELMET)&&
               player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.COPPER_CHESTPLATE)&&
               player.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.COPPER_LEGGINGS)&&
               player.getEquippedStack(EquipmentSlot.FEET).isOf(Items.COPPER_BOOTS)

        )
        {
            BlockPos blockPos = Objects.requireNonNull(player).getBlockPos();
            if (serverWorld.hasRain(blockPos)) {
                LightningEntity lightningEntity = (LightningEntity)EntityType.LIGHTNING_BOLT.create(serverWorld, SpawnReason.EVENT);
                if (lightningEntity != null) {
                    lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                    StatusEffectInstance st = new StatusEffectInstance(StatusEffects.SPEED, 10*20, 4, false, false, false);
                    this.getRandomAlivePlayer().addStatusEffect(st);
                    serverWorld.spawnEntity(lightningEntity);
                }
            }
        }

        profiler.pop();
    }
}
