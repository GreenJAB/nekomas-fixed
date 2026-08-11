package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

@Environment(EnvType.CLIENT)
public class SpearRenderState extends EntityRenderState {
	public ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
	public Direction direction = Direction.UP;
	public int duration;
	public int light = 15728880;
}
