package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class WildfireShieldModelRenderer implements SpecialModelRenderer<DataComponentMap> {
    private final ShieldModel model;

	public static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wildfire_shield/default.png");
	public static final Identifier TEXTURE_SOUL = NekomasFixed.id("textures/entity/wildfire_shield/soul.png");

	public WildfireShieldModelRenderer(ShieldModel model) {
        this.model = model;
	}

	@Nullable
	public DataComponentMap extractArgument(ItemStack itemStack) {
		return itemStack.immutableComponents();
	}

	public void submit(
			final @Nullable DataComponentMap components,
			final @NonNull PoseStack poseStack,
			final SubmitNodeCollector submitNodeCollector,
			final int lightCoords,
			final int overlayCoords,
			final boolean hasFoil,
			final int outlineColor
	) {
		int damage = components==null?0:components.getOrDefault(DataComponents.DAMAGE, 0);
		submitNodeCollector.order(0).submitModel(this.model, Unit.INSTANCE, poseStack, damage< ItemRegistry.WILDFIRE_SHIELD.getDefaultInstance().getMaxDamage()/2 ? TEXTURE:TEXTURE_SOUL, lightCoords, overlayCoords, outlineColor, null);
		if (hasFoil) submitNodeCollector.order(1).submitModel(this.model, Unit.INSTANCE, poseStack, RenderTypes.entityGlint(), lightCoords, overlayCoords, outlineColor, null);
	}

	@Override
	public void getExtents(@NonNull Consumer<Vector3fc> consumer) {
		PoseStack matrixStack = new PoseStack();
		this.model.root().getExtentsForGui(matrixStack, consumer);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked() implements SpecialModelRenderer.Unbaked<DataComponentMap> {
		public static final WildfireShieldModelRenderer.Unbaked INSTANCE = new WildfireShieldModelRenderer.Unbaked();
		public static final MapCodec<WildfireShieldModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

		@Override
		public @NonNull MapCodec<WildfireShieldModelRenderer.Unbaked> type() {
			return CODEC;
		}

		@Override
		public WildfireShieldModelRenderer bake(SpecialModelRenderer.BakingContext context) {
			return new WildfireShieldModelRenderer(new ShieldModel(context.entityModelSet().bakeLayer(ModelLayers.SHIELD)));
		}
	}
}
