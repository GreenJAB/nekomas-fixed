package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.util.Unit;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class WildfireTridentModelRenderer implements NoDataSpecialModelRenderer {
	private final TridentModel model;

	public WildfireTridentModelRenderer(TridentModel model) {
		this.model = model;
	}

	@Override
	public void submit(@NonNull PoseStack matrices, SubmitNodeCollector queue, int light, int overlay, boolean glint, int i) {
		queue.order(0).submitModel(this.model, Unit.INSTANCE, matrices, ThrownWildfireTridentRenderer.TEXTURE, light, overlay, i, null);
		if (glint) {
			queue.order(1).submitModel(this.model, Unit.INSTANCE, matrices, RenderTypes.entityGlint(), light, overlay, i, null);
		}
	}

	@Override
	public void getExtents(@NonNull Consumer<Vector3fc> consumer) {
		PoseStack matrixStack = new PoseStack();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
		this.model.root().getExtentsForGui(matrixStack, consumer);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
		public static final MapCodec<WildfireTridentModelRenderer.Unbaked> CODEC = MapCodec.unit(new WildfireTridentModelRenderer.Unbaked());

		@Override
		public @NonNull MapCodec<WildfireTridentModelRenderer.Unbaked> type() {
			return CODEC;
		}

		@Override
		public WildfireTridentModelRenderer bake(SpecialModelRenderer.BakingContext context) {
			return new WildfireTridentModelRenderer(new TridentModel(context.entityModelSet().bakeLayer(ModEntityLayerRegistry.WILDFIRE_TRIDENT)));
		}
	}
}
