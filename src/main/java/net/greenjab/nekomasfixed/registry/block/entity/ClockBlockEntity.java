package net.greenjab.nekomasfixed.registry.block.entity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.greenjab.nekomasfixed.network.UpdateClockPayload;
import net.greenjab.nekomasfixed.registry.block.AbstractClockBlock;
import net.greenjab.nekomasfixed.registry.block.FloorClockBlock;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class ClockBlockEntity extends BlockEntity {
    private int storedTime = -1;
    public static int timerDuration = 60;
    private int timer = -timerDuration;
    private boolean bell = false;
    private boolean showsTime = false;

    protected ClockBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public ClockBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("storedTime")) setStoredTime(tag.getInt("storedTime"));
        if (tag.contains("timer")) setTimer(tag.getInt("timer"));
        if (tag.contains("bell")) setBell(tag.getBoolean("bell"));
        if (tag.contains("showsTime")) setShowsTime(tag.getBoolean("showsTime"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("storedTime", getStoredTime());
        tag.putInt("timer", getTimer());
        tag.putBoolean("bell", hasBell());
        tag.putBoolean("showsTime", getShowsTime());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput components) {
        super.applyImplicitComponents(components);
        this.storedTime = components.getOrDefault(ComponentRegistry.STORED_TIME, new StoredTimeComponent(-1)).time();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        if (this.storedTime > 0) builder.set(ComponentRegistry.STORED_TIME, new StoredTimeComponent(this.storedTime));
    }

    public void setStoredTime(int time) {
        storedTime = time;
    }
    public int getStoredTime() {
        return storedTime;
    }
    public void setTimer(int time) {
        timer = time;
    }
    public int getTimer() {
        return timer;
    }
    public void setBell(boolean hasbell) {
        bell = hasbell;
    }
    public boolean hasBell() {
        return bell;
    }
    public void setShowsTime(boolean showTime) {
        showsTime = showTime;
    }
    public boolean getShowsTime() {
        return showsTime;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ClockBlockEntity blockEntity) {
        boolean powered = state.getValue(AbstractClockBlock.POWERED);
        boolean shouldBePowered = false;
        if ((int) ((level.getDayTime() + 6000) % 24000) == blockEntity.storedTime) {
            blockEntity.timer = 0;
        }
        if (blockEntity.timer > -timerDuration) {
            blockEntity.timer--;
            if (blockEntity.timer < 1) {
                shouldBePowered = true;
            }
        }
        if (level.getGameTime() % 20L == 0L) {
            if (level instanceof ServerLevel serverLevel) {
                level.updateNeighbourForOutputSignal(pos, state.getBlock());
                UpdateClockPayload payload = new UpdateClockPayload(pos.getX(), pos.getY(), pos.getZ(), blockEntity.getTimer(), blockEntity.hasBell(), blockEntity.getShowsTime());
                sendToAround(serverLevel.getServer().getPlayerList(), null, pos.getX(), pos.getY(), pos.getZ(), 100, level.dimension(), payload);
            }
        }
        if (powered != shouldBePowered) {
            ((AbstractClockBlock) state.getBlock()).setPower(level, pos, state, shouldBePowered);
        }
        if (blockEntity.hasBell() && state.getBlock() instanceof FloorClockBlock && shouldBePowered && level.getGameTime() % 5L == 0L) {
            level.gameEvent(GameEvent.NOTE_BLOCK_PLAY, pos, GameEvent.Context.of(state));
            level.playSound(null, pos, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 0.3F, 2f);
        }
    }

    public static void sendToAround(PlayerList playerManager, @Nullable Player player, double x, double y, double z, double distance, ResourceKey<Level> worldKey, CustomPacketPayload payload) {
        for (int i = 0; i < playerManager.getPlayers().size(); i++) {
            ServerPlayer serverPlayerEntity = playerManager.getPlayers().get(i);
            if (serverPlayerEntity != player && serverPlayerEntity.level().dimension() == worldKey) {
                double d = x - serverPlayerEntity.getX();
                double e = y - serverPlayerEntity.getY();
                double f = z - serverPlayerEntity.getZ();
                if (d * d + e * e + f * f < distance * distance) {
                    ServerPlayNetworking.send(serverPlayerEntity, payload);
                }
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ClockBlockEntity blockEntity) {
        if (blockEntity.timer > -timerDuration) {
            blockEntity.timer--;
        }
    }
}