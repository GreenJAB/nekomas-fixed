package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.entity.SuspiciousSpiderEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.*;
import net.greenjab.nekomasfixed.render.entity.CustomMinecartEntityRenderer;
import net.greenjab.nekomasfixed.render.entity.MoobloomEntityRenderer;
import net.greenjab.nekomasfixed.render.entity.TermiteRenderer;
import net.greenjab.nekomasfixed.render.entity.model.CustomMinecartEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.MoobloomEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.SuspiciousSpiderEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.TermiteModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.greenjab.nekomasfixed.screen.KilnScreen;
import net.greenjab.nekomasfixed.registries.BlockEntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NekomasFixedClient implements ClientModInitializer {
	public static EquipmentModel turtleArmorModel = createHumanoidOnlyModel("turtle_scute");
	public static EquipmentModel netheriteCrownModel = createHumanoidOnlyModel("netherite_crown");
	public static EquipmentModel copperCrownModel = createHumanoidOnlyModel("copper_crown");
	public static EquipmentModel ironCrownModel = createHumanoidOnlyModel("iron_crown");
	public static EquipmentModel goldenCrownModel = createHumanoidOnlyModel("golden_crown");
	public static EquipmentModel diamondCrownModel = createHumanoidOnlyModel("diamond_crown");
	private static final Map<Item, Integer> FOOD_COLORS = Map.ofEntries(
			Map.entry(Items.APPLE, 0xFF3B3B),
			Map.entry(Items.CARROT, 0xFFA500),
			Map.entry(Items.BREAD, 0xD2B48C),
			Map.entry(Items.MELON_SLICE, 0xFF6B6B),
			Map.entry(Items.POTATO, 0xC8A060),
			Map.entry(Items.BEEF, 0x8B2E2E),
			Map.entry(Items.COOKED_BEEF, 0x5A2C2C),
			Map.entry(Items.GOLDEN_APPLE, 0xFFD700)
	);

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.registerBlockEntityRenderer();
		ModEntityRendererRegistry.registerEntityRenderer();
		ModEntityLayerRegistry.registerEntityModelLayer();
		TextureRegistry.registerTextureRegistry();

		ClientSyncHandler.init();

		HandledScreens.register(ScreenHandlerRegistry.KILN_SCREEN_HANDLER, KilnScreen::new);

		BlockRenderLayerMap.putBlocks(
				BlockRenderLayer.TRANSLUCENT,
				BlockRegistry.AMBER_STAINED_GLASS,
				BlockRegistry.AQUA_STAINED_GLASS,
				BlockRegistry.INDIGO_STAINED_GLASS,
				BlockRegistry.CRIMSON_STAINED_GLASS,
				BlockRegistry.AMBER_STAINED_GLASS_PANE,
				BlockRegistry.AQUA_STAINED_GLASS_PANE,
				BlockRegistry.INDIGO_STAINED_GLASS_PANE,
				BlockRegistry.CRIMSON_STAINED_GLASS_PANE
		);

		BlockRenderLayerMap.putBlocks(
				BlockRenderLayer.CUTOUT,
				BlockRegistry.BOABAB_FRUIT,
				BlockRegistry.BOABAB_SAPLING,
				BlockRegistry.HOLLOW_OAK_LOG,
				BlockRegistry.HOLLOW_DARK_OAK_LOG,
				BlockRegistry.HOLLOW_ACACIA_LOG,
				BlockRegistry.HOLLOW_BIRCH_LOG,
				BlockRegistry.HOLLOW_CHERRY_LOG,
				BlockRegistry.HOLLOW_JUNGLE_LOG,
				BlockRegistry.HOLLOW_SPRUCE_LOG,
				BlockRegistry.HOLLOW_PALE_OAK_LOG,
				BlockRegistry.HOLLOW_CRIMSON_LOG,
				BlockRegistry.HOLLOW_WARPED_LOG

		);


//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_OAK_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowOakLogBlockEntityRenderer()
//		);
//
//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_SPRUCE_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowSpruceLogBlockEntityRenderer()
//		);
//
//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_BIRCH_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowBirchLogBlockEntityRenderer()
//		);
//
//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_JUNGLE_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowJungleLogBlockEntityRenderer()
//		);

		BlockEntityRendererFactories.register(
				BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY,
                SoupCauldronBlockEntityRenderer::new
		);

//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_ACACIA_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowAcaciaLogBlockEntityRenderer()
//		);

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (state != null) {
                assert world != null;
                return getTintIndex(state, world, pos, tintIndex);
            } else {
                return 0;
            }
        }, BlockRegistry.SOUP_CAULDRON);

