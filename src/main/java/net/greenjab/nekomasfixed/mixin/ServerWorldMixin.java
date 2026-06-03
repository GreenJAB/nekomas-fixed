package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow
    public abstract @Nullable ServerPlayerEntity getRandomAlivePlayer();

    @Inject(method = "tick", at = @At("HEAD"))
    private void depowerRedstoneStruckBlocks(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerWorld world = ((ServerWorld)(Object)this);
        HashMap<GlobalPos, Long> STRUCK_WIRES_COPY = new HashMap<>(RedstoneStrikerItem.STRUCK_WIRES);
        for (Map.Entry<GlobalPos, Long> entry : STRUCK_WIRES_COPY.entrySet()) {
            if (world.getTime() > entry.getValue()) {
                GlobalPos Gpos = entry.getKey();
                if (world.getRegistryKey() == Gpos.dimension()) {
                    BlockPos pos = Gpos.pos();
                    BlockState state = world.getBlockState(pos);
                    RedstoneStrikerItem.STRUCK_WIRES.remove(Gpos);
                    state.neighborUpdate(world, pos, Blocks.AIR, null, false);
                    world.updateNeighbors(pos, state.getBlock());
                }
            }
        }
    }

    @Inject(method = "tickThunder", at = @At("HEAD"))
    private void tickThunder(WorldChunk chunk, CallbackInfo ci) {
        ServerWorld serverWorld = (ServerWorld) (Object)this;
        boolean bl = serverWorld.isRaining();
        Profiler profiler = Profilers.get();
        profiler.push("thunder");
        ServerPlayerEntity player = this.getRandomAlivePlayer();
        if (ModConfigValues.enableCopperBuff && bl && serverWorld.isThundering() && serverWorld.random.nextInt(10000) == 0
        ) {
            assert player != null;
            if ( player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.COPPER_HELMET) &&
                    player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.COPPER_CHESTPLATE) &&
                    player.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.COPPER_LEGGINGS) &&
                    player.getEquippedStack(EquipmentSlot.FEET).isOf(Items.COPPER_BOOTS)) {
                BlockPos blockPos = Objects.requireNonNull(player).getBlockPos();
                if (serverWorld.hasRain(blockPos)) {
                    LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld, SpawnReason.EVENT);
                    if (lightningEntity != null) {
                        lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                        StatusEffectInstance st = new StatusEffectInstance(StatusEffects.SPEED, 10 * 20, 4, false, false, false);
                        StatusEffectInstance stH = new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1 * 20, 2, false, false, false);
                        player.addStatusEffect(st);
                        player.addStatusEffect(stH);
                        serverWorld.spawnEntity(lightningEntity);
                    }
                }
            }
        }
        profiler.pop();
    }
}
