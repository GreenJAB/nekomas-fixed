package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.WildFireEntity;
import net.greenjab.nekomasfixed.render.entity.model.WildFireEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.WildFireEntityRenderState;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class WildFireEntityRenderer extends MobEntityRenderer<WildFireEntity, WildFireEntityRenderState, WildFireEntityModel> {
	private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wild_fire.png");

	public WildFireEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WildFireEntityModel(context.getPart(EntityModelLayerRegistry.WILD_FIRE)), 0.5F);
	}

	protected int getBlockLight(WildFireEntity wildFireEntity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public Identifier getTexture(WildFireEntityRenderState state) {
		return TEXTURE;
	}

	public WildFireEntityRenderState createRenderState() {
		return new WildFireEntityRenderState();
	}
}
