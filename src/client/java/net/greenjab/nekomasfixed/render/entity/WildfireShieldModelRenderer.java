package net.greenjab.nekomasfixed.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class WildfireShieldModelRenderer implements SpecialModelRenderer<DataComponentMap> {
	private final SpriteGetter spriteHolder;
	private final ShieldModel model;
	public static final Identifier TEXTURE = NekomasFixed.id("textures/entity/wildfire_shield/default.png");
	public static final Identifier TEXTURE_SOUL = NekomasFixed.id("textures/entity/wildfire_shield/soul.png");

	public WildfireShieldModelRenderer(SpriteGetter spriteHolder, ShieldModel model) {
		this.spriteHolder = spriteHolder;
		this.model = model;
	}

	@Nullable
	public DataComponentMap extractArgument(ItemStack itemStack) {
		return itemStack.immutableComponents();
	}

	public void submit(
            @Nullable DataComponentMap componentMap, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue,
            int i, int j, boolean bl, int k) {
		matrixStack.pushPose();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
		SpriteId spriteIdentifier = Sheets.SHIELD_BASE_NO_PATTERN;
		orderedRenderCommandQueue.submitModelPart(
				this.model.handle(), matrixStack, this.model.renderType(spriteIdentifier.atlasLocation()),
				i, j, this.spriteHolder.get(spriteIdentifier),
				false,false,-1,null, k);
		int damage = componentMap.getOrDefault(DataComponents.DAMAGE, 0);
		orderedRenderCommandQueue.submitModelPart(
				this.model.plate(), matrixStack,
				this.model.renderType(damage< ItemRegistry.WILDFIRE_SHIELD.getDefaultInstance().getMaxDamage()/2 ? TEXTURE:TEXTURE_SOUL),
				i, j,null,false, bl,-1,null, k);
		matrixStack.popPose();
	}

	@Override
	public void getExtents(@NonNull Consumer<Vector3fc> consumer) {
		PoseStack matrixStack = new PoseStack();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
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
			return new WildfireShieldModelRenderer(context.sprites(), new ShieldModel(context.entityModelSet().bakeLayer(ModelLayers.SHIELD)));
		}
	}
}
