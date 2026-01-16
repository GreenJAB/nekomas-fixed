package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderState extends EntityRenderState {
	public ItemRenderState spearRenderState = new ItemRenderState();
	public Direction direction = Direction.UP;
	public int duration;
	public int light = 15728880;
}
