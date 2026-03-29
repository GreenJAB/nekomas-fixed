package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.render.entity.TermiteRenderer;
import net.greenjab.nekomasfixed.render.entity.model.TermiteModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.greenjab.nekomasfixed.screen.KilnScreen;
import net.greenjab.nekomasfixed.registries.BlockEntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.util.Identifier;

public class NekomasFixedClient implements ClientModInitializer {
	public static EquipmentModel turtleArmorModel = createHumanoidOnlyModel("turtle_scute");
	public static EquipmentModel netheriteCrownModel = createHumanoidOnlyModel("netherite_crown");
	public static EquipmentModel copperCrownModel = createHumanoidOnlyModel("copper_crown");
	public static EquipmentModel ironCrownModel = createHumanoidOnlyModel("iron_crown");
	public static EquipmentModel goldenCrownModel = createHumanoidOnlyModel("golden_crown");
	public static EquipmentModel diamondCrownModel = createHumanoidOnlyModel("diamond_crown");

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
				BlockRegistry.BOABAB_SAPLING
		);

		EntityModelLayerRegistry.registerModelLayer(TermiteModel.TERMITE, TermiteModel::getTexturedModelData);
		EntityRendererRegistry.register(EntityTypeRegistry.TERMITE, TermiteRenderer::new);

	}

	private static EquipmentModel createHumanoidOnlyModel(String id) {
		return EquipmentModel.builder()
				.addHumanoidLayers(Identifier.ofVanilla(id))
				.build();
	}

}