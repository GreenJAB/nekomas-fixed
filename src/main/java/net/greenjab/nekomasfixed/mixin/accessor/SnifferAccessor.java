package net.greenjab.nekomasfixed.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Sniffer.class)
public interface SnifferAccessor {

    @Accessor("DATA_DROP_SEED_AT_TICK")
    static EntityDataAccessor<Integer> getFinishDigTime() {
        throw new AssertionError();
    }

    @Invoker("getHeadBlock")
    BlockPos invokeGetDigPos();
}