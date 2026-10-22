package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireEntity;
import net.greenjab.nekomasfixed.render.entity.model.WildfireModel;
import net.greenjab.nekomasfixed.render.entity.state.WildfireRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class WildfireRenderer extends MobRenderer<WildfireEntity, WildfireRenderState, WildfireModel> {
	private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wildfire/default.png");
	private static final Identifier TEXTURE_SOUL = NekomasFixed.id("textures/entity/wildfire/soul.png");

	public WildfireRenderer(EntityRendererProvider.Context context) {
		super(context, new WildfireModel(context.bakeLayer(ModModelLayerRegistry.WILD_FIRE)), 0.5F);
	}

	protected int getBlockLightLevel(@NonNull WildfireEntity wildFireEntity, @NonNull BlockPos blockPos) {
		return 15;
	}

	@Override
	public @NonNull Identifier getTextureLocation(WildfireRenderState state) {
		if (state.soul) return TEXTURE_SOUL;
		return TEXTURE;
	}

	public @NonNull WildfireRenderState createRenderState() {
		return new WildfireRenderState();
	}

	public void extractRenderState(@NonNull WildfireEntity wildFireEntity, @NonNull WildfireRenderState wildFireRenderState, float f) {
		super.extractRenderState(wildFireEntity, wildFireRenderState, f);
		wildFireRenderState.soul = wildFireEntity.isSoulActive();
		wildFireRenderState.shields = wildFireEntity.getShieldsActive();
		wildFireRenderState.shieldAngle = 1-(Mth.cos(Math.PI*Mth.clamp(wildFireEntity.clientFireTime +0.5f*(f/20f)*(wildFireEntity.isOnFire()?1:-1), 0, 1))+1)/2f;
		wildFireRenderState.shieldExtraSpin = wildFireEntity.clientExtraSpin+ wildFireRenderState.shieldAngle*4*f;
	}
}
