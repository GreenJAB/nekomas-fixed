package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildFireEntity;
import net.greenjab.nekomasfixed.render.entity.model.WildFireEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.WildFireEntityRenderState;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class WildFireEntityRenderer extends MobEntityRenderer<WildFireEntity, WildFireEntityRenderState, WildFireEntityModel> {
	private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wild_fire/default.png");
	private static final Identifier TEXTURE_SOUL = NekomasFixed.id("textures/entity/wild_fire/soul.png");

	public WildFireEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WildFireEntityModel(context.getPart(EntityModelLayerRegistry.WILD_FIRE)), 0.5F);
	}

	protected int getBlockLight(WildFireEntity wildFireEntity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public Identifier getTexture(WildFireEntityRenderState state) {
		if (state.soul) return TEXTURE_SOUL;
		return TEXTURE;
	}

	public WildFireEntityRenderState createRenderState() {
		return new WildFireEntityRenderState();
	}

	public void updateRenderState(WildFireEntity wildFireEntity, WildFireEntityRenderState wildFireEntityRenderState, float f) {
		super.updateRenderState(wildFireEntity, wildFireEntityRenderState, f);
		wildFireEntityRenderState.soul = wildFireEntity.isSoulActive();
		wildFireEntityRenderState.shields = wildFireEntity.getShieldsActive();
		wildFireEntityRenderState.shieldAngle = 1-(MathHelper.cos(Math.PI*MathHelper.clamp(wildFireEntity.clientFireTime +0.5f*(f/20f)*(wildFireEntity.isOnFire()?1:-1), 0, 1))+1)/2f;
		wildFireEntityRenderState.shieldExtraSpin = wildFireEntity.clientExtraSpin+wildFireEntityRenderState.shieldAngle*4*f;
	}
}
