package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SnifferEntity.class)
public interface SnifferEntityAccessor {

    @Accessor("FINISH_DIG_TIME")
    static TrackedData<Integer> getFinishDigTime() {
        throw new AssertionError();
    }

    @Invoker("getDigPos")
    BlockPos invokeGetDigPos();
}