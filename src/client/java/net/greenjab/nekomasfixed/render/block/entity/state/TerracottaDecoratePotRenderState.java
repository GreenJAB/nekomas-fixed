package net.greenjab.nekomasfixed.render.block.entity.state;

import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TerracottaDecoratePotRenderState extends DecoratedPotRenderState {
    public PotEngravingDecoration engravedDecorations = PotEngravingDecoration.EMPTY;
    public PotFaceDecoration potFace = PotFaceDecoration.EMPTY;
}
