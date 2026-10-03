package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.BabyRimeModel;
import net.greenjab.nekomasfixed.render.entity.model.RimeModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class RimeOuterLayer extends RenderLayer<ZombieRenderState, RimeModel> {
	private static final Identifier RIME_OUTER_LAYER_LOCATION = NekomasFixed.id("textures/entity/zombie/rime_outer_layer.png");
	private static final Identifier BABY_RIME_OUTER_LAYER_LOCATION = NekomasFixed.id("textures/entity/zombie/rime_outer_layer_baby.png");
	private final RimeModel model;
	private final RimeModel babyModel;

	public RimeOuterLayer(final RenderLayerParent<ZombieRenderState, RimeModel> renderer, final EntityModelSet modelSet) {
		super(renderer);
		this.model = new RimeModel(modelSet.bakeLayer(ModModelLayerRegistry.RIME_OUTER_LAYER));
		this.babyModel = new BabyRimeModel(modelSet.bakeLayer(ModModelLayerRegistry.RIME_BABY_OUTER_LAYER));
	}

	public void submit(final @NonNull PoseStack poseStack, final @NonNull SubmitNodeCollector submitNodeCollector, final int lightCoords, final ZombieRenderState state, final float yRot, final float xRot) {
		RimeModel model = state.isBaby ? this.babyModel : this.model;
		Identifier layerLocation = state.isBaby ? BABY_RIME_OUTER_LAYER_LOCATION : RIME_OUTER_LAYER_LOCATION;
		coloredCutoutModelCopyLayerRender(model, layerLocation, poseStack, submitNodeCollector, lightCoords, state, -1, 1);
	}
}
