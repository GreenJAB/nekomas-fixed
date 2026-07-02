package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
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
        if (ModConfigValues.enableCopperBuff && bl && serverWorld.isThundering() && player != null) {
            int armor = getCopperArmor(player);
            if (armor > 0 && serverWorld.random.nextInt(14000-2000*armor) == 0) {
                BlockPos blockPos = player.getBlockPos();
                if (serverWorld.hasRain(blockPos)) {
                    LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld, SpawnReason.EVENT);
                    if (lightningEntity != null) {
                        lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 3*armor * 20, armor, false, false, false));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, armor, false, false, false));
                        serverWorld.spawnEntity(lightningEntity);
                    }
                }
            }
        }
        profiler.pop();
    }

    @Unique
    private static int getCopperArmor(LivingEntity entity) {
        int i =0;
        if (entity.getEquippedStack(EquipmentSlot.FEET).isOf(Items.COPPER_BOOTS)) i++;
        if (entity.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.COPPER_LEGGINGS)) i++;
        if (entity.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.COPPER_CHESTPLATE)) i++;
        if (entity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.COPPER_HELMET)) i++;
        return i;
    }
}
