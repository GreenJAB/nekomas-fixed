package net.greenjab.nekomasfixed.render.entity;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import org.joml.Vector3fc;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class SoulfireTridentModelRenderer implements SimpleSpecialModelRenderer {
	private final TridentEntityModel model;

	public SoulfireTridentModelRenderer(TridentEntityModel model) {
		this.model = model;
	}

	@Override
	public void render(ItemDisplayContext displayContext, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, boolean glint, int i) {
		matrices.push();
		matrices.scale(1.0F, -1.0F, -1.0F);
		queue.submitModelPart(this.model.getRootPart(), matrices, this.model.getLayer(SoulfireTridentEntityRenderer.TEXTURE), light, overlay, null, false, glint, -1, null, i);
		matrices.pop();
	}

	@Override
	public void collectVertices(Consumer<Vector3fc> consumer) {
		MatrixStack matrixStack = new MatrixStack();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
		this.model.getRootPart().collectVertices(matrixStack, consumer);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<SoulfireTridentModelRenderer.Unbaked> CODEC = MapCodec.unit(new SoulfireTridentModelRenderer.Unbaked());

		@Override
		public MapCodec<SoulfireTridentModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakeContext context) {
			return new SoulfireTridentModelRenderer(new TridentEntityModel(context.entityModelSet().getModelPart(EntityModelLayerRegistry.SOULFIRE_TRIDENT)));
		}
	}
}
