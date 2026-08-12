package net.greenjab.nekomasfixed.render.block.entity.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class GoatHornBlockEntityRenderState extends BlockEntityRenderState {
    public BlockModelRenderState displayBlockModel = new BlockModelRenderState();
    public Direction direction = Direction.SOUTH;
    public boolean waterlogged = false;

}
