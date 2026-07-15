package net.greenjab.nekomasfixed.registry.block.entity;

import com.mojang.logging.LogUtils;
import net.greenjab.nekomasfixed.registry.block.ClamBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

public class ClamBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity, ItemOwner {
	private static final Logger LOGGER = LogUtils.getLogger();
	private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	private int state = 0;
	private final ChestLidController lidAnimator = new ChestLidController();

	protected ClamBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public ClamBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityTypeRegistry.CLAM_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void loadAdditional(@NonNull ValueInput view) {
		super.loadAdditional(view);
		this.readInventoryNbt(view);
	}

	@Override
	protected void saveAdditional(@NonNull ValueOutput view) {
		super.saveAdditional(view);
		if (!this.trySaveLootTable(view)) {
			ContainerHelper.saveAllItems(view, this.inventory, false);
		}
	}

	public void readInventoryNbt(ValueInput readView) {
		this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(readView)) {
			ContainerHelper.loadAllItems(readView, this.inventory);
		}
	}

	@Override
	protected @NonNull Component getDefaultName() {
		return null;
	}

	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState oldState) {
	}

	@Override
	public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
		CompoundTag var4;
		try (ProblemReporter.ScopedCollector logging = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER)) {
			TagValueOutput nbtWriteView = TagValueOutput.createWithContext(logging, registries);
			ContainerHelper.saveAllItems(nbtWriteView, this.inventory, true);
			var4 = nbtWriteView.buildResult();
		}

		return var4;
	}

	public static void clientTick(Level level, BlockPos pos, BlockState state, ClamBlockEntity blockEntity) {
		blockEntity.lidAnimator.shouldBeOpen(state.getValue(ClamBlock.OPEN));
		blockEntity.lidAnimator.tickLid();
		if (state.getValue(ClamBlock.OPEN) && state.getValue(ClamBlock.WATERLOGGED) && blockEntity.lidAnimator.getOpenness(0)<1){
			blockEntity.level().addParticle(ParticleTypes.BUBBLE, pos.getX()+0.5+ level.getRandom().nextGaussian()*0.15, pos.getY()+0.2, pos.getZ()+0.5+ level.getRandom().nextGaussian()*0.15, 0.0, 0.75, 0.0);
		}

	}

	@Override
	public boolean triggerEvent(int type, int data) {
		return super.triggerEvent(type, data);
	}

	@Override
	public float getOpenNess(float tickProgress) {
		return this.lidAnimator.getOpenness(tickProgress);
	}


	@Override
	public @NonNull NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	public void setHeldStack(ItemStack itemStack) {
		this.inventory.set(0, itemStack);
	}


	@Override
	protected void setItems(@NonNull NonNullList<ItemStack> inventory) {
		this.inventory = inventory;
	}

	@Override
	protected @NonNull AbstractContainerMenu createMenu(int syncId, @NonNull Inventory playerInventory) {
		return null;
	}

	@Override
	public @NonNull Level level() {
		return this.level;
	}

	@Override
	public @NonNull Vec3 position() {
		return Vec3.atCenterOf(this.getBlockPos());
	}

	@Override
	public float getVisualRotationYInDegrees() {
		return (this.getBlockState().getValue(ClamBlock.FACING)).getOpposite().toYRot();
	}
	public ItemStack swapStack(int slot, ItemStack stack) {
		ItemStack itemStack = this.removeItemNoUpdate(slot);
		this.setItem(slot, stack);
		return itemStack;
	}
	public void markDirty(Holder.Reference<GameEvent> gameEvent) {
		super.setChanged();
		if (this.level != null) {
			this.level.gameEvent(gameEvent, this.worldPosition, GameEvent.Context.of(this.getBlockState()));
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		}
	}

	@Override
	public int getContainerSize() {
		return 1;
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.@NonNull Builder builder) {
		super.collectImplicitComponents(builder);
		if (state!=0) builder.set(ComponentRegistry.CLAM_STATE, state);
	}

	public void setState(int cstate) {
		state = cstate;
	}
}
