package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireEntity;
import net.greenjab.nekomasfixed.render.entity.model.WildfireEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.WildfireEntityRenderState;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class WildfireEntityRenderer extends MobEntityRenderer<WildfireEntity, WildfireEntityRenderState, WildfireEntityModel> {
	private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wildfire/default.png");
	private static final Identifier TEXTURE_SOUL = NekomasFixed.id("textures/entity/wildfire/soul.png");

	public WildfireEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WildfireEntityModel(context.getPart(ModEntityLayerRegistry.WILD_FIRE)), 0.5F);
	}

	protected int getBlockLight(WildfireEntity wildFireEntity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public Identifier getTexture(WildfireEntityRenderState state) {
		if (state.soul) return TEXTURE_SOUL;
		return TEXTURE;
	}

	public WildfireEntityRenderState createRenderState() {
		return new WildfireEntityRenderState();
	}

	public void updateRenderState(WildfireEntity wildFireEntity, WildfireEntityRenderState wildFireEntityRenderState, float f) {
		super.updateRenderState(wildFireEntity, wildFireEntityRenderState, f);
		wildFireEntityRenderState.soul = wildFireEntity.isSoulActive();
		wildFireEntityRenderState.shields = wildFireEntity.getShieldsActive();
		wildFireEntityRenderState.shieldAngle = 1-(MathHelper.cos(Math.PI*MathHelper.clamp(wildFireEntity.clientFireTime +0.5f*(f/20f)*(wildFireEntity.isOnFire()?1:-1), 0, 1))+1)/2f;
		wildFireEntityRenderState.shieldExtraSpin = wildFireEntity.clientExtraSpin+wildFireEntityRenderState.shieldAngle*4*f;
	}
}
