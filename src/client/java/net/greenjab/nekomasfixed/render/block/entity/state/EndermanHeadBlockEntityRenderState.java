package net.greenjab.nekomasfixed.render.block.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class EndermanHeadBlockEntityRenderState extends BlockEntityRenderState {
	public Direction facing = Direction.NORTH;
	public float yaw;
	public boolean wall;
	public boolean powered;
}
