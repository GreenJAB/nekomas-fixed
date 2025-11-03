package net.greenjab.nekomasfixed.registry.block.entity;

import com.mojang.logging.LogUtils;
import net.greenjab.nekomasfixed.registry.block.ClamBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.ListInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ErrorReporter;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;

public class ClamBlockEntity extends LootableContainerBlockEntity implements LidOpenable, HeldItemContext, ListInventory {
	private static final Logger LOGGER = LogUtils.getLogger();
	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

	private final ChestLidAnimator lidAnimator = new ChestLidAnimator();

	protected ClamBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public ClamBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityTypeRegistry.Clam_Block_Entity, pos, state);
	}

	@Override
	protected void readData(ReadView view) {
		super.readData(view);
		this.readInventoryNbt(view);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		if (!this.writeLootTable(view)) {
			Inventories.writeData(view, this.inventory, false);
		}
	}

	public void readInventoryNbt(ReadView readView) {
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		if (!this.readLootTable(readView)) {
			Inventories.readData(readView, this.inventory);
		}
	}

	@Override
	protected Text getContainerName() {
		return null;
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public void onBlockReplaced(BlockPos pos, BlockState oldState) {
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
		NbtCompound var4;
		try (ErrorReporter.Logging logging = new ErrorReporter.Logging(this.getReporterContext(), LOGGER)) {
			NbtWriteView nbtWriteView = NbtWriteView.create(logging, registries);
			Inventories.writeData(nbtWriteView, this.inventory, true);
			var4 = nbtWriteView.getNbt();
		}

		return var4;
	}

	public static void clientTick(World world, BlockPos pos, BlockState state, ClamBlockEntity blockEntity) {
		blockEntity.lidAnimator.setOpen(state.get(ClamBlock.OPEN));
		blockEntity.lidAnimator.step();
	}

	@Override
	public boolean onSyncedBlockEvent(int type, int data) {
		return super.onSyncedBlockEvent(type, data);
	}

	@Override
	public float getAnimationProgress(float tickProgress) {
		return this.lidAnimator.getProgress(tickProgress);
	}


	@Override
	public DefaultedList<ItemStack> getHeldStacks() {
		return this.inventory;
	}

	public void setHeldStack(ItemStack itemStack) {
		this.inventory.set(0, itemStack);
	}


	@Override
	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		this.inventory = inventory;
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return null;
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
		return ((Direction)this.getCachedState().get(ClamBlock.FACING)).getOpposite().getPositiveHorizontalDegrees();
	}
	public ItemStack swapStackNoMarkDirty(int slot, ItemStack stack) {
		ItemStack itemStack = this.removeStack(slot);
		this.setStackNoMarkDirty(slot, stack);
		return itemStack;
	}
	public void markDirty(RegistryEntry.Reference<GameEvent> gameEvent) {
		super.markDirty();
		if (this.world != null) {
			this.world.emitGameEvent(gameEvent, this.pos, GameEvent.Emitter.of(this.getCachedState()));
			this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
		}
	}
}
