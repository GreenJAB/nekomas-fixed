package net.greenjab.nekomasfixed.registry.woldgen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.RopeBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

import static net.greenjab.nekomasfixed.registry.block.BaobabFruitBlock.AGE;

public class BaobabTreeDecorator extends TreeDecorator {

    private final float probability;
    public static final MapCodec<BaobabTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(BaobabTreeDecorator::new, (decorator) -> decorator.probability);

    public BaobabTreeDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModTreeDecorators.BAOBAB_TREE_DECORATOR;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        Random random = generator.getRandom();

        List<BlockPos> list = generator.getLeavesPositions();
        if (!list.isEmpty()) {
            for(BlockPos pos : list){
                if (random.nextFloat()<0.1f) {
                    BlockPos fruitPos = pos.down();
                    if (generator.getWorld().testBlockState(fruitPos, state -> state.isIn(BlockTags.REPLACEABLE)) && !generator.getLogPositions().contains(fruitPos)) {
                        for (int rope = 3 + random.nextInt(5); rope >= 0; rope--) {
                            BlockPos finalFruitPos = fruitPos;
                            if (generator.getWorld().testBlockState(fruitPos.down(), state -> state.isIn(BlockTags.REPLACEABLE) && !generator.getLogPositions().contains(finalFruitPos))) {
                                generator.replace(fruitPos, BlockRegistry.ROPE.getDefaultState().with(RopeBlock.ATTACHED, true));
                                fruitPos = fruitPos.down();
                            }
                        }
                        generator.replace(fruitPos, BlockRegistry.BAOBAB_FRUIT.getDefaultState().with(AGE, 1));
                    }
                }
            }
        }
    }
}
