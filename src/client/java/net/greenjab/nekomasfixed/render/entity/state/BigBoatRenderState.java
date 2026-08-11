package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Environment(EnvType.CLIENT)
public class BigBoatRenderState extends EntityRenderState {
	public float yaw;
	public int damageWobbleSide;
	public float damageWobbleTicks;
	public float damageWobbleStrength;
	public float bubbleWobble;
	public boolean submergedInWater;
	public float leftPaddleAngle;
	public float rightPaddleAngle;

	public boolean hasChest;
	public int players;
	public final ItemStackRenderState bannerRenderState = new ItemStackRenderState();

	public boolean huge;
}
