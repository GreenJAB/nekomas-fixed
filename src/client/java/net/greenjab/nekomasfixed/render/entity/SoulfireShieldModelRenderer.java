package net.greenjab.nekomasfixed.render.entity;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class SoulfireShieldModelRenderer implements SpecialModelRenderer<ComponentMap> {
	private final SpriteHolder spriteHolder;
	private final ShieldEntityModel model;
	public static final Identifier TEXTURE = NekomasFixed.id("textures/item/soulfire_shield_3d.png");
	public static final Identifier TEXTURE_SOUL = NekomasFixed.id("textures/item/soulfire_shield_soul_3d.png");

	public SoulfireShieldModelRenderer(SpriteHolder spriteHolder, ShieldEntityModel model) {
		this.spriteHolder = spriteHolder;
		this.model = model;
	}

	@Nullable
	public ComponentMap getData(ItemStack itemStack) {
		return itemStack.getImmutableComponents();
	}

	public void render(
			@Nullable ComponentMap componentMap,
			ItemDisplayContext itemDisplayContext,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			int i,
			int j,
			boolean bl,
			int k
	) {
		matrixStack.push();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
		SpriteIdentifier spriteIdentifier = ModelBaker.SHIELD_BASE_NO_PATTERN;
		orderedRenderCommandQueue.submitModelPart(
				this.model.getHandle(),
				matrixStack,
				this.model.getLayer(spriteIdentifier.getAtlasId()),
				i,
				j,
				this.spriteHolder.getSprite(spriteIdentifier),
				false,
				false,
				-1,
				null,
				k
		);
		int damage = componentMap.getOrDefault(DataComponentTypes.DAMAGE, 0);
		orderedRenderCommandQueue.submitModelPart(
				this.model.getPlate(),
				matrixStack,
				this.model.getLayer(damage< ItemRegistry.SOULFIRE_SHIELD.getDefaultStack().getMaxDamage()/2 ? TEXTURE:TEXTURE_SOUL),
				i,
				j,
				null,
				false,
				bl,
				-1,
				null,
				k
		);

		matrixStack.pop();
	}

	@Override
	public void collectVertices(Consumer<Vector3fc> consumer) {
		MatrixStack matrixStack = new MatrixStack();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
		this.model.getRootPart().collectVertices(matrixStack, consumer);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final SoulfireShieldModelRenderer.Unbaked INSTANCE = new SoulfireShieldModelRenderer.Unbaked();
		public static final MapCodec<SoulfireShieldModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

		@Override
		public MapCodec<SoulfireShieldModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakeContext context) {
			return new SoulfireShieldModelRenderer(context.spriteHolder(), new ShieldEntityModel(context.entityModelSet().getModelPart(EntityModelLayers.SHIELD)));
		}
	}
}