//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_DARK_OAK_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowDarkOakLogBlockEntityRenderer()
//		);
//
//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_CRIMSON_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowCrimsonLogBlockEntityRenderer()
//		);
//
//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_WARPED_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowWarpedLogBlockEntityRenderer()
//		);
//
//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_CHERRY_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowCherryLogBlockEntityRenderer()
//		);
//
//		BlockEntityRendererFactories.register(
//				BlockEntityTypeRegistry.HOLLOW_PALE_OAK_LOG_BLOCK_ENTITY_TYPE,
//				(ctx) -> new HollowPaleOakLogBlockEntityRenderer()
//		);

		EntityModelLayerRegistry.registerModelLayer(TermiteModel.TERMITE, TermiteModel::getTexturedModelData);
		EntityRendererFactories.register(EntityTypeRegistry.TERMITE, TermiteRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MoobloomEntityModel.MOOBLOOM, MoobloomEntityModel::getTexturedModelData);
		EntityRendererFactories.register(EntityTypeRegistry.MOOBLOOM, MoobloomEntityRenderer::new);
//		EntityModelLayerRegistry.registerModelLayer(CustomMinecartEntityModel.CUSTOM_MINECART, CustomMinecartEntityModel::getTexturedModelData);
//		EntityRendererRegistry.register(EntityTypeRegistry.CUSTOM_MINECART, CustomMinecartEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SuspiciousSpiderEntityModel.SUS_SPIDER, SuspiciousSpiderEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(EntityTypeRegistry.SUS_SPIDER, SuspiciousSpiderEntityRenderer::new);

	}

	private static EquipmentModel createHumanoidOnlyModel(String id) {
		return EquipmentModel.builder()
				.addHumanoidLayers(Identifier.ofVanilla(id))
				.build();
	}

	private static int getTintIndex(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex){
		if(world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soupCauldronBlockEntity){
			return tintIndex == 0 ? blendFoodColors(soupCauldronBlockEntity.getInputs()) : 0xFFFFFFFF;
		}else{
			return -1;
		}
	}

	public static Optional<Integer> getFoodColor(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return Optional.empty();

		Item item = stack.getItem();
		Integer foodColor = FOOD_COLORS.get(item);
		if (foodColor != null) {
			return Optional.of(foodColor);
		}
		if (stack.contains(DataComponentTypes.POTION_CONTENTS)) {
			var contents = stack.get(DataComponentTypes.POTION_CONTENTS);
			if (contents != null) {
				return Optional.of(contents.getColor());
			}
		}

		return Optional.empty();
	}

	public static int blendFoodColors(List<ItemStack> items) {
		float totalR = 0.0F;
		float totalG = 0.0F;
		float totalB = 0.0F;
		float totalWeight = 0.0F;

		for (ItemStack stack : items) {
			if (stack.isEmpty()) continue;

			Optional<Integer> colorOpt = getFoodColor(stack);
			if (colorOpt.isEmpty()) continue;

			int color = colorOpt.get();

			float r = (float)(color >> 16 & 255) / 255.0F;
			float g = (float)(color >> 8 & 255) / 255.0F;
			float b = (float)(color & 255) / 255.0F;

			float weight = stack.getCount();

			totalR += r * weight;
			totalG += g * weight;
			totalB += b * weight;
			totalWeight += weight;
		}

		if (totalWeight == 0) return 0x385DC6; // fallback

		float r = totalR / totalWeight;
		float g = totalG / totalWeight;
		float b = totalB / totalWeight;

		// optional brightness normalization (potion feel)
		float max = Math.max(r, Math.max(g, b));
		if (max > 0) {
			float scale = 1.0F / max;
			r *= scale;
			g *= scale;
			b *= scale;
		}

		int finalR = (int)(r * 255.0F);
		int finalG = (int)(g * 255.0F);
		int finalB = (int)(b * 255.0F);

		return (finalR << 16) | (finalG << 8) | finalB;
	}

}