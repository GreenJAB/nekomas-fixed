package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
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

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin {
    @Shadow
    public abstract @Nullable ServerPlayer getRandomPlayer();

    @Inject(method = "tick", at = @At("HEAD"))
    private void depowerRedstoneStruckBlocks(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerLevel world = ((ServerLevel)(Object)this);
        HashMap<GlobalPos, Long> STRUCK_WIRES_COPY = new HashMap<>(RedstoneStrikerItem.STRUCK_WIRES);
        for (Map.Entry<GlobalPos, Long> entry : STRUCK_WIRES_COPY.entrySet()) {
            if (world.getGameTime() > entry.getValue()) {
                GlobalPos Gpos = entry.getKey();
                if (world.dimension() == Gpos.dimension()) {
                    BlockPos pos = Gpos.pos();
                    BlockState state = world.getBlockState(pos);
                    RedstoneStrikerItem.STRUCK_WIRES.remove(Gpos);
                    state.handleNeighborChanged(world, pos, Blocks.AIR, null, false);
                    world.updateNeighborsAt(pos, state.getBlock());
                }
            }
        }
    }

    @Inject(method = "tickThunder", at = @At("HEAD"))
    private void tickThunder(LevelChunk chunk, CallbackInfo ci) {
        ServerLevel serverWorld = (ServerLevel) (Object)this;
        boolean bl = serverWorld.isRaining();
        ProfilerFiller profiler = Profiler.get();
        profiler.push("thunder");
        ServerPlayer player = this.getRandomPlayer();
        if (ModConfigValues.enableCopperBuff && bl && serverWorld.isThundering() && player != null) {
            int armor = getCopperArmor(player);
            if (armor > 0 && serverWorld.getRandom().nextInt(14000-2000*armor) == 0) {
                BlockPos blockPos = player.blockPosition();
                if (serverWorld.isRainingAt(blockPos)) {
                    LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld, EntitySpawnReason.EVENT);
                    if (lightningEntity != null) {
                        lightningEntity.snapTo(Vec3.atBottomCenterOf(blockPos));
                        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 3*armor * 20, armor, false, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, armor, false, false, false));
                        serverWorld.addFreshEntity(lightningEntity);
                    }
                }
            }
        }
        profiler.pop();
    }

    @Unique
    private static int getCopperArmor(LivingEntity entity) {
        int i =0;
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(Items.COPPER_BOOTS)) i++;
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(Items.COPPER_LEGGINGS)) i++;
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(Items.COPPER_CHESTPLATE)) i++;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.COPPER_HELMET)) i++;
        return i;
    }
}
