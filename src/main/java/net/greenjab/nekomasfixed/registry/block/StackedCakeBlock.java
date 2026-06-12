package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StackedCakeBlock extends Block implements BlockEntityProvider {
    public List<BlockState> STACKED_CAKES = new ArrayList<>();
    public static final MapCodec<StackedCakeBlock> CODEC = createCodec(StackedCakeBlock::new);
    public StackedCakeBlock(Settings settings) {
        super(settings);
    }

    public List<BlockState> getCakes(){return this.STACKED_CAKES;}

    @Override
    public MapCodec<StackedCakeBlock> getCodec(){return CODEC;}

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StackedCakeBlockEntity(pos, state);
    }
}
