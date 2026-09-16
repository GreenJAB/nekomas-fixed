package net.greenjab.nekomasfixed.mixin.accessor;

import alternate.current.wire.Node;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Node.class)
public interface NodeAccessor {
    @Accessor("pos")
    BlockPos getPos();
}