package net.greenjab.nekomasfixed.render.block.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.block.enums.ClamType;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

@Environment(EnvType.CLIENT)
public class ClamBlockEntityRenderState extends BlockEntityRenderState {
	public float lidAnimationProgress;
	public float yaw;
	public ClamBlockEntityRenderState.Variant variant = ClamBlockEntityRenderState.Variant.REGULAR;
	public ItemRenderState itemRenderState = new ItemRenderState();

	@Environment(EnvType.CLIENT)
	public static enum Variant {
		BLUE,
		PINK,
		PURPLE,
		REGULAR;
	}
}
