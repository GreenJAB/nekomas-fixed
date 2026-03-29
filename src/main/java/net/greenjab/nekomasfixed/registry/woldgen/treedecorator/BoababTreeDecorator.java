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

import static net.greenjab.nekomasfixed.registry.block.BoababFruitBlock.AGE;

public class BoababTreeDecorator extends TreeDecorator {

    private final float probability;
    public static final MapCodec<BoababTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(BoababTreeDecorator::new, (decorator) -> decorator.probability);

    public BoababTreeDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModTreeDecorators.BOABAB_TREE_DECORATOR;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        Random random = generator.getRandom();
        if (!(random.nextFloat() >= this.probability)) {
            List<BlockPos> list = generator.getLeavesPositions();
            int scale = 1;
            if (!list.isEmpty()) {
                for(BlockPos pos : list){
                    if (random.nextFloat() > 1f / scale) continue;
                    BlockPos down = pos.down();
                    if(generator.getWorld().testBlockState(down, (AbstractBlock.AbstractBlockState::isAir))){
                        generator.replace(down, BlockRegistry.BOABAB_FRUIT.getDefaultState().with(AGE, 1));
                    }
                }
            }
        }
    }
}
