package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.shapes.Shapes;
import org.jspecify.annotations.NonNull;

public class HollowLogBlockEntity extends BlockEntity implements Container {
    private BlockState storedBlock = Blocks.AIR.defaultBlockState();
    private NonNullList<ItemStack> storedStack = NonNullList.withSize(1, ItemStack.EMPTY);

    public HollowLogBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.HOLLOW_LOG_BLOCK_ENTITY, pos, state);
    }

    public BlockState getStoredBlock() {
        return this.storedBlock;
    }
    public ItemStack getStoredStack() {
        return getHeldStack();
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public void setStoredBlock(ItemStack stack, BlockState state) {
        this.storedBlock = state;
        setHeldStack(stack);
        setChanged();

        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput view) {
        super.saveAdditional(view);

        view.store("StoredBlock", BlockState.CODEC, storedBlock);
        ContainerHelper.saveAllItems(view, this.storedStack, false);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput view) {
        super.loadAdditional(view);

        storedBlock = view.read("StoredBlock", BlockState.CODEC)
                .orElse(Blocks.AIR.defaultBlockState());
        this.storedStack = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(view, this.storedStack);
    }
    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.getHeldStacks()) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public @NonNull ItemStack getItem(int slot) {
        return this.getHeldStacks().get(slot);
    }

    @Override
    public @NonNull ItemStack removeItem(int slot, int amount) {
        ItemStack itemStack = ContainerHelper.removeItem(this.getHeldStacks(), slot, amount);
        if (!itemStack.isEmpty()) {
            this.setChanged();
        }

        return itemStack;
    }

    @Override
    public @NonNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.getHeldStacks(), slot);
    }

    @Override
    public void setItem(int slot, @NonNull ItemStack stack) {
        this.getHeldStacks().set(slot, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        this.setChanged();
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.getHeldStacks().clear();
    }
    public NonNullList<ItemStack> getHeldStacks() {
        return this.storedStack;
    }
    public void setHeldStack(ItemStack itemStack) {
        this.storedStack.set(0, itemStack);
    }
    public ItemStack getHeldStack() {
        return this.storedStack.get(0);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static boolean canStoreBlock(HollowLogBlockEntity logBE, BlockItem blockItem, boolean vertical){
        BlockState blockItemState = blockItem.getBlock().defaultBlockState();
        if (!logBE.getStoredBlock().isAir()) return false;
        if (blockItem.getBlock().defaultBlockState().is(BlockTags.SHULKER_BOXES)) return false;
        if (blockItemState.getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO)==Shapes.block()) return true;
        if (!vertical) return blockItemState.is(BlockTags.SMALL_FLOWERS) || blockItemState.is(Blocks.FLOWER_POT)||
                blockItemState.is(Blocks.TORCH) || blockItemState.is(Blocks.SOUL_TORCH) ||
                blockItemState.is(BlockTags.LANTERNS);
        return false;
    }


}
