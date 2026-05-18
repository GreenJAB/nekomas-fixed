package net.greenjab.nekomasfixed.registry.woldgen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.minecraft.block.AbstractBlock;
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
        if (random.nextBoolean()) {
            List<BlockPos> list = generator.getLeavesPositions();
            if (!list.isEmpty()) {
                for(BlockPos pos : list){
                    if (!(Math.sin(random.nextDouble() * 100) >= 0.809)) continue;
                    BlockPos down = pos.down();
                    if(generator.getWorld().testBlockState(down, (AbstractBlock.AbstractBlockState::isAir))){
                        generator.replace(down, BlockRegistry.BAOBAB_FRUIT.getDefaultState().with(AGE, 1));
                    }
                }
            }
        }
    }
}
