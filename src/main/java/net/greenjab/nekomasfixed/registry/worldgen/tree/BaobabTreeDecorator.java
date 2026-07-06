package net.greenjab.nekomasfixed.registry.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.RopeBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jspecify.annotations.NonNull;

import java.util.List;

import static net.greenjab.nekomasfixed.registry.block.BaobabFruitBlock.AGE;

public class BaobabTreeDecorator extends TreeDecorator {

    private final float probability;
    public static final MapCodec<BaobabTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(BaobabTreeDecorator::new, (decorator) -> decorator.probability);

    public BaobabTreeDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected @NonNull TreeDecoratorType<?> type() {
        return ModTreeDecorators.BAOBAB_TREE_DECORATOR;
    }

    @Override
    public void place(TreeDecorator.Context generator) {
        RandomSource random = generator.random();

        List<BlockPos> list = generator.leaves();
        if (!list.isEmpty()) {
            for(BlockPos pos : list){
                if (random.nextFloat()<0.1f) {
                    BlockPos fruitPos = pos.below();
                    if (generator.level().isStateAtPosition(fruitPos, state -> state.is(BlockTags.REPLACEABLE)) && !generator.logs().contains(fruitPos)) {
                        for (int rope = 3 + random.nextInt(5); rope >= 0; rope--) {
                            BlockPos finalFruitPos = fruitPos;
                            if (generator.level().isStateAtPosition(fruitPos.below(), state -> state.is(BlockTags.REPLACEABLE) && !generator.logs().contains(finalFruitPos))) {
                                generator.setBlock(fruitPos, BlockRegistry.ROPE.defaultBlockState().setValue(RopeBlock.ATTACHED, true));
                                fruitPos = fruitPos.below();
                            }
                        }
                        generator.setBlock(fruitPos, BlockRegistry.BAOBAB_FRUIT.defaultBlockState().setValue(AGE, 1));
                    }
                }
            }
        }
    }
}
