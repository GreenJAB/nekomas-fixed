package net.greenjab.nekomasfixed.mixin.alternate_current;

import alternate.current.wire.Node;
import net.greenjab.nekomasfixed.mixin.accessor.NodeAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Node.class)
public class NodeMixin implements NodeAccessor {

    @Shadow
    BlockPos pos;


    @Override
    public BlockPos getPos() {
        return pos;
    }
}
