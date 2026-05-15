package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.EmptyBlockView;

public class HollowLogBlockEntity extends BlockEntity implements Inventory {
    private BlockState storedBlock = Blocks.AIR.getDefaultState();
    private DefaultedList<ItemStack> storedStack = DefaultedList.ofSize(1, ItemStack.EMPTY);

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
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    public void setStoredBlock(ItemStack stack, BlockState state) {
        this.storedBlock = state;
        setHeldStack(stack);
        markDirty();

        if (world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);

        view.put("StoredBlock", BlockState.CODEC, storedBlock);
        Inventories.writeData(view, this.storedStack, false);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);

        storedBlock = view.read("StoredBlock", BlockState.CODEC)
                .orElse(Blocks.AIR.getDefaultState());
        this.storedStack = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readData(view, this.storedStack);
    }
    @Override
    public int size() {
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
    public ItemStack getStack(int slot) {
        return this.getHeldStacks().get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.getHeldStacks(), slot, amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }

        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.getHeldStacks(), slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.getHeldStacks().set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        this.getHeldStacks().clear();
    }
    public DefaultedList<ItemStack> getHeldStacks() {
        return this.storedStack;
    }
    public void setHeldStack(ItemStack itemStack) {
        this.storedStack.set(0, itemStack);
    }
    public ItemStack getHeldStack() {
        return this.storedStack.get(0);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public static boolean canStoreBlock(HollowLogBlockEntity logBE, BlockItem blockItem, boolean vertical){
        BlockState blockItemState = blockItem.getBlock().getDefaultState();
        if (!logBE.getStoredBlock().isAir()) return false;
        if (blockItem.getBlock().getDefaultState().isIn(BlockTags.SHULKER_BOXES)) return false;
        if (blockItemState.getOutlineShape(EmptyBlockView.INSTANCE, BlockPos.ORIGIN)==VoxelShapes.fullCube()) return true;
        if (!vertical) return blockItemState.isIn(BlockTags.SMALL_FLOWERS) || blockItemState.isOf(Blocks.FLOWER_POT)||
                blockItemState.isOf(Blocks.TORCH) || blockItemState.isOf(Blocks.SOUL_TORCH) ||
                blockItemState.isIn(BlockTags.LANTERNS);
        return false;
    }


}
