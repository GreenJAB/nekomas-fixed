package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.render.entity.model.BabyDerelictModel;
import net.greenjab.nekomasfixed.render.entity.model.DerelictModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DerelictOuterLayer extends RenderLayer<ZombieRenderState, DerelictModel> {
	private static final Identifier DERELICT_OUTER_LAYER_LOCATION = NekomasFixed.id("textures/entity/zombie/derelict_outer_layer.png");
	private static final Identifier BABY_DERELICT_OUTER_LAYER_LOCATION = NekomasFixed.id("textures/entity/zombie/derelict_outer_layer_baby.png");
	private final DerelictModel model;
	private final DerelictModel babyModel;

	public DerelictOuterLayer(final RenderLayerParent<ZombieRenderState, DerelictModel> renderer, final EntityModelSet modelSet) {
		super(renderer);
		this.model = new DerelictModel(modelSet.bakeLayer(ModModelLayerRegistry.DERELICT_OUTER_LAYER));
		this.babyModel = new BabyDerelictModel(modelSet.bakeLayer(ModModelLayerRegistry.DERELICT_BABY_OUTER_LAYER));
	}

	public void submit(final @NonNull PoseStack poseStack, final @NonNull SubmitNodeCollector submitNodeCollector, final int lightCoords, final ZombieRenderState state, final float yRot, final float xRot) {
		DerelictModel model = state.isBaby ? this.babyModel : this.model;
		Identifier layerLocation = state.isBaby ? BABY_DERELICT_OUTER_LAYER_LOCATION : DERELICT_OUTER_LAYER_LOCATION;
		coloredCutoutModelCopyLayerRender(model, layerLocation, poseStack, submitNodeCollector, lightCoords, state, -1, 1);
	}
}
