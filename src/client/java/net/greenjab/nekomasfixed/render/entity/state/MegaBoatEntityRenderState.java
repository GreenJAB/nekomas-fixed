package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.ItemStack;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class MegaBoatEntityRenderState extends EntityRenderState {
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
	public final ItemRenderState bannerRenderState = new ItemRenderState();
}
