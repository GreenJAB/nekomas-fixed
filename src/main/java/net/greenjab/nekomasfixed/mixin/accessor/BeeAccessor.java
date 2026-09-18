package net.greenjab.nekomasfixed.mixin.accessor;

import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// Exposes Bee.setHasNectar (package-private in 1.21.1) so the moobloom-pollination
// goal can flag nectar on the bee it sends over.
@Mixin(Bee.class)
public interface BeeAccessor {
    @Invoker("setHasNectar")
    void invokeSetHasNectar(boolean hasNectar);
}