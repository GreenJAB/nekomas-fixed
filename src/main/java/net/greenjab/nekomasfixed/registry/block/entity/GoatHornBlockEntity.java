package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.block.GoatHornBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class GoatHornBlockEntity extends BlockEntity  {
    public BlockState storedTorchState = Blocks.AIR.defaultBlockState();
    public Direction facing = Direction.NORTH;
    private NonNullList<BlockState> storedState = NonNullList.withSize(1, Blocks.AIR.defaultBlockState());


    public boolean waterlogged = false;

    public GoatHornBlockEntity( BlockPos worldPosition, BlockState blockState) {
        super(BlockEntityTypeRegistry.GOAT_HORN_BLOCK_ENTITY, worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GoatHornBlockEntity blockEntity){
        if(level.getBlockEntity(pos) instanceof GoatHornBlockEntity ){
            blockEntity.setWaterlogged(state.getValue(GoatHornBlock.WATERLOGGED));
            blockEntity.setFacing(state.getValue(GoatHornBlock.FACING));
        }
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput view) {
        super.saveAdditional(view);

        view.store("Torch", BlockState.CODEC, storedTorchState);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput view) {
        super.loadAdditional(view);

        this.storedTorchState = view.read("Torch", BlockState.CODEC)
                .orElse(Blocks.AIR.defaultBlockState());

    }

    @Override
    public void saveCustomOnly(ValueOutput output) {
        super.saveCustomOnly(output);
        output.store("Torch", BlockState.CODEC, storedTorchState);
    }



    public BlockState getTorch(){
        return this.storedTorchState;
    }

    public void deleteStoredTorch(){
        this.storedTorchState = Blocks.AIR.defaultBlockState();
    }

    public Direction getFacing(){
        return facing;
    }

    public void setFacing(Direction newDir){
        facing = newDir;
    }

    public boolean isWaterLogged(){
        return waterlogged;
    }

    public void setWaterlogged(boolean logging){
        this.waterlogged = logging;
    }

    public void setTorch(BlockState torch){
        if(torch.is(ModTags.TORCHES) && !torch.is(storedTorchState.getBlock())){
            storedTorchState = torch;
        }
    }

    public void setTorch(ItemStack torch){
        if(torch.getItem() instanceof BlockItem item){
            BlockState state = item.getBlock().defaultBlockState();
            if(state.is(ModTags.TORCHES) && !state.is(storedTorchState.getBlock())){
                storedTorchState = state;
            }
        }

    }

//    @Override
//    public int getContainerSize() {
//        return 0;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return storedTorchState.isAir();
//    }
//
//    @Override
//    public ItemStack getItem(int slot) {
//        return storedTorchState.getBlock().asItem().getDefaultInstance();
//    }
//
//    @Override
//    public ItemStack removeItem(int slot, int count) {
//        return storedTorchState;
//    }
//
//    @Override
//    public ItemStack removeItemNoUpdate(int slot) {
//        return null;
//    }
//
//    @Override
//    public void setItem(int slot, ItemStack itemStack) {
//
//    }
//
//    @Override
//    public boolean stillValid(Player player) {
//        return false;
//    }
//
//    @Override
//    public void clearContent() {
//
//    }
}
