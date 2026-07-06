package net.greenjab.nekomasfixed.render.block.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

@Environment(EnvType.CLIENT)
public class ClockBlockEntityRenderState extends BlockEntityRenderState {
	public float poweredTicks;
	public Direction facing = Direction.NORTH;
	public float yaw;
	public boolean wall;
	public boolean bell;
	public int timer;
	public int dayTime;
	public ItemStackRenderState clockRenderState = new ItemStackRenderState();
	public ItemStackRenderState standRenderState = new ItemStackRenderState();
	public ItemStackRenderState bellRenderState = new ItemStackRenderState();
}
