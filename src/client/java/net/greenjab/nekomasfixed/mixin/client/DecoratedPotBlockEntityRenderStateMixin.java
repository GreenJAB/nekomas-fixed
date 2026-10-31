package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.target_access.DecoratedPotAccess;
import net.minecraft.client.render.block.entity.state.DecoratedPotBlockEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.BitSet;

@Mixin(DecoratedPotBlockEntityRenderState.class)
public class DecoratedPotBlockEntityRenderStateMixin implements DecoratedPotAccess {
    @Unique
    public BitSet nekomasfixed$sherdGlow = new BitSet();

    @Override
    public boolean nekomasfixed$getSherdGlow(int index) {
        return nekomasfixed$sherdGlow.get(index);
    }

    @Override
    public void nekomasfixed$setSherdGlow(int index, boolean glowing) {
        nekomasfixed$sherdGlow.set(index, glowing);
    }
}
