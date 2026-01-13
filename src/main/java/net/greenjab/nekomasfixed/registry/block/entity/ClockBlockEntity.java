package net.greenjab.nekomasfixed.registry.block.entity;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.block.ClockBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.component.ComponentMap;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ClockBlockEntity extends BlockEntity implements HeldItemContext {
	private int storedTime =-1;
	private int timer = -1;

	protected ClockBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public ClockBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void readData(ReadView view) {
		super.readData(view);
		this.storedTime = view.read("storedTimeOfDay", Codec.INT).orElse(-1);
		this.timer = view.read("timer", Codec.INT).orElse(-1);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		view.putNullable("storedTimeOfDay", Codec.INT, this.storedTime);
		view.putNullable("timer", Codec.INT, this.timer);
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public void onBlockReplaced(BlockPos pos, BlockState oldState) {
	}


	@Override
	public boolean onSyncedBlockEvent(int type, int data) {
		return super.onSyncedBlockEvent(type, data);
	}


	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		super.addComponents(builder);
		if (storedTime!=-1) builder.add(OtherRegistry.STORED_TIME, storedTime);
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
		return this.getCachedState().get(ClockBlock.ROTATION);
	}
}
