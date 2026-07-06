package net.greenjab.nekomasfixed.render.block.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Environment(EnvType.CLIENT)
public class ClamBlockEntityRenderState extends BlockEntityRenderState {
	public float lidAnimationProgress;
	public float yaw;
	public ClamBlockEntityRenderState.Variant variant = ClamBlockEntityRenderState.Variant.REGULAR;
	public ItemStackRenderState itemRenderState = new ItemStackRenderState();

	@Environment(EnvType.CLIENT)
	public enum Variant {
		BLUE,
		PINK,
		PURPLE,
		REGULAR
	}
}
