package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
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

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
abstract class ServerWorldMixin {
    @Shadow
    public abstract @Nullable ServerPlayerEntity getRandomAlivePlayer();

    @Shadow
    public abstract <T extends ParticleEffect> int spawnParticles(T parameters, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed);

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerWorld world = ((ServerWorld)(Object)this);
        Iterator<Map.Entry<BlockPos, Long>> wireIterator =
                RedstoneStrikerItem.STRUCK_WIRES.entrySet().iterator();

        while (wireIterator.hasNext()) {
            Map.Entry<BlockPos, Long> entry = wireIterator.next();
            BlockPos pos = entry.getKey();
            if (world.getTime() > entry.getValue() && world.getBlockState(pos).isOf(Blocks.REDSTONE_WIRE)) {
                wireIterator.remove();
                world.setBlockState(pos, world.getBlockState(pos).with(RedstoneWireBlock.POWER, 0));
                world.updateNeighbors(pos, Blocks.REDSTONE_WIRE);
            }
        }
//        Iterator<Map.Entry<BlockPos, Long>> blockIterator = RedstoneStrikerItem.STRUCK_BLOCKS.entrySet().iterator();
//
//        while (blockIterator.hasNext()) {
//            Map.Entry<BlockPos, Long> entry = blockIterator.next();
//            BlockPos pos = entry.getKey();
//
//            if (world.getTime() > entry.getValue()) {
//                blockIterator.remove();
//                world.setBlockState(pos, world.getBlockState(pos));
//                BlockState state = world.getBlockState(pos);
//                world.shouldTickBlockAt(pos);
//                System.out.println("REMOVED");
//                for (Direction dir : Direction.values()) {
//                    BlockPos neighborPos = pos.offset(dir);
//                    world.updateNeighbor(neighborPos, world.getBlockState(neighborPos).getBlock(), null);
//                    world.updateNeighborsAlways(neighborPos, world.getBlockState(neighborPos).getBlock(), null);
//                }
//                world.updateNeighborsAlways(pos, state.getBlock(), null);
//            }
//        }

    }

    @Inject(method = "tickThunder", at = @At("HEAD"))
    private void tickThunder(WorldChunk chunk, CallbackInfo ci) {
        ChunkPos chunkPos = chunk.getPos();
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
