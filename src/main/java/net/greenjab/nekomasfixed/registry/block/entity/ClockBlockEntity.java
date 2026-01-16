package net.greenjab.nekomasfixed.registry.block.entity;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.greenjab.nekomasfixed.network.UpdateClockPayload;
import net.greenjab.nekomasfixed.registry.block.AbstractClockBlock;
import net.greenjab.nekomasfixed.registry.block.FloorClockBlock;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jspecify.annotations.Nullable;

public class ClockBlockEntity extends BlockEntity implements HeldItemContext {
	private int storedTime =-1;
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
	protected void readData(ReadView view) {
		super.readData(view);
		view.read("storedTime", Codec.INT).ifPresent(this::setStoredTime);
		view.read("timer", Codec.INT).ifPresent(this::setTimer);
		view.read("bell", Codec.BOOL).ifPresent(this::setBell);
		view.read("showsTime", Codec.BOOL).ifPresent(this::setShowsTime);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		view.putNullable("storedTime", Codec.INT, getStoredTime());
		view.putNullable("timer", Codec.INT, getTimer());
		view.putNullable("bell", Codec.BOOL, hasBell());
		view.putNullable("showsTime", Codec.BOOL, getShowsTime());
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public void onBlockReplaced(BlockPos pos, BlockState oldState) {
		if (this.world != null && bell) {
			ItemScatterer.spawn(this.world, pos.getX(), pos.getY(), pos.getZ(), Items.BELL.getDefaultStack());
		}
	}

	@Override
	public boolean onSyncedBlockEvent(int type, int data) {
		return super.onSyncedBlockEvent(type, data);
	}

	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
		this.storedTime = components.getOrDefault(OtherRegistry.STORED_TIME, new StoredTimeComponent(-1)).time();
	}

	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		super.addComponents(builder);
		if (this.storedTime>0) builder.add(OtherRegistry.STORED_TIME, new StoredTimeComponent(this.storedTime));
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

	@Override
	public World getEntityWorld() {
		return this.world;
	}

	@Override
	public Vec3d getEntityPos() {
		return this.getPos().toCenterPos();
	}

	@Override
	public float getBodyYaw() {
		return this.getCachedState().get(FloorClockBlock.ROTATION);
	}

	public static void tick(World world, BlockPos pos, BlockState state, ClockBlockEntity blockEntity) {
		boolean powered = state.get(AbstractClockBlock.POWERED);
		boolean shouldBePowered = false;
		if ((int) ((world.getTimeOfDay() + 6000) % 24000)==blockEntity.storedTime) {
			blockEntity.timer=0;
		}
		if (blockEntity.timer>-timerDuration) {
			blockEntity.timer--;
			if (blockEntity.timer<1) {
				shouldBePowered = true;
			}
		}
		if (world.getTime() % 20L == 0L) {
			if (world instanceof ServerWorld serverWorld) {
				world.updateComparators(pos, state.getBlock());
				UpdateClockPayload payload = new UpdateClockPayload(pos.getX(), pos.getY(), pos.getZ(), blockEntity.getTimer(), blockEntity.hasBell(), blockEntity.getShowsTime());
				sendToAround(serverWorld.getServer()
								.getPlayerManager(),
						null,
						pos.getX(),
						pos.getY(),
						pos.getZ(),
						100,
						world.getRegistryKey(),
						payload
				);
			}
		}
		if (powered!=shouldBePowered) {
			((AbstractClockBlock)state.getBlock()).setPower(world, pos, state, shouldBePowered);
		}
		if (state.getBlock() instanceof FloorClockBlock && shouldBePowered && world.getTime() % 5L == 0L){
			world.emitGameEvent(null, GameEvent.NOTE_BLOCK_PLAY, pos);
			world.playSound(null, pos, SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS, 0.3F, 2f);
		}
	}

	public static void sendToAround(PlayerManager playerManager, @Nullable PlayerEntity player, double x, double y, double z, double distance, RegistryKey<World> worldKey, CustomPayload payload) {
		for (int i = 0; i < playerManager.getPlayerList().size(); i++) {
			ServerPlayerEntity serverPlayerEntity = playerManager.getPlayerList().get(i);
			if (serverPlayerEntity != player && serverPlayerEntity.getEntityWorld().getRegistryKey() == worldKey) {
				double d = x - serverPlayerEntity.getX();
				double e = y - serverPlayerEntity.getY();
				double f = z - serverPlayerEntity.getZ();
				if (d * d + e * e + f * f < distance * distance) {
					ServerPlayNetworking.send(serverPlayerEntity, payload);
				}
			}
		}
	}

	public static void clientTick(World world, BlockPos pos, BlockState state, ClockBlockEntity blockEntity) {
		if (blockEntity.timer>-timerDuration) {
			blockEntity.timer--;
		}
	}

}
