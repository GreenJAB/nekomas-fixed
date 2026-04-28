package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;


public class SoupCauldronBlock extends AbstractCauldronBlock implements BlockEntityProvider {
    public static final MapCodec<SoupCauldronBlock> CODEC = createCodec(SoupCauldronBlock::new);

    public SoupCauldronBlock(Settings settings) {
        super(settings, CauldronBehavior.WATER_CAULDRON_BEHAVIOR);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient() && world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity blockEntity){
            blockEntity.addInput(stack);
            for(ItemStack itemStack : blockEntity.getInputs()){
                System.out.println(itemStack);
            }
            System.out.println();
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }


    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoupCauldronBlockEntity(pos, state);
    }
}
